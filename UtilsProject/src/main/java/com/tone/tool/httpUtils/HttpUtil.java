package com.tone.tool.httputils;


import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String sendGet(String url) throws IOException {
        return sendGet(url, null);
    }
    public static String sendGet(String url, Map<String, String> headers) throws IOException {
        byte[] resp = sendGet2(url, headers);
        return new String(resp, "utf-8");
    }

    public static byte[] sendGet2(String url, Map<String, String> headers) throws IOException {
        CloseableHttpClient client =HttpClients.createDefault();
        byte[] buff = new byte[0];
        try {
            HttpGet get = new HttpGet(url);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    get.addHeader(key, headers.get(key));
                }
            }
            CloseableHttpResponse response = client.execute(get);

            int code = response.getStatusLine().getStatusCode();
            if (code >= 200 && code < 300) {
                buff = EntityUtils.toByteArray(response.getEntity());
            }
            response.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {

            client.close();
        }
        return buff;
    }

    public static String sendPost(String url) {
        return requestUrl(url);
    }
    public static String sendPostProxy(String url) {
        return requestUrlProxy(url);
    }


    public static String requestUrl(String url) {
        return requestUrl(url, null, (Map) null);
    }
    public static String requestUrlProxy(String url) {
        return requestUrlProxy(url, null, (Map) null);
    }

    public static String requestUrl(String url, Map<String, String> headers, Map<String, String> body) {
        List<NameValuePair> params = new LinkedList<NameValuePair>();
        if (body != null) {
            for (String key : body.keySet()) {
                params.add(new BasicNameValuePair(key, body.get(key)));
            }
        }
        byte[] bResp = requestUrl(url, headers, params);
        try {
            return new String(bResp, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }
    public static String requestUrlProxy(String url, Map<String, String> headers, Map<String, String> body) {
        List<NameValuePair> params = new LinkedList<NameValuePair>();
        if (body != null) {
            for (String key : body.keySet()) {
                params.add(new BasicNameValuePair(key, body.get(key)));
            }
        }
        byte[] bResp = requestUrlProxy(url, headers, params);
        try {
            return new String(bResp, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    public static byte[] requestUrl(String url, Map<String, String> headers, List<NameValuePair> params) {
        CloseableHttpClient client =HttpClients.createDefault();
        byte[] buff = new byte[0];
        try {
            HttpPost post = new HttpPost(url);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    post.addHeader(key, headers.get(key));
                }
            }
            if (params != null) {
                post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            }
            HttpResponse response = client.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code >= 200 && code < 300) {
                buff = EntityUtils.toByteArray(response.getEntity());
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            client.getConnectionManager().shutdown();
        }
        return buff;
    }

    public static byte[] requestUrlProxy(String url, Map<String, String> headers, List<NameValuePair> params) {
        CloseableHttpClient client =HttpClients.createDefault();
        byte[] buff = new byte[0];
        try {
            HttpPost post = new HttpPost(url);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    post.addHeader(key, headers.get(key));
                }
            }
            if (params != null) {
                post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            }

            HttpHost proxy=new HttpHost("116.226.217.54", 9999);
            RequestConfig requestConfig= RequestConfig.custom().setProxy(proxy).build();
            post.setConfig(requestConfig);
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");

            HttpResponse response = client.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code >= 200 && code < 300) {
                buff = EntityUtils.toByteArray(response.getEntity());
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            client.getConnectionManager().shutdown();
        }
        return buff;
    }





    public static String sendPost(String url, Map<String, String> headers, Map<String, String> body) {
        return requestUrl(url, headers, body);
    }

    public static byte[] sendPost(String url, Map<String, String> headers, List<NameValuePair> params) {
        return requestUrl(url, headers, params);
    }

    public static String sendPost(String url, Map<String, String> headers, String content) {
        byte[] bResp = requestUrl(url, headers, content);
        try {
            return new String(bResp, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    public static byte[] requestUrl(String url, Map<String, String> headers, String content) {
        HttpClient client = HttpClients.createDefault();
        byte[] buff = new byte[0];
        try {
            HttpPost post = new HttpPost(url);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    post.addHeader(key, headers.get(key));
                }
            }
            if (content != null) {
                post.setEntity(new StringEntity(content, HTTP.UTF_8));
            }
            HttpResponse response = client.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code >= 200 && code < 300) {
                buff = EntityUtils.toByteArray(response.getEntity());
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            client.getConnectionManager().shutdown();
        }
        return buff;
    }






}
