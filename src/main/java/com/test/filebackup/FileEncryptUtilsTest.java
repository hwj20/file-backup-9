package com.test.filebackup;

import com.example.filebackup.utils.FileEncryptUtils;
import org.junit.Test;
import org.testng.annotations.Factory;


class FileEncryptUtilsTest {


    @Test
    public void encrypt() {
        String srcFile = "D:\\code\\123.txt";
        String dstFile = "D:\\code\\123\\123.txt";
        String key="awdqcf";
        try {
            FileEncryptUtils.encrypt(srcFile,dstFile,key);
        }catch (Exception exception){
            assert false;
        }
    }

    @Test
    public void decrypt() {
        String srcFile = "D:\\code\\123.txt.erp";
        String dstFile = "D:\\code\\";
        String key="awdqcf";
        try {
            FileEncryptUtils.decrypt(srcFile,dstFile,key);
        }catch (Exception exception){
            assert false;
        }
    }
}