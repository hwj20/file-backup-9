package com.example.filebackup.ui;

import com.example.filebackup.MainController;
import com.example.filebackup.utils.*;
import com.example.filebackup.utils.huffman.HuffmanCode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
TODO: 覆盖文件，网络
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
    private boolean chooseDstPath;

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
        chooseDstPath = true;
        dstPathSelect.setText("目标目录选择");
        srcPathSelect.setText("源文件选择");
        if (mode == MainController.Mode.ZIP) {
//        if (mode == MainController.Mode.ZIP || mode == MainController.Mode.STORE_RESTORE) {
            chooseSrcPath = true;
            srcPathSelect.setText("源目录选择");
        }
        if(mode == MainController.Mode.VERIFY || mode == MainController.Mode.RESTORE){
//            if(mode == MainController.Mode.VERIFY){
            chooseDstPath = false;
            dstPathSelect.setText("目标文件选择");

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
        File file = null;
        if(chooseDstPath){
            file = onPathSelect();
        }
        else {
            file = onFileSelect();
        }
        String dstPath = file.getAbsolutePath();
        dstTextField.setText(dstPath);
    }

    void showMessageDialog(String str, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.show();
    }

    String showInputDialog(String str){
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText(str);
        td.showAndWait();
        return td.getEditor().getText().toString();
    }
    void copyAction(Path srcFile, Path dstPath){
        File file= new File(srcFile.toString().replace('\\','/'));
        try {
            if(mode == MainController.Mode.STORE) {
                FileCopyUtils.copy(file.toString(), dstPath.toString().replace('\\', '/') + "/"+file.getName());
            }
            else{
                FileCopyUtils.copy(file.toString(), dstPath.toString().replace('\\', '/') );
            }
        }catch (Exception exception){
            showMessageDialog(exception.toString(), Alert.AlertType.ERROR);
            return;
        }
        showMessageDialog("copy finished", Alert.AlertType.INFORMATION);
    }
    void uploadAction(Path srcFile, Path dstPath){
        File file= new File(srcFile.toString().replace('\\','/'));
        String key = showInputDialog("Enter the key");
        try {
            FileEncryptUtils.decrypt(file.toString(), dstPath.toString().replace('\\', '/') + '/' + file.getName().replace(".erp",""), key);
        } catch (Exception exception){
            showMessageDialog(exception.toString(), Alert.AlertType.ERROR);
            return;
        }
        showMessageDialog("decrypt finished", Alert.AlertType.INFORMATION);
    }
    void decryptAction(Path srcFile, Path dstPath){
        File file= new File(srcFile.toString().replace('\\','/'));
        String key = showInputDialog("Enter the key");
        try {
            FileEncryptUtils.decrypt(file.toString(), dstPath.toString().replace('\\', '/') + '/' + file.getName().replace(".erp",""), key);
        } catch (Exception exception){
            showMessageDialog(exception.toString(), Alert.AlertType.ERROR);
            return;
        }
        showMessageDialog("decrypt finished", Alert.AlertType.INFORMATION);
    }
    void encryptAction(Path srcFile, Path dstPath){
        File file= new File(srcFile.toString().replace('\\','/'));
        String key = showInputDialog("Enter the key");
        try {
            FileEncryptUtils.encrypt(file.toString(), dstPath.toString().replace('\\', '/') + '/' + file.getName()+".erp", key);
        } catch (Exception exception){
            showMessageDialog(exception.toString(), Alert.AlertType.ERROR);
            return;
        }
        showMessageDialog("encrypt finished", Alert.AlertType.INFORMATION);
    }
    void unzipAction(Path srcFile, Path dstPath){
        File file = srcFile.toFile();
        String filename=file.getName();
        if(!filename.substring(filename.lastIndexOf(".") + 1).equals("zip")){
            showMessageDialog("source type is not .zip file", Alert.AlertType.ERROR);
        }
        else {
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

            if(suffix.equals("zip")){
                try {
                    UnZipUtil.unzip(file.toString(), dstPath.toString().replace('\\', '/'));
                }catch (Exception exception){
                    showMessageDialog(exception.toString(), Alert.AlertType.ERROR);
                    return;
                }
            }
            showMessageDialog("unzip finished", Alert.AlertType.INFORMATION);
        }
    }
    void zipAction(Path srcPath, Path dstPath){
        File file= new File(srcPath.toString().replace('\\','/'));
        try {
            FileZipUtil.compressFile(file.toString(),dstPath.toString().replace('\\','/')+'/'+file.getName()+".zip");
        }catch (Exception exception){
            showMessageDialog(exception.toString(), Alert.AlertType.ERROR);
            return;
        }
        showMessageDialog("zip finished", Alert.AlertType.INFORMATION);
    }

    @FXML
    protected void onAction() throws IOException {
        // check file available
        if (srcTextField.getText() == null || Objects.equals(srcTextField.getText(), "")){
            showMessageDialog("source path is null", Alert.AlertType.ERROR);
            return;
        }
        if (dstTextField.getText() == null|| Objects.equals(dstTextField.getText(), "")){
            showMessageDialog("destination path is null", Alert.AlertType.ERROR);
            return;
        }
        Path srcPath = Paths.get(srcTextField.getText());
        Path dstPath = Paths.get(dstTextField.getText());
        if(chooseSrcPath){
            if(!Files.isDirectory(srcPath)){
                showMessageDialog("could not load source path", Alert.AlertType.ERROR);
                return;
            }
        }
        else{
            if(!Files.isRegularFile(srcPath)){
                showMessageDialog("could not load source path", Alert.AlertType.ERROR);
                return;
            }
        }
        if(chooseDstPath){
            if(!Files.isDirectory(dstPath)){
                showMessageDialog("could not load destination path", Alert.AlertType.ERROR);
                return;
            }
        }
        else{
            if(!Files.isRegularFile(dstPath)){
                showMessageDialog("could not load destination path", Alert.AlertType.ERROR);
                return;
            }
        }

        switch (mode){
            case ZIP -> {zipAction(srcPath,dstPath);}
            case UNZIP -> {unzipAction(srcPath,dstPath);}
            case STORE,RESTORE-> {copyAction(srcPath,dstPath);}
            case ENCRYPT -> {encryptAction(srcPath,dstPath);}
            case DECRYPT -> {decryptAction(srcPath,dstPath);}
        }
    }



}
