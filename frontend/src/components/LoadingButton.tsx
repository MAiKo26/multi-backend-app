import { Loader2 } from "lucide-react";
import { Button } from "./ui/button";

interface Props extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
  loading: boolean;
}

function LoadingButton({ children, loading, ...props }: Props) {
  return (
    <Button {...props} disabled={loading}>
      {loading ? <Loader2 className="h-full w-full animate-spin" /> : children}
    </Button>
  );
}

export default LoadingButton;
