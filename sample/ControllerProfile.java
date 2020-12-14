package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerProfile extends ControllerLogin
{

    @FXML
    public JFXButton login;
    @FXML
    public JFXButton createAccount;
    @FXML
    public Label newUser;
    @FXML
    public Label usernameLabel;
    public void switchDashboard(Event event) throws IOException
    {
        Parent dashboard = FXMLLoader.load(getClass().getResource("../Resources/Dashboard.fxml"));
        Scene d = new Scene(dashboard);
        Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
        current.setScene(d);
    }
    public void switchLogin(Event event) throws IOException
    {
        Parent dashboard = FXMLLoader.load(getClass().getResource("../Resources/Login.fxml"));
        Scene l = new Scene(dashboard);
        Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
        current.setScene(l);
    }
    public void logOut(Event event) throws IOException
    {
        usernameLabel.setText("");
        login.setVisible(true);
        createAccount.setVisible(true);
        newUser.setVisible(true);
        switchDashboard(event);
    }
    public void closeApp(Event event)
    {
        Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
        current.close();
    }
}
