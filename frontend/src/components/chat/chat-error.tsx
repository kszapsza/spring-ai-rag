import { AlertCircle } from "lucide-react";

import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";

interface ChatErrorProps {
  error: string | null;
}

export function ChatError({ error }: ChatErrorProps) {
  if (!error) {
    return <></>;
  }
  return (
    <Alert variant="destructive" className="w-auto m-6">
      <AlertCircle className="h-4 w-4" />
      <AlertTitle>Error</AlertTitle>
      <AlertDescription>{error}</AlertDescription>
    </Alert>
  );
}
