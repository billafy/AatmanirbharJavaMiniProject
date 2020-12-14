package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerDashboard extends ControllerProfile
{
    public void switchProfile(MouseEvent event) throws IOException
    {
        Parent profile = FXMLLoader.load(getClass().getResource("../Resources/Profile.fxml"));
        Scene p = new Scene(profile);
        Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
        current.setScene(p);

    }
    public void switchMultipleProduct(Event event) throws IOException
    {
        JFXButton button = (JFXButton)event.getSource();
        String buttonName = "";
        buttonName += button.getId().charAt(0);
        buttonName = buttonName.toUpperCase();
        buttonName += button.getId().substring(1) + "View";
        Parent multipleProduct = FXMLLoader.load(getClass().getResource("../Resources/"+buttonName+".fxml"));
        Scene mP = new Scene(multipleProduct);
        Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
        current.setScene(mP);
    }
}
