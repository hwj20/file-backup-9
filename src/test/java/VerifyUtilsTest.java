import com.example.filebackup.utils.VerifyUtils;
import org.junit.Test;

import java.io.IOException;


public class VerifyUtilsTest {
    String srcFile = "D:\\test_file\\123.txt";
    String dstFile = "D:\\test_file\\123.txt";

    @Test
    public void search() {
        try {
            VerifyUtils first=new VerifyUtils();
            VerifyUtils second=new VerifyUtils();
            String firstpath=srcFile.toString().replace('\\','/');
            String secondpath=dstFile.toString().replace('\\','/');

            first.search(firstpath,firstpath);
            second.search(secondpath,secondpath);
            assert (first.map.equals(second.map));

        } catch (IOException e) {
            assert false;
        }
    }
}