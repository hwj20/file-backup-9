package com.example.filebackup.utils;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


class UploadUtilsTest {
    private static final String TAG = "UploadUtilsTest";
    private static final Logger logger = Logger.getLogger(TAG);

    @Test
    void uploadFile() {
        try {
            File f = File.createTempFile("testfile","test");
            UploadUtils.uploadFile(f,"http://10.252.163.24/",8000);
        }catch (IOException ioException){
            logger.severe( ioException.toString());
            assert false;
}

        assert true;
//        File f =
//        UploadUtils.uploadFile();
    }

}