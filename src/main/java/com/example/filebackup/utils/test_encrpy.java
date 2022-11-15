import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class test_encrpy {

    public static void main(String[] args) throws Exception {

        String srcFile = "D:\\code\\123.txt";
        String dstFile = "D:\\code\\123\\123.txt";
        String key="awdqcf";
        encrp(srcFile,dstFile,key);
        decrp(dstFile,dstFile,key);
    }

    private static void encrp(String srcFile, String dstFile, String key) throws Exception {
        File srcfile=new File(srcFile);
        Path path=srcfile.toPath();
        String s= Files.readString(path);
        String s1= AESUtils.encrypt(s, key);
        FileOutputStream outfile=new FileOutputStream(dstFile);
        outfile.write(s1.getBytes(StandardCharsets.UTF_8));

    }
    private static void decrp(String srcFile, String dstFile, String key) throws Exception {
        File srcfile=new File(srcFile);
        Path path=srcfile.toPath();
        String s= Files.readString(path);
        String s1= AESUtils.decrypt(s, key);
        FileOutputStream outfile=new FileOutputStream(dstFile);
        outfile.write(s1.getBytes(StandardCharsets.UTF_8));

    }

}
