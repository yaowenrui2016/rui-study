package indi.rui.study.unittest.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-03-18
 */
@Slf4j
public class HttpClientUtils {

    public static String httpPost(String url, JSON body, Map<String, String> header)
            throws Exception {
        String response;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json");
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(60000)
                    .setConnectTimeout(60000)
                    .setCookieSpec(CookieSpecs.STANDARD)
                    .build();
            httpPost.setConfig(requestConfig);
            if (header != null) {
                Iterator h = header.entrySet().iterator();
                while (h.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry) h.next();
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            if (body != null) {
                StringEntity requestEntity = new StringEntity(body.toJSONString(), "utf-8");
                requestEntity.setContentEncoding("UTF-8");
                httpPost.setEntity(requestEntity);
            }
            response = httpClient.execute(httpPost, responseHandler);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("Close Failed", e);
            }
        }
        return response;
    }

    public static String httpPost(String url, JSON body, Map<String, String> header,
                                  CookieStore cookieStore)
            throws Exception {
        String response;
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json");
            RequestConfig requestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD)
                    .setSocketTimeout(30000)
                    .setConnectTimeout(30000)
                    .build();
            httpPost.setConfig(requestConfig);
            if (header != null) {
                Iterator h = header.entrySet().iterator();
                while (h.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry) h.next();
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            if (body != null) {
                StringEntity requestEntity = new StringEntity(body.toJSONString(), "utf-8");
                requestEntity.setContentEncoding("UTF-8");
                httpPost.setEntity(requestEntity);
            }
            response = httpClient.execute(httpPost, responseHandler);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("Close Failed", e);
            }
        }
        return response;
    }


    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                @Override
                public boolean isTrusted(java.security.cert.X509Certificate[] var1, String var2) {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            log.error("Create SSL Http Client Failed", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("Create SSL Http Client Failed", e);
        } catch (KeyStoreException e) {
            log.error("Create SSL Http Client Failed", e);
        }
        return HttpClients.createDefault();
    }
}
