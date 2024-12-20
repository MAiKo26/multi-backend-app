import {
  AudioWaveform,
  Command,
  FolderKanban,
  GalleryVerticalEnd,
  MessageSquareText,
  SquareTerminal,
} from "lucide-react";

// This is sample data.
export const data = {
  user: {
    name: "Mohamed Aziz Karoui",
    email: "contact@maiko26.tn",
    avatar: "/avatars/shadcn.jpg",
  },
  teams: [
    {
      name: "Acme Inc",
      logo: GalleryVerticalEnd,
      plan: "Enterprise",
    },
    {
      name: "Acme Corp.",
      logo: AudioWaveform,
      plan: "Startup",
    },
    {
      name: "Evil Corp.",
      logo: Command,
      plan: "Free",
    },
  ],
  navMain: [
    {
      title: "Playground",
      url: "#",
      icon: SquareTerminal,
      isActive: true,
      items: [
        {
          title: "Dashboard",
          url: "/",
        },
        {
          title: "Tasks",
          url: "/tasks",
        },
        {
          title: "History",
          url: "/history",
        },
        {
          title: "Starred",
          url: "/starred",
        },
      ],
    },

    {
      title: "Chat",
      url: "#",
      icon: MessageSquareText,
      items: [
        {
          title: "General Team Chat",
          url: "chat",
        },
      ],
    },

    {
      title: "Projects",
      url: "#",
      icon: FolderKanban,
      items: [
        {
          title: "Design Engineering ",
          url: "#",
        },
        {
          title: "Sales & Marketing",
          url: "#",
        },
        {
          title: "Travel",
          url: "#",
        },
      ],
    },
  ],
};
