import bcrypt from "bcrypt";
import {sql, eq, gt} from "drizzle-orm";
import {NextFunction, Request, Response, Router} from "express";
import jwt from "jsonwebtoken";
import {db} from "../db/db.ts";
import {sessions, users} from "../db/schema.ts";
import {transporter} from "../lib/email-transporter.ts";
import crypto from "crypto";

const router = Router();

const secret = process.env.JWT_SECRET!;

// Logging in

router.post(
  "/login",
  async (req: Request, res: Response, next: NextFunction) => {
    try {
      const {email, password} = req.body as {email: string; password: string};
      const date = new Date();

      const user = await db.query.users.findFirst({
        where: eq(users.email, email.toLowerCase()),
      });

      if (!user) {
        res.status(400).json({message: "Invalid email"});
        return;
      }

      if (user.accountLockedUntil && user.accountLockedUntil > date) {
        res.status(401).json({
          message:
            "Account locked, try again in " +
            user.accountLockedUntil.getTime() / 1000 +
            "s",
        });
        return;
      }

      if (!(await bcrypt.compare(password, user.password))) {
        await db
          .update(users)
          .set({failedLoginAttempts: sql`${user.failedLoginAttempts} + 1`})
          .where(eq(users.email, user.email));

        if (user.failedLoginAttempts >= 5) {
          await db
            .update(users)
            .set({
              accountLockedUntil: new Date(Date.now() + 15 * 60 * 1000), // 15 min lock
              failedLoginAttempts: 0,
            })
            .where(eq(users.email, user.email));

          res.status(401).json({
            message: "Invalid login attempt. Please try again later.",
          });
          return;
        }

        res.status(401).json({message: "Invalid password, Try Again."});
        return;
      }

      if (!user.isVerified) {
        res.status(401).json({message: "Email not verified, check your email"});
        return;
      }

      const session = await db.query.sessions.findFirst({
        where: eq(sessions.email, user.email),
      });

      if (session) {
        res.status(200).json({token: session.sessionId});
      } else {
        const token = jwt.sign({email: user.email}, secret, {
          expiresIn: "1h",
        });

        await db.insert(sessions).values({
          sessionId: token,
          email: user.email,
          expiresAt: new Date(Date.now() + 3600 * 1000),
        });

        await db
          .update(users)
          .set({lastLogin: new Date()})
          .where(eq(users.email, user.email));

        res.status(200).json({token});
      }
    } catch (error) {
      next(error);
    }
  }
);

router.post(
  "/logout",
  async (req: Request, res: Response, next: NextFunction) => {
    try {
      const {sessionId} = req.body as {sessionId: string};

      const decoded = jwt.verify(sessionId, secret);
      if (!decoded) {
        res.status(400).json({message: "Invalid session ID"});
        return;
      }

      const result = await db
        .delete(sessions)
        .where(eq(sessions.sessionId, sessionId));

      res.status(200).json({message: "Logged out successfully"});
    } catch (error) {
      if (error instanceof jwt.TokenExpiredError) {
        res.status(401).json({message: "Session expired"});
        return;
      }
      next(error);
    }
  }
);

// Registering

router.post(
  "/register",
  async (req: Request, res: Response, next: NextFunction) => {
    try {
      const {email, password} = req.body as {email: string; password: string};

      const existingUser = await db.query.users.findFirst({
        where: eq(users.email, email),
      });

      if (existingUser) {
        res.status(400).json({message: "Email already exists"});
        return;
      }

      const hashedPassword = await bcrypt.hash(password, 10);

      const verificationToken = crypto.randomBytes(32).toString("hex");

      await db.insert(users).values({
        email: email.toLowerCase(),
        password: hashedPassword,
        verificationToken: verificationToken,
        isVerified: false,
        createdAt: new Date(),
      });

      transporter.sendMail({
        from: "jewel.green12@ethereal.email",
        to: email,
        subject: "Verify your email",

        html: `<div>
      <div>  Your verification link is : </div>
        <a href="http://localhost:5173/auth/email-confirmation/${verificationToken}">http://localhost:5173/auth/email-confirmation/${verificationToken}</a>
        
        </div>`,
      });

      res.status(200).json({message: "Email sent successfully"});
      return;
    } catch (error) {
      next(error);
    }
  }
);

router.post(
  "/register/verification",
  async (req: Request, res: Response, next: NextFunction) => {
    try {
      const {verificationToken} = req.body as {
        verificationToken: string;
      };

      const user = await db.query.users.findFirst({
        where: eq(users.verificationToken, verificationToken),
      });
      if (!user) {
        res.status(400).json({message: "Invalid verification token"});
        return;
      }
      await db
        .update(users)
        .set({isVerified: true, verificationToken: null})
        .where(eq(users.verificationToken, verificationToken));

      res.status(200).json({message: "Verification successful"});
      return;
    } catch (error) {
      next(error);
    }
  }
);

// Password reset

router.post(
  "/password-reset",
  async (req: Request, res: Response, next: NextFunction) => {
    try {
      const {email} = req.body;

      const existingUser = await db.query.users.findFirst({
        where: eq(users.email, email),
      });

      if (!existingUser) {
        res.status(400).json({message: "Email not found"});
        return;
      }

      const resetPasswordToken = crypto.randomBytes(32).toString("hex");

      // Save the code to the database
      const user = await db
        .update(users)
        .set({
          resetPasswordToken: resetPasswordToken,
          resetPasswordExpiry: new Date(Date.now() + 3600 * 1000),
        })
        .where(eq(users.email, email));

      // Send the email
      const mailOptions = {
        from: "jewel.green12@ethereal.email", // Sender address
        to: email, // Receiver address
        subject: "Password Reset Code",
        html: `<p>
        Your password reset code is: 
        <strong><a href="http://localhost:5173/auth/password-reset/${resetPasswordToken}">http://localhost:5173/auth/password-reset/${resetPasswordToken}</a></strong>
         </p>`, // HTML body
      };

      await transporter.sendMail(mailOptions);

      res.status(200).json({message: "Password reset link sent to email"});
      return;
    } catch (error) {
      next(error);
    }
  }
);

router.post(
  "/password-reset/verification",
  async (req: Request, res: Response, next: NextFunction) => {
    try {
      const {resetPasswordToken} = req.body as {resetPasswordToken: string};

      const now = new Date();

      // Clean up expired tokens
      const user = await db
        .update(users)
        .set({resetPasswordToken: null, resetPasswordExpiry: null})
        .where(
          eq(users.resetPasswordToken, resetPasswordToken) &&
            gt(users.resetPasswordExpiry, now)
        )
        .returning();

      if (user.length === 0) {
        res.status(400).json({message: "Invalid or expired reset token"});
        return;
      }

      res.status(200).json({message: "Valid token"});
    } catch (error) {
      next(error);
    }
  }
);

router.post(
  "/password-reset/confirmation",
  async (req: Request, res: Response, next: NextFunction) => {
    try {
      const {resetPasswordToken, newPassword} = req.body;

      const hashedPassword = await bcrypt.hash(newPassword, 10);

      const user = await db
        .update(users)
        .set({password: hashedPassword})
        .where(eq(users.resetPasswordToken, resetPasswordToken))
        .returning();

      await db
        .update(users)
        .set({resetPasswordToken: null, resetPasswordExpiry: null})
        .where(eq(users.email, user[0].email));

      res.status(200).json({message: "Password reset successful"});
      return;
    } catch (error) {
      next(error);
    }
  }
);

export default router;
