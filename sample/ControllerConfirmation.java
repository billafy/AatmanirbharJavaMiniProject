package sample;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ControllerConfirmation
{
    public void switchLogin(Event event) throws IOException
    {
        Parent login = FXMLLoader.load(getClass().getResource("../Resources/Login.fxml"));
        Scene l = new Scene(login);
        Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
        current.setScene(l);
        current.show();
    }
    public void switchDashboard(Event event) throws IOException
    {
        Parent dashboard = FXMLLoader.load(getClass().getResource("../Resources/Dashboard.fxml"));
        Scene d = new Scene(dashboard);
        Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
        current.setScene(d);
        current.show();
    }
}
