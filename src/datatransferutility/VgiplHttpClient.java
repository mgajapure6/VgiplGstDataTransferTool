/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatransferutility;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import static java.net.Proxy.Type.HTTP;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Mayur Gajapure
 */
public class VgiplHttpClient {
    
    public static String authenticateClient(JsonObject data) throws IOException {
        String url = null;
        String result = null;
        File f = new File("app.properties");
        try (InputStream input = new FileInputStream(f)) {
            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("loginUrl");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        InetAddress addr = InetAddress.getLocalHost();
        String ipAddress = addr.getHostAddress();
        System.out.println("login url::" + url);
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("Content-Type", "application/json");
        postRequest.addHeader("ip", ipAddress);
        Gson gson = new Gson();
        StringEntity postingString = new StringEntity(gson.toJson(data));
        postingString.setContentType(new BasicHeader("Content-Type", "application/json"));
        postRequest.setEntity(postingString);
        System.out.println("login postRequest::" + postRequest);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(postRequest)) {
            result = EntityUtils.toString(response.getEntity());
            System.out.println("login result::" + result);
        }

        return result;
    }

    public static String sendData(String data,JsonObject header, String date, String flag, String monthYear) throws IOException {
        String url = null;
        String branchId = null;
        String result = null;
        Gson gson = new Gson();
        File f = new File("app.properties");
        try (InputStream input = new FileInputStream(f)) {
            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("url");
            branchId = prop.getProperty("branchId");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        header.addProperty("branchId", branchId);
        
        InetAddress addr = InetAddress.getLocalHost();
        String ipAddress = addr.getHostAddress();

        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("Content-Type", "application/json");
        postRequest.addHeader("ip", ipAddress);
        postRequest.addHeader("user", gson.toJson(header));
        postRequest.addHeader("flag", flag);
        postRequest.addHeader("monthYear", monthYear);
        
        StringEntity postingString = new StringEntity(gson.toJson(data));
        postingString.setContentType(new BasicHeader("Content-Type", "application/json"));
        System.out.println("postingString::" + postingString);
        postRequest.setEntity(postingString);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(postRequest)) {

            result = EntityUtils.toString(response.getEntity());
            System.out.println("sendData result::" + result);
        }
        return result;
    }

}
