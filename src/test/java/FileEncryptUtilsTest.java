import com.example.filebackup.utils.FileEncryptUtils;
import org.junit.Test;


public class FileEncryptUtilsTest {


    @Test
    public void encrypt() {
        String srcFile = "D:\\test_file\\123.txt";
        String dstFile = "D:\\test_file\\123.txt.erp";
        String key="awdqcf";
        try {
            FileEncryptUtils.encrypt(srcFile,dstFile,key);
        }catch (Exception exception){
            assert false;
        }
    }

    @Test
    public void decrypt() {
        String srcFile = "D:\\test_file\\123.txt.erp";
        String dstFile = "D:\\test_file\\123.txt";
        String key="awdqcf";
        try {
            FileEncryptUtils.decrypt(srcFile,dstFile,key);
        }catch (Exception exception){
            exception.printStackTrace();
            assert false;
        }
    }
}