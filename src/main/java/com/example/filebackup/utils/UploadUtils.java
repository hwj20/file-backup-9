package com.example.filebackup.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Logger;


public class UploadUtils {


    /**
     * 上传文件到服务器类
     *
     * @author tom
     */

        private static final String TAG = "uploadFile";
        private static final int TIME_OUT = 30 * 1000; // 超时时间
        private static final String CHARSET = "utf-8"; // 设置编码
        private static final Logger logger = Logger.getLogger(TAG);
        /**
         * Android上传文件到服务端 (POST)
         *
         * @param file 需要上传的文件
         * @param RequestURL 请求的rul
         * @return 返回响应的内容
         */
        public static String uploadFile(File file, String RequestURL, int port) {
            String result = null;
            String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
            String PREFIX = "--", LINE_END = "\r\n";
            String CONTENT_TYPE = "multipart/form-data"; // 内容类型
            try {
                URL targetUrl = new URL(RequestURL);
                URL url = new URL(targetUrl.getProtocol(),targetUrl.getHost(), port, targetUrl.getFile());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(TIME_OUT);
                conn.setConnectTimeout(TIME_OUT);
                conn.setDoInput(true); // 允许输入流
                conn.setDoOutput(true); // 允许输出流
                conn.setUseCaches(false); // 不允许使用缓存
                conn.setRequestMethod("POST"); // 请求方式
                conn.setRequestProperty("Charset", CHARSET); // 设置编码
                conn.setRequestProperty("connection", "keep-alive");
                conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
                if (file != null) {
                    /**
                     * 当文件不为空，把文件包装并且上传
                     */
                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                    String sb = PREFIX +
                            BOUNDARY +
                            LINE_END +
                            /**
                             * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
                             * filename是文件的名字，包含后缀名的 比如:abc.png
                             */
                            "Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                            + file.getName() + "\"" + LINE_END +
                            "Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END +
                            LINE_END;
                    dos.write(sb.getBytes());
                    InputStream is = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = is.read(bytes)) != -1) {
                        logger.info("data  "+ new String(bytes));
                        dos.write(bytes, 0, len);
                    }
                    is.close();
                    dos.write(LINE_END.getBytes());
                    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                    dos.write(end_data);
                    dos.flush();
                    /**
                     * 获取响应码 200=成功 当响应成功，获取响应的流
                     */
                    int res = conn.getResponseCode();
                    logger.info( "response code:" + res);
                    if(res==200) {
                        logger.info( "request success");
                        InputStream input = conn.getInputStream();
                        StringBuilder sb1 = new StringBuilder();
                        int ss;
                        while ((ss = input.read()) != -1) {
                            sb1.append((char) ss);
                        }
                        result = sb1.toString();
                        logger.info( "result : " + result);
                    }
                    else{
                        logger.warning( "request error");
                    }
                }
            } catch (IOException e) {
                logger.severe( e.toString());
            }
            return result;
        }
//        /**
//         * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
//         *
//         * @param url Service net address
//         * @param params text content
//         * @param files pictures
//         * @return String result of Service response
//         * @throws IOException
//         */
//        public static String post(String url, Map<String, String> params, Map<String, File> files)
//                throws IOException {
//            String BOUNDARY = java.util.UUID.randomUUID().toString();
//            String PREFIX = "--", LINEND = "\r\n";
//            String MULTIPART_FROM_DATA = "multipart/form-data";
//            String CHARSET = "UTF-8";
//            URL uri = new URL(url);
//            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
//            conn.setReadTimeout(10 * 1000); // 缓存的最长时间
//            conn.setDoInput(true);// 允许输入
//            conn.setDoOutput(true);// 允许输出
//            conn.setUseCaches(false); // 不允许使用缓存
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("connection", "keep-alive");
//            conn.setRequestProperty("Charsert", "UTF-8");
//            conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
//            // 首先组拼文本类型的参数
//            StringBuilder sb = new StringBuilder();
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                sb.append(PREFIX);
//                sb.append(BOUNDARY);
//                sb.append(LINEND);
//                sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
//                sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
//                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
//                sb.append(LINEND);
//                sb.append(entry.getValue());
//                sb.append(LINEND);
//            }
//            DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
//            outStream.write(sb.toString().getBytes());
//            // 发送文件数据
//            if (files != null)
//                for (Map.Entry<String, File> file : files.entrySet()) {
//                    StringBuilder sb1 = new StringBuilder();
//                    sb1.append(PREFIX);
//                    sb1.append(BOUNDARY);
//                    sb1.append(LINEND);
//                    sb1.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
//                            + file.getValue().getName() + "\"" + LINEND);
//                    sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
//                    sb1.append(LINEND);
//                    outStream.write(sb1.toString().getBytes());
//                    InputStream is = new FileInputStream(file.getValue());
//                    byte[] buffer = new byte[1024];
//                    int len = 0;
//                    while ((len = is.read(buffer)) != -1) {
//                        outStream.write(buffer, 0, len);
//                    }
//                    is.close();
//                    outStream.write(LINEND.getBytes());
//                }
//            // 请求结束标志
//            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
//            outStream.write(end_data);
//            outStream.flush();
//            // 得到响应码
//            int res = conn.getResponseCode();
//            InputStream in = conn.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
//
//            StringBuilder sb2 = new StringBuilder();
//            if (res == 200) {
//                int ch;
//                while ((ch = inputStreamReader.read()) != -1) {
//                    sb2.append(ch);
//                }
//            }
//            outStream.close();
//            conn.disconnect();
//
//            logger.info( sb2.toString());
//            return sb2.toString();
//        }

}
