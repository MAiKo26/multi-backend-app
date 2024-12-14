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
import { useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";

// Enhanced validation schemas
const emailSchema = z.object({
  email: z
    .string()
    .min(1, { message: "Email is required" })
    .email("Invalid email address"),
});

function PasswordReset() {
  const [loadingButton, setLoadingButton] = useState(false);
  const [sent, setSent] = useState(false);
  const { toast } = useToast();

  // Email form
  const emailForm = useForm<z.infer<typeof emailSchema>>({
    resolver: zodResolver(emailSchema),
    defaultValues: { email: "" },
  });

  // Handle email submission for password reset
  async function handlePasswordResetRequest(
    values: z.infer<typeof emailSchema>,
  ) {
    try {
      setLoadingButton(true);
      const response = await fetch(
        "http://localhost:3636/auth/password-reset",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(values),
        },
      );

      console.log("ayoo");

      const result = await response.json();
      const status = await response.status;

      if (status === 200) {
        toast({ title: "Reset link sent to your email" });
      } else {
        toast({ title: result.message || "Failed to send reset code" });
        emailForm.setError("email", {
          message: result.message || "An error occurred",
        });
      }
      setSent(true);
    } catch {
      toast({ title: "Network error. Please try again." });
    }
  }

  return (
    <div className="flex h-full w-full items-center justify-center xl:pt-40">
      {sent ? (
        <Card>
          <CardHeader>
            <CardTitle>
              A Password Reset Link Has Been Sent To Your Email
            </CardTitle>
          </CardHeader>
        </Card>
      ) : (
        <Card className="w-full max-w-md">
          <CardHeader>
            <CardTitle>Password Reset</CardTitle>
            <CardDescription>
              Enter your email to receive a reset code
            </CardDescription>
          </CardHeader>
          <CardContent>
            <Form {...emailForm}>
              <form
                onSubmit={emailForm.handleSubmit(handlePasswordResetRequest)}
                className="flex flex-col gap-5"
              >
                <FormField
                  control={emailForm.control}
                  name="email"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Email</FormLabel>
                      <FormControl>
                        <Input placeholder="you@example.com" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <LoadingButton
                  type="submit"
                  className="w-full"
                  loading={loadingButton}
                >
                  Send Reset Code
                </LoadingButton>
              </form>
            </Form>
          </CardContent>
        </Card>
      )}
    </div>
  );
}

export default PasswordReset;
