import java.util.Scanner;
import java.util.ArrayList;

public class SocialNetwork {

    public Scanner scanner = new Scanner(System.in);
    
    
    public static void main(String[] args) {
	SocialNetwork sn = new SocialNetwork();
	sn.start();     
    }
    

    public void start() {
	Scanner s = new Scanner(System.in);
	System.out.println("Welcome to BuzMo!");
	System.out.println("Select an option: ");
	System.out.println("1.) Login");
	System.out.println("2.) Create an account");
	System.out.println("3.) Quit");

	int input;

	do {
	    input = s.nextInt();
	    if(input != 1 && input != 2 && input != 3)
		System.out.println("Invalid selection");
	} while (input != 1 && input != 2 && input != 3);

	if(input == 1) {
	    login();
	}

	if(input == 2) {
	    createAccount();
	}
	
	if(input == 3) {
	    System.out.println("Goodbye!");
	    return;
	}
    }

    public void login() {
	scanner = new Scanner(System.in);
	System.out.println("Enter your email: ");
	String email = scanner.next();
	System.out.println("Enter your password: ");
	String password = scanner.next();

	//do database stuff to check if valid email and pass combination.
	boolean isValid = true;

	if(isValid) {
	    System.out.println("-----------------------------------------------------");
	    System.out.println("Successfully logged in!");
	    System.out.println("-----------------------------------------------------");
	    displayHomePage("Brandon");
	    
	}

	else {
	    System.out.println("-----------------------------------------------------");
	    System.out.println("Your email and password combination does not exist!");
	    System.out.println("-----------------------------------------------------");
	   
	    start();  
	}       	
    }

    public void createAccount() {
	System.out.println("Enter the email you wish to use as your login: ");
	String email = scanner.next();
	System.out.println("Enter a password: ");
	String password = scanner.next();

	// check if email entered is not already in database

	boolean isValid = true;

	if(isValid) {
	    System.out.println("------------------------------------------------------------");
	    System.out.println("Success! Your account has been created and is ready to use!");
	    System.out.println("------------------------------------------------------------");
	    start();
	}

	else {
	    System.out.println("----------------------------------------------------------");
	    System.out.println("Error! Email already in use. Please choose another email.");
	    System.out.println("----------------------------------------------------------");
	    createAccount();
	}
	
    }

    public void displayHomePage(String user) {
	 System.out.println("******************************************************");
	 System.out.println("Hello " + user + "! What would you like to do today?");
	 System.out.println("******************************************************");
	 System.out.println("1.) View myCircle");
	 System.out.println("2.) View my ChatGroups");
	 System.out.println("3.) Browse messages in Buzmo");
	 System.out.println("4.) Manage my circle of friends");
	 System.out.println("5.) Manage my topic words");
	 System.out.println("6.) Logout");

	 int input;

	 do {
	     input = scanner.nextInt();
	     if(input != 1 && input != 2 && input != 3 && input != 4 && input != 5 && input != 6)
	       	System.out.println("Invalid selection");
	 } while (input != 1 && input != 2 && input != 3 && input != 4 && input != 5 && input != 6);

	 if(input == 1) {
	     viewMyCircle();
	 }

	 if(input == 2) {
	     viewChatGroups();
	 }
	
	 if(input == 3) {
	     browseMessages();
	 }

	 if(input == 4) {
	     manageFriends();
	 }

	 if(input == 5) {
	     manageTopicWords();
	 }

	 if(input == 6) {
	     System.out.println("-------------------------------------------------");
	     System.out.println("You have successfully logged out! See you later!");
	     System.out.println("-------------------------------------------------");
	     start();
	 }		           	 
     }
 
    public void viewMyCircle() {
	System.out.println("*****************************************************");
	System.out.println("Welcome to your myCircle! What would you like to do?");
	System.out.println("*****************************************************");
	System.out.println("1.) View recent messages");
	System.out.println("2.) Post a new message");
	System.out.println("3.) Delete a message");
	System.out.println("4.) Back to main menu");

	int input;

	do {
	     input = scanner.nextInt();
	     if(input != 1 && input != 2 && input != 3 && input != 4)
	       	System.out.println("Invalid selection");
	 } while (input != 1 && input != 2 && input != 3 && input != 4);

	 if(input == 1) {
	     viewRecentMessages();
	 }

	 if(input == 2) {
	     postMyCircleMessage();
	 }
	
	 if(input == 3) {
	     deleteMyCircleMessage();
	 }

	 if(input == 4) {
	     displayHomePage("Brandon");
	 }
	

    }

