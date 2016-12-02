public class ChatGroups {
    public String name;
    public int duration;
    public String owner;
    public String cid;
    public ArrayList<String> members;
    public ArrayList<String> messages;


    public ChatGroups(String name, String owner, ArrayList<String> members, String id) {
        // add chatgroup to database
        cid = id;
        name = name;
        duration = duraiton;
        owner = owner;
        members = new ArrayList<>();
        members = members;
        messages = new ArrayList<>();
    }

    public ChatGroups(String name, int duration, String owner, ArrayList<String> members, String id) {
        // add chatgroup to database
        cid = id;
        name = name;
        duration = duraiton;
        owner = owner;
        members = new ArrayList<>();
        members = members;
        messages = new ArrayList<>();
    }

    public void getMessages() {
        // database stuff to get messages

    }

    public void getMembers() {
        // database stuff
    }





}
