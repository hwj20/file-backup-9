import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileEncrypyUtils {
//    在复制目录的过程中判断源文件下所有文件对象是否为目录，是的话则利用递归调用自己复制目录
//    如果是文件的话，则调用encrypy方法加密文件,加密是根据用户输入的数字，在每个字节上加上这个数字，实现加密

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
                    encrypy(srcPath + File.separator + aSrcList.getName(), destPath + File.separator + aSrcList.getName(),key);
                }
            }
        }
    }


    //    实现对文件的复制
    public static void encrypy(String isFile, String osFile, String key) throws Exception {
        File srcfile=new File(isFile);
        Path path=srcfile.toPath();
        String s= Files.readString(path);
        String s1= AESUtils.encrypt(s, key);
        FileOutputStream outfile=new FileOutputStream(osFile);
        outfile.write(s1.getBytes(StandardCharsets.UTF_8));

    }

    public static void decrypy(String isFile, String osFile, String key) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        File srcfile=new File(isFile);
        Path path=srcfile.toPath();
        String s= Files.readString(path);
        String s1= AESUtils.decrypt(s,key);
        FileOutputStream outfile=new FileOutputStream(osFile);
        outfile.write(s1.getBytes(StandardCharsets.UTF_8));
    }


}