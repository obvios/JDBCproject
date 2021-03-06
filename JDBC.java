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

    public static void main(String[] args) throws IOException {

        try{
            //STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            /*Display Menu*/
            displayMenu();
            
            /*Main Loop*/
            int option;
            String option1 = reader.readLine();
            if(Character.isDigit(option1.charAt(0))){
                option = Integer.parseInt(option1);
            }else{
                option = 10;
            }
            
            
            while(option !=0){
                switch(option){
                    case 1: listAllWritingGroups();
                    break;
                    case 2 : listWritingGroup();
                    break;
                    case 3 : listAllPublishers();
                    break;
                    case 4 : listPublisher();
                    break;
                    case 5 : listAllBooks();
                    break;
                    case 6 : listBook();
                    break;
                    case 7 : insertBook();
                    break;
                    case 8 : insertAndUpdatePub();
                    break;
                    case 9 : removeBook();
                    listAllBooks();
                    break;
                    default : System.out.println("Not an option");
                    break;
                }
                
                
                displayMenu();
                option1 = reader.readLine();
                if(Character.isDigit(option1.charAt(0))){
                    option = Integer.parseInt(option1);
                }else{
                    option = 10;
                }
            }
            
            //STEP 5: Clean-up environment
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
    System.out.print("Enter number or 0 to exit: ");
}

/*Option 1*/
public static void listAllWritingGroups(){
    try{
        String sql = "SELECT GroupName FROM WritingGroup";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        while(rs.next())
        {
            System.out.println(rs.getString("GroupName"));
        }
        /*Close*/
        rs.close();
        stmt.close();
        
    }
    catch(SQLException ex)
    {
        Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE,null,ex);
    }
}

/*Option 2*/
public static void listWritingGroup() throws IOException{
    try{
        String sql = "SELECT * FROM WritingGroup natural join publisher natural join book WHERE GroupName = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        System.out.println("Which writing group would you like to know more about?");
        String writingGroup = reader.readLine();
        pstmt.setString(1, writingGroup);
        
        ResultSet rs = pstmt.executeQuery();
        
            if(rs.next()){
            String gName = rs.getString("GroupName");
            String headWriter = rs.getString("HeadWriter");
            String year = rs.getString("YearFormed");
            String subject = rs.getString("Subject");
            
            String bookTitle = rs.getString("BookTitle") + ", ";
            String yearWritten = rs.getString("YearPublished");
            String numberPages = rs.getString("NumberPages");
            
            String pubName = rs.getString("PublisherName");
            String pubAddress= rs.getString("PublisherAddress");
            String pubPhone = rs.getString("PublisherPhone");
            String pubEmail = rs.getString("PublisherEmail");
            
            System.out.println("Writing Group: " + gName + ", Head Writer: " + headWriter + ", Year Formed: " + year +", Subject: " + subject);
            System.out.println("Book Title: " + bookTitle + ", Year Published: " + yearWritten + ", Number of Pages: " + numberPages + ", Written by: " + gName);
            System.out.println("Publisher: " + pubName + ", Publisher Address: " + pubAddress + ", Publisher Phone: " + pubPhone + ", Publisher Email: " + pubEmail);

            }
            while(rs.next())
            {
            String bookTitle = rs.getString("BookTitle") + ", ";
            String yearWritten = rs.getString("YearPublished");
            String numberPages = rs.getString("NumberPages");
            
            String pubName = rs.getString("PublisherName");
            String pubAddress= rs.getString("PublisherAddress");
            String pubPhone = rs.getString("PublisherPhone");
            String pubEmail = rs.getString("PublisherEmail");
            
            System.out.println("Book Title: " + bookTitle + ", Year Published: " + yearWritten + ", Number of Pages: " + numberPages);
            System.out.println("Publisher: " + pubName + ", Publisher Address: " + pubAddress + ", Publisher Phone: " + pubPhone + ", Publisher Email: " + pubEmail);
            }
            
           
          
            
        
        /*Close*/
        rs.close();
        pstmt.close();
        
        
    }
    catch(SQLException ex)
    {
        Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE,null, ex);
    }
    
}


/*Option 3*/
public static void listAllPublishers(){
    try{
        String sq1 = "SELECT PublisherName FROM Publisher";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sq1);
        
        while(rs.next())
        {
            System.out.println(rs.getString("PublisherName"));
        }
        /*Close*/
        rs.close();
        stmt.close();
        
    }
    catch(SQLException ex)
    {
        Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE,null, ex);
    }
    
}

