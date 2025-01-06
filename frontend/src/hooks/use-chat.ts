import { API_BASE_URL } from "@/api-config";
import { useState } from "react";

interface ChatRequest {
  conversationId: string;
  message: string;
}

interface ChatResponse {
  message: string;
}

export const useChat = () => {
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const sendMessage = async (conversationId: string, message: string) => {
    setLoading(true);
    setError(null);

    try {
      const response = await fetch(`${API_BASE_URL}/api/chat`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ conversationId, message } as ChatRequest),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || "Failed to send the message.");
      }

      const data = await response.json();
      return data.message;
    } catch (err) {
      setError((err as Error).message || "Unexpected error occurred.");
      return null;
    } finally {
      setLoading(false);
    }
  };

  return { sendMessage, loading, error };
};
