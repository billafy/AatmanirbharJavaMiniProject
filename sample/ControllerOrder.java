package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
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
import java.time.LocalDate;

public class ControllerOrder extends ControllerDashboard
{
    public Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx?useTimezone=true&serverTimezone=UTC","root","Yash@welcome1");
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXTextArea addressArea;
    private int productId;

    public ControllerOrder() throws SQLException {}

    public void setProductId(String prod) throws SQLException
    {
        PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE Ordersession");
        statement.executeUpdate();
        productId = Integer.parseInt(prod.substring(prod.length()-1));
        statement = connection.prepareStatement("INSERT INTO Ordersession(ProductID) VALUES(?)");
        statement.setInt(1,productId);
        statement.executeUpdate();
    }

    public void placeOrder(MouseEvent event) throws SQLException, IOException
    {
        String username = usernameField.getText(), password = passwordField.getText(), address = addressArea.getText();
        if(username.length()==0 || password.length()==0 || address.length()==0)
        {
            infobox("Failed","Incomplete details","Please fill all the fields.");
            return;
        }
        PreparedStatement statement = connection.prepareStatement("SELECT Password FROM User WHERE Username = ? AND Password = ?;");
        statement.setString(1,username);
        statement.setString(2,password);

        ResultSet resultSet = statement.executeQuery();
        if(!resultSet.next())
        {
            infobox("Failed","Invalid Username And Password","Please re-enter.");
        }
        else
        {
            LocalDate date = LocalDate.now();
            String deliveryDate = date.toString();
            deliveryDate = deliveryDate.substring(0,deliveryDate.length()-1) + String.format("%d",Integer.parseInt(deliveryDate.substring(deliveryDate.length()-1))+1);

            statement = connection.prepareStatement("SELECT * FROM Ordersession;");
            resultSet = statement.executeQuery();
            if(resultSet.next())
                productId = resultSet.getInt("ProductID");

            statement = connection.prepareStatement("INSERT INTO Ordertable(Username,ProductID,DeliveryDate,DeliveryAddress,ShipperID) VALUES(?,?,STR_TO_DATE(?,'%Y-%m-%d'),?,1)");
            statement.setString(1,username);
            statement.setInt(2,productId);
            statement.setString(3,deliveryDate);
            statement.setString(4,address);
            statement.executeUpdate();
            infobox("Order Placed Successfully","","Redirecting to the dashboard");

            Parent dashboard = FXMLLoader.load(getClass().getResource("../Resources/Dashboard.fxml"));
            Scene d = new Scene(dashboard);
            Stage current = (Stage)((Node)event.getSource()).getScene().getWindow();
            current.setScene(d);
            current.show();
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
