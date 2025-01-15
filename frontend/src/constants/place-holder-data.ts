import { FolderKanban, MessageSquareText, SquareTerminal } from "lucide-react";

// This is sample data.
export const data = {
  user: {
    name: "Mohamed Aziz Karoui",
    email: "contact@maiko26.tn",
    avatar: "/avatars/shadcn.jpg",
    role: "user",
  },
  navMain: [
    {
      title: "Playground",
      url: "/dashboard",
      icon: SquareTerminal,
      isActive: true,
      items: [
        {
          title: "Dashboard",
          url: "/dashboard",
        },
        {
          title: "My Finished Tasks",
          url: "/tasks",
        },
        {
          title: "My History",
          url: "/history",
        },
        {
          title: "My Stars",
          url: "/starred",
        },
      ],
    },

    {
      title: "Chat",
      url: "/chat",
      icon: MessageSquareText,
      items: [
        {
          title: "General",
          url: "/chat",
        },
      ],
    },

    {
      title: "Projects",
      url: "/projects",
      icon: FolderKanban,
    },
  ],
};
