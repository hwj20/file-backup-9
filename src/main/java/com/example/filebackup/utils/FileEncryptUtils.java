package com.example.filebackup.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 文件加密攻击
 */
public class FileEncryptUtils {

    /**
     * 判断是目录还是文件，文件就文件加密，目录就递归加密
     * @param srcPath 源地址
     * @param destPath 目的地址
     * @param key 密钥
     * @throws Exception 文件加密错误（比如文件读写出错）
     */
    public static void search(String srcPath,String destPath,String key) throws Exception {
        File src = new File(srcPath);//源头
        File dest = new File(destPath);//目的地
        //判断是否为目录，不存在则不作操作
        if(!src.isDirectory()) {
            return;
        }
        //判断目的目录是否存在，不存在就创建目录
        if(!dest.exists()) {
            dest.mkdir();
        }
        //获取源头目录下的文件列表组成数组，每个对象代表一个目录或者文件
        File[] srcList = src.listFiles();
        if (null != srcList && srcList.length > 0){
            //遍历源头目录下的文件列表
            for (File aSrcList : srcList) {
                //如果是目录的话
                if (aSrcList.isDirectory()) {
                    //递归调用遍历该目录
                    search(srcPath + File.separator + aSrcList.getName(), destPath + File.separator + aSrcList.getName(),key);
                } else if (aSrcList.isFile()) {
                    encrypt(srcPath + File.separator + aSrcList.getName(), destPath + File.separator + aSrcList.getName(),key);
                }
            }
        }
    }


    /**
     *  文件加密
     * @param isFile 源文件
     * @param osFile 目标文件
     * @param key 密钥
     * @throws Exception 文件操作错误
     */
    public static void encrypt(String isFile, String osFile, String key) throws Exception {
        File srcfile=new File(isFile);
        Path path=srcfile.toPath();
        String s= Files.readString(path);
        String s1= AESUtils.encrypt(s, key);
        FileOutputStream outfile=new FileOutputStream(osFile);
        outfile.write(s1.getBytes(StandardCharsets.UTF_8));

    }

    /**
     * 文件解密
     * @param isFile 源文件
     * @param osFile 目标文件
     * @param key 密钥
     * @throws Exception 文件操作错误
     */
    public static void decrypt(String isFile, String osFile, String key) throws Exception{
        File srcfile=new File(isFile);
        Path path=srcfile.toPath();
        String s= Files.readString(path);
        String s1= AESUtils.decrypt(s,key);
        FileOutputStream outfile=new FileOutputStream(osFile);
        outfile.write(s1.getBytes(StandardCharsets.UTF_8));
    }


}