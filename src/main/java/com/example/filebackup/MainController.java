package com.example.filebackup;

import com.example.filebackup.ui.PathSelectView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {
    public enum Mode{
        ENCRYPT,DECRYPT,STORE,RESTORE,ZIP,UNZIP,UPLOAD,VERIFY
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
		Scene scene = new Scene(root, 320, 400);
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
    protected void onRestoreButtonClick() throws IOException {
        openPathSelectView(Mode.RESTORE);
    }
    @FXML
    protected void onStoreButtonClick() throws IOException {
        openPathSelectView(Mode.STORE);
    }
}