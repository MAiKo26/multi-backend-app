import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
} from "@/components/ui/card";
import { useToast } from "@/hooks/use-toast";
import { Loader2 } from "lucide-react";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router";

function EmailConfirmation() {
  const [loading, setLoading] = useState(true);
  const { toast } = useToast();
  const params = useParams();

  useEffect(() => {
    async function fetching() {
      try {
        const response = await fetch(
          "http://localhost:3636/auth/register/verification",
          {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
              verificationToken: params.id,
            }),
          },
        );
        const status = response.status;

        if (status === 200) {
          setLoading(false);
        } else {
          const result = await response.json();
          toast({
            title: "Error",
            description: result.message,
            variant: "destructive",
          });
        }
      } catch (error) {
        toast({
          title: "Error",
          description: "Something went wrong " + error,
          variant: "destructive",
        });
      }
    }
    fetching();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div className="flex h-full w-full items-center justify-center xl:pt-40">
      <Card className="w-full max-w-md">
        <CardHeader>
          <CardDescription className="flex items-center justify-center p-5">
            {loading ? (
              <Loader2 className="mr-2 h-20 w-20 animate-spin" />
            ) : (
              <div className="flex w-full flex-col gap-5 text-black">
                <p className="font-bold">Email Verified Successfully!</p>
                <Link
                  to="/auth/login"
                  className="self-center transition ease-in-out hover:underline hover:underline-offset-4"
                >
                  Go to Login Page
                </Link>
              </div>
            )}
          </CardDescription>
        </CardHeader>
        <CardContent></CardContent>
      </Card>
    </div>
  );
}
export default EmailConfirmation;
