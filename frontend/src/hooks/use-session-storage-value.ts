import { useState, useEffect } from "react";

export function useSessionStorageValue(key: string) {
  const [value, setValue] = useState<string | null>(
    sessionStorage.getItem(key),
  );

  useEffect(() => {
    const handleStorageChange = () => {
      setValue(sessionStorage.getItem(key));
    };

    window.addEventListener("storage", handleStorageChange);
    return () => window.removeEventListener("storage", handleStorageChange);
  }, [key]);

  return value;
}
