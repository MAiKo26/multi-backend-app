import nodemailer from "nodemailer";
// Configure the transporter
export const transporter = nodemailer.createTransport({
  host: "smtp.ethereal.email",
  port: 587,
  secure: false, // true for 465, false for other ports
  auth: {
    user: "jewel.green12@ethereal.email", // process.env.EMAIL_USER!,  Your email address
    pass: "J4R8eUEJ3QckNPM4Bm", // process.env.EMAIL_PASSWORD!, // Your email password or app password
  },
});

// Verify connection configuration
transporter.verify((error, success) => {
  if (error) {
    console.error("Email server connection failed:", error);
  } else {
    console.log("Email server is ready to send messages");
  }
});
