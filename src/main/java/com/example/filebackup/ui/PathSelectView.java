package com.example.filebackup.ui;

import com.example.filebackup.MainController;
import com.example.filebackup.utils.FileCopy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.testng.annotations.Factory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/*
TODO: 目录和文件混合打包，覆盖文件，返回窗口，网络
TODO: 丑
 */
public class PathSelectView {

    public VBox pathSelectLayout;
    public TextField srcTextField;
    public TextField dstTextField;
    public Button srcPathSelect;
    public Button dstPathSelect;
    public Label modeLabel;
    public Button action;
    MainController.Mode mode;
    private boolean chooseSrcPath;
//    private boolean chooseDstPath;

    public File onPathSelect() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose File");
        File file = directoryChooser.showDialog(pathSelectLayout.getScene().getWindow());
        if (file != null){
            System.out.println(file.getAbsolutePath());
            return file;
        }
        return null;
    }
    public File onFileSelect() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File file = fileChooser.showOpenDialog(pathSelectLayout.getScene().getWindow());
        if (file != null){
            System.out.println(file.getAbsolutePath());
            return file;
        }
        return null;
    }

    public void initData(MainController.Mode m){
        mode = m;
        chooseSrcPath = false;
        dstPathSelect.setText("目标目录选择");
        srcPathSelect.setText("源文件选择");
        if (mode == MainController.Mode.ZIP) {
            chooseSrcPath = true;
            srcPathSelect.setText("源目录选择");
        }
        modeLabel.setText(mode.name());
    }

    @FXML
    protected void onSrcPathSelect() throws IOException {
        File file = null;
        if(chooseSrcPath){
             file = onPathSelect();
        }
        else {
            file = onFileSelect();
        }
        String srcPath = file.getAbsolutePath();
        srcTextField.setText(srcPath);
    }
    @FXML
    protected void onDstPathSelect() throws IOException {
        File file =onPathSelect();
        String dstPath = file.getAbsolutePath();
        dstTextField.setText(dstPath);
    }

    void showReturnDialog(String str){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("喵");
        alert.setContentText(str);
        alert.show();
    }

    @FXML
    protected void onAction() throws IOException {
        // check file available
        if (srcTextField.getText() == null || Objects.equals(srcTextField.getText(), "")){
            showReturnDialog("source path is null");
        }
        if (dstTextField.getText() == null|| Objects.equals(dstTextField.getText(), "")){
            showReturnDialog("destination path is null");
        }
        Path srcPath = Paths.get(srcTextField.getText());
        Path dstPath = Paths.get(dstTextField.getText());
        if(chooseSrcPath){
            if(!Files.isDirectory(srcPath)){
                showReturnDialog("could not load source path");
            }
        }
        else{
            if(!Files.isRegularFile(srcPath)){
                showReturnDialog("could not load source path");
            }
        }
        if (!Files.isDirectory(dstPath)){
            showReturnDialog("could not load destination path");
        }

    }


}
