import { BrowserRouter, Route, Routes } from "react-router";

import Header from "./components/Header";
import { Toaster } from "./components/ui/toaster";
import Home from "./pages/dashboard/home.tsx";
import Login from "./pages/auth/sign-in.tsx";
import NotFound from "./pages/not-found";
import PasswordReset from "./pages/auth/password-reset/password-reset.tsx";
import PasswordResetUrl from "./pages/auth/password-reset/password-reset-id.tsx";
import EmailConfirmation from "./pages/auth/email-confirmation.tsx";

function App() {
  return (
    <BrowserRouter>
      <Header />
      <div className="pt-10">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="auth">
            <Route path="sign-in" element={<Login />} />
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
