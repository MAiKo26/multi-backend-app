import { BrowserRouter, Route, Routes } from "react-router";

import Header from "./components/Header";
import { Toaster } from "./components/ui/toaster";
import Home from "./pages/Home";
import Login from "./pages/auth/Login.tsx";
import NotFound from "./pages/not-found";
import PasswordReset from "./pages/auth/password-reset/PasswordReset.tsx";
import PasswordResetUrl from "./pages/auth/password-reset/PasswordResetId.tsx";
import EmailConfirmation from "./pages/auth/EmailConfirmation";

function App() {
  return (
    <BrowserRouter>
      <Header />
      <div className="pt-10">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="auth">
            <Route path="login" element={<Login />} />
            <Route
              path="email-confirmation/:id"
              element={<EmailConfirmation />}
            />
            <Route path="password-reset" element={<PasswordReset />} />
            <Route path="password-reset/:id" element={<PasswordResetUrl />} />
          </Route>
          <Route path="*" element={<NotFound />} />
        </Routes>
      </div>
      <Toaster />
    </BrowserRouter>
  );
}
export default App;
