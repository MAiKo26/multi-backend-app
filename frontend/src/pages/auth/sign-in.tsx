import { Tabs, TabsList, TabsTrigger } from "@/components/ui/tabs";

import SignInForm from "@/components/SignInForm";
import SignUpForm from "@/components/SignUpForm";
import { useToast } from "@/hooks/use-toast";
import { verifyAuth } from "@/lib/auth-repo";
import { Navigate } from "react-router";

function Login() {
  const { toast } = useToast();

  if (!verifyAuth()) {
    toast({ title: "You are already logged in" });
    return <Navigate to="/" />;
  }

  return (
    <div className="flex h-full justify-center pt-10 md:pt-10 xl:pt-20">
      <Tabs
        defaultValue="signin"
        className="flex w-[400px] flex-col items-start justify-center"
      >
        <TabsList className="grid w-full grid-cols-2">
          <TabsTrigger value="signin">Sign In</TabsTrigger>
          <TabsTrigger value="signup">Sign Up</TabsTrigger>
        </TabsList>
        <SignInForm />
        <SignUpForm />
      </Tabs>
    </div>
  );
}

export default Login;
