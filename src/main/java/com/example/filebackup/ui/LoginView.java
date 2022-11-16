package com.example.filebackup.ui;

import com.example.filebackup.utils.UploadUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class LoginView {
    public Button login;
    public VBox loginLayout;
    public Label loginLabel;
    public Button upload;
    public TextField filepathSelect;
    public TextField serverip;
    public Button fileChoose;
    public TextField serverPort;
    private String clientKey;


    public File onFileSelect() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File file = fileChooser.showOpenDialog(loginLayout.getScene().getWindow());
        if (file != null){
            filepathSelect.setText(file.getAbsolutePath());
            return file;
        }
        return null;
    }
    void showMessageDialog(String str, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.show();
    }

    @FXML
    protected void uploadAction(){
        if (filepathSelect.getText() == null || Objects.equals(filepathSelect.getText(), "")){
            showMessageDialog("source path is null", Alert.AlertType.ERROR);
            return;
        }
        Path srcPath = Path.of(filepathSelect.getText().toString());
        if(!Files.isRegularFile(srcPath)){
            showMessageDialog("could not load source path", Alert.AlertType.ERROR);
            return;
        }

        File file = srcPath.toFile();

        UploadUtils.uploadFile(file, "http://"+serverip.getText().toString(),Integer.parseInt(serverPort.getText().toString()));

    }
}
