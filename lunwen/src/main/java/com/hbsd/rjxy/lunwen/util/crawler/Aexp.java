package com.hbsd.rjxy.lunwen.util.crawler;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class Aexp {
    public Double getScore(String sId,String sPwd){
        BasicCookieStore basicCookieStore=new BasicCookieStore();

        CloseableHttpClient httpClient= HttpClients
                .custom()
                .setDefaultCookieStore(basicCookieStore)
                .build();
        try{
            String no=sId;
            String password=sPwd;
            HttpGet httpGet=new HttpGet("http://202.206.100.221:8080/StuExpbook/index/login.jsp?no="+no+"&password="+password);
            httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            httpGet.setHeader("Accept-Encoding","gzip, deflate");
            httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.9");
            httpGet.setHeader("Cache-Control","max-age=0");
            httpGet.setHeader("Connection","keep-alive");
            httpGet.setHeader("Host","202.206.100.221:8080");
            httpGet.setHeader("Upgrade-Insecure-Requests","1");
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
            httpGet.setHeader("Referer","http://202.206.100.221:8080/aexp/");
            CloseableHttpResponse response=httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode()==200){
                if(response.getEntity()!=null){
                    String content= EntityUtils.toString(response.getEntity(),"utf8");
                }
            }
            List<Cookie> cookies=basicCookieStore.getCookies();
            if(cookies.isEmpty()){
                System.out.println("Cookie Is None.");
            }else{
                for(Cookie cookie:cookies){

                }
            }
            HttpGet scoreHttpGet=new HttpGet("http://202.206.100.221:8080/StuExpbook/two/creadit.jsp");
            scoreHttpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            scoreHttpGet.setHeader("Accept-Encoding","gzip, deflate");
            scoreHttpGet.setHeader("Accept-Language","zh-CN,zh;q=0.9");
            scoreHttpGet.setHeader("Connection","keep-alive");
            scoreHttpGet.setHeader("Host","202.206.100.221:8080");
            scoreHttpGet.setHeader("Upgrade-Insecure-Requests","1");
            scoreHttpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
            scoreHttpGet.setHeader("Referer","http://202.206.100.221:8080/StuExpbook/index/indexAction.do?op=index");
            CloseableHttpResponse scoreResponse=httpClient.execute(scoreHttpGet);
            if(scoreResponse.getStatusLine().getStatusCode()==200){
                if(scoreResponse.getEntity()!=null){
                    String content=EntityUtils.toString(scoreResponse.getEntity(),"utf8");
                    Document doc= Jsoup.parse(content);
                    Elements personInfo=doc
                            .select("table.table1").get(0)
                            .select("tbody")
                            .select("tr").get(0)
                            .select("td")
                            ,scoreInfo=doc
                            .select("table.table1").get(0)
                            .select("tbody")
                            .select("tr").get(1)
                            .select("td")
                            ,certificate=doc
                            .select("table.table1").get(1)
                            .select("tbody")
                            .select("tr:gt(1)");

                    Map<String,String> personMap=new HashMap<String, String>();
                    for(Element element:personInfo){
                        String[]line=element.text().split("：");
                        personMap.put(line[0],line[1]);
                    }

                    Map<String,String>scoreMap=new HashMap<String, String>();
                    for(Element element:scoreInfo){
                        String[]line=element.text().split("：");
                        personMap.put(line[0],line[1]);
                    }
                    return Double.parseDouble(personMap.get("总学分"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