/*Option 4*/
public static void listPublisher() throws IOException{
    try{
        String sql = "Select * from publisher natural join WritingGroup natural join book where PublisherName = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        System.out.println("What publisher would you like to learn more about?");
        String publisher = reader.readLine();
        pstmt.setString(1, publisher);
        
        ResultSet rs = pstmt.executeQuery();
        
        if(rs.next())
        {
        String pubName = rs.getString("PublisherName");
        String address = rs.getString("PublisherAddress");
        String phone = rs.getString("PublisherPhone");
        String email = rs.getString("PublisherEmail");
        
        String groupName = rs.getString("GroupName");
        String headWriter = rs.getString("HeadWriter");
        String year = rs.getString("YearFormed");
        String subject = rs.getString("Subject");
        
        String bookTitle = rs.getString("BookTitle");
        String yearWritten = rs.getString("YearPublished");
        String numberPages = rs.getString("NumberPages");
        
        System.out.println("Publisher: " + pubName + ", Publisher Address: " + address + ", Publisher Phone: " + phone + ", Publisher Email: " + email);
        System.out.println("Book Title: " + bookTitle + ", Year Published: " + yearWritten + ", Number of Pages: " + numberPages + ", Written by: " + groupName);
        System.out.println("Writing Group: " + groupName + ", Head Writer: " + headWriter + ", Year Formed: " + year +", Subject: " + subject);
        }
       
        
        
        while(rs.next())
        {
        
        
        String groupName = rs.getString("GroupName");
        String headWriter = rs.getString("HeadWriter");
        String year = rs.getString("YearFormed");
        String subject = rs.getString("Subject");
        
        String bookTitle = rs.getString("BookTitle");
        String yearWritten = rs.getString("YearPublished");
        String numberPages = rs.getString("NumberPages");
        
        System.out.println("Book Title: " + bookTitle + ", Year Published: " + yearWritten + ", Number of Pages: " + numberPages + ", Written by: " + groupName);
        System.out.println("Writing Group: " + groupName + ", Head Writer: " + headWriter + ", Year Formed: " + year +", Subject: " + subject);
        
        }
        /*Close*/
        rs.close();
        pstmt.close();
       
        
        
    }
    catch(SQLException ex)
    {
        Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE,null, ex);
    }
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
            System.out.print("Pages: ");
            String pages = reader.readLine();
            System.out.println();
      
            //check that writing group exists
            String checkSQL = "select groupname from writinggroup where groupname = ?";
            PreparedStatement checkPstmt = conn.prepareStatement(checkSQL);
            checkPstmt.setString(1, group);
            ResultSet checkRS = checkPstmt.executeQuery();
            if(!checkRS.next()){         //writing group doesnt exist so add it
                System.out.println("WARNING: writing group doesn't exist");
                return;
            }
            checkPstmt.clearParameters();
            
            //check that publisher exists
            checkSQL = "select publishername from publisher where publishername = ?";
            checkPstmt = conn.prepareStatement(checkSQL);
            checkPstmt.setString(1, publisher);
            checkRS = checkPstmt.executeQuery();
            if(!checkRS.next()){         //publisher doesnt exist so add it
                System.out.println("WARNING: publisher doensn't exist");
                return;
            }
            
            /*Check if writing group and title already exist*/
            checkSQL = "SELECT * FROM book WHERE booktitle = ? AND groupname = ?";
            checkPstmt = conn.prepareStatement(checkSQL);
            checkPstmt.setString(1, title);
            checkPstmt.setString(2, group);
            checkRS = checkPstmt.executeQuery();
            if(checkRS.next()){         //book and group already exist
                System.out.println("WARNING: book already exists");
                return;
            }
            
            //insert book
            pstmt.setString(1, group);
            pstmt.setString(2, publisher);
            pstmt.setString(3, title);
            pstmt.setString(4, year);
            pstmt.setString(5, pages);
            
            pstmt.executeUpdate();
            
            /*close*/
            pstmt.close();
            checkPstmt.close();
            checkRS.close();
        }catch(SQLException ex){
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null,ex);
        }
    }
    
    /*Option 8*/
    public static void insertAndUpdatePub() throws IOException {
        try{
            //get info from user
            System.out.print("Enter new Publisher name: ");
            String newPub = reader.readLine();
            System.out.print("Enter new Publisher address: ");
            String newAdd = reader.readLine();
            System.out.print("Enter new Publisher phone: ");
            String newPhone = reader.readLine();
            System.out.print("Enter new Publisher email: ");
            String newEmail = reader.readLine();
            System.out.print("Enter old Publisher name: ");
            String oldPub = reader.readLine();
            
            //Insert new publisher
            String sql = "INSERT INTO publisher (publishername,publisheraddress,publisherphone,publisheremail) VALUES "
                    + "(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPub);
            pstmt.setString(2, newAdd);
            pstmt.setString(3, newPhone);
            pstmt.setString(4, newEmail);
            pstmt.executeUpdate();
            
            //check if old publisher exists
            String checkSQL = "select publishername from publisher where publishername = ?";
            PreparedStatement checkPstmt = conn.prepareStatement(checkSQL);
            checkPstmt.setString(1, oldPub);
            ResultSet checkRS = checkPstmt.executeQuery();
            if(!checkRS.next()){         //old publisher doesn't exist
                System.out.println("WARNING: old publisher invalid");
                return;
            }
            
            //update and change ownership
            sql = "UPDATE book SET publishername = ? where publishername = ?";
            pstmt.clearParameters();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPub);
            pstmt.setString(2, oldPub);
            pstmt.executeUpdate();
            pstmt.clearParameters();
            
            //print all info of books whose ownership changed
            sql = "select * from book where publishername = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPub);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String group = rs.getString("groupname");
                String publisher = rs.getString("publishername");
                String title = rs.getString("booktitle");
                String yr = rs.getString("yearpublished");
                String pages = rs.getString("numberpages");
                
                System.out.println("Group: " + group + ", Publisher: " + publisher + ", Title: "
                + title + " Year: " + yr + ", Pages: " + pages);
            }
            
            /*close*/
            pstmt.close();
            rs.close();
            checkRS.close();
            checkPstmt.close();
        }catch(SQLException ex){
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null,ex);
        }
    }

    /*option 9*/
    public static void removeBook() throws IOException{
        try{
            //get book info from user
            String sql = "DELETE FROM book WHERE booktitle = ? AND groupname = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            System.out.print("Enter Book title: ");
            String title = reader.readLine();
            System.out.print("Enter Group name: ");
            String group = reader.readLine();
            
            //execute update
            pstmt.setString(1, title);
            pstmt.setString(2, group);
            pstmt.executeUpdate();
            
            //close
            pstmt.close();
        }catch (SQLException ex){
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null,ex);
        }
    }
//first 4 : daniel
//last 5 : eric

}//End of program
