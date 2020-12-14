package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerMultipleProduct extends ControllerDashboard
{
    public void switchOrder(MouseEvent event) throws IOException, SQLException
    {
        Parent multipleProduct = FXMLLoader.load(getClass().getResource("../Resources/Order.fxml"));
        Scene mP = new Scene(multipleProduct);
        Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
        current.setScene(mP);
        JFXButton button = (JFXButton)event.getSource();
        ControllerOrder order = new ControllerOrder();
        order.setProductId(button.getId());
    }
}
