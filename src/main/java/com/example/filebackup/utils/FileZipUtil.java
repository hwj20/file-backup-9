package com.example.filebackup.utils;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.example.filebackup.utils.huffman.HuffmanCode.decode;

/**
 * 文件压缩
 */
public class FileZipUtil {
    /**
     *  压缩文件
     * @param srcPath 源地址
     * @param outPath 目标地址
     * @throws IOException 文件读写错误
     * @throws ClassNotFoundException 文件不存在错误
     */
    public static void compressFile(String srcPath, String outPath) throws IOException, ClassNotFoundException {
        File srcFile = new File(srcPath);
        File outFile = new File(outPath);
        if (outFile.isDirectory()) {
            if (outPath.endsWith(File.separator)) {
                outPath += srcFile.getName().split("\\.")[0] + ".zip";
            } else {
                outPath += File.separator + srcFile.getName().split("\\.")[0] + ".zip";
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(outPath);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        compressFile(srcFile, srcFile.getName(),zipOutputStream);
        zipOutputStream.close();
        fileOutputStream.close();
    }

    /**
     *
     * @param file 文件
     * @param fileName 文件名
     * @param outputStream 输出流
     * @throws IOException IO错误
     */
    private static void compressFile(File file, String fileName, final ZipOutputStream outputStream) throws IOException {
        //如果是目录
        if (file.isDirectory()) {
            //创建文件夹
            outputStream.putNextEntry(new ZipEntry(fileName+"/"));
            //迭代判断，并且加入对应文件路径
            File[] files = file.listFiles();
            Iterator<File> iterator = Arrays.asList(files).iterator();
            while (iterator.hasNext()) {
                File f = iterator.next();
                compressFile(f, fileName+"/"+f.getName(), outputStream);
            }
        } else {
            //创建文件
            outputStream.putNextEntry(new ZipEntry(fileName));
            //读取文件并写出
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] bytes = new byte[1024];
            int n;
            while ((n = bufferedInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, n);
            }
            //关闭流
            fileInputStream.close();
            bufferedInputStream.close();
        }
    }

//    private static void compressFile(File file, String fileName, final ZipOutputStream outputStream) throws IOException, ClassNotFoundException {
//        //如果是目录
//        if (file.isDirectory()) {
//            //创建文件夹
//            outputStream.putNextEntry(new ZipEntry(fileName+"/"));
//            //迭代判断，并且加入对应文件路径
//            File[] files = file.listFiles();
//            for (File f : files) {
//                compressFile(f, fileName + "/" + f.getName(), outputStream);
//            }
//        } else {
//            //创建文件
//            outputStream.putNextEntry(new ZipEntry(fileName));
//            //读取文件并写出
//            ObjectInputStream ois = null;
//            FileInputStream  is = new FileInputStream(file);
////            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//            ois = new ObjectInputStream(is);
//            byte[] huffmanBytes = (byte[])ois.readObject();
//            Map<Byte, String> huffmanCodes = (Map)ois.readObject();
//            byte[] bytes = decode(huffmanCodes, huffmanBytes);
//            outputStream.write(bytes);
////            int n;
////            while ((n = bufferedInputStream.read(bytes)) != -1) {
////                outputStream.write(bytes, 0, n);
////            }
//            //关闭流
//            is.close();
//            ois.close();
//        }
//    }
}
