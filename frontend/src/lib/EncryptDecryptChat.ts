import CryptoJS from "crypto-js";

const secretKey = "verysecretkey123"; // A 16-character key (simplified)

// Function to encrypt emails
export function encryptEmails(email1: string, email2: string): string {
  const sortedEmails = [email1, email2].sort().join(""); // Sort and join emails
  return CryptoJS.AES.encrypt(sortedEmails, secretKey)
    .toString()
    .replaceAll("/", ""); // Encrypt
}

// Function to decrypt emails
export function decryptEmails(encryptedEmails: string): string[] {
  const decrypted = CryptoJS.AES.decrypt(encryptedEmails, secretKey).toString(
    CryptoJS.enc.Utf8,
  ); // Decrypt
  return decrypted.split("").sort(); // Split and sort to recover email order
}
