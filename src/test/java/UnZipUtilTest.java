import com.example.filebackup.utils.UnZipUtil;
import org.junit.Test;


public class UnZipUtilTest {
    String srcPath = "D:\\test_file\\to_compress.zip";
    String dstPath = "D:\\test_file\\";

    @Test
    public void unzip() {
        try {
            UnZipUtil.unzip(srcPath,dstPath);
        }catch (Exception exception){
            assert false;
        }
        assert true;
    }
}