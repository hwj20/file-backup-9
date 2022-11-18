package com.test.filebackup;

import com.example.filebackup.utils.UploadUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


public class UploadUtilsTest {
    private static final String TAG = "UploadUtilsTest";
    private static final Logger logger = Logger.getLogger(TAG);

    @Test
    void uploadFile() {
        // 以下填服务器ip和port
        int port = 8000;
        String url = "http://10.252.163.24/";
        try {
            File f = File.createTempFile("testfile","test");
            UploadUtils.uploadFile(f, url,port);
        }catch (IOException ioException){
            logger.severe( ioException.toString());
            assert false;
        }
        assert true;

    }

}