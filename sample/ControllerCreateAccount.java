package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class ControllerCreateAccount
{
    @FXML
    public JFXTextField usernameField;
    @FXML
    public JFXTextField emailIdorPhoneField;
    @FXML
    public JFXPasswordField passwordField;
    public Connection conn;
    public Statement st;

    public void signUp(MouseEvent mouseEvent) throws SQLException, IOException
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx?useTimezone=true&serverTimezone=UTC","root","Yash@welcome1");

        String username = usernameField.getText(); // fetching all the user input values
        String eOp = emailIdorPhoneField.getText();
        String password = passwordField.getText();
        if(checkEmpty(username,eOp,password)) // if they are empty show an alert
        {
            infobox("Failed","Incomplete Details","Please fill up all the columns");
            return;
        }
        if(!usernamePasswordValidation(username,password)) // validate username and password
        {
            infobox("Failed","Invalid Username or Password","Username should contain at least 1 alphabetical character and must be of 6 to 14 length.\nPassword should contain 6 to 14 characters.");
            return;
        }

        String emailID = ""; // differentiating eOp to emailID and phoneNumber
        long phone = 0;
        try
        {
            phone = Long.parseLong(eOp);
            if(!phoneNumberValidation(eOp)) // checking for validation and showing alert if it is invalid
            {
                infobox("Failed","Invalid Phone Number","Please re-enter");
                return;
            }
            st = conn.createStatement();
            String sql1 = "SELECT * FROM User WHERE Username = '"+username+"'";
            String sql2 = "SELECT * FROM User WHERE PhoneNumber = '"+eOp+"'";
            ResultSet resultSet1 = st.executeQuery(sql1);
            if(resultSet1.next())
            {
                infobox("Failed","Username Already Exists","Please try a new one or try to login with it.");
                return;
            }
            ResultSet resultSet2 = st.executeQuery(sql2);
            if(resultSet2.next())
            {
                infobox("Failed","PhoneNumber Already Exists","Try logging in with the registered phone number.");
                return;
            }
        }
        catch (Exception e)
        {
            emailID = eOp;
            if(!emailAddressValidation(eOp))
            {
                infobox("Failed","Invalid Email Address","Please re-enter");
                return;
            }
            Statement stm = conn.createStatement();
            String sql1 = "SELECT * FROM User WHERE Username = '"+username+"'";
            String sql2 = "SELECT * FROM User WHERE EmailAddress = '"+eOp+"'";
            ResultSet resultSet1 = stm.executeQuery(sql1);
            if(resultSet1.next())
            {
                infobox("Failed","Username Already Exists","Please try a new one or try to login with it.");
                return;
            }
            ResultSet resultSet2 = stm.executeQuery(sql2);
            if(resultSet2.next())
            {
                infobox("Failed","Email Address Already Exists","Try logging in with the registered email address");
                return;
            }
        }

        String sql; // firing the sql statement
        if(phone==0)
        {
            sql = "INSERT INTO User(Username,Password,EmailAddress) VALUES('"+username+"','"+password+"','"+emailID+"')";
        }
        else
        {
            sql = "INSERT INTO User(Username,Password,PhoneNumber) VALUES('"+username+"','"+password+"',"+phone+")";
        }
        st = conn.createStatement();
        st.executeUpdate(sql);
        infobox("Success","Registered Successfully","Please login again with your freshly registered credentials.");
        Parent confirmation = FXMLLoader.load(getClass().getResource("../Resources/Confirmation.fxml"));
        Scene cNF = new Scene(confirmation);
        Stage current = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        current.setScene(cNF);
        current.show();
    }

    private void infobox(String title, String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean emailAddressValidation(String eOp)
    {
        if(eOp.charAt(0)=='@' || eOp.charAt(eOp.length()-1)=='@' || eOp.charAt(eOp.length()-1)=='.' || eOp.charAt(0)=='.')
            return false;
        int apos = eOp.indexOf('@');
        int dpos = eOp.indexOf('.');
        return apos != -1 && dpos != -1 && apos <= dpos;
    }

    private boolean phoneNumberValidation(String eOp)
    {
        return eOp.length() == 10;
    }

    private boolean usernamePasswordValidation(String username, String password)
    {
        int flag = 0;
        try
        {
            long u = Long.parseLong(username);
        }
        catch(Exception e)
        {
            flag = 1;
        }
        return username.length() >= 6 && username.length() <= 14 && password.length() >= 6 && password.length() <= 14 && flag==1;
    }

    private boolean checkEmpty(String username, String eOp, String password)
    {
        return username.length() == 0 || eOp.length() == 0 || password.length() == 0;
    }

    public void switchLogin(MouseEvent mouseEvent) throws IOException
    {
        Parent login = FXMLLoader.load(getClass().getResource("../Resources/Login.fxml"));
        Scene lA = new Scene(login);
        Stage current = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        current.setScene(lA);
        current.show();
    }
}
