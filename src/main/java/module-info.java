module com.example.kolokwium_dla_nieobecnych {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kolokwium_dla_nieobecnych to javafx.fxml;
    exports com.example.kolokwium_dla_nieobecnych;
}