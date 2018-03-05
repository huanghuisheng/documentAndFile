//package com.tone.tool.httputils;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.conn.ssl.SSLContexts;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.jsoup.Jsoup;
//
//import javax.net.ssl.SSLContext;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.security.*;
//import java.security.cert.CertificateException;
//import java.util.Map;
//import java.util.TreeMap;
//
//
///**
// * 生成预支付订单号
// */
//public class WXPrepay {
//
//	private Log log = LogFactory.getLog(WXPrepay.class);
//	//统一下单接口地址
//    private  String unifiedorder;
//    //查询订单接口地址
//    private  String orderquery;
//    //关闭订单接口地址
//    private String closeorder;
//    //退款接口地址
//    private String refund;
//    private String appid;
//    private String mch_id;
//    private String nonce_str;
//    private String body;
//    private String out_trade_no;
//    private String total_fee;
//    private String spbill_create_ip;
//    private String trade_type;
//    private String notify_url;
//    private String sign;
//    private String transaction_id;
//    private String partnerKey;
//    private String sslPath;
//    // 预支付订单号
//    private String prepay_id;
//
//    /**
//     * 生成预支付订单
//     *
//     * @return
//     */
//    public String submitXmlGetPrepayId() {
//        // 创建HttpClientBuilder
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//        // HttpClient
//        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
//        HttpPost httpPost = new HttpPost(unifiedorder);
//        String xml = getPackage();
//        StringEntity entity;
//        try {
//            entity = new StringEntity(xml, "utf-8");
//            httpPost.setEntity(entity);
//            HttpResponse httpResponse;
//            // post请求
//            httpResponse = closeableHttpClient.execute(httpPost);
//            HttpEntity httpEntity = httpResponse.getEntity();
//            if (httpEntity != null) {
//                // 打印响应内容
//                String result = EntityUtils.toString(httpEntity, "UTF-8");
//                log.info("统一下单接口返回数据======>"+result);
//                //System.out.println(result);
//                // 过滤
//                result = result.replaceAll("<![CDATA[|]]>", "");
//                String prepay_id = Jsoup.parse(result).select("prepay_id").html();
//                this.prepay_id = prepay_id;
//                if (prepay_id != null)
//                    return prepay_id;
//            }
//            // 释放资源
//            closeableHttpClient.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return prepay_id;
//    }
//
//    /**
//     * 退费
//     * @return
//     * @throws KeyStoreException
//     * @throws IOException
//     * @throws CertificateException
//     * @throws NoSuchAlgorithmException
//     * @throws UnrecoverableKeyException
//     * @throws KeyManagementException
//     */
//    public String refund() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException{
//    	//加载证书
//    	 KeyStore keyStore  = KeyStore.getInstance("PKCS12");
//         FileInputStream instream = new FileInputStream(new File(getSslPath()));
//         try {
//             keyStore.load(instream, "10016225".toCharArray());
//         } finally {
//             instream.close();
//         }
//
//         // Trust own CA and all self-signed certs
//         SSLContext sslcontext = SSLContexts.custom()
//                 .loadKeyMaterial(keyStore, "10016225".toCharArray())
//                 .build();
//         // Allow TLSv1 protocol only
//         SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                 sslcontext,
//                 new String[] { "TLSv1" },
//                 null,
//                 SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//         CloseableHttpClient httpclient = HttpClients.custom()
//                 .setSSLSocketFactory(sslsf)
//                 .build();
//         String resultCode = null;
//         try {
//
//             HttpGet httpget = new HttpGet(getRefund());
//            // log.info("executing request" + httpget.getRequestLine());
//             CloseableHttpResponse response = httpclient.execute(httpget);
//             try {
//                 HttpEntity httpEntity = response.getEntity();
//                 if (httpEntity != null) {
//                	 //响应内容
//                     String result = EntityUtils.toString(httpEntity, "UTF-8");
//                     log.info("退款接口返回数据======>"+result);
//                     // 过滤  [CDATA[|]]
//                     result = result.replaceAll("<![CDATA[|]]>", "");
//                     resultCode = Jsoup.parse(result).select("result_code").html();
//                 }
//                 EntityUtils.consume(httpEntity);
//             } finally {
//                 response.close();
//             }
//         } finally {
//             httpclient.close();
//         }
//
//    	return resultCode;
//    }
//
//    //查询退款
//    public String refundquery(){
//    	return null;
//    }
//
//    /**
//     * 请求订单查询接口
//     * SUCCESS—支付成功
//     * REFUND—转入退款
//     * NOTPAY—未支付
//     * CLOSED—已关闭
//     * REVOKED—已撤销（刷卡支付）
//     * USERPAYING--用户支付中
//     * PAYERROR--支付失败(其他原因，如银行返回失败)trade_state,transaction_id
//     */
//    public String orderQuery() {
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
//        HttpPost httpPost = new HttpPost(orderquery);
//        String xml = getOrderqueryTradeNOPackage();
//        StringEntity entity;
//        String  tradeState = null;
//        String result = null;
//        try {
//            entity = new StringEntity(xml, "utf-8");
//            httpPost.setEntity(entity);
//            HttpResponse httpResponse;
//            // post请求
//            httpResponse = closeableHttpClient.execute(httpPost);
//            // getEntity()
//            HttpEntity httpEntity = httpResponse.getEntity();
//            if (httpEntity != null) {
//                // 打印响应内容
//            	result = EntityUtils.toString(httpEntity, "UTF-8");
//                log.info("查询订单接口返回数据======>"+result);
//                // 过滤
//                result = result.replaceAll("<![CDATA[|]]>", "");
//                //tradeState = Jsoup.parse(result).select("trade_state").html();
//            }
//            // 释放资源
//            closeableHttpClient.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    /**
//     * 关闭订单
//     * @return
//     * ORDERPAID 订单已支付
//     * SYSTEMERROR 系统错误
//     * ORDERNOTEXIST 订单不存在
//     * ORDERCLOSED 订单已关闭
//     * SIGNERROR 签名错误
//     * REQUIRE_POST_METHOD 请使用post方法
//     * XML_FORMAT_ERROR XML格式错误
//     */
//    public String closeOrder(){
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
//        HttpPost httpPost = new HttpPost(closeorder);
//        String xml =  getCloseorderPackage();
//        StringEntity entity;
//        String  tradeState = null;
//        try {
//            entity = new StringEntity(xml, "utf-8");
//            httpPost.setEntity(entity);
//            HttpResponse httpResponse;
//            // post请求
//            httpResponse = closeableHttpClient.execute(httpPost);
//            // getEntity()
//            HttpEntity httpEntity = httpResponse.getEntity();
//            if (httpEntity != null) {
//                // 打印响应内容
//                String result = EntityUtils.toString(httpEntity, "UTF-8");
//                log.info("关闭订单接口返回数据======>"+result);
//                // 过滤
//                result = result.replaceAll("<![CDATA[|]]>", "");
//                tradeState = Jsoup.parse(result).select("result_code").html();
//            }
//            // 释放资源
//            closeableHttpClient.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return tradeState;
//    }
//
//    //返回APP端封装参数
//    public Map<String,String> responsePackage(){
//    	 TreeMap<String, String> treeMap = new TreeMap<String, String>();
//    	 treeMap.put("appid", this.getAppid());
//         treeMap.put("partnerid", this.getMch_id());
//         treeMap.put("prepayid", this.getPrepay_id());
//         treeMap.put("package", "Sign=WXPay");
//         treeMap.put("noncestr", this.createRandomStr());
//         treeMap.put("timestamp", (System.currentTimeMillis()/1000)+"");
//         StringBuilder sb = new StringBuilder();
//         for (String key : treeMap.keySet()) {
//             sb.append(key).append("=").append(treeMap.get(key)).append("&");
//         }
//         sb.append("key=" + partnerKey);
//         sign = MD5Util.MD5Encode(sb.toString(), "utf-8",true);
//         treeMap.put("sign", sign);
//         System.out.println(treeMap.toString());
////         StringBuilder xml = new StringBuilder();
////         xml.append("<xml>\n");
////         for (Map.Entry<String, String> entry : treeMap.entrySet()) {
////             if ("body".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
////                 xml.append("<" + entry.getKey() + "><![CDATA[").append(entry.getValue()).append("]]></" + entry.getKey() + ">\n");
////             } else {
////                 xml.append("<" + entry.getKey() + ">").append(entry.getValue()).append("</" + entry.getKey() + ">\n");
////             }
////         }
////         xml.append("</xml>");
////         return xml.toString();
//         return treeMap;
//    }
//
//    //查询订单
//    //根据商户订单号查询
//    private String getOrderqueryTradeNOPackage()
//    {
//    	TreeMap<String, String> treeMap = new TreeMap<String, String>();
//    	 treeMap.put("appid", this.appid);
//         treeMap.put("mch_id", this.mch_id);
//         treeMap.put("out_trade_no", this.out_trade_no);
//         treeMap.put("nonce_str", this.createRandomStr());
//         StringBuilder sb = new StringBuilder();
//         for (String key : treeMap.keySet()) {
//             sb.append(key).append("=").append(treeMap.get(key)).append("&");
//         }
//         sb.append("key=" + partnerKey);
//         sign = MD5Util.MD5Encode(sb.toString(), "utf-8",true);
//
//         treeMap.put("sign", sign);
//         StringBuilder xml = new StringBuilder();
//         xml.append("<xml>\n");
//         for (Map.Entry<String, String> entry : treeMap.entrySet()) {
//             if ("body".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
//                 xml.append("<" + entry.getKey() + "><![CDATA[").append(entry.getValue()).append("]]></" + entry.getKey() + ">\n");
//             } else {
//                 xml.append("<" + entry.getKey() + ">").append(entry.getValue()).append("</" + entry.getKey() + ">\n");
//             }
//         }
//         xml.append("</xml>");
//         return xml.toString();
//    }
//
//    //查询订单
//    //根据微信订单号查询
//    private String getOrderqueryTransactionIdPackage()
//    {
//    	TreeMap<String, String> treeMap = new TreeMap<String, String>();
//    	 treeMap.put("appid", this.appid);
//         treeMap.put("mch_id", this.mch_id);
//         treeMap.put("transaction_id", this.getTransaction_id());
//         treeMap.put("nonce_str", this.createRandomStr());
//         StringBuilder sb = new StringBuilder();
//         for (String key : treeMap.keySet()) {
//             sb.append(key).append("=").append(treeMap.get(key)).append("&");
//         }
//         sb.append("key=" + partnerKey);
//         sign = MD5Util.MD5Encode(sb.toString(), "utf-8",true);
//
//         treeMap.put("sign", sign);
//         StringBuilder xml = new StringBuilder();
//         xml.append("<xml>\n");
//         for (Map.Entry<String, String> entry : treeMap.entrySet()) {
//             if ("body".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
//                 xml.append("<" + entry.getKey() + "><![CDATA[").append(entry.getValue()).append("]]></" + entry.getKey() + ">\n");
//             } else {
//                 xml.append("<" + entry.getKey() + ">").append(entry.getValue()).append("</" + entry.getKey() + ">\n");
//             }
//         }
//         xml.append("</xml>");
//         return xml.toString();
//    }
//
//
//    //关闭订单
//    private String getCloseorderPackage()
//    {
//    	TreeMap<String, String> treeMap = new TreeMap<String, String>();
//   	 	treeMap.put("appid", this.appid);
//        treeMap.put("mch_id", this.mch_id);
//        treeMap.put("out_trade_no", this.out_trade_no);
//        treeMap.put("nonce_str", this.createRandomStr());
//        StringBuilder sb = new StringBuilder();
//        for (String key : treeMap.keySet()) {
//            sb.append(key).append("=").append(treeMap.get(key)).append("&");
//        }
//        sb.append("key=" + partnerKey);
//        sign = MD5Util.MD5Encode(sb.toString(), "utf-8",true);
//
//        treeMap.put("sign", sign);
//        StringBuilder xml = new StringBuilder();
//        xml.append("<xml>\n");
//        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
//            if ("body".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
//                xml.append("<" + entry.getKey() + "><![CDATA[").append(entry.getValue()).append("]]></" + entry.getKey() + ">\n");
//            } else {
//                xml.append("<" + entry.getKey() + ">").append(entry.getValue()).append("</" + entry.getKey() + ">\n");
//            }
//        }
//        xml.append("</xml>");
//        return xml.toString();
//    }
//
//    //统一封装参数
//    private String getPackage() {
//        TreeMap<String, String> treeMap = new TreeMap<String, String>();
//        treeMap.put("appid", this.appid);
//        treeMap.put("mch_id", this.mch_id);
//        treeMap.put("nonce_str", this.createRandomStr());
//        treeMap.put("body", this.body);
//        treeMap.put("out_trade_no", this.out_trade_no);
//        treeMap.put("total_fee", this.total_fee);
//        treeMap.put("spbill_create_ip", this.spbill_create_ip);
//        treeMap.put("trade_type", this.trade_type);
//        treeMap.put("notify_url", this.notify_url);
//        StringBuilder sb = new StringBuilder();
//        for (String key : treeMap.keySet()) {
//            sb.append(key).append("=").append(treeMap.get(key)).append("&");
//        }
//        sb.append("key=" + partnerKey);
//        sign = MD5Util.MD5Encode(sb.toString(), "utf-8",true);
//
//        treeMap.put("sign", sign);
//        StringBuilder xml = new StringBuilder();
//        xml.append("<xml>\n");
//        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
//            if ("body".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
//                xml.append("<" + entry.getKey() + "><![CDATA[").append(entry.getValue()).append("]]></" + entry.getKey() + ">\n");
//            } else {
//                xml.append("<" + entry.getKey() + ">").append(entry.getValue()).append("</" + entry.getKey() + ">\n");
//            }
//        }
//        xml.append("</xml>");
//       // System.out.println(xml.toString());
//        return xml.toString();
//    }
//
//    private String createRandomStr(){
//    	String currTime = TenpayUtil.getCurrTime();
//        //8位日期
//        String strTime = currTime.substring(8, currTime.length());
//        //四位随机数
//        String strRandom = TenpayUtil.buildRandom(4) + "";
//        //10位序列号,可以自行调整。
//        String strReq = strTime + strRandom;
//        return strReq;
//    }
//
//    public String getAppid() {
//        return appid;
//    }
//
//    public void setAppid(String appid) {
//        this.appid = appid;
//    }
//
//    public String getMch_id() {
//        return mch_id;
//    }
//
//    public void setMch_id(String mch_id) {
//        this.mch_id = mch_id;
//    }
//
//    public String getBody() {
//        return body;
//    }
//
//    public void setBody(String body) {
//        this.body = body;
//    }
//
//    public String getOut_trade_no() {
//        return out_trade_no;
//    }
//
//    public void setOut_trade_no(String out_trade_no) {
//        this.out_trade_no = out_trade_no;
//    }
//
//    public String getTotal_fee() {
//        return total_fee;
//    }
//
//    public void setTotal_fee(String total_fee) {
//        this.total_fee = total_fee;
//    }
//
//    public String getSpbill_create_ip() {
//        return spbill_create_ip;
//    }
//
//    public void setSpbill_create_ip(String spbill_create_ip) {
//        this.spbill_create_ip = spbill_create_ip;
//    }
//
//    public String getTrade_type() {
//        return trade_type;
//    }
//
//    public void setTrade_type(String trade_type) {
//        this.trade_type = trade_type;
//    }
//
//    public String getNotify_url() {
//        return notify_url;
//    }
//
//    public void setNotify_url(String notify_url) {
//        this.notify_url = notify_url;
//    }
//
//    public String getPartnerKey() {
//        return partnerKey;
//    }
//
//    public void setPartnerKey(String partnerKey) {
//        this.partnerKey = partnerKey;
//    }
//
//	public String getPrepay_id() {
//		return prepay_id;
//	}
//
//	public void setPrepay_id(String prepay_id) {
//		this.prepay_id = prepay_id;
//	}
//
//	public String getUnifiedorder() {
//		return unifiedorder;
//	}
//
//	public void setUnifiedorder(String unifiedorder) {
//		this.unifiedorder = unifiedorder;
//	}
//
//	public String getTransaction_id() {
//		return transaction_id;
//	}
//
//	public void setTransaction_id(String transaction_id) {
//		this.transaction_id = transaction_id;
//	}
//
//	public String getCloseorder() {
//		return closeorder;
//	}
//
//	public void setCloseorder(String closeorder) {
//		this.closeorder = closeorder;
//	}
//
//	public String getOrderquery() {
//		return orderquery;
//	}
//
//	public void setOrderquery(String orderquery) {
//		this.orderquery = orderquery;
//	}
//
//	public String getRefund() {
//		return refund;
//	}
//
//	public void setRefund(String refund) {
//		this.refund = refund;
//	}
//
//	public String getSslPath() {
//		return sslPath;
//	}
//
//	public void setSslPath(String sslPath) {
//		this.sslPath = sslPath;
//	}
//
//
//
//
//}