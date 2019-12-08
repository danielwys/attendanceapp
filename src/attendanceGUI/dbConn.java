package attendanceGUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author Daniel
 */
public class dbConn 
{
    Connection con;
    ResultSet rs;
    Statement stmt;
    
    public dbConn()
    {
        try
        {
            String host = "jdbc:derby://localhost:1527/attendance";
            String uName = "attendance";
            String uPass = "attendance";
            con = DriverManager.getConnection(host, uName, uPass); 
            
            stmt = con.createStatement(); 
            attendanceLogin.setServerStatus(true);
        }
        catch (SQLException err)
        {
            err.getMessage();
        }        
    }
    
    //Login function
    public boolean login(String name, String pwd)
    {
        try
        {                       
            boolean flag = false;
            
            String SQL = "SELECT PASS FROM ATTENDANCE.AUTH WHERE UNAME='"+name+"'";
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            
            String checkpwd = rs.getString("PASS");
                            
            if(pwd.equals(checkpwd))
            {
                flag = true;
            }

            return flag;

        }
        
        catch (SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
    }
        
    public boolean markAttendance(String barcode)
    {
        try
        {
            boolean flag=false;
            
            if((checkNRIC(barcode))==true)
            {
                java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            
                String sql = "INSERT INTO ATTENDANCE.ATTDTRANS (BARCODE, TIME) VALUES('"+barcode+"', '"+sqlDate+"')";
                stmt.executeUpdate(sql);
            
                System.out.println("Attendance marked!");
                
                flag=true;
            }
            
            else
            {
                flag=false;
            }
            
            return flag;
        }
                
        catch (SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
    }
    
    public boolean deleteAttendance(String barcode)
    {
        try
        {   
            Boolean flag=false;
            
            if((checkNRIC(barcode))==true)
            {
                java.sql.Timestamp sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            
                String sql = "DELETE FROM ATTENDANCE.ATTDTRANS WHERE BARCODE='"+barcode+"'";            
                stmt.executeUpdate(sql);
            
                System.out.println("Attendance deleted!"); 
                flag=true;
            }
            return flag;
        }
        
        catch (SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
    }
    
    public boolean addExcuse(String barcode, String excuse)
    {
        try
        {        
            boolean flag=false;
            
            if((checkNRIC(barcode))==true)
            {
                java.sql.Timestamp sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            
                String sql = "INSERT INTO ATTENDANCE.ATTDTRANS (BARCODE, TIME) VALUES('"+barcode+"', '"+sqlDate+"', '"+excuse+"')";
                stmt.executeUpdate(sql);
            
                System.out.println("Excuse Added");
                flag=true;
            }
            return true;
        }
        
        catch (SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
    }
    //NRIC Check Function
    public boolean checkNRIC (String NRIC)
    {
        char[] NRICCharArray = NRIC.toCharArray();
        
        int check = Character.getNumericValue(NRICCharArray[1])*2;
        int j=7;
        
        for (int i=2; i<8; i++)
        {
            check = check + (Character.getNumericValue(NRICCharArray[i])*j);
            j--;
        }
                
        int mod = check % 11;
        boolean flag=false;
                
        if (NRICCharArray[0]=='S' || NRICCharArray[0]=='T')
        {
            String[] checkLetter = {"J", "Z", "I", "H", "G", "F", "E", "D", "C", "B", "A"};
            if ((Character.toString(NRICCharArray[8])).equals(checkLetter[mod])==true)
            {
                flag = true;
            }
        }
        else if (NRICCharArray[0]=='F' || NRICCharArray[0]=='G')
        {
            String[] checkLetter = {"X", "W", "U", "T", "R", "Q", "P", "N", "M", "L", "K"};
            if ((Character.toString(NRICCharArray[8])).equals(checkLetter[mod])==true)
            {
                flag = true;
            }            
        }
        else
        {
            flag = false;
        }
        
        return flag;
    }
    

}
