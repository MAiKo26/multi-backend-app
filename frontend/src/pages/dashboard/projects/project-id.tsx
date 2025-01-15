import FeaturedTask from "@/components/FeaturedTask";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { formatDate } from "@/lib/utils";
import { NotFound } from "@/pages/not-found";
import useStore, { Project } from "@/store/useStore";
import { Loader2 } from "lucide-react";
import { useEffect, useState } from "react";
import { useParams } from "react-router";

export default function ProjectDisplay() {
  const { projects } = useStore();
  const { projectId } = useParams();
  const [project, setProject] = useState<Project>();
  const [isLoadingCurrentProject, setIsLoadingCurrentProject] = useState(false);

  useEffect(() => {
    setIsLoadingCurrentProject(true);
    setProject(projects.find((p) => p.id === projectId));
    setIsLoadingCurrentProject(false);
  }, []);

  if (!project) return <NotFound />;

  return isLoadingCurrentProject ? (
    <Loader2 className="animate-spin" />
  ) : (
    <Card>
      <CardHeader>
        <CardTitle>{project.name}</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="mb-6 space-y-2">
          <div className="flex items-center justify-between">
            <span className="text-sm font-medium">Started At</span>
            <span className="text-sm text-muted-foreground">
              {formatDate(project.createdAt)}
            </span>
          </div>
        </div>

        <Tabs defaultValue="tasks">
          <TabsList>
            <TabsTrigger value="tasks">Tasks</TabsTrigger>
            <TabsTrigger value="files">Files</TabsTrigger>
          </TabsList>

          <TabsContent value="tasks" className="mt-4">
            <div className="space-y-4">
              {project.tasks.map((task) => (
                <FeaturedTask {...task} />
              ))}
            </div>
          </TabsContent>

          <TabsContent value="files" className="mt-4">
            <div className="rounded-lg border p-4">
              <div className="flex items-center justify-between">
                <span className="font-medium">Project Files</span>
                <Button>Upload</Button>
              </div>
            </div>
          </TabsContent>
        </Tabs>
      </CardContent>
    </Card>
  );
}
