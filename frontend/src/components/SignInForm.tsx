import { useToast } from "@/hooks/use-toast";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { Link } from "react-router";
import { z } from "zod";
import { Button } from "./ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "./ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "./ui/form";
import { Input } from "./ui/input";
import { TabsContent } from "./ui/tabs";
import { useState } from "react";
import LoadingButton from "./LoadingButton";

function SignInForm() {
  const [loadingButtonState, setLoadingButtonState] = useState(false);
  const { toast } = useToast();

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  async function SignIn(values: z.infer<typeof formSchema>) {
    try {
      setLoadingButtonState(true);
      const res = await fetch("http://localhost:3636/auth/sign-in", {
        headers: {
          "Content-Type": "application/json",
        },
        method: "POST",
        body: JSON.stringify(values),
      });

      if (res.status === 200) {
        toast({
          title: "Success",
          description: "Login successful",
        });
        const response = await res.json();
        sessionStorage.setItem("authToken", response.token);
        window.location.href = "/";
      } else {
        const response = await res.json();
        toast({
          title: "Error",
          description: response.message,
          variant: "destructive",
        });
        form.setError("email", {
          message: response.message,
        });
        setLoadingButtonState(false);
      }
    } catch {
      toast({
        title: "Error",
        description: "Client side error",
        variant: "destructive",
      });
    }
  }

  return (
    <TabsContent value="signin" className="h-full w-full">
      <Card>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(SignIn)} className="">
            <CardHeader>
              <CardTitle>Welcome</CardTitle>
              <CardDescription>
                Welcome to the platform! Please sign in to continue.
              </CardDescription>
            </CardHeader>
            <CardContent>
              <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>E-mail</FormLabel>
                    <FormControl>
                      <Input placeholder="name@example.com" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="password"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Password</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="password"
                        type="password"
                        {...field}
                      />
                    </FormControl>

                    <FormMessage />
                  </FormItem>
                )}
              />
            </CardContent>
            <CardFooter className="m-0 flex h-full w-full flex-col items-center justify-between gap-2">
              <LoadingButton type="submit" loading={loadingButtonState}>
                Submit
              </LoadingButton>
              <Button variant="link" type="button">
                <Link to="/auth/password-reset">Forgot Password</Link>
              </Button>
            </CardFooter>
          </form>
        </Form>
      </Card>
    </TabsContent>
  );
}
export default SignInForm;

const formSchema = z.object({
  email: z
    .string()
    .min(1, { message: "This field has to be filled." })
    .email("This is not a valid email."),
  // .refine(async (e) => {
  //   const emails = await fetchEmails();
  //   return emails.includes(e);
  // }, "This email is not in our database"),
  password: z.string().min(8, {
    message: "This field has to be filled with at least 8 characters.",
  }),
});
