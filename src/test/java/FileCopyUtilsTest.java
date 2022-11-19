import com.example.filebackup.utils.FileCopyUtils;
import org.junit.Test;


public class FileCopyUtilsTest {

    String srcPath = "D:\\test_file\\origin";
    String dstPath= "D:\\test_file\\dest";
    @Test
    public void copy() {
        try {
            FileCopyUtils.copy(srcPath, dstPath);
        }catch (Exception e){
            e.printStackTrace();
            assert false;
        }
        assert true;
    }

    /**
     * 尝试错误的复制地址
     */
    @Test
    public void copyFalse() {
        srcPath = "D:\\test_file\\origin_false_path";
        try {
            FileCopyUtils.copy(srcPath, dstPath);
        }catch (Exception e){
            assert true;
        }
    }
}