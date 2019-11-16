package se.turingturtles;

import java.io.IOException;
import javafx.fxml.FXML;

public class StartPageController {

    @FXML
    private void switchToSecondary() throws IOException {
        Main.setRoot("secondary");
    }
}
