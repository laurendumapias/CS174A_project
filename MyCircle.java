import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class MyCircle {

    public Scanner scanner = new Scanner(System.in);
    public Connection con;
    
    public int id;
    public ArrayList<MyCircleMessages> messagesList;

    public MyCircle(int id) {
        // id = databse thing where it grabs the last number
        id = id;
        messagesList = new ArrayList<MyCircleMessages>();
    }

    public void populateMessages(User user, Connection c) {
        // database stuff
        int postNum = 0;
        ArrayList<MyCircleMessages> postNumList = new ArrayList<MyCircleMessages>();
        messagesList.clear();
        ArrayList<Integer> messageIdList = new ArrayList<Integer>();
        con = c;
        try {
            String sql = "SELECT mcm.*, mctw.word FROM my_circle_messages mcm INNER JOIN my_circle_members mcme ON mcm.mid = mcme.message_id INNER JOIN my_circle_topic_words mctw ON mcm.mid = mctw.message_id WHERE mcme.user_email = ?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, user.getEmail());
            //System.out.println("" + user.getEmail());
            ResultSet rs = st.executeQuery();
            postNum = 0;
            while(rs.next()) {
                String mId = rs.getString(1);
                String time = rs.getString(2);
                String email = rs.getString(3);
                String text = rs.getString(4);
                String topic = rs.getString(5);

                int mIdInt = Integer.parseInt(mId);
                if(messageIdList.contains(mIdInt)) {
                    messagesList.get(postNum-1).getTopicWords().add(topic);
                }
                else {
                    messageIdList.add(mIdInt);
                    ArrayList<String> topicWords = new ArrayList<String>();
                    topicWords.add(topic);
                    MyCircleMessages message = new MyCircleMessages(email, time, text, topicWords, mId);
                    messagesList.add(message);
                    postNumList.add(message);
                    postNum++;
                }

                //System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5));

            }

            for(int j = 0; j < messagesList.size(); j++) {
                System.out.println("------------------------------------------------------------------");
                System.out.println("Post Num: " + (j+1));
                System.out.println("Sender: " + messagesList.get(j).getSender());
                System.out.println("Posted on: " + messagesList.get(j).getTimestamp());
                System.out.println("Topic(s): " + messagesList.get(j).getTopicWordsToString());
                System.out.println("");
                System.out.println("" + messagesList.get(j).getText());
                System.out.println("");
            }


        } catch(Exception e) {
                System.out.println(e);
        }


	System.out.println("------------------------------------------------------------------");
        System.out.println("******************************************************************");
        System.out.println("What would you like to do?");
        System.out.println("******************************************************************");
        System.out.println("1.) View more messages");
	System.out.println("2.) Delete a message");
        System.out.println("3.) Back to myCircle menu");

        int input;

        do {
             input = scanner.nextInt();
             if(input != 1 && input != 2 && input != 3)
                System.out.println("Invalid selection");
         } while (input != 1 && input != 2 && input != 3);

         if(input == 1) {
             // do some sql
         }

         if(input == 2) {
                System.out.println("Enter the post number for the message you wish to delete: ");
                do {
                     input = scanner.nextInt();
                     if(input > postNum || input < 1)
                        System.out.println("Invalid selection");
                 } while (input > postNum || input < 1);

                 if(!messagesList.get(input-1).getSender().equals(user.getEmail())) {
                    System.out.println("Delete failed! You can only delete posts you have authored!");
                    return;
                 }
                 else {
                     String mId = messagesList.get(input-1).getMId();
                     try {
                                 String sql1 = "DELETE FROM my_circle_messages WHERE mid = ?";
                                 PreparedStatement st = con.prepareStatement(sql1);
                                 st.setString(1, mId);
                                 ResultSet rs = st.executeQuery();

                             } catch(Exception e) {
                                     //System.out.println(e);
                      }

                      try {
                                  String sql1 = "DELETE FROM my_circle_members WHERE message_id = ?";
                                  PreparedStatement st = con.prepareStatement(sql1);
                                  st.setString(1, mId);
                                  ResultSet rs = st.executeQuery();

                              } catch(Exception e) {
                                      //System.out.println(e);
                              }

                       try {
                                   String sql1 = "DELETE FROM my_circle_topic_words WHERE message_id = ?";
                                   PreparedStatement st = con.prepareStatement(sql1);
                                   st.setString(1, mId);
                                   ResultSet rs = st.executeQuery();

                               } catch(Exception e) {
                                       //System.out.println(e);
                               }


                        System.out.println("Success! You deleted your post!");
                        return;
                 }

         }

         if(input == 3) {
             // do some sql
             scanner.nextLine();
             return;
         }


	
	
    }

    public void searchMessages(User u, Connection c, ArrayList<String> tWords) {
        con = c;

        // database stuff
        System.out.println("Enter the topic word you want to search: ");
        String topicWord = scanner.next();
        tWords.add(topicWord);
        System.out.println("Do you want to add another topic word to search with?");
        System.out.println("1.) Yes");
        System.out.println("2.) No");
        int in;
        do {
             in = scanner.nextInt();
             if(in != 1 && in != 2)
             System.out.println("Invalid selection");
         } while (in != 1 && in != 2);

        if(in == 1) {
            searchMessages(u, c, tWords);
        }

        if(in == 2) {

            System.out.println("**********************************************************************");
            System.out.println("                            SEARCH RESULTS                            ");
            System.out.println("**********************************************************************");


            try {
                        String sql1 = "SELECT  mcm.timestamp, mcm.sender, mcm.text FROM my_circle_messages mcm INNER JOIN my_circle_topic_words mctw ON mcm.mid =  mctw.message_id WHERE word = ?";
                        PreparedStatement st = con.prepareStatement(sql1);
                        st.setString(1, topicWord);
                        ResultSet rs = st.executeQuery();
                        while(rs.next()) {
                            String time = rs.getString(1);
                            String sender = rs.getString(2);
                            String text = rs.getString(3);
                            //System.out.println(s + " " + s2 + " " + s3);
                            System.out.println("------------------------------------------------------------------");
                            System.out.println("Sender: " + sender);
                            System.out.println("Posted on: " + time);
                            System.out.println("Topic(s): " + topicWord);
                            System.out.println("");
                            System.out.println("" + text);
                            System.out.println("");


                        }
                    } catch(Exception e) {
                            System.out.println(e);
                    }
             System.out.println("------------------------------------------------------------------");
             return;

        }




    }


    public void postMyCircleMessage(User user, Connection c) {
        con = c;
        System.out.println("Enter the text for the message:");
        String messageText = scanner.nextLine();
	ArrayList<String> words = new ArrayList<>();
        words = addMessageTopicWords(words);
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
             queryToInsertMyCircleMessage(user, messageText, words);
	     System.out.println("---------------------------------");
	     System.out.println("Message successfully posted!");
             System.out.println("---------------------------------");

	     return;
	 }

	 if(input == 2) {
	     ArrayList<String> names = new ArrayList<>();
             addMessageReceivers(user, messageText, words, names);
	 }
	
    }

    public void queryToInsertMyCircleMessage(User u, String message, ArrayList<String> topicWords) {
        int message_id = 0, mcm_id = 0, wid = 0;
        try {
            String sql1 = "SELECT MAX(mid) FROM my_circle_messages";
            PreparedStatement st = con.prepareStatement(sql1);
            //System.out.println("" + user.getEmail());
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                message_id = Integer.parseInt(rs.getString(1));
                message_id = message_id + 1;
                System.out.println("MAX message_id: " + rs.getString(1));
            }
        } catch(Exception e) {
                System.out.println(e);
        }

        try {
            String sql2 = "INSERT INTO my_circle_messages (mid, timestamp, sender, text) VALUES (?, CURRENT_TIMESTAMP, ?, ?)";
            PreparedStatement st2 = con.prepareStatement(sql2);
            st2.setString(1, String.valueOf(message_id));
            st2.setString(2, u.getEmail());
            st2.setString(3, message);
            ResultSet rs2 = st2.executeQuery();
        } catch(Exception e) {
                System.out.println(e);
        }

        try {
            String sql3 = "SELECT MAX(mcm_id) FROM my_circle_members";
            PreparedStatement st3 = con.prepareStatement(sql3);
            ResultSet rs3 = st3.executeQuery();
            while(rs3.next()) {
                mcm_id = Integer.parseInt(rs3.getString(1));
                mcm_id = mcm_id + 2;
                System.out.println("MAX mcm_id: " + rs3.getString(1));
            }
        } catch(Exception e) {
                System.out.println(e);
        }

        System.out.println("SIZE OF MY FRIEND LIST: " + u.getFriendsList().size());

        for(int i = 0; i < u.getFriendsList().size(); i++) {

            try {
                String sql4 = "INSERT INTO my_circle_members (mcm_id, message_id, user_email) VALUES (?, ?, ?)";
                PreparedStatement st4 = con.prepareStatement(sql4);
                st4.setString(1, String.valueOf(mcm_id));
                st4.setString(2, String.valueOf(message_id));
                st4.setString(3, u.getFriendsList().get(i));
                ResultSet rs4 = st4.executeQuery();
                mcm_id++;
            } catch(Exception e) {
                    System.out.println(e);
            }
        }

        try {
            String sql5 = "SELECT MAX(wid) FROM my_circle_topic_words";
            PreparedStatement st5 = con.prepareStatement(sql5);
            ResultSet rs5 = st5.executeQuery();
            while(rs5.next()) {
                wid = Integer.parseInt(rs5.getString(1));
                wid = wid + 1;
                System.out.println("MAX wid: " + rs5.getString(1));
            }
        } catch(Exception e) {
                System.out.println(e);
        }

        for(int j = 0; j < topicWords.size(); j++) {

            try {
                String sql6 = "INSERT INTO my_circle_topic_words (wid, word, message_id) VALUES (?, ?, ?)";
                PreparedStatement st6 = con.prepareStatement(sql6);
                st6.setString(1, String.valueOf(wid));
                st6.setString(2, topicWords.get(j));
                st6.setString(3, String.valueOf(message_id));
                ResultSet rs6 = st6.executeQuery();
                wid++;
            } catch(Exception e) {
                    System.out.println(e);
            }

        }


    }

    public void addMessageReceivers(User u, String message, ArrayList<String> topicWords, ArrayList<String> names) {
        System.out.println("Enter the email of who you want to see the message:");
        String friend = scanner.next();

        if(!(u.getFriendsList().contains(friend))) {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Error! The email you entered is not a part of your friends list!");
            System.out.println("--------------------------------------------------------------------------------------");
            return;
        }

        names.add(friend);
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
            addMessageReceivers(u, message, topicWords, names);
	}

	if(in == 2) {

            queryToInsertMyCircleMessageToSpecific(u, message, topicWords, names);

	    System.out.println("--------------------------------");
	    System.out.println("Message successfully sent!");
	    System.out.println("--------------------------------");
	    return;
	}
    }

     public ArrayList<String> addMessageTopicWords(ArrayList<String> words) {

        System.out.println("Enter a topic word to be associated with your message:");

        String s = scanner.nextLine();
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
        scanner.nextLine();

	if(in == 1) {
	    addMessageTopicWords(words);
	}

	if(in == 2) {
            return words;
	}

        return words;
    }

    public void queryToInsertMyCircleMessageToSpecific(User u, String message, ArrayList<String> topicWords, ArrayList<String> names) {
        int message_id = 0, mcm_id = 0, wid = 0;
        names.add(u.getEmail());
        try {
            String sql1 = "SELECT MAX(mid) FROM my_circle_messages";
            PreparedStatement st = con.prepareStatement(sql1);
            //System.out.println("" + user.getEmail());
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                message_id = Integer.parseInt(rs.getString(1));
                message_id = message_id + 1;
                System.out.println("MAX message_id: " + rs.getString(1));
            }
        } catch(Exception e) {
                System.out.println(e);
        }

        try {
            String sql2 = "INSERT INTO my_circle_messages (mid, timestamp, sender, text) VALUES (?, CURRENT_TIMESTAMP, ?, ?)";
            PreparedStatement st2 = con.prepareStatement(sql2);
            st2.setString(1, String.valueOf(message_id));
            st2.setString(2, u.getEmail());
            st2.setString(3, message);
            ResultSet rs2 = st2.executeQuery();
        } catch(Exception e) {
                System.out.println(e);
        }

        try {
            String sql3 = "SELECT MAX(mcm_id) FROM my_circle_members";
            PreparedStatement st3 = con.prepareStatement(sql3);
            ResultSet rs3 = st3.executeQuery();
            while(rs3.next()) {
                mcm_id = Integer.parseInt(rs3.getString(1));
                mcm_id = mcm_id + 2;
                System.out.println("MAX mcm_id: " + rs3.getString(1));
            }
        } catch(Exception e) {
                System.out.println(e);
        }


        for(int i = 0; i < names.size(); i++) {

            try {
                String sql4 = "INSERT INTO my_circle_members (mcm_id, message_id, user_email) VALUES (?, ?, ?)";
                PreparedStatement st4 = con.prepareStatement(sql4);
                st4.setString(1, String.valueOf(mcm_id));
                st4.setString(2, String.valueOf(message_id));
                st4.setString(3, names.get(i));
                ResultSet rs4 = st4.executeQuery();
                mcm_id++;
            } catch(Exception e) {
                    System.out.println(e);
            }
        }

        try {
            String sql5 = "SELECT MAX(wid) FROM my_circle_topic_words";
            PreparedStatement st5 = con.prepareStatement(sql5);
            ResultSet rs5 = st5.executeQuery();
            while(rs5.next()) {
                wid = Integer.parseInt(rs5.getString(1));
                wid = wid + 1;
                System.out.println("MAX wid: " + rs5.getString(1));
            }
        } catch(Exception e) {
                System.out.println(e);
        }

        for(int j = 0; j < topicWords.size(); j++) {

            try {
                String sql6 = "INSERT INTO my_circle_topic_words (wid, word, message_id) VALUES (?, ?, ?)";
                PreparedStatement st6 = con.prepareStatement(sql6);
                st6.setString(1, String.valueOf(wid));
                st6.setString(2, topicWords.get(j));
                st6.setString(3, String.valueOf(message_id));
                ResultSet rs6 = st6.executeQuery();
                wid++;
            } catch(Exception e) {
                    System.out.println(e);
            }

        }


    }

}
