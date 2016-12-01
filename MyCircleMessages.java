import java.util.ArrayList;

public class MyCircleMessages {

    public String text;
    public String timestamp;
    public String sender;
    public ArrayList<String> receivers;


    public MyCircleMessages(String send, String time, String body, ArrayList<String> people) {
        text = body;
        timestamp = time;
        sender = send;
        receivers = new ArrayList<String>();
        receivers = people;
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

    public ArrayList<String> getReceivers() {
	return receivers;
    }

}
