import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Send } from "lucide-react";
import { useState } from "react";

interface ChatInputProps {
  onSendClick: (message: string) => Promise<void>;
}

export const ChatInput = ({ onSendClick }: ChatInputProps) => {
  const [message, setMessage] = useState("");

  const onSubmit = async () => {
    if (message.trim()) {
      setMessage("");
      await onSendClick(message);
    }
  };

  return (
    <div className="shrink-0 p-6">
      <form
        className="flex w-full items-center space-x-2"
        onSubmit={(e) => {
          e.preventDefault();
          onSubmit();
        }}
      >
        <Input
          id="message"
          placeholder="Type your message..."
          autoComplete="off"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
        />
        <Button
          variant="default"
          size="icon"
          disabled={message.trim().length === 0}
          aria-label="Send message"
        >
          <Send aria-hidden="true" />
          <span className="sr-only">Send</span>
        </Button>
      </form>
    </div>
  );
};
