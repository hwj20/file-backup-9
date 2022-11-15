module com.example.filebackup {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.logging;
    requires org.testng;

    opens com.example.filebackup to javafx.fxml;
    exports com.example.filebackup;
    exports com.example.filebackup.ui;
    opens com.example.filebackup.ui to javafx.fxml;
}