    public void viewRecentMessages() {
	ArrayList<String> receivers = new ArrayList<String>();
	receivers.add("Joe");
	ArrayList<MyCircleMessages> m = new ArrayList<MyCircleMessages>();
	m.add(new MyCircleMessages("Brandon", "12 Oct 2016", "Hey dude whats up", receivers));
	m.add(new MyCircleMessages("Brandon", "13 Oct 2016", "I hate u", receivers));
	m.add(new MyCircleMessages("Brandon", "1 Dec 2016", "where am i?", receivers));
	m.add(new MyCircleMessages("Brandon", "22 June 2016", "Vote for me for president", receivers));

	for(int i = 0; i < 4; i++) {
	    System.out.println("------------------------------------------------------------------");
	    System.out.println("Sender: " + m.get(i).getSender());
	    System.out.println("Posted on: " + m.get(i).getTimestamp());
	    System.out.println("");
	    System.out.println("" + m.get(i).getText());
	    System.out.println("");	 	   
	}
	System.out.println("------------------------------------------------------------------");
	System.out.println("")
    }

    public void postMyCircleMessage() {
	System.out.println("Enter the text for the message: ");
	String messageText = scanner.next();
	ArrayList<String> words = new ArrayList<>();
	addMessageTopicWords(words);
	System.out.println("Who would you like to receive this message?");
	System.out.println("1.) Everyone");
	System.out.println("2.) Only certain people");

	int input;

	do {
	     input = scanner.nextInt();
	     if(input != 1 && input != 2)
	       	System.out.println("Invalid selection");
	 } while (input != 1 && input != 2);

	 if(input == 1) {
	     System.out.println("---------------------------------");
	     System.out.println("Message successfully posted!");
	     System.out.println("---------------------------------");
	     viewMyCircle();
	 }

	 if(input == 2) {
	     ArrayList<String> names = new ArrayList<>();
	     addMessageReceivers(names);
	     
	 }
	
    }

    public void addMessageReceivers(ArrayList<String> names) {
	System.out.println("Enter the name of who you want to see the message:");
	String s = scanner.next();
	names.add(s);
        System.out.println("Do you want to send this message to another person?"); 
	System.out.println("1.) Yes");
	System.out.println("2.) No");
	int in;
	do {
	     in = scanner.nextInt();
	     if(in != 1 && in != 2)
      	     System.out.println("Invalid selection");
	 } while (in != 1 && in != 2);

	if(in == 1) {
	    addMessageReceivers(names);
	}

	if(in == 2) {
	    System.out.println("--------------------------------");
	    System.out.println("Message successfully sent!");
	    System.out.println("--------------------------------");
	    displayHomePage("Brandon");
	}
    }

     public void addMessageTopicWords(ArrayList<String> words) {
	System.out.println("Enter a topic word to be associated with your message:");
	String s = scanner.next();
	words.add(s);
        System.out.println("Do you want to add another topic word?"); 
	System.out.println("1.) Yes");
	System.out.println("2.) No");
	int in;
	do {
	     in = scanner.nextInt();
	     if(in != 1 && in != 2)
      	     System.out.println("Invalid selection");
	 } while (in != 1 && in != 2);

	if(in == 1) {
	    addMessageTopicWords(words);
	}

	if(in == 2) {
	    return;
	}
    }

    public void deleteMyCircleMessage() {

    }

    public void viewChatGroups() {
	System.out.println("********************************************************");
	System.out.println("Welcome to your ChatGroups! What would you like to do?");
	System.out.println("********************************************************");
	System.out.println("1.) View my ChatGroups");
	System.out.println("2.) Create a ChatGroup");
	System.out.println("3.) Modify my existing ChatGroups");
	System.out.println("4.) View my invitations");
	System.out.println("5.) Back to main menu");

	int input;
	do {
	     input = scanner.nextInt();
	     if(input != 1 && input != 2 && input != 3 && input != 4 && input != 5)
	       	System.out.println("Invalid selection");
	 } while (input != 1 && input != 2 && input != 3 && input != 4 && input != 5);

	 if(input == 1) {
	     
	 }

	 if(input == 2) {

	 }
	
	 if(input == 3) {

	 }

	 if(input == 4) {
	     
	 }

	 if(input == 5) {
	     displayHomePage("Brandon");
	 }
    }

    public void browseMessages() {

    }

    public void manageFriends() {
	System.out.println("*************************");
	System.out.println("My Circle of Friends");
	System.out.println("*************************");
	System.out.println("1.) View my friends");
	System.out.println("2.) View my friend requests");
	System.out.println("3.) Send a friend request");
	System.out.println("4.) Send a private message");
	System.out.println("5.) Back to main menu");
	int input;
	do {
	     input = scanner.nextInt();
	     if(input != 1 && input != 2 && input != 3 && input != 4 && input != 5)
	       	System.out.println("Invalid selection");
	 } while (input != 1 && input != 2 && input != 3 && input != 4 && input != 5);

	 if(input == 1) {
	    
	 }

	 if(input == 2) {
	   
	 }
	
	 if(input == 3) {
        
	 }

	 if(input == 4) {
        
	 }

	 if(input == 5) {
	    displayHomePage("Brandon");
	 }
    }

    public void manageTopicWords() {

    }

    
}
