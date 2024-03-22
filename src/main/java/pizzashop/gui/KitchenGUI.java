package pizzashop.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;

public class KitchenGUI {
    public void loadKitchenGUI() {
        VBox vBoxKitchen = null;

        try {
            vBoxKitchen = FXMLLoader.load(getClass().getResource("/fxml/kitchenGUIFXML.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Kitchen");
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to exit Kitchen window?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.get() == ButtonType.YES){
                stage.close();
            }
            // consume event
            else if (result.get() == ButtonType.NO){
                event.consume();
            }
            else {
                event.consume();
            }
        });
        stage.setAlwaysOnTop(false);
        stage.setScene(new Scene(vBoxKitchen));
        stage.show();
        stage.toBack();
    }
}
