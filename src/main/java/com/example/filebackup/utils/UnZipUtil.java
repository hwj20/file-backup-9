import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZipUtil {
    public static void unzip(String zipFilePath, String desDirectory) throws Exception {
        File desDir = new File(desDirectory);
        if (!desDir.exists()) {
            boolean mkdirSuccess = desDir.mkdir();
            if (!mkdirSuccess) {
                throw new Exception("创建解压目标文件夹失败");
            }
        }
        // 读入流
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
        // 遍历每一个文件
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            if (zipEntry.isDirectory()) { // 文件夹
                String unzipFilePath = desDirectory + File.separator + zipEntry.getName();
                // 直接创建
                mkdir(new File(unzipFilePath));
            } else { // 文件
                String unzipFilePath = desDirectory + File.separator + zipEntry.getName();
                File file = new File(unzipFilePath);
                // 创建父目录
                mkdir(file.getParentFile());
                // 写出文件流
                BufferedOutputStream bufferedOutputStream =
                        new BufferedOutputStream(new FileOutputStream(unzipFilePath));
                byte[] bytes = new byte[1024];
                int readLen;
                while ((readLen = zipInputStream.read(bytes)) != -1) {
                    bufferedOutputStream.write(bytes, 0, readLen);
                }
                bufferedOutputStream.close();
            }
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

    // 如果父目录不存在则创建
    private static void mkdir(File file) {
        if (null == file || file.exists()) {
            return;
        }
        mkdir(file.getParentFile());
        file.mkdir();
    }
}

