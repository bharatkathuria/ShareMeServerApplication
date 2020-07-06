package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class Controller {

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    public void handleSubmitButtonAction(javafx.event.ActionEvent actionEvent) throws IOException {

        Window reference  = submitButton.getScene().getWindow();
        if(userNameField.getText().isEmpty()){

            AlertHelper.showAlert(Alert.AlertType.ERROR, reference, "Form Error!",
                    "Please enter valid username");
        }

        if(passwordField.getText().isEmpty()){

            AlertHelper.showAlert(Alert.AlertType.ERROR, reference, "Form Error!",
                    "Please enter password");
        }
        if(!passwordField.getText().isEmpty() && !userNameField.getText().isEmpty()){

            Stage stage = (Stage)reference;
            new startServer().startAllservers(stage);
        }

    }
}
