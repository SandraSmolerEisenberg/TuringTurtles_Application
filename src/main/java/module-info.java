module se.turingturtles {
    requires javafx.controls;
    requires javafx.fxml;

    opens se.turingturtles to javafx.fxml;
    exports se.turingturtles;
}