import { ThemeProvider } from "@/components/theme/theme-provider";
import "./index.css";
import { Header } from "@/components/header";
import { Chat } from "./components/chat/chat";

export const App = () => {
  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <div className="h-screen flex flex-col">
        <Header />
        <div className="flex-1 overflow-hidden">
          <Chat />
        </div>
      </div>
    </ThemeProvider>
  );
};
