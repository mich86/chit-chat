package android.example.chitchat;

public class ChatMessage {

    String chatMessageText;
    String chatMessageSendTime;
    String chatMessageSender;

    public ChatMessage() {;}

    public String getChatMessageText() { return chatMessageText; }
    public String getChatMessageSendTime() { return chatMessageSendTime; }
    public String getChatMessageSender() { return chatMessageSender; }

    public String generateKey() {

        String result = chatMessageSendTime + " " + chatMessageSender;
        return result;
    }

    public String toString() {

        String result = "Sender [" + chatMessageSender + "] Time [" + chatMessageSendTime + "] Message [" + chatMessageText + "]";
        return result;
    }
}

