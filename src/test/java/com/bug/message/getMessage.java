package com.bug.message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.util.Properties;

/**
 * 用于爬取数据
 */
public class getMessage {

    private static int count = 0;
    private static int errorCount = 0;


    /**
     * 先游后付
     * http://vacations.lvmama.com/w/vacation/100011468
     * <p>
     * <p>
     * <p>
     * 甄选优品
     * http://dujia.lvmama.com/freetour/1601352
     */
    @Test
    public void test() throws IOException {
        String url = "http://www.lvmama.com/abroad/";
        String savaPath = "F:/Test/Tourism";
        Document doc = Jsoup.parse(MyUtils.getHtmlByGet(url)); // 解析网页 得到文档对象
        Elements elements = doc.getElementsByClass("proList-img");
        for (Element element : elements) {
            String specificUrl = element.attributes().get("href");
            String rname = element.attributes().get("title");
            if (specificUrl.contains("/vacation/") || specificUrl.contains("freetour") || specificUrl.contains("tour")) {
                count++;
                File file = new File(savaPath + File.separator + count);
                file.mkdir();
                Properties properties = new Properties();
                properties.put("rname", rname);
                properties.put("url", specificUrl);


                try {
                    //先游后付  http://vacations.lvmama.com/w/vacation/100028261
                    if (specificUrl.contains("/vacation/")) {
                        System.out.println("下载先游后付：" + count + specificUrl);
                        properties.setProperty("type", "先游后付");
                        vacation(specificUrl, file, properties);
                    } else if (specificUrl.contains("freetour")) { // http://dujia.lvmama.com/freetour/1601352
                        System.out.println("下载甄选优品：" + count + specificUrl);
                        properties.setProperty("type", "甄选优品");
                        freetour(specificUrl, file, properties);
                    } else if (specificUrl.contains("tour")) {
                        System.out.println("下载tour：" + count + specificUrl);
                        properties.setProperty("type", "tour");
                        tour(specificUrl, file, properties);
                    }
                    panduan(file);
                    System.out.println("\n\n----------------" + count + "-------------------------\n\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
        System.out.println(count);
        System.out.println("异常" + errorCount);

    }


    /**
     * 先游后付
     * http://vacations.lvmama.com/w/vacation/100028261
     */
    public static void vacation(String url, File file, Properties properties) throws IOException {
        Document doc = Jsoup.parse(MyUtils.getHtmlByGet(url));
        String routeIntroduce = doc.select("h2.d-tit em").text();//旅游景点的标题
        String price = doc.select(".total-price span.crred em").text();//价格

        properties.put("routeIntroduce", routeIntroduce);
        properties.put("price", price);
        System.out.println(properties);
        Elements select = doc.select("div.list-in ul li img");
        for (Element element : select) {
            String ingSrc = element.attributes().get("src");
            System.out.println("开始保存图片：" + ingSrc);
            MyUtils.saveImgToFile(file, ingSrc);
        }
        properties.store(new FileOutputStream(file + File.separator + "proper.properties"), "");
    }


    /**
     * 甄选优品
     * http://dujia.lvmama.com/freetour/1601352
     */
    public void freetour(String url, File file, Properties properties) throws IOException {
        Document doc = Jsoup.parse(MyUtils.getHtmlByGet(url));
        String routeIntroduce = doc.select("h1.detail_product_tit").text();//旅游景点的标题
        String price = doc.select(".product_top_price span.price_num dfn").text();//价格
        properties.put("routeIntroduce", routeIntroduce);
        properties.put("price", price);
        System.out.println(properties);

        Elements select = doc.select("ul.img_scroll_tab_list li img");
        for (Element element : select) {
            String ingSrc = element.attributes().get("src");
            System.out.println("开始保存图片：" + ingSrc);
            MyUtils.saveImgToFile(file, ingSrc);
        }
        properties.store(new FileOutputStream(file + File.separator + "proper.properties"), "");

    }


//
//    @Test
//    public void test_savexcellent() throws IOException {
//        String uri = "http://dujia.lvmama.com/freetour/1601352";
//        File file = new File("F:/Test/Tourism");
//        Properties properties = new Properties();
//
//        freetour(uri,file,properties);
//    }
//

    /**
     * http://vacations.lvmama.com/w/tour/100002574
     */
    @Test
    public void test_() throws IOException {

        String uri = "http://vacations.lvmama.com/w/tour/100029489";
        File file = new File("F:/Test/Tourism");
        Properties properties = new Properties();

        vacation(uri, file, properties);
    }


    /**
     * http://vacations.lvmama.com/w/tour/100029489
     */
    public void tour(String url, File file, Properties properties) throws IOException {
        Document doc = Jsoup.parse(MyUtils.getHtmlByGet(url));
        String routeIntroduce = doc.select("h2.d-tit em").text();//旅游景点的标题
        String price = doc.select("span.total-price span em").text();//价格

        properties.put("routeIntroduce", routeIntroduce);
        properties.put("price", price);
        System.out.println(properties);
        Elements select = doc.select("div.list-in ul li img");

        for (Element element : select) {
            String ingSrc = element.attributes().get("src");
            System.out.println("开始保存图片：" + ingSrc);
            MyUtils.saveImgToFile(file, ingSrc);
        }
        FileOutputStream outputStream = new FileOutputStream(file + File.separator + "proper.properties");
        properties.store(outputStream, "");

    }

    private void panduan(File filePath) throws IOException {
        File file = new File(filePath.getAbsolutePath() + File.separator + "proper.properties");
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));

        if (properties.get("rname") == null ||
                properties.get("price") == null ||
                properties.get("type") == null ||
                properties.get("routeIntroduce") == null) {
            errorCount++;
            System.out.println("------------------出现异常----------------" + filePath);
        }

    }


}
