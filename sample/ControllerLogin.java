package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
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
import sample.ControllerConfirmation;

public class ControllerLogin
{
    @FXML
    public JFXTextField usernameField;
    @FXML
    public JFXPasswordField passwordField;


    public void switchCreateAccount(MouseEvent mouseEvent) throws IOException
    {
        Parent createAccount = FXMLLoader.load(getClass().getResource("../Resources/CreateAccount.fxml"));
        Scene cA = new Scene(createAccount);
        Stage current = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        current.setScene(cA);
        current.show();
    }

    public void forgotPassword(MouseEvent mouseEvent)
    {

    }

    public void login(Event event) throws SQLException
    {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx?useTimezone=true&serverTimezone=UTC","root","Yash@welcome1");
        Statement stm;

        String username = usernameField.getText();
        String password = passwordField.getText();
        String sql = "SELECT * FROM User WHERE Username = '"+username+"' AND Password = '"+password+"'";
        try
        {
            stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            if(!resultSet.next())
            {
                infobox("Failed","Invalid Username And Password","Please re-enter.");
            }
            else
            {
                infobox("Login Successful","","Successfully logged in to your account");
                ControllerConfirmation cC = new ControllerConfirmation();
                cC.switchDashboard(event);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void infobox(String title, String header,String content)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
