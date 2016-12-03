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
        System.out.println("2.) Quit");

	int input;

	do {
	    input = s.nextInt();
            if(input != 1 && input != 2)
		System.out.println("Invalid selection");
        } while (input != 1 && input != 2);

	if(input == 1) {
	    login();
	}
	
        if(input == 2) {

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
        String isAMan = "";
	try {
            String sql = "SELECT email, password, isAManager FROM Users where email = ? and password = ?";
	    PreparedStatement st = con.prepareStatement(sql);
	    st.setString(1, email);
            st.setString(2, password);
	    ResultSet rs = st.executeQuery();
	    while(rs.next()) {		
		System.out.println(rs.getString(1) + " " + rs.getString(2));
	        String s = rs.getString(1);
                isAMan = rs.getString(3);
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





            user = new User(email, password, isAMan);
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
                //System.out.println("FRIEND: " + friend);
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
               // System.out.println("FRIEND: " + friend);
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



         if(u.getIsAManager().equals("1")) {
             System.out.println("Do you want to log in as a user or manager?");
             System.out.println("1. User");
             System.out.println("2. Manager");
             int inp;

             do {
                 inp = scanner.nextInt();
                 if(inp != 1 && inp != 2)
                    System.out.println("Invalid selection");
             } while (inp != 1 && inp != 2);

             if(inp == 1) {

                 System.out.println("1.) View myCircle");
                 System.out.println("2.) View my ChatGroups");
                 System.out.println("3.) Browse messages in Buzmo");
                 System.out.println("4.) Manage my circle of friends");
                 System.out.println("5.) Logout");

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
                     displayHomePage(u);
                 }

                 if(input == 3) {
                     browseMessages();
                 }

                 if(input == 4) {
                     manageFriends(u);
                     displayHomePage(u);
                 }

                 if(input == 5) {
                     System.out.println("-------------------------------------------------");
                     System.out.println("You have successfully logged out! See you later!");
                     System.out.println("-------------------------------------------------");
                     start();
                 }
             }

             if(inp == 2) {
                 System.out.println("1.) View myCircle");
                 System.out.println("2.) View my ChatGroups");
                 System.out.println("3.) Browse messages in Buzmo");
                 System.out.println("4.) Manage my circle of friends");
                 System.out.println("5.) Create a new user");
                 System.out.println("6.) Find active users");
                 System.out.println("7.) Find top messages");
                 System.out.println("8.) Show number of inactive users");
                 System.out.println("9.) Summary report");
                 System.out.println("0.) Logout");

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
                     displayHomePage(u);
                 }

                 if(input == 3) {
                     browseMessages();
                 }

                 if(input == 4) {
                     manageFriends(u);
                     displayHomePage(u);
                 }

                 if(input == 5) {
                    createAccount();
                 }

                 if(input == 0) {
                     System.out.println("-------------------------------------------------");
                     System.out.println("You have successfully logged out! See you later!");
                     System.out.println("-------------------------------------------------");
                     start();
                 }
             }
         }
     else{

             System.out.println("1.) View myCircle");
             System.out.println("2.) View my ChatGroups");
             System.out.println("3.) Browse messages in Buzmo");
             System.out.println("4.) Manage my circle of friends");
             System.out.println("5.) Logout");

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
                 displayHomePage(u);
             }

             if(input == 3) {
                 browseMessages();
             }

             if(input == 4) {
                 manageFriends(u);
                 displayHomePage(u);
             }

             if(input == 5) {
                 System.out.println("-------------------------------------------------");
                 System.out.println("You have successfully logged out! See you later!");
                 System.out.println("-------------------------------------------------");
                 start();
             }
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
                     numOfGroups++;
                 }
             } catch(Exception e) {
                     System.out.println(e);
             }

             System.out.println("******************************************************************");
             System.out.println("                     What would you like to do?");
             System.out.println("******************************************************************");
             System.out.println("1.) View messages in a ChatGroup");
             System.out.println("2.) Invite friends to a ChatGroup");
             System.out.println("3.) Back to ChatGroup menu");



             do {
                  input = scanner.nextInt();
                  if(input != 1 && input != 2 && input != 3)
                     System.out.println("Invalid selection");
              } while (input != 1 && input != 2 && input != 3);

              if(input == 1) {
                   int postNum = 0;
                  // do some sql
                  System.out.println("Type the name of the chat group you wish to view");
                  scanner.nextLine();
                  String in = scanner.nextLine();

                  System.out.println("***********************************************************");
                  System.out.println("Messages in: " + in);
                  System.out.println("***********************************************************");


                  ArrayList<ChatGroupMessages> messagesList = new ArrayList<ChatGroupMessages>();

                  try {
                      String sql2 = "SELECT cg.owner, cgm.timestamp, cgm.sender, cgm.text, cgm.id FROM chat_group_messages cgm INNER JOIN Chat_Groups cg ON cg.name = cgm.chat_group_name WHERE cg.name = ?";
                      PreparedStatement st2 = con.prepareStatement(sql2);
                      st2.setString(1, in);
                      ResultSet rs2 = st2.executeQuery();

                      while(rs2.next()) {
                          String owner = rs2.getString(1);
                          String time = rs2.getString(2);
                          String sender = rs2.getString(3);
                          String text = rs2.getString(4);
                          String mId = rs2.getString(5);

                          messagesList.add(new ChatGroupMessages(mId, text, time, sender));

                          System.out.println("------------------------------------------------------------------");
                          System.out.println("Post Num: " + (postNum+1));
                          System.out.println("Sender: " + sender);
                          System.out.println("Posted on: " + time);
                          System.out.println("");
                          System.out.println("" + text);
                          System.out.println("");
                          postNum++;

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
                          st2.setString(4, messageText);

                          ResultSet rs2 = st2.executeQuery();
                      } catch(Exception e) {
                              System.out.println(e);
                      }

                      System.out.println("---------------------------------");
                      System.out.println("Message successfully posted!");
                      System.out.println("---------------------------------");
                       return;
                      //viewChatGroups(u);


                  }

                  if(input == 2) {
                      System.out.println("Enter the post number for the message you wish to delete: ");
                      do {
                           input = scanner.nextInt();
                           if(input > postNum || input < 1)
                              System.out.println("Invalid selection");
                       } while (input > postNum || input < 1);

                       if(!messagesList.get(input-1).getSender().equals(u.getEmail())) {
                          System.out.println("--------------------------------------------------------------");
                          System.out.println("Delete failed! You can only delete posts you have authored!");
                          System.out.println("--------------------------------------------------------------");
                          return;
                          //viewChatGroups(u);
                       }

                       else {
                           String mId = messagesList.get(input-1).getMId();
                           try {
                                       String sql1 = "DELETE FROM chat_group_messages WHERE id = ?";
                                       PreparedStatement st = con.prepareStatement(sql1);
                                       st.setString(1, mId);
                                       ResultSet rs = st.executeQuery();

                                   } catch(Exception e) {
                                           //System.out.println(e);
                            }
                            System.out.println("--------------------------------------------------------------");
                            System.out.println("Success! You deleted your post!");
                            System.out.println("--------------------------------------------------------------");

                            return;
                            //viewChatGroups(u);
                         }
                 }

                  if(input == 3) {
                       return;
                        //ewChatGroups(u);
                  }



              }

              if(input == 2) {
                  //invite
                  System.out.println("Enter the name of the ChatGroup you wish to invite a friend to: ");
                  scanner.nextLine();
                  String cName = scanner.nextLine();
                  System.out.println("Enter the email of the friend you wish to add to the ChatGroup:");
                  String friend = scanner.next();
                  if(!(u.getFriendsList().contains(friend))) {
                      System.out.println("--------------------------------------------------------------------------------------");
                      System.out.println("Error! The email you entered is not a part of your friends list!");
                      System.out.println("--------------------------------------------------------------------------------------");
                      viewChatGroups(u);
                  }
                  int iid = 0;
                  try {
                      String sql1 = "SELECT MAX(iid) FROM chat_group_invites";
                      PreparedStatement st = con.prepareStatement(sql1);

                      ResultSet rs = st.executeQuery();
                      while(rs.next()) {
                          iid = Integer.parseInt(rs.getString(1));
                          iid = iid + 1;
                          System.out.println("MAX iid: " + rs.getString(1));
                      }
                  } catch(Exception e) {
                          System.out.println(e);
                  }

                  try {
                      String sql2 = "INSERT INTO chat_group_invites (iid, group_chat_name, user_email, sender) VALUES (?,?,?,?)";
                      PreparedStatement st2 = con.prepareStatement(sql2);
                      st2.setString(1, String.valueOf(iid));
                      st2.setString(2, cName);
                      st2.setString(3, friend);
                      st2.setString(4, u.getEmail());
                      ResultSet rs2 = st2.executeQuery();
                  } catch(Exception e) {
                          System.out.println(e);
                  }

                  System.out.println("--------------------------------------------------------------");
                  System.out.println("Success! You sent a request to add" + friend + " to ChatGroup: " + cName);
                  System.out.println("--------------------------------------------------------------");
                  //viewChatGroups(u);
                  return;


              }

              if(input == 3) {
                  //viewChatGroups(u);
                  return;
              }






	 }

	 if(input == 2) {
             Boolean isValid = true;
             int dur = 0;
             String d = "";
             System.out.println("Enter a name for the ChatGroup you wish to create: ");
             scanner.nextLine();
             String in = scanner.nextLine();
             System.out.println("Do you want to enter a duration for your ChatGroup?");
             System.out.println("1. Yes");
             System.out.println("2. No");
             do {
                  dur = scanner.nextInt();
                  if(dur != 1 && dur != 2)
                     System.out.println("Invalid selection");
              } while (dur != 1 && dur != 2);

              if(dur == 1) {
                  System.out.println("Enter a duration for the ChatGroup you wish to create: ");
                  d = scanner.next();
              }
              else {
                d = "7";
              }

             try {
                 String sql2 = "SELECT * FROM Chat_Groups WHERE name = ?";
                 PreparedStatement st2 = con.prepareStatement(sql2);
                 st2.setString(1, in);
                 ResultSet rs2 = st2.executeQuery();
                 while(rs2.next()) {
                     String s = rs2.getString(1);
                     if(s.equals("")) {
                         isValid = true;
                     }
                     else {
                         isValid = false;
                     }
                 }
             } catch(Exception e) {
                     System.out.println(e);
             }

             if(isValid) {
                 int cgm_id = 0;
                 try {
                     String sql2 = "INSERT INTO Chat_Groups (name, duration, owner) VALUES (?,?,?)";
                     PreparedStatement st2 = con.prepareStatement(sql2);
                     st2.setString(1, in);
                     st2.setString(2, d);
                     st2.setString(3, u.getEmail());
                     ResultSet rs2 = st2.executeQuery();

                 } catch(Exception e) {
                         System.out.println(e);
                 }

                 try {
                     String sql1 = "SELECT MAX(cgm_id) FROM chat_group_members";
                     PreparedStatement st = con.prepareStatement(sql1);

                     ResultSet rs = st.executeQuery();
                     while(rs.next()) {
                         cgm_id = Integer.parseInt(rs.getString(1));
                         cgm_id = cgm_id + 1;
                         System.out.println("MAX cgm_id: " + rs.getString(1));
                     }
                 } catch(Exception e) {
                         System.out.println(e);
                 }

                 try {
                     String sql2 = "INSERT INTO chat_group_members (cgm_id, cg_name, user_email) VALUES (?, ?, ?)";
                     PreparedStatement st2 = con.prepareStatement(sql2);
                     st2.setString(1, String.valueOf(cgm_id));
                     st2.setString(2, in);
                     st2.setString(3, u.getEmail());
                     ResultSet rs2 = st2.executeQuery();

                 } catch(Exception e) {
                         System.out.println(e);
                 }


                 System.out.println("--------------------------------------------------------------");
                 System.out.println("Success! You have created ChatGroup: " + in);
                 System.out.println("--------------------------------------------------------------");
                 //viewChatGroups(u);
                 return;



             }

             else {
                 System.out.println("Error! ChatGroup name already exists. Choose another!");
                // viewChatGroups(u);
                return;
             }



	 }
	
	 if(input == 3) {
             ArrayList<String> gNameList = new ArrayList<String>();

             System.out.println("Here are the ChatGroups you own: ");
             try {
                 String sql2 = "SELECT name FROM Chat_Groups WHERE owner = ?";
                 PreparedStatement st2 = con.prepareStatement(sql2);
                 st2.setString(1, u.getEmail());

                 ResultSet rs2 = st2.executeQuery();
                 while(rs2.next()) {
                     String gName = rs2.getString(1);
                     System.out.println(gName);
                     gNameList.add(gName);
                 }

             } catch(Exception e) {
                     System.out.println(e);
             }

             System.out.println("Enter the ChatGroup name you wish to modify: ");
             scanner.nextLine();
             String result = scanner.nextLine();
             if(!gNameList.contains(result)) {
                System.out.println("Error! The name you entered does not match any of your groups!");
                //viewChatGroups(u);
                return;
             }
             System.out.println("Select what you would like to modify");
             System.out.println("1. Change name");
             System.out.println("2. Change duration");
             int res;
             do {
                  res = scanner.nextInt();
                  if(res != 1 && res != 2)
                     System.out.println("Invalid selection");
              } while (res != 1 && res != 2);

              if(res == 1) {
                  System.out.println("Enter the new name:");
                  scanner.nextLine();
                  String newName = scanner.nextLine();

                  try {
                      String sql6 = "UPDATE chat_group_members SET cg_name = ? WHERE cg_name = ?";
                      PreparedStatement st6 = con.prepareStatement(sql6);
                      st6.setString(1, newName);
                      st6.setString(2, result);
                      ResultSet rs6 = st6.executeQuery();

                  } catch(Exception e) {
                          System.out.println(e);
                  }

                  try {
                      String sql6 = "UPDATE chat_group_messages SET chat_group_name = ? WHERE chat_group_name = ?";
                      PreparedStatement st6 = con.prepareStatement(sql6);
                      st6.setString(1, newName);
                      st6.setString(2, result);
                      ResultSet rs6 = st6.executeQuery();

                  } catch(Exception e) {
                          System.out.println(e);
                  }

                  try {
                      String sql6 = "UPDATE Chat_Groups SET name = ? WHERE name = ?";
                      PreparedStatement st6 = con.prepareStatement(sql6);
                      st6.setString(1, newName);
                      st6.setString(2, result);
                      ResultSet rs6 = st6.executeQuery();

                  } catch(Exception e) {
                          System.out.println(e);
                  }

                  System.out.println("Success! Your group name has been updated!");
                  return;

              }

              if(res == 2) {
                  System.out.println("Enter the new duration:");
                  scanner.nextLine();
                  String newDuration = scanner.nextLine();
                  try {
                      String sql6 = "UPDATE Chat_Groups SET duration = ? WHERE name = ?";
                      PreparedStatement st6 = con.prepareStatement(sql6);
                      st6.setString(1, newDuration);
                      st6.setString(2, result);
                      ResultSet rs6 = st6.executeQuery();

                  } catch(Exception e) {
                          System.out.println(e);
                  }

                  System.out.println("Success! Your group's duration has been updated!");
                  return;

              }





	 }

	 if(input == 4) {
             ArrayList<String> gNameList = new ArrayList<String>();
             System.out.println("***********************************************");
             System.out.println("Here are your pending ChatGroup invitations:");
             System.out.println("***********************************************");
             try {
                 String sql1 = "SELECT group_chat_name, sender FROM chat_group_invites WHERE user_email = ?";
                 PreparedStatement st = con.prepareStatement(sql1);
                 st.setString(1, u.getEmail());
                 ResultSet rs = st.executeQuery();
                 while(rs.next()) {
                     String gName = rs.getString(1);
                     String sender = rs.getString(2);
                     System.out.println("____________________________________");
                     System.out.println("Invitation to join: " + gName);
                     System.out.println("From: " + sender);
                     System.out.println("____________________________________");
                     gNameList.add(gName);
                 }
             } catch(Exception e) {
                     System.out.println(e);
             }

              System.out.println("Enter the name of the group you wish to join:");
              scanner.nextLine();
              String result = scanner.nextLine();

              if(!gNameList.contains(result)) {
                 System.out.println("Error! The name you entered does not match any of your invitations!");
                // viewChatGroups(u);
                return;
              }

              int cgm_id = 0;
              try {
                  String sql1 = "SELECT MAX(cgm_id) FROM chat_group_members";
                  PreparedStatement st = con.prepareStatement(sql1);

                  ResultSet rs = st.executeQuery();
                  while(rs.next()) {
                      cgm_id = Integer.parseInt(rs.getString(1));
                      cgm_id = cgm_id + 1;
                      System.out.println("MAX cgm_id: " + rs.getString(1));
                  }
              } catch(Exception e) {
                      System.out.println(e);
              }

              try {
                  String sql2 = "INSERT INTO chat_group_members (cgm_id, cg_name, user_email) VALUES (?, ?, ?)";
                  PreparedStatement st2 = con.prepareStatement(sql2);
                  st2.setString(1, String.valueOf(cgm_id));
                  st2.setString(2, result);
                  st2.setString(3, u.getEmail());
                  ResultSet rs2 = st2.executeQuery();

              } catch(Exception e) {
                      System.out.println(e);
              }

              try {
                  String sql2 = "DELETE FROM chat_group_invites WHERE group_chat_name = ? AND user_email = ?";
                  PreparedStatement st2 = con.prepareStatement(sql2);
                  st2.setString(1, result);
                  st2.setString(2, u.getEmail());
                  ResultSet rs2 = st2.executeQuery();

              } catch(Exception e) {
                      System.out.println(e);
              }



              System.out.println("--------------------------------------------------------------");
              System.out.println("Success! You have been added to ChatGroup: " + result);
              System.out.println("--------------------------------------------------------------");
              //viewChatGroups(u);
              return;


	     
	 }

	 if(input == 5) {
             return;
             //displayHomePage(user);
	 }
    }

    public void browseMessages() {

    }

    public void manageFriends(User u) {
	System.out.println("*************************");
	System.out.println("My Circle of Friends");
	System.out.println("*************************");
        System.out.println("1.) Search my friends");
        System.out.println("2.) View my private messages");
        System.out.println("3.) Send a private message");
        System.out.println("4.) Back to main menu");
	int input;
	do {
	     input = scanner.nextInt();
	     if(input != 1 && input != 2 && input != 3 && input != 4 && input != 5)
	       	System.out.println("Invalid selection");
	 } while (input != 1 && input != 2 && input != 3 && input != 4 && input != 5);

	 if(input == 1) {
            System.out.println("Enter the topic word you wish to search for: ");
            String word = scanner.next();
            System.out.println("******************************************************");
            System.out.println("                     SEARCH RESULTS                   ");
            System.out.println("******************************************************");

            try {
                String sql1 = "SELECT f.user1 FROM Friendships f INNER JOIN User_Topic_Words utw ON f.user1 = utw.userA WHERE utw.word = ? AND user2 = ?";
                PreparedStatement st = con.prepareStatement(sql1);
                st.setString(1, word);
                st.setString(2, u.getEmail());

                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    System.out.println(rs.getString(1));
                }
            } catch(Exception e) {
                    System.out.println(e);
            }

            try {
                String sql1 = "SELECT f.user2 FROM Friendships f INNER JOIN User_Topic_words utw ON f.user1 = utw.userA WHERE utw.word = ? AND f.user1 = ?";
                PreparedStatement st = con.prepareStatement(sql1);
                st.setString(1, word);
                st.setString(2, u.getEmail());

                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    System.out.println(rs.getString(1));
                }
            } catch(Exception e) {
                    System.out.println(e);
            }

            return;


	 }

	 if(input == 2) {

            System.out.println("Select an option: ");
            System.out.println("1. View messages I sent");
            System.out.println("2. View messages I received");
            int in;
            do {
                 in = scanner.nextInt();
                 if(in != 1 && in != 2)
                    System.out.println("Invalid selection");
             } while (in != 1 && in != 2);

             if(in == 1) {
                 ArrayList<PrivateMessages> messagesList = new ArrayList<PrivateMessages>();
                 int messNum = 0;
                 System.out.println("******************************************************");
                 System.out.println("        MY PRIVATE MESSAGES (that I sent)             ");
                 System.out.println("******************************************************");

                 try {
                     String sql2 = "SELECT * FROM direct_message WHERE sender = ?";
                     PreparedStatement st2 = con.prepareStatement(sql2);
                     st2.setString(1, u.getEmail());

                     ResultSet rs2 = st2.executeQuery();
                     while(rs2.next()) {
                         String mId = rs2.getString(1);
                         String time = rs2.getString(2);
                         String sender = rs2.getString(3);
                         String receiver = rs2.getString(4);
                         String text = rs2.getString(5);
                         PrivateMessages p = new PrivateMessages(mId, text, time, sender, receiver);
                         messagesList.add(p);
                         System.out.println("------------------------------------------------------------------");
                         System.out.println("Message Num: " + (messNum+1));
                         System.out.println("Sender: " + sender);
                         System.out.println("Sent on: " + time);
                         System.out.println("To: " + receiver);
                         System.out.println("");
                         System.out.println("" + text);
                         System.out.println("");
                         messNum++;

                     }

                 } catch(Exception e) {
                         System.out.println(e);
                 }

                 System.out.println("------------------------------------------------------------------");
                 System.out.println("*******************************************************************");
                  System.out.println("What would you like to do?");
                  System.out.println("*******************************************************************");
                  System.out.println("1. Delete a message");
                  System.out.println("2. Go back");
                  do {
                       in = scanner.nextInt();
                       if(in != 1 && in != 2)
                          System.out.println("Invalid selection");
                   } while (input != 1 && input != 2);
                   if(in == 1) {
                        System.out.println("Enter the Message number of the message you wish to delete:");
                        do {
                             input = scanner.nextInt();
                             if(input > messNum || input < 1)
                                System.out.println("Invalid selection");
                         } while (input > messNum || input < 1);
                        String mId = messagesList.get(input-1).getMId();
                        try {
                                    String sql1 = "UPDATE direct_message SET sender = '-1' WHERE dm_id = ?";
                                    PreparedStatement st = con.prepareStatement(sql1);
                                    st.setString(1, mId);
                                    ResultSet rs = st.executeQuery();

                                } catch(Exception e) {
                                        System.out.println(e);
                         }
                         System.out.println("--------------------------------------------------------------");
                         System.out.println("Success! You deleted your message!");
                         System.out.println("--------------------------------------------------------------");
                         return;

                   }
                   if(in == 2) {
                       return;
                   }


             }

             if(in == 2) {
                 ArrayList<PrivateMessages> messagesList = new ArrayList<PrivateMessages>();
                 int j = 0;
                 System.out.println("******************************************************");
                 System.out.println("        MY PRIVATE MESSAGES (that I received)             ");
                 System.out.println("******************************************************");

                 try {
                     String sql2 = "SELECT * FROM direct_message WHERE receiver = ?";
                     PreparedStatement st2 = con.prepareStatement(sql2);
                     st2.setString(1, u.getEmail());

                     ResultSet rs2 = st2.executeQuery();
                     while(rs2.next()) {
                         String mId = rs2.getString(1);
                         String time = rs2.getString(2);
                         String sender = rs2.getString(3);
                         String receiver = rs2.getString(4);
                         String text = rs2.getString(5);
                         PrivateMessages p = new PrivateMessages(mId, text, time, sender, receiver);
                         messagesList.add(p);
                         System.out.println("------------------------------------------------------------------");
                         System.out.println("Message Num: " + (j+1));
                         System.out.println("Sender: " + sender);
                         System.out.println("Sent on: " + time);
                         System.out.println("To: " + receiver);
                         System.out.println("");
                         System.out.println("" + text);
                         System.out.println("");
                         j++;

                     }

                 } catch(Exception e) {
                         System.out.println(e);
                 }

                 System.out.println("------------------------------------------------------------------");
                 System.out.println("*******************************************************************");
                  System.out.println("What would you like to do?");
                  System.out.println("*******************************************************************");
                  System.out.println("1. Delete a message");
                  System.out.println("2. Go back");
                  do {
                       in = scanner.nextInt();
                       if(in != 1 && in != 2)
                          System.out.println("Invalid selection");
                   } while (input != 1 && input != 2);
                   if(in == 1) {
                       System.out.println("Enter the Message number of the message you wish to delete:");
                       do {
                            input = scanner.nextInt();
                            if(input > j || input < 1)
                               System.out.println("Invalid selection");
                        } while (input > j || input < 1);
                       String mId = messagesList.get(input-1).getMId();
                       try {
                                   String sql1 = "UPDATE direct_message SET receiver = '-1' WHERE dm_id = ?";
                                   PreparedStatement st = con.prepareStatement(sql1);
                                   st.setString(1, mId);
                                   ResultSet rs = st.executeQuery();

                               } catch(Exception e) {
                                       System.out.println(e);
                        }
                        System.out.println("--------------------------------------------------------------");
                        System.out.println("Success! You deleted your message!");
                        System.out.println("--------------------------------------------------------------");
                        return;
                   }
                   if(in == 2) {
                       return;
                   }

             }





	 }
	
	 if(input == 3) {
             int dm_id = 0;
             System.out.println("Enter the text for your new direct message: ");
             scanner.nextLine();
             String text = scanner.nextLine();

             System.out.println("Enter the email of the friend you want to send this message to:");
             String friend = scanner.next();
             if(!(u.getFriendsList().contains(friend))) {
                 System.out.println("--------------------------------------------------------------------------------------");
                 System.out.println("Error! The email you entered is not a part of your friends list!");
                 System.out.println("--------------------------------------------------------------------------------------");
                 return;
             }

             try {
                 String sql6 = "SELECT MAX(dm_id) FROM direct_message";
                 PreparedStatement st6 = con.prepareStatement(sql6);
                 ResultSet rs6 = st6.executeQuery();
                 while(rs6.next()) {
                     dm_id = Integer.parseInt(rs6.getString(1));
                     dm_id = dm_id + 1;
                 }

             } catch(Exception e) {
                     System.out.println(e);
             }


             try {
                 String sql6 = "INSERT INTO direct_message (dm_id, timestamp, sender, receiver, text) VALUES (?, CURRENT_TIMESTAMP, ?,?,?)";
                 PreparedStatement st6 = con.prepareStatement(sql6);
                 st6.setString(1, String.valueOf(dm_id));
                 st6.setString(2, u.getEmail());
                 st6.setString(3, friend);
                 st6.setString(4, text);

                 ResultSet rs6 = st6.executeQuery();

             } catch(Exception e) {
                     System.out.println(e);
             }


             System.out.println("--------------------------------");
             System.out.println("Message successfully sent!");
             System.out.println("--------------------------------");
             return;
	 }

	 if(input == 4) {
             //displayHomePage(user);
             return;
	 }

	 if(input == 5) {
            return;
            //displayHomePage(user);
	 }
    }

    public void manageTopicWords() {

    }

    
}
