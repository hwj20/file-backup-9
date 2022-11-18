package com.test.filebackup;

import com.example.filebackup.utils.AESUtils;
import org.junit.Test;

import java.util.Objects;


public class AESUtilsTest {

    String toTest = "to test";
    String key = "2132";
    String encryptedRes = null;
    @Test
    public void encrypt() {
        try {
            encryptedRes = AESUtils.encrypt(toTest,key);
        }
        catch (Exception exception){
            assert false;
        }
        assert true;
    }

    @Test
    public void decrypt() {
        String origin = null;
        try{
            origin = AESUtils.decrypt(encryptedRes,key);

        }catch (Exception exception){
            assert false;
        }
        assert Objects.equals(toTest, origin);
    }
}