import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class VerifyUtils {
    /**
     * 　　* 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    Map<String,String> map=new HashMap<>();

    protected char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    protected MessageDigest messagedigest = null;

    {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

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

    private void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }


    public void search(String rootpath,String parentroot) throws IOException {

        File root=new File(rootpath);
        if(!root.isDirectory()) {
            map.put(root.getName(),new VerifyUtils().getFileMD5String(root));
            return;
        }

        File[] srcList = root.listFiles();
        if (null != srcList && srcList.length > 0){
            //遍历源头目录下的文件列表
            for (File aSrcList : srcList) {
                //如果是目录的话
                if (aSrcList.isDirectory()) {
                    //递归调用遍历该目录
                    search(rootpath + "/" + aSrcList.getName(),parentroot);
                }else if(aSrcList.isFile()){
//                   将文件取md5值并将相对路径添加至hashmap中
                    File current=new File(rootpath + "/" + aSrcList.getName());
//                    System.out.println((rootpath + "/" + aSrcList.getName()).substring(parentroot.length()+1));
                    map.put((rootpath + "/" + aSrcList.getName()).substring(parentroot.length()+1), new VerifyUtils().getFileMD5String(new FileInputStream(current)));
                }
            }
        }
    }
}

//    public static void main(String[] args) throws  Exception{
////        String str1=new varifyUtils().getFileMD5String(new FileInputStream(new File("D:/测试/test.txt")));
////        String str2=new varifyUtils().getFileMD5String(new FileInputStream(new File("D:/test.txt")));
////        System.out.println(str1);
////        System.out.println(str2);
//
//        varifyUtils vu=new varifyUtils();
//        vu.search("D:/测试","D:/测试");
//        System.out.println(vu.map);
//
//        //662e3b2144b3665f755a52492976e31d
//        //6e87b6a554412ee577ebfa21c6555a02
//    }
//}