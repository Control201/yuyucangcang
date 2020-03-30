package com.share.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * @Description TODO:根据ip获取所在城市
 * @Author YuYu
 * @Date 2020-02-16 19:46
 * @Version 1.0
 */
public class AddressByIpUtils {

    public static String getAddressByIP(String strIP) {
        String address = "";
        try {
            URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + strIP);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
//   {"code":0,"data":{"ip":"xxx","country":"中国","area":"","region":"湖北","city":"武汉","county":"XX","isp":"移动","country_id":"CN","area_id":"","region_id":"420000","city_id":"420100","county_id":"xx","isp_id":"100025"}}
            }
            String resultStr = result.toString();
            //省份
            int a = resultStr.indexOf("region") + 9;
            int b = resultStr.indexOf("city") - 3;
            //城市
            int c = resultStr.indexOf("city") + 7;
            int d = resultStr.indexOf("county") - 3;
            //国家
            int e = resultStr.indexOf("country") + 10;
            int f = resultStr.indexOf("area") - 3;
            String country = resultStr.substring(e,f);
            if (!country.equals("中国")){
                return country;
            }
            //国内则省份加城市
            address = resultStr.substring(a, b) + resultStr.substring(c, d);
            return address;
        } catch (Exception e) {
            return "喵星球";
        }
    }
}
