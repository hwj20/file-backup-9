import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZipUtil {
    public static void compressFile(String srcPath, String outPath) throws IOException {
        //读取源文件
        File srcFile = new File(srcPath);
        //判断输出路径是否正确
        File outFile = new File(outPath);
        //如果只是路劲加入对应的压缩名称
        if (outFile.isDirectory()) {
            //用"/"作文判断标准
            if (outPath.endsWith(File.separator)) {
                outPath += srcFile.getName().split("\\.")[0] + ".zip";
            } else {
                outPath += File.separator + srcFile.getName().split("\\.")[0] + ".zip";
            }
        }
        //读取文件流
        FileOutputStream fileOutputStream = new FileOutputStream(outPath);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        //压缩文件
        compressFile(srcFile, srcFile.getName(),zipOutputStream);
        //关闭流
        zipOutputStream.close();
        fileOutputStream.close();
    }


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
}
