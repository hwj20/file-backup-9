package com.example.filebackup.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用MD5进行文件验证的工具
 */
public class VerifyUtils {
    public Map<String,String> map=new HashMap<>();

    protected char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    protected MessageDigest messagedigest = null;

    {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件 MD5 编码
     * @param file 文件
     * @return MD5 编码
     * @throws IOException 读写错误
     */
    public String getFileMD5String(File file) throws IOException {
        InputStream fis;
        fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messagedigest.digest());
    }


    /**
     * 获取文件 MD5 编码
     * @param in  文件输入流
     * @return MD5 编码
     * @throws IOException 读写错误
     */
    public String getFileMD5String(InputStream in) throws IOException {
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = in.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        in.close();
        return bufferToHex(messagedigest.digest());
    }

    private String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    /**
     * 转换string buffer，将其高位和低位 4 bit 与 byte 字符 and 后，交换加在buffer中
     * @param bt byte 字符
     * @param stringbuffer string buffer
     */
    private void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char r0 = hexDigits[(bt & 0xf0) >> 4];
        char r1 = hexDigits[bt & 0xf];
        stringbuffer.append(r0);
        stringbuffer.append(r1);
    }


    /**
     * 通过递归调用，保存目录 md5 编码
     * @param rootpath 源地址
     * @param parentroot 递归父地址
     * @throws IOException 文件操作错误
     */
    public void search(String rootpath, String parentroot) throws IOException {

        File root=new File(rootpath);
        if(!root.isDirectory()) {
            map.put(root.getName(),new VerifyUtils().getFileMD5String(root));
            return;
        }

        File[] srcList = root.listFiles();
        if (null != srcList && srcList.length > 0){
            for (File aSrcList : srcList) {
                if (aSrcList.isDirectory()) {
                    search(rootpath + "/" + aSrcList.getName(),parentroot);
                }else if(aSrcList.isFile()){
                    File current=new File(rootpath + "/" + aSrcList.getName());
                    map.put((rootpath + "/" + aSrcList.getName()).substring(parentroot.length()+1), new VerifyUtils().getFileMD5String(new FileInputStream(current)));
                }
            }
        }
    }
}
