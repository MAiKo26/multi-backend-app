import { BrowserRouter, Route, Routes } from "react-router";

import { Toaster } from "./components/ui/toaster";

import AuthLayout from "./layouts/AuthLayout.tsx";
import SideBar from "./layouts/SideLayout.tsx";

import EmailConfirmation from "./pages/auth/email-confirmation.tsx";
import PasswordResetUrl from "./pages/auth/password-reset/password-reset-id.tsx";
import PasswordReset from "./pages/auth/password-reset/password-reset.tsx";
import Login from "./pages/auth/sign-in.tsx";

import Home from "./pages/dashboard/playground/home.tsx";
import Tasks from "./pages/dashboard/playground/tasks.tsx";
import History from "./pages/dashboard/playground/history.tsx";
import Starred from "./pages/dashboard/playground/starred.tsx";

import Chat from "./pages/dashboard/chat/chat.tsx";
import PrivateChat from "./pages/dashboard/chat/private-chat.tsx";

import Projects from "./pages/dashboard/projects/projects.tsx";
import ProjectId from "./pages/dashboard/projects/project-id.tsx";

import UpgradeToPro from "./pages/dashboard/upgrade-to-pro.tsx";
import Account from "./pages/dashboard/account.tsx";
import Billing from "./pages/dashboard/billing.tsx";
import Notifications from "./pages/dashboard/notifications.tsx";
import AdminPanel from "./pages/dashboard/admin-panel.tsx";
import Settings from "./pages/dashboard/settings.tsx";

import { NotFound } from "./pages/not-found";
import TaskId from "./pages/dashboard/projects/task-id";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<SideBar />}>
          {/* Playground */}
          <Route path="/dashboard" element={<Home />} />

          <Route path="/tasks" element={<Tasks />} />

          <Route path="/history" element={<History />} />
          <Route path="/starred" element={<Starred />} />
          {/* Chat */}
          <Route path="/chat" element={<Chat />} />
          <Route path="/chat/:id" element={<PrivateChat />} />
          {/* Projects */}
          <Route path="/projects" element={<Projects />} />
          <Route path="/projects/:projectId" element={<ProjectId />} />
          <Route path="/projects/:projectId/:taskId" element={<TaskId />} />

          {/* Footer links */}
          <Route path="/upgrade-to-pro" element={<UpgradeToPro />} />
          <Route path="/account" element={<Account />} />
          <Route path="/billing" element={<Billing />} />
          <Route path="/notifications" element={<Notifications />} />
          <Route path="/admin-panel" element={<AdminPanel />} />
          <Route path="/settings" element={<Settings />} />

          {/* Others */}
          <Route path="*" element={<NotFound />} />
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
