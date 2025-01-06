import { API_BASE_URL } from "@/api-config";
import { ChatMessage, ChatMessageType } from "@/types/chat-message";
import { useState, useEffect, useCallback } from "react";

interface ConversationHistory {
  conversationId: string | null;
  messages: ChatMessage[];
}

interface UseConversationHistoryProps {
  conversationId: string | null;
  lastN?: number;
}

export const useConversationHistory = ({
  conversationId,
  lastN = 10,
}: UseConversationHistoryProps) => {
  const [history, setHistory] = useState<ConversationHistory>({
    conversationId: null,
    messages: [],
  });
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!conversationId) {
      return;
    }

    const fetchConversationHistory = async () => {
      setLoading(true);
      setError(null);

      try {
        const response = await fetch(
          `${API_BASE_URL}/api/conversation/${conversationId}?lastN=${lastN}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        if (!response.ok) {
          if (response.status === 404) {
            // Suppress 404, new conversation – not created on the server.
            setHistory({ conversationId, messages: [] });
            setError(null);
          } else {
            throw new Error(`Error: ${response.statusText}`);
          }
        } else {
          const data = await response.json();
          setHistory(data);
        }
      } catch (err) {
        console.error(err);
        setError(
          (err as Error).message || "Failed to fetch conversation history."
        );
      } finally {
        setLoading(false);
      }
    };

    fetchConversationHistory();
  }, [conversationId, lastN]);

  const appendMessage = useCallback(
    (content: string, type: ChatMessageType) => {
      setHistory((prevHistory) => {
        const updatedMessages = [
          ...prevHistory.messages,
          { content, type } as ChatMessage,
        ];
        return { ...prevHistory, messages: updatedMessages };
      });
    },
    []
  );

  return { history, loading, error, appendMessage };
};
