import { useState, useEffect, useCallback } from "react";

const generateUUID = (): string => {
  return crypto.randomUUID();
};

export const useConversationId = () => {
  const STORAGE_KEY = "conversationId";

  const [conversationId, setConversationId] = useState<string | null>(null);

  const createNewConversation = useCallback(() => {
    const newId = generateUUID();
    localStorage.setItem(STORAGE_KEY, newId);
    setConversationId(newId);
  }, []);

  useEffect(() => {
    const savedId = localStorage.getItem(STORAGE_KEY);
    if (savedId) {
      setConversationId(savedId);
    } else {
      createNewConversation();
    }
  }, [createNewConversation]);

  return { conversationId, createNewConversation };
};
