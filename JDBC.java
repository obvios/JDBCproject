//Eric Palma
//Daniel Gione
//JDBC Project
package jdbc;
import java.sql.*;

public class JDBC {
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/JDBCdatabase";

    //  Database credentials
    static final String USER = "user1";
    static final String PASS = "pass1";
    
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        
        try{
            //STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            /*Display Menu*/
            displayMenu();

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                String id  = rs.getString("au_id");
                String phone = rs.getString("phone");
                String first = rs.getString("au_fname");
                String last = rs.getString("au_lname");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", First: " + first);
                System.out.print(", Last: " + last);
                System.out.println(", Phone: " + phone);
            }
            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
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
        
    }
    
}

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