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

            //test1
            
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
            String sql = "Select booktitle,yearpublished,numberpages,groupname,publishername from book where booktitle = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            System.out.println("Which book would you like more information on? ");
            String book = reader.readLine();
            
            pstmt.setString(1, book);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
        	    String name = rs.getString("bookTitle");
                    String year = rs.getString("yearpublished");
                    String pages = rs.getString("numberpages");
                    String group = rs.getString("groupname");
                    String publisher = rs.getString("publishername");
                    
        	    System.out.print("Name: " + name);
                    System.out.print(", Year: "+year);
                    System.out.print(", Pages: "+pages);
                    System.out.print(", Group: "+group);
                    System.out.println(", Publisher: "+publisher);
            }
            /*Close*/
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


//first 4 : daniel
//last 5 : eric

}//End of program
