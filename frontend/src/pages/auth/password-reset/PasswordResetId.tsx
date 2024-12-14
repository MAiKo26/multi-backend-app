import LoadingButton from "@/components/LoadingButton";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useToast } from "@/hooks/use-toast"; // Assuming you're using shadcn/ui toasts
import { zodResolver } from "@hookform/resolvers/zod";
import { Loader2 } from "lucide-react";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useParams } from "react-router";
import { z } from "zod";

const passwordSchema = z
  .object({
    newPassword: z
      .string()
      .min(8, { message: "Password must be at least 8 characters" })
      .regex(/[A-Z]/, { message: "Password must contain an uppercase letter" })
      .regex(/[a-z]/, { message: "Password must contain a lowercase letter" })
      .regex(/[0-9]/, { message: "Password must contain a number" }),
    confirmPassword: z.string(),
  })
  .refine((data) => data.newPassword === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });

function PasswordResetUrl() {
  const params = useParams();
  const [verified, setVerified] = useState(false);
  const [loadingButton, setLoadingButton] = useState(true);
  const { toast } = useToast();

  const passwordForm = useForm<z.infer<typeof passwordSchema>>({
    resolver: zodResolver(passwordSchema),
    defaultValues: {
      newPassword: "",
      confirmPassword: "",
    },
  });

  useEffect(() => {
    async function fetching() {
      try {
        const response = await fetch(
          "http://localhost:3636/auth/password-reset/verification",
          {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
              resetPasswordToken: params.id,
            }),
          },
        );

        const status = await response.status;

        if (status === 200) {
          setVerified(true);
          setLoadingButton(false);
        } else {
          const result = await response.json();
          toast({
            title: "Error",
            description: result.message,
            variant: "destructive",
          });
        }
      } catch {
        toast({ title: "Verification failed. Please try again." });
      }
    }

    fetching();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  async function handlePasswordReset(values: z.infer<typeof passwordSchema>) {
    try {
      const response = await fetch(
        "http://localhost:3636/auth/password-reset/confirmation",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            resetPasswordToken: params.id,
            newPassword: values.newPassword,
          }),
        },
      );

      const status = await response.status;

      if (status === 200) {
        toast({ title: "Password successfully reset" });
        window.location.href = "/auth/login";
      } else {
        toast({ title: "Password reset failed" });
        passwordForm.setError("newPassword", {
          message: "Reset unsuccessful",
        });
      }
    } catch (error) {
      console.error("Password reset error:", error);
      toast({ title: "Network error. Please try again." });
    }
  }

  return (
    <div className="flex h-full w-full items-center justify-center xl:pt-40">
      {verified ? (
        <Card className="w-full max-w-md">
          <CardHeader>
            <CardTitle>Create New Password</CardTitle>
            <CardDescription>Choose a strong, unique password</CardDescription>
          </CardHeader>
          <CardContent>
            <Form {...passwordForm}>
              <form
                onSubmit={passwordForm.handleSubmit(handlePasswordReset)}
                className="space-y-4"
              >
                <FormField
                  control={passwordForm.control}
                  name="newPassword"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>New Password</FormLabel>
                      <FormControl>
                        <Input
                          type="password"
                          placeholder="Enter new password"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={passwordForm.control}
                  name="confirmPassword"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Confirm Password</FormLabel>
                      <FormControl>
                        <Input
                          type="password"
                          placeholder="Confirm new password"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <LoadingButton
                  loading={loadingButton}
                  type="submit"
                  className="w-full"
                >
                  Reset Password
                </LoadingButton>
              </form>
            </Form>
          </CardContent>
        </Card>
      ) : (
        <Card>
          <Loader2 className="h-20 w-20 animate-spin" />
        </Card>
      )}
    </div>
  );
}

export default PasswordResetUrl;
