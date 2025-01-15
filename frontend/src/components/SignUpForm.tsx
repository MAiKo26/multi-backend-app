import { useToast } from "@/hooks/use-toast";
import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import LoadingButton from "./LoadingButton";
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

function SignUpForm() {
  const [loadingButtonState, setLoadingButtonState] = useState(false);
  const [sent, setSent] = useState("");
  const { toast } = useToast();

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      email: "",
      password: "",
      name: "",
    },
  });

  async function SignUp(values: z.infer<typeof formSchema>) {
    try {
      setLoadingButtonState(true);

      // Create FormData and append fields
      const formData = new FormData();
      formData.append("email", values.email);
      formData.append("password", values.password);
      formData.append("name", values.name);

      if (values.avatar && values.avatar.length > 0) {
        formData.append("avatar", values.avatar[0]); // Attach the first file
      }
      if (values.phoneNumber) {
        formData.append("phoneNumber", values.phoneNumber);
      }

      const res = await fetch("http://localhost:3636/auth/register", {
        method: "POST",
        body: formData, // Use FormData for the request body
      });

      if (res.status === 200) {
        toast({
          title: "Success",
          description: "Verification Email Sent, Please Check Your Email.",
        });
        setSent(values.email);
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
    } catch (error) {
      toast({
        title: "Error",
        description: "Something went wrong " + error,
        variant: "destructive",
      });
      form.setError("email", {
        message: "Something went wrong " + error,
      });
      setLoadingButtonState(false);
    }
  }

  return (
    <TabsContent value="signup" className="h-fit w-full">
      {sent ? (
        <Card>
          <CardHeader>
            <CardTitle>
              Verification Email has been sent to your email {sent}
            </CardTitle>
          </CardHeader>
        </Card>
      ) : (
        <Card>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(SignUp)} className="">
              <CardHeader>
                <CardTitle>Sign Up</CardTitle>
                <CardDescription>
                  Enter your unique email and minimum 8 characters password to
                  get started!
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
                <FormField
                  control={form.control}
                  name="avatar"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Avatar</FormLabel>
                      <FormControl>
                        <Input
                          placeholder="Select an avatar"
                          type="file"
                          accept="image/*"
                          onChange={(e) => {
                            field.onChange(e.target.files);
                          }}
                          onBlur={field.onBlur}
                          name={field.name}
                          ref={field.ref}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="name"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Full Name</FormLabel>
                      <FormControl>
                        <Input
                          placeholder="Your Full Name"
                          type="text"
                          {...field}
                        />
                      </FormControl>

                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="phoneNumber"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Phone Number</FormLabel>
                      <FormControl>
                        <Input placeholder="+216 ..." type="tel" {...field} />
                      </FormControl>

                      <FormMessage />
                    </FormItem>
                  )}
                />
              </CardContent>
              <CardFooter className="m-0 flex h-full w-full flex-col items-center justify-between gap-2">
                <LoadingButton loading={loadingButtonState} type="submit">
                  Submit
                </LoadingButton>
              </CardFooter>
            </form>
          </Form>
        </Card>
      )}
    </TabsContent>
  );
}
export default SignUpForm;

const formSchema = z.object({
  email: z
    .string()
    .min(1, { message: "This field has to be filled." })
    .email("This is not a valid email."),
  password: z
    .string()
    .min(8, { message: "Password must be at least 8 characters" })
    .regex(/[A-Z]/, { message: "Password must contain an uppercase letter" })
    .regex(/[a-z]/, { message: "Password must contain a lowercase letter" })
    .regex(/[0-9]/, { message: "Password must contain a number" }),
  name: z.string().min(1, { message: "This field has to be filled." }),
  avatar: z.instanceof(FileList).optional(),
  phoneNumber: z.string().optional(),
});
