public class User {

    // MAY NEED TO ADD MY CIRCLE ID
    
    public String name, email, phone, password, screenname;
    public int isAManager;
    public ArrayList<String> friendList;
    public ArrayList<String> topicWords;
    public int myCircleId;
    public MyCircle myCircle;

    public User(String name, String email, String phone, String password, String screenname, int isAManager) {
        // insert user into database
        name = name;
	email = email;
	phone = phone;
	password = password;
	screenname = screenname;
	isAManager = isAManager;
	friendList = new ArrayList<String>();
	topicWords = new ArrayList<String>();

    }

    public User(String name, String email, String phone, String password, int isAManager) {
        // insert user into database
        name = name;
	email = email;
	phone = phone;
	password = password;
	isAManager = isAManager;
	friendList = new ArrayList<String>();
	topicWords = new ArrayList<String>();
    }

    public void getFriendsList() {
	//database stuff
    }

    public void addFriends(String email) {
	friendList.add(email);
	//add to the database
    }

    public void addTopicWord(String word) {
	topicWords.add(word);
	//add to the database
    }

    public void getMyCircleId() {
        //databse to get circle id
        myCircleId = //databse stuff
        myCircle = new MyCircle(id);

    }

    public void postMyCircleMessage(String message, ArrayList<String> topicWords, ArrayList<String> receivers) {
        // add message to mycircle in database

    }

    public void sendDirectMessage(String message, String receiver) {
        // add message to database
    }

    public void deletePrivateMessage() {
        // alter message receiever/sender in database
    }

    public void deleteGroupMessage() {

    }




    
}
