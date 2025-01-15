import { Bell, Mail, Settings } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Switch } from "@/components/ui/switch";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";

export default function NotificationsPage() {
  return (
    <div className="container mx-auto p-6">
      <h1 className="mb-8 text-3xl font-bold">Notifications</h1>

      <div className="grid gap-6">
        <Card>
          <CardHeader>
            <CardTitle>Notification Preferences</CardTitle>
          </CardHeader>
          <CardContent className="space-y-6">
            <div className="flex items-center justify-between space-x-2">
              <div className="flex items-center space-x-4">
                <Mail className="h-5 w-5" />
                <div className="space-y-0.5">
                  <Label>Email Notifications</Label>
                  <p className="text-sm text-muted-foreground">
                    Receive email notifications about your activity
                  </p>
                </div>
              </div>
              <Switch />
            </div>

            <Separator />

            <div className="flex items-center justify-between space-x-2">
              <div className="flex items-center space-x-4">
                <Bell className="h-5 w-5" />
                <div className="space-y-0.5">
                  <Label>Push Notifications</Label>
                  <p className="text-sm text-muted-foreground">
                    Receive push notifications about your activity
                  </p>
                </div>
              </div>
              <Switch />
            </div>

            <Separator />

            <div className="flex items-center justify-between space-x-2">
              <div className="flex items-center space-x-4">
                <Settings className="h-5 w-5" />
                <div className="space-y-0.5">
                  <Label>Task Reminders</Label>
                  <p className="text-sm text-muted-foreground">
                    Get notified about task deadlines and updates
                  </p>
                </div>
              </div>
              <Switch />
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Recent Notifications</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {[1, 2, 3].map((i) => (
                <div
                  key={i}
                  className="flex items-start gap-4 rounded-lg border p-4"
                >
                  <Bell className="mt-1 h-5 w-5" />
                  <div className="space-y-1">
                    <p className="font-medium">New Task Assignment</p>
                    <p className="text-sm text-muted-foreground">
                      You have been assigned to a new task in the Project Alpha
                    </p>
                    <p className="text-xs text-muted-foreground">2 hours ago</p>
                  </div>
                </div>
              ))}
            </div>
            <Button className="mt-4 w-full">View All Notifications</Button>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
