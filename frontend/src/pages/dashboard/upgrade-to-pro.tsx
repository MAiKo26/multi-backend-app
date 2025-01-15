import { Check } from "lucide-react";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";

export default function UpgradePage() {
  return (
    <div className="container mx-auto p-6">
      <h1 className="mb-8 text-3xl font-bold">Upgrade to Pro</h1>

      <div className="grid gap-6 md:grid-cols-3">
        <Card>
          <CardHeader>
            <CardTitle>Basic</CardTitle>
            <p className="text-2xl font-bold">$0</p>
            <p className="text-sm text-muted-foreground">Free forever</p>
          </CardHeader>
          <CardContent>
            <ul className="space-y-2">
              {["5 projects", "2 team members", "Basic support"].map(
                (feature) => (
                  <li key={feature} className="flex items-center gap-2">
                    <Check className="h-4 w-4" />
                    <span>{feature}</span>
                  </li>
                ),
              )}
            </ul>
          </CardContent>
          <CardFooter>
            <Button className="w-full" disabled>
              Current Plan
            </Button>
          </CardFooter>
        </Card>

        <Card className="border-primary">
          <CardHeader>
            <CardTitle>Pro</CardTitle>
            <p className="text-2xl font-bold">$29</p>
            <p className="text-sm text-muted-foreground">per user/month</p>
          </CardHeader>
          <CardContent>
            <ul className="space-y-2">
              {[
                "Unlimited projects",
                "Unlimited team members",
                "Priority support",
                "Advanced features",
                "Custom integrations",
              ].map((feature) => (
                <li key={feature} className="flex items-center gap-2">
                  <Check className="h-4 w-4" />
                  <span>{feature}</span>
                </li>
              ))}
            </ul>
          </CardContent>
          <CardFooter>
            <Button className="w-full">Upgrade to Pro</Button>
          </CardFooter>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Enterprise</CardTitle>
            <p className="text-2xl font-bold">Custom</p>
            <p className="text-sm text-muted-foreground">Contact sales</p>
          </CardHeader>
          <CardContent>
            <ul className="space-y-2">
              {[
                "Everything in Pro",
                "Dedicated support",
                "Custom SLA",
                "On-premise deployment",
                "Enterprise security",
              ].map((feature) => (
                <li key={feature} className="flex items-center gap-2">
                  <Check className="h-4 w-4" />
                  <span>{feature}</span>
                </li>
              ))}
            </ul>
          </CardContent>
          <CardFooter>
            <Button className="w-full" variant="outline">
              Contact Sales
            </Button>
          </CardFooter>
        </Card>
      </div>
    </div>
  );
}
