module com.example.captiongenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;


    opens com.example.captiongenerator to javafx.fxml;
    exports com.example.captiongenerator;
}