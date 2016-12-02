import java.util.Scanner;
import java.util.ArrayList;
import java.sql.*;

public class SocialNetwork {

    public Scanner scanner = new Scanner(System.in);
    public User user;
    public Connection con;


    public SocialNetwork() {
	try {
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    String url = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
	    String username = "brandonwicka";
	    String password = "171";
	    con = DriverManager.getConnection(url, username, password);
	  
	}
	catch(Exception e) {
	    System.out.println(e);
	}
    }
    
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

	    try {
		con.close();
	    }
	    catch (Exception e) {
		System.out.println(e);
	    }
	    System.out.println("Goodbye!");
	    return;
	}
    }

    public void login() {
	boolean isValid = false;
	scanner = new Scanner(System.in);
	System.out.println("Enter your email: ");
	String email = scanner.next();
        System.out.println("Enter your password: ");
	String password = scanner.next();


	//do database stuff to check if valid email and pass combination.
	try {
	    String sql = "SELECT email, password FROM Users where email = ? and password = ?";
	    PreparedStatement st = con.prepareStatement(sql);
	    st.setString(1, email);
	    st.setString(2, password);
	    ResultSet rs = st.executeQuery();
	    while(rs.next()) {		
		System.out.println(rs.getString(1) + " " + rs.getString(2));
	        String s = rs.getString(1);
		if(s.equals("")) {
		    isValid = false;
		}
		else {
		    isValid = true;
		}
	    }
            //con.close();
	} catch(Exception e) {
                System.out.println("FHJDHFJDFJFDHKFDHJF");
	    	System.out.println(e);
	}   

	if(isValid) {
	    System.out.println("-----------------------------------------------------");
	    System.out.println("Successfully logged in!");
	    System.out.println("-----------------------------------------------------");





            user = new User(email, password);
            populateFriendList(user);
	    displayHomePage(user);
	    
	}

	else {
	    System.out.println("-----------------------------------------------------");
	    System.out.println("Your email and password combination does not exist!");
	    System.out.println("-----------------------------------------------------");
	   
	    start();  
	}       	
    }

    public void populateFriendList(User u) {
        try {
            String sql = "SELECT user1 FROM Friendships WHERE user2 = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, u.getEmail());
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                String friend = rs.getString(1);
                System.out.println("FRIEND: " + friend);
                u.addFriends(friend);
            }
            //con.close();
        } catch(Exception e) {
                System.out.println(e);
        }

        try {
            String sql = "SELECT user2 FROM Friendships WHERE user1 = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, u.getEmail());
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                String friend = rs.getString(1);
                System.out.println("FRIEND: " + friend);
                u.addFriends(friend);
            }
            //con.close();
        } catch(Exception e) {
                System.out.println(e);
        }

        u.addFriends(u.getEmail());

        return;


    }

    public void createAccount() {
	boolean isValid = true;
        int manager = 0;
	System.out.println("Enter the email you wish to use as your login: ");
	String email = scanner.next();
	System.out.println("Enter a password: ");
	String password = scanner.next();
        System.out.println("Enter your name: ");
        String name = scanner.next();
        System.out.println("Enter your phone number: ");
        String phone = scanner.next();
        System.out.println("Enter your screenname: ");
        String screenname = scanner.next();
        System.out.println("Are you a manager? Enter 1 for yes, 0 for no");
        String isAManager = scanner.next();
        int isAMan = Integer.parseInt(isAManager);


	// check if email entered is not already in database
	try {
	    String sql = "SELECT email FROM Users where email = ?";
	    PreparedStatement st = con.prepareStatement(sql);
	    st.setString(1, email);
	    ResultSet r = st.executeQuery();
	    while(r.next()) {		
		System.out.println(r.getString(1));
	        String s = r.getString(1);
		if(s != null) {
		    isValid = false;
		}
		else {
		    isValid = true;
		}
	    }
	    //con.close();
	} catch(Exception e) {
	    System.out.println("try failed :(");
	    System.out.println(e);
	}   

	
	if(isValid) {
	    
	    try {
	    String sql = "INSERT INTO Users(email,name,phone,password,screenname,isAManager) VALUES (?,?,?,?,?,?)";
	    PreparedStatement st = con.prepareStatement(sql);
	    st.setString(1, email);
            st.setString(2, name);
            st.setString(3, phone);
            st.setString(4, password);
            st.setString(5, screenname);
            st.setInt(6, isAMan);
            ResultSet r = st.executeQuery();
	    //con.close();
	    } catch(Exception e) {
		System.out.println(e);
            }


	    System.out.println("------------------------------------------------------------");
	    System.out.println("Success! Your account has been created and is ready to use!");
	    System.out.println("------------------------------------------------------------");
	    start();
	}

	else {
	    System.out.println("----------------------------------------------------------");
	    System.out.println("Error! Email already in use. Please choose another email.");
	    System.out.println("----------------------------------------------------------");
	    start();
	}
	
    }

    public void displayHomePage(User u) {
	 System.out.println("******************************************************");
	 System.out.println("Hello " + u.getName() + "! What would you like to do today?");
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
	     viewMyCircle(u);
	 }

	 if(input == 2) {
             viewChatGroups(u);
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
 
    public void viewMyCircle(User u) {
	System.out.println("*****************************************************");
	System.out.println("Welcome to your myCircle! What would you like to do?");
	System.out.println("*****************************************************");
	System.out.println("1.) View myCircle posts");
	System.out.println("2.) Post a new message");
        System.out.println("3.) Search posts by topic words");
        System.out.println("4.) Back to main menu");

	int input;

	do {
	     input = scanner.nextInt();
             if(input != 1 && input != 2 && input != 3 && input != 4)
	       	System.out.println("Invalid selection");
         } while (input != 1 && input != 2 && input != 3 && input != 4);

	 if(input == 1) {
             u.getMyCircle().populateMessages(u, con);
             viewMyCircle(u);
	 }

	 if(input == 2) {
             u.getMyCircle().postMyCircleMessage(u, con);
	     viewMyCircle(u);
	 }

         if(input == 3) {
             ArrayList<String> topics = new ArrayList<String>();
             u.getMyCircle().searchMessages(u, con, topics);
             viewMyCircle(u);
         }

         if(input == 4) {
	     displayHomePage(u);
	 }
	

    }


    

    public void viewChatGroups(User u) {
	System.out.println("********************************************************");
	System.out.println("Welcome to your ChatGroups! What would you like to do?");
	System.out.println("********************************************************");
	System.out.println("1.) View my ChatGroups");
	System.out.println("2.) Create a ChatGroup");
	System.out.println("3.) Modify my existing ChatGroups");
	System.out.println("4.) View my invitations");
	System.out.println("5.) Back to main menu");

	int input;
        int numOfGroups = 0;
        ArrayList<String> chatGroupList = new ArrayList<String>();
	do {
	     input = scanner.nextInt();
	     if(input != 1 && input != 2 && input != 3 && input != 4 && input != 5)
	       	System.out.println("Invalid selection");
	 } while (input != 1 && input != 2 && input != 3 && input != 4 && input != 5);

	 if(input == 1) {
             System.out.println("******************************************");
             System.out.println("Here are the ChatGroups you belong to: ");
             System.out.println("******************************************");
             try {
                 String sql2 = "SELECT cg.name FROM Chat_Groups cg INNER JOIN chat_group_members cgm ON cg.name = cgm.cg_name WHERE cgm.user_email = ?";
                 PreparedStatement st2 = con.prepareStatement(sql2);
                 st2.setString(1, user.getEmail());
                 ResultSet rs2 = st2.executeQuery();
                 while(rs2.next()) {
                     chatGroupList.add(rs2.getString(1));
                     System.out.println("" + (numOfGroups+1) + ". " + rs2.getString(1));
                 }
             } catch(Exception e) {
                     System.out.println(e);
             }

             System.out.println("******************************************************************");
             System.out.println("                     What would you like to do?");
             System.out.println("******************************************************************");
             System.out.println("1.) View messages in a ChatGroup");
             System.out.println("2.) Back to ChatGroup menu");



             do {
                  input = scanner.nextInt();
                  if(input != 1 && input != 2)
                     System.out.println("Invalid selection");
              } while (input != 1 && input != 2);

              if(input == 1) {
                  // do some sql
                  System.out.println("Type the name of the chat group you wish to view");
                  scanner.nextLine();
                  String in = scanner.nextLine();

                  System.out.println("***********************************************************");
                  System.out.println("Messages in: " + in);
                  System.out.println("***********************************************************");

                  try {
                      String sql2 = "SELECT cg.owner, cgm.timestamp, cgm.sender, cgm.text FROM chat_group_messages cgm INNER JOIN Chat_Groups cg ON cg.name = cgm.chat_group_name WHERE cg.name = ?";
                      PreparedStatement st2 = con.prepareStatement(sql2);
                      st2.setString(1, in);
                      ResultSet rs2 = st2.executeQuery();
                      int j = 0;
                      while(rs2.next()) {
                          String owner = rs2.getString(1);
                          String time = rs2.getString(2);
                          String sender = rs2.getString(3);
                          String text = rs2.getString(4);

                          System.out.println("------------------------------------------------------------------");
                          System.out.println("Post Num: " + (j+1));
                          System.out.println("Sender: " + sender);
                          System.out.println("Posted on: " + time);
                          System.out.println("");
                          System.out.println("" + text);
                          System.out.println("");
                          j++;

                      }
                  } catch(Exception e) {
                          System.out.println(e);
                  }

                 System.out.println("------------------------------------------------------------------");
                 System.out.println("******************************************************************");
                 System.out.println("                     What would you like to do?");
                 System.out.println("******************************************************************");
                 System.out.println("1.) Post a message");
                 System.out.println("2.) Delete a message");
                 System.out.println("3.) Back to my ChatGroups");



                 do {
                      input = scanner.nextInt();
                      if(input != 1 && input != 2 && input != 3)
                         System.out.println("Invalid selection");
                  } while (input != 1 && input != 2 && input != 3);

                  if(input == 1) {
                      scanner.nextLine();
                      System.out.println("Enter the text for the message:");
                      String messageText = scanner.nextLine();


                      int id = 0;
                      try {
                          String sql1 = "SELECT MAX(id) FROM chat_group_messages";
                          PreparedStatement st = con.prepareStatement(sql1);

                          ResultSet rs = st.executeQuery();
                          while(rs.next()) {
                              id = Integer.parseInt(rs.getString(1));
                              id = id + 1;
                              System.out.println("MAX id: " + rs.getString(1));
                          }
                      } catch(Exception e) {
                              System.out.println(e);
                      }

                      try {
                          String sql2 = "INSERT INTO chat_group_messages (id, timestamp, chat_group_name, sender, text) VALUES (?, CURRENT_TIMESTAMP, ?,?,?)";
                          PreparedStatement st2 = con.prepareStatement(sql2);
                          st2.setString(1, String.valueOf(id));
                          st2.setString(2, in);
                          st2.setString(3, u.getEmail());
                          st2.setString(3, messageText);

                          ResultSet rs2 = st2.executeQuery();
                      } catch(Exception e) {
                              System.out.println(e);
                      }

                      System.out.println("---------------------------------");
                      System.out.println("Message successfully posted!");
                      System.out.println("---------------------------------");


                  }

                  if(input == 2) {

                  }

                  if(input == 3) {

                  }



              }





	 }

	 if(input == 2) {

	 }
	
	 if(input == 3) {

	 }

	 if(input == 4) {
	     
	 }

	 if(input == 5) {
	     displayHomePage(user);
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
	    displayHomePage(user);
	 }
    }

    public void manageTopicWords() {

    }

    
}
