import { Skeleton } from "@/components/ui/skeleton";
import { useEffect, useRef } from "react";
import { ChatMessageBubble } from "@/components/chat/chat-message-bubble";
import { ChatError } from "@/components/chat/chat-error";

interface ChatMessagesProps {
  conversationHistoryLoading: boolean;
  generatingResponse: boolean;
  messages: ChatMessage[];
}

interface ChatMessage {
  content: string;
  type: ChatMessageType;
}

type ChatMessageType = "USER" | "ASSISTANT";

const MESSAGE_BUBBLE_HEIGHT = "36px";

export const ChatConversationHistory = ({
  conversationHistoryLoading = false,
  generatingResponse = true,
  messages,
}: ChatMessagesProps) => {
  const chatEnd = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (chatEnd.current) {
      chatEnd.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [messages]);

  return (
    <div className="flex flex-col justify-end px-6 py-2 space-y-4">
      {conversationHistoryLoading && <ChatMessagesSkeleton />}
      {!conversationHistoryLoading &&
        messages.map((message, idx) => (
          <ChatMessageBubble message={message} key={`message-${idx}`} />
        ))}
      {generatingResponse && (
        <Skeleton
          className="w-max"
          style={{ width: "200px", height: MESSAGE_BUBBLE_HEIGHT }}
        />
      )}

      <div ref={chatEnd} />
    </div>
  );
};

const ChatMessagesSkeleton = () => {
  const randomWidth = () =>
    `${Math.floor(Math.random() * (250 - 120 + 1) + 120)}px`;

  const getMessageClass = (idx: number) =>
    idx % 2 === 1 ? "w-max ml-auto" : "w-max";

  return (
    <>
      {Array.from({ length: 4 }).map((_, idx) => (
        <Skeleton
          key={idx}
          className={getMessageClass(idx)}
          style={{ width: randomWidth(), height: MESSAGE_BUBBLE_HEIGHT }}
        />
      ))}
    </>
  );
};
