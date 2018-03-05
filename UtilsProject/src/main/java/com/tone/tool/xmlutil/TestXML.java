package com.tone.tool.xmlutil;




//import net.sf.json.JSONObject;

import org.json.JSONObject;
import org.json.XML;

import java.util.Map;

public class TestXML {

	public static void main(String[] args) throws Exception {
		
//        String xml =
//       " <?xml version='1.0' encoding='utf-8'?>"+
//       " <inspectList>"+
//       "  <inspectGroups>"+
//       "     <inspectGroup bdccCodeA='1' codeA='' codeSystemA='' valueA='尿常规B'> "+
//       "       <inspectKs> "+
//       "         <inspectKsId bdccCode='' code='' codeSystem='' value='4403110030010000500'/> "+
//       "         <inspectKsName bdccCode='' code='' codeSystem='' value='胃肠外科'/> "+
//       "       </inspectKs>  "+
//       "       <specimenNo bdccCode='5' code='' codeSystem='' value='尿液'/>"+
//       "       <inspectDate bdccCode='' code='' codeSystem='' value='2017-07-10'/> "+
//       "       <inspectItems> "+
//       "         <inspectItem bdccCode='12217' code='1' codeSystem='' description='TX' itemName='颜色(COL)' range='淡黄色-黄色' refValueLower='' refValueUpper='' unit='' value='淡黄色' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='12218' code='1' codeSystem='' description='TX' itemName='透明度(CLT)' range='透明' refValueLower='' refValueUpper='' unit='' value='透明' abnormalMark=''/>  "+
//       "         <inspectItem bdccCode='12219' code='1' codeSystem='' description='TX' itemName='葡萄糖(Glu)' range='阴性(-)' refValueLower='' refValueUpper='' unit='' value='阴性(-)' abnormalMark=''/>"+
//       "         <inspectItem bdccCode='12220' code='1' codeSystem='' description='TX' itemName='胆红素(Bil)' range='阴性(-)' refValueLower='' refValueUpper='' unit='' value='阴性(-)' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='12224' code='1' codeSystem='' description='TX' itemName='酮体(KET)' range='阴性(-)' refValueLower='' refValueUpper='' unit='' value='阴性(-)' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='12221' code='1' codeSystem='' description='NM' itemName='酸碱度(pH)' range='5.0-7.0' refValueLower='5.0' refValueUpper='7.0' unit='' value='6.5' abnormalMark=''/>"+
//       "         <inspectItem bdccCode='12222' code='1' codeSystem='' description='TX' itemName='蛋白质(PRO)' range='阴性(-)' refValueLower='' refValueUpper='' unit='' value='阴性(-)' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='12223' code='1' codeSystem='' description='NM' itemName='比重(SG)' range='1.005-1.030' refValueLower='1.005' refValueUpper='1.030' unit='' value='1.011' abnormalMark=''/>"+
//       "         <inspectItem bdccCode='12225' code='1' codeSystem='' description='TX' itemName='尿胆原(URO)' range='阴性(-)' refValueLower='' refValueUpper='' unit='' value='阴性(-)' abnormalMark=''/>  "+
//       "         <inspectItem bdccCode='12228' code='1' codeSystem='' description='TX' itemName='亚硝酸盐(NIT)' range='阴性(-)' refValueLower='' refValueUpper='' unit='' value='阴性(-)' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='12227' code='1' codeSystem='' description='TX' itemName='红细胞(隐血,BLD)' range='阴性(-)' refValueLower='' refValueUpper='' unit='' value='阴性(-)' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='12229' code='1' codeSystem='' description='TX' itemName='白细胞(WBC)' range='阴性(-)' refValueLower='' refValueUpper='' unit='' value='阴性(-)' abnormalMark=''/>  "+
//       "         <inspectItem bdccCode='12230' code='1' codeSystem='' description='NM' itemName='沉渣红细胞(RBC.)' range='0-17.6' refValueLower='0' refValueUpper='17.6' unit='uL' value='8.00' abnormalMark=''/>"+
//       "         <inspectItem bdccCode='12307' code='1' codeSystem='' description='NM' itemName='沉渣白细胞(WBC.)' range='0-9' refValueLower='0' refValueUpper='9' unit='uL' value='7.50' abnormalMark=''/>  "+
//       "         <inspectItem bdccCode='12231' code='1' codeSystem='' description='NM' itemName='上皮细胞(EPC)' range='' refValueLower='' refValueUpper='' unit='' value='8.00' abnormalMark=''/>  "+
//       "         <inspectItem bdccCode='12232' code='1' codeSystem='' description='NM' itemName='小圆上皮细胞(SRC)' range='' refValueLower='' refValueUpper='' unit='' value='0.6' abnormalMark=''/>  "+
//       "         <inspectItem bdccCode='10150' code='1' codeSystem='' description='NM' itemName='沉渣管型(CAST)' range='0-0.62' refValueLower='0' refValueUpper='0.62' unit='ul' value='0' abnormalMark=''/>"+
//       "         <inspectItem bdccCode='10151' code='1' codeSystem='' description='NM' itemName='沉渣细菌(BACT)' range='0-3324' refValueLower='0' refValueUpper='3324' unit='ul' value='291.10' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='12246' code='1' codeSystem='' description='NM' itemName='非溶血RBC(NLRBC)' range='' refValueLower='' refValueUpper='' unit='' value='3.10' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='12247' code='1' codeSystem='' description='NM' itemName='非溶血RBC百分比(NLRBC%)' range='' refValueLower='' refValueUpper='' unit='' value='38.70' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='12248' code='1' codeSystem='' description='NM' itemName='电导率(COND.)' range='' refValueLower='' refValueUpper='' unit='' value='8.6' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='12250' code='1' codeSystem='' description='NM' itemName='红细胞形态(RBC-M)' range='' refValueLower='' refValueUpper='' unit='' value='1.4' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='12310' code='1' codeSystem='' description='NM' itemName='大红细胞(L-RBC)' range='' refValueLower='' refValueUpper='' unit='' value='0.60' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='12311' code='1' codeSystem='' description='NM' itemName='小红细胞(S-RBC)' range='' refValueLower='' refValueUpper='' unit='' value='2.40' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='10155' code='1' codeSystem='' description='NM' itemName='沉渣分类不明微粒(QTKLSL)' range='' refValueLower='' refValueUpper='' unit='ul' value='29.10' abnormalMark=''/> "+
//       "         <inspectItem bdccCode='10153' code='1' codeSystem='' description='NM' itemName='沉渣结晶数量(JJSL)' range='' refValueLower='' refValueUpper='' unit='ul' value='0.2' abnormalMark=''/> "+
//       "       </inspectItems> "+
//       "       <inspectFeeItems/>"+
//       "     </inspectGroup> "+
//       "   </inspectGroups>"+
//       " </inspectList>";
        
		String xml ="<?xml version='1.0' encoding='utf-8'?><base><producerType>1</producerType><producerId>4403110030010003366</producerId><orgId>4403110030010000000</orgId><providerId>123456789</providerId><workDate>2017-03-25</workDate><subscribeNo>44031100300100011703245615380004</subscribeNo></base>";
        /* 第一种方法，使用JSON-JAVA提供的方法 */
//		String json="{'orgId':4403110030010000000,'workDate':'2017-03-25','producerId':440311003001000336631}";
        //将xml转为json
//		JSONObject xmlJSONObj	=TestXml2Json.xml2Json(xml);
//		System.out.println("-------"+xmlJSONObj);
        
       JSONObject xmlJSONObj =null;
//        System.out.println("----"+xmlJSONObj.toJSONString());
//		try {
			xmlJSONObj = XML.toJSONObject(xml);
//        System.out.println("---000---"+xmlJSONObj.getJSONObject("inspectList").getJSONObject("inspectGroups").toString());
        System.out.println("--1-"+xmlJSONObj.toString());
//        System.out.println("--"+Base64.encode("13"));;

//		} catch (JSONException e) {
//		 TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        Map<String, Object> a= PaserDom4jXmlUtil.Dom2Map(xml);

        System.out.println("-2--"+a.toString());


////
//        System.out.println(xmlJSONObj);

        /* 第二种方法，使用json-lib提供的方法 */
        //创建 XMLSerializer对象
//        XMLSerializer xmlSerializer = new XMLSerializer();
//        //将xml转为json（注：如果是元素的属性，会在json里的key前加一个@标识）
//        String result = xmlSerializer.read(xml).toString();
//        //输出json内容
//        System.out.println(result);
//        JSONObject json=JSONObject.fromObject(result);
//        System.out.println(json.toString());
	}
	
	
}
