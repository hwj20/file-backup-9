module com.example.filebackup {
    requires javafx.controls;
    requires javafx.fxml;
            
                                requires com.almasb.fxgl.all;
    
    opens com.example.filebackup to javafx.fxml;
    exports com.example.filebackup;
}