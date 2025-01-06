import { ChatMessage, ChatMessageType } from "@/types/chat-message";
import clsx from "clsx";
import { memo } from "react";
import ReactMarkdown from "react-markdown";

interface ChatMessageBubbleProps {
  message: ChatMessage;
}

export const ChatMessageBubble = memo(
  ({ message: { content, type } }: ChatMessageBubbleProps) => {
    const commonStyle =
      "flex w-max max-w-[75%] flex-col gap-2 rounded-lg px-3 py-2 text-sm";

    const typeSpecificStyle: Record<ChatMessageType, string> = {
      ASSISTANT: "bg-muted",
      USER: "ml-auto bg-primary text-primary-foreground",
    };

    return (
      <div className={clsx(commonStyle, typeSpecificStyle[type])}>
        <ReactMarkdown
          components={{
            h3: (props) => <h3 className="text-lg font-semibold" {...props} />,
            hr: (props) => <hr className="my-4 border-gray-400" {...props} />,
            ul: (props) => <ul className="list-disc pl-6" {...props} />,
            li: (props) => <li className="text-sm" {...props} />,
          }}
        >
          {content}
        </ReactMarkdown>
      </div>
    );
  }
);
