package com.test.filebackup;

import com.example.filebackup.utils.FileZipUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;


public class FileZipUtilTest {

    String srcPath = "D:\\test_file\\to_compress";
    String dstPath = "D:\\test_file\\";


    @Test
    public void compressFile() {

        try {
            FileZipUtil.compressFile(srcPath,dstPath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            assert false;
        }
    }
}