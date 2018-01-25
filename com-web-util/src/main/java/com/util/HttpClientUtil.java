package com.util;


import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * httpclient-4.1工具类
 *
 * @author kouyi
 * @version 1.0
 */
public class HttpClientUtil {
    // http代理参数
    private static final boolean isproxy = false; // 设置代理开关
    private static final String proxy_IP = "127.0.0.1";
    private static final int proxy_POST = 8088;
    private static final String proxy_USER = "test";
    private static final String proxy_PASSWD = "test";
    private static final String proxy_DOMAIN = "domain";
    // http连接参数
    private static final int proxy_CONNECTTIMEOUT = 20000; // 连接超时毫秒
    private static final int proxy_SOTIMEOUT = 30000; // 读取超时毫秒
    private static final boolean proxy_SO_REUSEADDR = true; // 是否重用地址
    private static final boolean proxy_TCP_NODELAY = true; // 是否禁用nodelay算法
    private static final boolean proxy_STALE_CONNECTION_CHECK = false; // 是否开启陈旧的连接检查

    /**
     * 执行httpclient GET请求
     *
     * @param url
     * @return
     */
    public static String callHttpGet(String url) {
        return call(new HttpGet(url));
    }


    /**
     * httpclient调用执行
     *
     * @param base
     * @return
     */
    private static String call(HttpRequestBase base) {
        HttpClient httpClient = null;
        try {
            httpClient = getHttpClient();
            HttpResponse response = httpClient.execute(base);
            StatusLine httpstatus = response.getStatusLine();
            if (httpstatus.getStatusCode() != HttpStatus.SC_OK) {
                System.out.println("httpclient：调用[" + base.getURI() + "]失败,状态码[" + httpstatus.getStatusCode() + "]");
                return null;
            }
            HttpEntity resEntity = response.getEntity();
            //String charSet = EntityUtils.getContentCharSet(resEntity); // 获取字符编码
            String relsult = EntityUtils.toString(resEntity, "UTF-8");
            EntityUtils.consume(resEntity); // 关闭流
            return relsult;
        } catch (Exception e) {
            System.out.println("httpclient：调用[" + base.getURI() + "]失败,原因[" + e.getLocalizedMessage() + "]");
            return null;
        } finally {
            if (httpClient != null) { // 关闭连接
                httpClient.getConnectionManager().shutdown();
            }
        }
    }

    /**
     * 创建httpclient对象
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    private static HttpClient getHttpClient() {
        KeyStore trustStore;
        SSLSocketFactory sf = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            sf = new HttpSSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            System.out.println("获取httpclient失败: [" + e.getMessage() + "]");
        }
        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schReg.register(new Scheme("https", sf, 443));

        DefaultHttpClient httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(schReg));

        // 处理是否需要代理
        if (isproxy) {
            // 加载代理配置
            HttpHost proxy = new HttpHost(proxy_IP, proxy_POST, HttpHost.DEFAULT_SCHEME_NAME);
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            // NT认证
            NTCredentials ntCredentials = new NTCredentials(proxy_USER, proxy_PASSWD, proxy_IP, proxy_DOMAIN);
            httpClient.getCredentialsProvider().setCredentials(
                    new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM, AuthScope.ANY_SCHEME), ntCredentials);
            // 认证协议类型，
            List<String> authpref = new ArrayList<String>();
            authpref.add(AuthPolicy.NTLM);
            authpref.add(AuthPolicy.BASIC);
            authpref.add(AuthPolicy.DIGEST);
            httpClient.getParams().setParameter(AuthPNames.PROXY_AUTH_PREF, authpref);
        }
        httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, proxy_CONNECTTIMEOUT);
        httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, proxy_SOTIMEOUT);
        httpClient.getParams().setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, proxy_TCP_NODELAY);
        httpClient.getParams().setBooleanParameter(CoreConnectionPNames.SO_REUSEADDR, proxy_SO_REUSEADDR);
        httpClient.getParams().setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, proxy_STALE_CONNECTION_CHECK);
        return httpClient;
    }

    /**
     * 执行httpclient POST请求
     *
     * @param url
     * @param mapParams map参数
     * @return
     * @throws Exception
     */
    public static String callHttpPost_Map(String url, Map<String, String> mapParams) {
        HttpPost post = new HttpPost(url);
        List<NameValuePair> data = null;
        UrlEncodedFormEntity formEntity = null;
        if (mapParams != null) {
            data = new ArrayList<NameValuePair>(mapParams.size());
            for (Entry<String, String> entry : mapParams.entrySet()) {
                data.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 对参数编码
            try {
                formEntity = new UrlEncodedFormEntity(data, HTTP.UTF_8);
            } catch (UnsupportedEncodingException e) {
                System.out.println("httpclient：编码POST参数失败,原因[" + e.getLocalizedMessage() + "]");
                return null;
            }
            post.setEntity(formEntity);
        }
        return call(post);
    }

    /**
     * 执行httpclient POST请求
     *
     * @param url
     * @param body 字符串参数
     * @return 请求返回的串
     */
    public static String callHttpPost_String(String url, String body) throws Exception {
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(body, HTTP.UTF_8);
        entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "UTF-8"));
        post.setEntity(entity);
        return call(post);
    }
}
