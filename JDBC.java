//Eric Palma
//Daniel Gione
//JDBC Project
package jdbc;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
public class JDBC {
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/JDBCdatabase";

    //  Database credentials
    static final String USER = "user1";
    static final String PASS = "pass1";
    //connections
    static Connection conn = null;
    static Statement stmt = null;
    //buffered reader
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {

        try{
            //STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            /*Display Menu*/
            displayMenu();
                        
            //STEP 5: Clean-up environment
           // rs.close();
           //stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                stmt.close();
            }catch(SQLException se2){}

            try{
                if(conn!=null)
                conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

    }//end of main

    /*Display Menu*/
static void displayMenu(){
    System.out.println("Please choose one of the following:");
    System.out.println("1: List all writing groups");
    System.out.println("2: List specific writing group data");
    System.out.println("3: List all publishers");
    System.out.println("4: List specific publisher data");
    System.out.println("5: List all book titles");
    System.out.println("6: List data about a specific book");
    System.out.println("7: Insert a new book into database");
    System.out.println("8: Insert new publisher and change ownership");
    System.out.println("9: Remove a specific book");
}

/*option 5*/
public static void listAllBooks(){

        try {
            String sql = "SELECT bookTitle FROM book";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
        	    String name = rs.getString("bookTitle");
        	    System.out.println(name);
            }
            /*Close*/
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

}

/*option 6*/
    public static void listBook() throws IOException{
        try {
            String sql = "Select * from book natural join writinggroup natural join publisher where booktitle = ? and "
                    + "groupname = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            System.out.println("Which book would you like more information on? ");
            String book = reader.readLine();
            System.out.print("Specify Writing group name: ");
            String groupName = reader.readLine();
            
            pstmt.setString(1, book);
            pstmt.setString(2, groupName);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
        	    String name = rs.getString("bookTitle");
                    String year = rs.getString("yearpublished");
                    String pages = rs.getString("numberpages");
                    
                    String group = rs.getString("groupname");
                    String subj = rs.getString("subject");
                    String headWriter = rs.getString("headwriter");
                    String yrFormed = rs.getString("yearformed");
                    
                    String publisher = rs.getString("publishername");
                    String pubAdd = rs.getString("publisheraddress");
                    String pubPhone = rs.getString("publisherphone");
                    String pubEmail = rs.getString("publisheremail");
                    
        	    System.out.println("Title: " + name + ", Year: " + year + ", Pages: " + pages + 
                            "\nGroup: " + group + ", Subject: " + subj + ", Head Writer: " + headWriter + ", Year Formed: " + yrFormed +
                            "\nPublisher: " + publisher + ", Address: " + pubAdd + ", Phone: " + pubPhone + ", Email: " + pubEmail);
            }
            /*Close*/
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /*Option 7*/
    public static void insertBook() throws IOException {
        try{
            String sql = "Insert into book(groupname,publishername,booktitle,yearpublished,numberpages) "
                    + "values (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            //get info from user
            System.out.print("Enter Group name: ");
            String group = reader.readLine();
            System.out.println();
            System.out.print("Enter Publisher name: ");
            String publisher = reader.readLine();
            System.out.println();
            System.out.print("Book Title: ");
            String title = reader.readLine();
            System.out.println();
            System.out.print("Year: ");
            String year = reader.readLine();
            System.out.println("Pages: ");
            String pages = reader.readLine();
      
            
            //check that publisher exists
            String checkSQL = "select publishername from publisher where publishername = ?";
            PreparedStatement checkPstmt = conn.prepareStatement(checkSQL);
            ResultSet checkRS = checkPstmt.executeQuery();
            if(checkRS.getString("publishername") == null){         //publisher doesnt exist so add it
                
            }
            checkPstmt.clearParameters();
            
            //check that writing group exists
            checkSQL = "select groupname from writinggroup where groupname = ?";
            checkPstmt = conn.prepareStatement(checkSQL);
            checkRS = checkPstmt.executeQuery();
            if(checkRS.getString("groupname") == null){         //writing group doesnt exist so add it
                
            }
            
            
            //insert book
            pstmt.setString(1, group);
            pstmt.setString(2, publisher);
            pstmt.setString(3, title);
            pstmt.setString(4, year);
            pstmt.setString(5, pages);
            
            pstmt.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null,ex);
        }
    }

//first 4 : daniel
//last 5 : eric

}//End of program
