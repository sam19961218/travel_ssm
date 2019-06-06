package com.bug.message;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MyUtils {

    //创建连接池管理器
    private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

    static {
        //设置最大的连接数
        //就是这个链接池，最多有多少个 HttpClient
        cm.setMaxTotal(100);
        // 设置每个主机的最大连接数（每个主机的并发数）
        //比如我访问 http://yun.itheima.com 对应的主机名是 yun.itheima.com，如果我要访问很多网站，这个时候为了避免连接分配的不均匀，这个时候我们就可以限制每个主机分配的连接数
        cm.setDefaultMaxPerRoute(20);
        //使用连接池管理器发起请求
    }


    /**
     * 输入一个网址返回这个网址的字符串
     */
    public static String getHtmlByGet(String str) throws IOException {
        //不是每次创建新的HttpClient，而是重连接池中获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        HttpGet httpGet = new HttpGet(str); // 创建httpget实例
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");

        CloseableHttpResponse response = httpClient.execute(httpGet); // 执行get请求
        HttpEntity entity = response.getEntity(); // 获取返回实体
        String content = EntityUtils.toString(entity, "utf-8");
        response.close(); // 关闭流和释放系统资源
        // httpClient 不需要关闭
        return content;
    }


    /**
     * 保存网络图片到指定文件母下
     *
     * @param file 文件目录
     * @param imgUrl   http://pic.lvmama.com/uploads/pc/place2/2016-10-27/be49019a-7603-4028-bce1-f6d9e1863465_480_320.jpg
     */
    public static void saveImgToFile(File file, String imgUrl) throws IOException {
        // 若指定文件夹没有，则先创建

        // 截取图片文件名
        String fileName = imgUrl.substring(imgUrl.lastIndexOf('/') + 1, imgUrl.length());

        // 文件名里面可能有中文或者空格，所以这里要进行处理。但空格又会被URLEncoder转义为加号
        String urlTail = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        // 因此要将加号转化为UTF-8格式的%20
        imgUrl = imgUrl.substring(0, imgUrl.lastIndexOf('/') + 1) + urlTail.replaceAll("\\+", "\\%20");


        // 写出的路径
        file =  new File(file.getAbsolutePath() + File.separator + fileName);



        // 获取图片URL
        URL url = new URL(imgUrl);
        // 获得连接
        URLConnection connection = url.openConnection();
        // 设置10秒的相应时间
        connection.setConnectTimeout(10 * 1000);
        // 获得输入流
        BufferedInputStream buffin =  new BufferedInputStream(connection.getInputStream());


        // 获得输出流
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        // 构建缓冲区
        byte[] buf = new byte[1024*10];
        int size;
        // 写入到文件
        while (-1 != (size = buffin.read(buf))) {
            out.write(buf, 0, size);
        }

        buffin.close();
        out.close();
    }


//    @Test
//    public void test() throws IOException {
//        saveImgToFile("F:/Test/img","http://pic.lvmama.com/uploads/pc/place2/2016-10-27/be49019a-7603-4028-bce1-f6d9e1863465_480_320.jpg");
//    }



}
