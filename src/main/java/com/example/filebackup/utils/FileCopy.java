package com.example.filebackup.utils;

import com.example.filebackup.utils.huffman.HuffmanCode;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

//定义了复制、压缩、解压、加密、解密、验证功能函数，处理源路径是目录，目的路径不存在的问题
public class FileCopy {
    public void copy(String srcPath,String dstPath) throws IOException {
        try {
            File file=new File(srcPath);
            if(file.isDirectory()){
                FileCopyUtils.copyDir(srcPath,dstPath);
            }
            else {
                File f=new File(dstPath);
                if(!f.exists()){
                    f.createNewFile();
                }
                FileCopyUtils.copyFile(srcPath,dstPath);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void compress(String srcPath,String dstPath){
            HuffmanCode.zipFile(srcPath,dstPath);
            //FileZipUtil.compressFile(srcPath,dstPath);

    }

    public void unzip(String srcPath, String dstPath) throws Exception {
            HuffmanCode.unZipFile(srcPath,dstPath);
    }


    public void encrypy(String srcPath,String dstPath,String key) throws IOException {
            try {
                File file=new File(srcPath);
                if(file.isDirectory()){
                    FileEncrypyUtils.search(srcPath,dstPath, key);
                }
                else {
                    File f=new File(dstPath);
                    if(!f.exists()){
                        f.createNewFile();
                    }
                    FileEncrypyUtils.encrypy(srcPath,dstPath,key);
                }
            }catch (IOException e){
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void decrypy(String srcPath,String dstPath,String key) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FileEncrypyUtils.decrypy(srcPath,dstPath,key);
    }

    public void varify(String firstpath,String secondpath) throws IOException {
        VerifyUtils first=new VerifyUtils();
        VerifyUtils second=new VerifyUtils();
        first.search(firstpath,firstpath);
        second.search(secondpath,secondpath);

//        System.out.println(first.map);
//        System.out.println(second.map);
//        if (first.map.equals(second.map)){
//            System.out.println(true);
//        }else {
//            System.out.println(false);
//        }
    }
}

