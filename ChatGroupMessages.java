public class ChatGroupMessages {


    public String mid;
    public String text;
    public String timestamp;
    public String sender;

    public ChatGroupMessages(String id, String message, String time, String send) {
        mid = id;
        text = message;
        timestamp = time;
        sender = send;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getMId() {
        return mid;
    }



}
