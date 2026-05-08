import nodemailer from "nodemailer";

export const transporter = nodemailer.createTransport({
  host: "smtp.ethereal.email",
  port: 587,
  secure: false,
  auth: {
    user: "norma.stiedemann@ethereal.email", // process.env.EMAIL_USER!,
    pass: "21WH9qheTcqCu284nS", // process.env.EMAIL_PASSWORD!,
  },
});

transporter.verify((error, success) => {
  if (error) {
    console.error("Email server connection failed:", error);
  } else {
    console.log("Email server is ready to send messages");
  }
});
