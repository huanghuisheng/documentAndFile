package com.tone.tool.xmlutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;

public class PaserXmlUtil {

    private static Logger log = LoggerFactory.getLogger(PaserXmlUtil.class);

    /**
     * 把一个xml格式的字符串转换成一个document对象 Jan 18, 2010
     *
     * @author 莫沛英
     * @param xmlStr
     * @return
     */
    public static Document getDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            // 删除非XML数据
            doc.normalize();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return doc;
    }

    // 根据一个相应的匹配格式从一个document 中获得一个节点
    public static String getNode(Document doc, String str) {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        Object result = null;
        try {
            XPathExpression expr = xpath.compile(str);
            result = expr.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            log.error(doc.getDocumentURI()+"  paserxmlUtil:数据解析异常！: 节点："+str);
            //return "<?xml version='1.0' encoding='UTF-8'?>\n<xmlinfo><globalinfo><dostatus>fail</dostatus>\n<error_message>数据解析异常！</error_message>\n</globalinfo></xmlinfo>";
        }
        NodeList nodes = null;
        if (result != null) {
            nodes = (NodeList) result;
        }
        Node node = null;
        if (nodes != null && nodes.getLength() == 1) {
            node = nodes.item(0);
        }
        if (node != null) {
            if (node.getNodeValue() != null) {
                return node.getNodeValue();
            } else {
                return "";
            }
        }
        return null;
    }

    // 根据一个相应的匹配格式从一个document 中获得一个节点List
    public static NodeList getNodeList(Document doc, String str) {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        Object result = null;
        try {
            XPathExpression expr = xpath.compile(str);
            result = expr.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            log.error(doc.getDocumentURI()+"  paserxmlUtil:数据解析异常！: 节点："+str);
        }
        NodeList nodes = null;
        if (result != null) {
            nodes = (NodeList) result;
        }

        if (nodes != null && nodes.getLength() >= 1) {
            return nodes;
        }

        return null;
    }

    /**
     * xml格式拼接
     * Des:<br>
     * Logic:<br>
     * zhangyf crated the method at 2012-9-4
     */
    public static String getXmlUtil(String node, String value){
        return "<"+node+" code=\"\" codeSystem=\"\" value=\""+value+"\"/>";
    }

    public static String getXmlUtil2(String node,String addition, String value){
        return "<"+node+" code=\"\" codeSystem=\"\" additionDescription=\""+addition+"\" value=\""+value+"\"/>";
    }

    public static String getXmlUtil3(String node,String addition2,String addition, String value){
        return "<"+node+" code=\"\" additionDescription2=\""+addition2+"\" codeSystem=\"\" additionDescription=\""+addition+"\" value=\""+value+"\"/>";
    }

}