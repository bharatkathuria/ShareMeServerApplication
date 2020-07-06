package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{



        Parent root = FXMLLoader.load(getClass().getResource("LogInGUI.fxml"));
        stage.setScene(new Scene(root,400,400));
        stage.setTitle("ShareMe - Message and File Sharing");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


//        Button btn1 = new Button("submit");
//        btn1.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                System.out.println("Hello World");
//            }
//        });
//        StackPane root = new StackPane();
//        root.getChildren().add(btn1);
//        Scene scene =new Scene(root,400,400);
//
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Hello World");
//        primaryStage.show();
