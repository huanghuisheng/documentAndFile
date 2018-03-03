package com.tone.tool;

import com.tone.tool.httputils.HttpUtil;

public class Test {

    public static void main(String[] args) {
//      String html=HttpUtil.sendPost("http://localhost:8090/hoffice");

        String html= null;
        try {
//            html = HttpUtil.sendGet("http://localhost:8090/hoffice");
            html=HttpUtil.sendPost("https://localhost:8443/hoffice/");
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("----"+html);
    }

}
