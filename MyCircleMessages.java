import java.util.ArrayList;

public class MyCircleMessages {

    public String mid;
    public String text;
    public String timestamp;
    public String sender;
    public ArrayList<String> topicWords;


    public MyCircleMessages(String send, String time, String body, ArrayList<String> topics, String id) {
        mid = id;
        text = body;
        timestamp = time;
        sender = send;
        topicWords = new ArrayList<String>();
        topicWords = topics;
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


    public ArrayList<String> getTopicWords() {

        return topicWords;
    }

    public String getTopicWordsToString() {
        String s = "";
        for(int i = 0; i < topicWords.size(); i++) {
                s = s + " " + topicWords.get(i);
        }
        return s;

    }



}
