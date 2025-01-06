import { ChatHeader } from "@/components/chat/chat-header";
import { ChatInput } from "@/components/chat/chat-input";
import { ChatConversationHistory } from "@/components/chat/chat-conversation-history";
import { useChat } from "@/hooks/use-chat";
import { useConversationHistory } from "@/hooks/use-conversation-history";
import { useConversationId } from "@/hooks/use-conversation-id";
import { useCallback } from "react";
import { ChatError } from "@/components/chat/chat-error";

export const Chat = () => {
  const { conversationId, createNewConversation } = useConversationId();
  const {
    history,
    loading: conversationHistoryLoading,
    error: conversationHistoryError,
    appendMessage,
  } = useConversationHistory({
    conversationId,
  });
  const {
    sendMessage,
    loading: generatingResponse,
    error: generatingError,
  } = useChat();

  const onSendClick = useCallback(
    async (message: string) => {
      if (!conversationId) {
        return;
      }
      appendMessage(message, "USER");
      const response = await sendMessage(conversationId, message);
      if (response) {
        appendMessage(response, "ASSISTANT");
      }
    },
    [conversationId, appendMessage, sendMessage]
  );

  const errorMessage = (): string | null => {
    if (conversationHistoryError) {
      return "Could not load the conversation, please create a new one";
    }
    if (generatingError) {
      return "Our chatbot is unavailable, please try again later";
    }
    return null;
  };

  return (
    <div className="container mx-auto flex flex-col w-full w-ma h-full">
      <div className="flex flex-col flex-1 rounded-xl border bg-card text-card-foreground overflow-hidden mx-4 md:mx-6 my-4">
        <ChatHeader onNewConversationClick={createNewConversation} />

        <div className="flex-1 overflow-y-auto">
          <ChatConversationHistory
            conversationHistoryLoading={conversationHistoryLoading}
            generatingResponse={generatingResponse}
            messages={history?.messages ?? []}
          />
        </div>

        <ChatError error={errorMessage()} />
        <ChatInput onSendClick={onSendClick} />
      </div>
    </div>
  );
};
