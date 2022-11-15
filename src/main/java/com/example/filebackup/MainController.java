package com.example.filebackup;

import com.example.filebackup.ui.PathSelectView;
import com.example.filebackup.utils.FileCopy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


public class MainController {
    public enum Mode{
        ENCRYPT,DECRYPT,COPY,ZIP,UNZIP,UPLOAD
    }
    @FXML
    private Label welcomeText;
    @FXML
    private VBox rootLayout;

    public void openPathSelectView(Mode mode) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("pathselect-view.fxml"));
		Parent root = fxmlLoader.load();

        PathSelectView pathSelectView = fxmlLoader.getController();
        pathSelectView.initData(mode);

		Stage stage = new Stage();
		Scene scene = new Scene(root, 320, 320);
		stage.setTitle("路径选择");
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest((event) -> {
			System.out.println("正在关闭当前的窗口..");
			stage.close();
		});
    }

    @FXML
    protected void onEncryptButtonClick() throws IOException{
        openPathSelectView(Mode.ENCRYPT);
    }
    @FXML
    protected void onDecryptButtonClick() throws IOException {
        openPathSelectView(Mode.DECRYPT);
    }
    @FXML
    protected void onUploadButtonClick() throws IOException {
        openPathSelectView(Mode.UPLOAD);
    }
    @FXML
    protected void onZipButtonClick() throws IOException {
        openPathSelectView(Mode.ZIP);
    }
    @FXML
    protected void onUnzipButtonClick() throws IOException {
        openPathSelectView(Mode.UNZIP);
    }
    @FXML
    protected void onCopyButtonClick() throws IOException {
        openPathSelectView(Mode.COPY);
    }
}