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

/**
 * 文件上传的login-view controller
 * 本来想做注册功能，所以取这个名字
 */
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


    /**
     *
     * @return 选择文件
     * @throws IOException 文件选择错误
     */
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

    /**
     *
     * @param str 显示信息
     * @param alertType 消息显示类别
     */
    void showMessageDialog(String str, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.show();
    }

    /**
     * 文件上传按钮动作
     */
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
