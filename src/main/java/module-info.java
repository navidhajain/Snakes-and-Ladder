module com.example.snl {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.snl to javafx.fxml;
    exports com.example.snl;
}