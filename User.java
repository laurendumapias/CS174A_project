import java.util.ArrayList;
import java.util.Scanner;

public class User {

    public Scanner scanner = new Scanner(System.in);

    // MAY NEED TO ADD MY CIRCLE ID
    
    public String name, email, phone, password, screenname;
    public String isAManager;
    public ArrayList<String> friendList;
    public ArrayList<String> topicWords;
    public int myCircleId;
    public MyCircle myCircle;

    public User(String e, String pass) {
	name = e;
	email = e;
	phone = "N/A";
	password = pass;
	screenname = "N/A";
        isAManager = "0";
	friendList = new ArrayList<String>();
	topicWords = new ArrayList<String>();
	myCircleId = 0;
	myCircle = new MyCircle(myCircleId);
    }

    public User(String e, String pass, String isAMan) {

        name = e;
        email = e;
        phone = "N/A";
        password = pass;
        screenname = "N/A";
        isAManager = isAMan;
        friendList = new ArrayList<String>();
        topicWords = new ArrayList<String>();
        myCircleId = 0;
        myCircle = new MyCircle(myCircleId);
    }


    public User(String actualName, String e, String pass, String pNum, String sName) {
	name = actualName;
	email = e;
	phone = pNum;
	password = pass;
	screenname = sName;
        isAManager = "0";
	friendList = new ArrayList<String>();
	topicWords = new ArrayList<String>();
    }

    public User(String name, String email, String phone, String password, String screenname, String isAManager) {
        // insert user into database
        name = name;
	email = email;
	phone = phone;
	password = password;
	screenname = screenname;
        isAManager = "0";
	friendList = new ArrayList<String>();
	topicWords = new ArrayList<String>();

    }


    

    public void addFriends(String email) {
	friendList.add(email);
	//add to the database
    }

    public void addTopicWord(String word) {
	topicWords.add(word);
	//add to the database
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



    //getter and setter
    public String getName() {
	return name;
    }

    public String getEmail() {
	return email;
    }

    public ArrayList<String> getFriendsList() {
	//database stuff
	return friendList;
    }

    public ArrayList<String> getTopicWords() {
	//database stuff
	return topicWords;
    }

    public MyCircle getMyCircle() {
	return myCircle;
    }
    
    public String getIsAManager() {
         return isAManager;
    }





    
}
