import { useEffect } from "react";
import useStore from "../store/useStore";
import { useNavigate } from "react-router";

export function useAuth() {
  const { isAuthenticated, currentUser } = useStore();
  const navigate = useNavigate();

  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/auth/sign-in");
    }
  }, [isAuthenticated, navigate]);

  return { isAuthenticated, user: currentUser };
}
