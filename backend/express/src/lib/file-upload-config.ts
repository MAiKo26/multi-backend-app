import multer from "multer";

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    const uploadDir =
      "C:/Users/MSI/Workstation/multi-backend-app/backend/uploads";
    cb(null, uploadDir);
  },
  filename: (req, file, cb) => {
    const uniqueName = `${Date.now()}-${file.originalname.replace(
      /\.[^/.]+$/,
      ""
    )}.webp`;
    cb(null, uniqueName);
  },
});

export const uploadAThing = multer({storage});
