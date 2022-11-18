package com.example.filebackup;

import com.example.filebackup.ui.PathSelectView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * 操作选择的 view 的 controller
 */
public class MainController {



    public enum Mode{
        ENCRYPT,DECRYPT,STORE,RESTORE,ZIP,UNZIP,UPLOAD,VERIFY,S_ZIP,S_UNZIP
    }
    @FXML
    private Label welcomeText;
    @FXML
    private VBox rootLayout;

    /**
     * 打开地址选择窗口(pathselect-view.xml)，选择源地址和目标地址，进行操作
     * @param mode 所选操作模式
     * @throws IOException 文件操作错误
     */
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

    /**
     * 打开文件上传窗口(login-view.fxml)，选择文件，输入ip和端口号，进行上传操作
     * @throws IOException 文件操作错误
     */
    public void openUploadSelectView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root, 320, 400);
        stage.setTitle("上传文件");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((event) -> {
            System.out.println("正在关闭当前的窗口..");
            stage.close();
        });
    }


    /**
     *
     * @throws IOException 文件操作错误
     */
    @FXML
    protected void onEncryptButtonClick() throws IOException{
        openPathSelectView(Mode.ENCRYPT);
    }


    /**
     *
     * @throws IOException 文件操作错误
     */
    @FXML
    protected void onDecryptButtonClick() throws IOException {
        openPathSelectView(Mode.DECRYPT);
    }


    /**
     *
     * @throws IOException 文件操作错误
     */
    @FXML
    public void onSingleZipButtonClick() throws IOException {
        openPathSelectView(Mode.S_ZIP);
    }


    /**
     *
     * @throws IOException 文件操作错误
     */
    @FXML
    public void onUnSingleUnzipButtonClick() throws IOException {
        openPathSelectView(Mode.S_UNZIP);
    }


    /**
     *
     * @throws IOException 文件操作错误
     */
    @FXML
    protected void onUploadButtonClick() throws IOException {
        openUploadSelectView();
    }


    /**
     *
     * @throws IOException 文件操作错误
     */
    @FXML
    protected void onZipButtonClick() throws IOException {
        openPathSelectView(Mode.ZIP);
    }


    /**
     *
     * @throws IOException 文件操作错误
     */
    @FXML
    protected void onUnzipButtonClick() throws IOException {
        openPathSelectView(Mode.UNZIP);
    }


    /**
     *
     * @throws IOException 文件操作错误
     */
    @FXML
    protected void onRestoreButtonClick() throws IOException {
        openPathSelectView(Mode.RESTORE);
    }


    /**
     *
     * @throws IOException 文件操作错误
     */
    @FXML
    protected void onStoreButtonClick() throws IOException {
        openPathSelectView(Mode.STORE);
    }


    /**
     *
     * @throws IOException 文件操作错误
     */
    @FXML
    protected void onVerifyButtonClick() throws IOException {
        openPathSelectView(Mode.VERIFY);
    }

}