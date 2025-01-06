import { Bot, Plus } from "lucide-react";
import { Button } from "@/components/ui/button";

interface ChatHeaderProps {
  onNewConversationClick: () => void;
}

export const ChatHeader = ({ onNewConversationClick }: ChatHeaderProps) => {
  return (
    <header className="shrink-0 p-6 flex items-center justify-between">
      <div className="flex items-center gap-4">
        <Bot className="h-6 w-6 text-muted-foreground" />
        <div className="space-y-1">
          <p className="text-sm font-medium leading-tight">
            DJF Development Chatbot
          </p>
          <p className="text-sm text-muted-foreground leading-tight">
            Built with Spring AI, using OpenAI GPT & Embedding Models
          </p>
        </div>
      </div>
      <Button
        variant="outline"
        size="icon"
        className="rounded-full h-9 w-9"
        title="Start a new conversation"
        onClick={onNewConversationClick}
        aria-label="Start a new conversation"
      >
        <Plus />
        <span className="sr-only">New conversation</span>
      </Button>
    </header>
  );
};
