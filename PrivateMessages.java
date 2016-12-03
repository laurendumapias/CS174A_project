public class PrivateMessages {

    public String mid;
    public String text;
    public String timestamp;
    public String sender;
    public String receiver;

    public PrivateMessages(String id, String message, String time, String send, String r) {
        mid = id;
        text = message;
        timestamp = time;
        sender = send;
        receiver = r;
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

    public String getReceiver() {
        return receiver;
    }

}
