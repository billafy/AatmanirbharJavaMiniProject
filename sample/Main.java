package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        Parent root = FXMLLoader.load(getClass().getResource("../Resources/Dashboard.fxml")); // Parent variable which will hold our screen (Stage here)
        primaryStage.setTitle("Register"); // Stage Title
        primaryStage.setScene(new Scene(root, 360, 600)); // Stage size parameters

        primaryStage.initStyle(StageStyle.UNDECORATED); // initializing stage without default windows borders

        final double[] xCoord = {0}; // as we removed windows borders, we can't move the application screen
        final double[] yCoord = {0}; // so this is what we need to do, declare two variables x and y

        // when mouse is clicked
        root.setOnMousePressed(mouseEvent -> {
            xCoord[0] = mouseEvent.getSceneX(); // x will get the current x co-ordinate value
            yCoord[0] = mouseEvent.getSceneY(); // and y similar to x
        });
        // when the screen is dragged with mouse
        root.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() - xCoord[0]); // position of x after getting dragged - original position of x
            primaryStage.setY(mouseEvent.getScreenY() - yCoord[0]); // similar with y
        });
        primaryStage.show(); // show our Stage
    }


    public static void main(String[] args) {
        launch(args);
    }
}
