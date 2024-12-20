import { BrowserRouter, Route, Routes } from "react-router";

import { Toaster } from "./components/ui/toaster";

import AuthLayout from "./layouts/AuthLayout.tsx";
import SideBar from "./layouts/SideLayout.tsx";

import EmailConfirmation from "./pages/auth/email-confirmation.tsx";
import PasswordResetUrl from "./pages/auth/password-reset/password-reset-id.tsx";
import PasswordReset from "./pages/auth/password-reset/password-reset.tsx";
import Login from "./pages/auth/sign-in.tsx";

import Home from "./pages/dashboard/home.tsx";
import Chat from "./pages/dashboard/chat.tsx";

import { NotFound, NotFoundDashboard } from "./pages/not-found";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<SideBar />}>
          <Route path="/" element={<Home />} />
          <Route path="/chat" element={<Chat />} />
          <Route path="*" element={<NotFoundDashboard />} />
        </Route>
        <Route path="auth" element={<AuthLayout />}>
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
      <Toaster />
    </BrowserRouter>
  );
}
export default App;
