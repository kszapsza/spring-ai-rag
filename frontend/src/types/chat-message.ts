export type ChatMessageType = "USER" | "ASSISTANT";

export interface ChatMessage {
  content: string;
  type: ChatMessageType;
}
