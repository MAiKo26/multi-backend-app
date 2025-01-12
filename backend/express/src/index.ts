import app from "./server.ts";

const PORT = process.env.PORT;
app.listen(PORT, () => {
  console.info(`Server is running on port ${PORT}`);
});
