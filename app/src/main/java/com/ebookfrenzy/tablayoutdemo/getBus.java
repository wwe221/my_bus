package com.ebookfrenzy.tablayoutdemo;

import android.os.AsyncTask;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class getBus extends AsyncTask<String, Void, Document> {
    Document Doc;
    String s = "";
    String bus;
    String a = "7016";
    String b = "7018";
    String c = "1711";
    String d = "1020";
    String f = "7212";
    String A[] = {a, b, c, d, f};
    String Bus[] = {"", "", "", "", ""};
    String texts[] = new String[5];
    URL url;
    String url2="http://ws.bus.go.kr/api/rest/arrive/getLowArrInfoByStId?ServiceKey=ZUpYRcYG5aEM7ugMSQoNmjtAc0leLgZoJJqH2Z5feXJGJkYa1EGV3Ag6evY6QU%2FKczL6URU8QkG7lO%2FO%2FAkZCA%3D%3D&stId=";
    @Override
    protected Document doInBackground(String... urls) {
        try {
            Log.i("@@@@", "로그인");
            String q = urls[0];
            // 100000180 상명대 정문
            // 100000040 상명대 입구 7018
            // 100000189 상명대 입구
            // 100000021 경복궁역
            // 100000023 광화문
            url = new URL(url2+q);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Doc = db.parse(new InputSource(url.openStream()));
            Doc.getDocumentElement().normalize();
        } catch (Exception e) {
            Log.i("try", "wrong");
        }Log.i("Root element :", Doc.getDocumentElement().getNodeName());// root tag
        NodeList nodeList = Doc.getElementsByTagName("itemList");
        Log.i("파싱할 리스트 수 : ", nodeList.getLength() + "개");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element eElement = (Element) node;
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                bus = getTagValue("rtNm", eElement);
                for (int ii = 0; ii < 5; ii++) {
                    if (bus.equals(A[ii])) {
                        s = "이번: " + getTagValue("arrmsg1", eElement) + "     ";
                        s += "탑승: " + getTagValue("reride_Num1", eElement) + "명" + "\n";
                        s += "다음: " + getTagValue("arrmsg2", eElement) + "     ";
                        s += "탑승: " + getTagValue("reride_Num2", eElement) + "명" + "\n";
                        Bus[ii] = bus;
                        texts[ii] = s;
                    }
                }

            }
        }
        return Doc;
    }
    @Override
    protected void onPostExecute(Document doc) {
    }
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }
    public void swap(){
            Bus[0]=Bus[4];
            Bus[1]=Bus[3];
            Bus[3]=null;
            Bus[4]=null;
            texts[0]=texts[4];
            texts[1]=texts[3];
            texts[3]=null;
            texts[4]=null;
    }

    public String[] getB(){
        return Bus;
    }
    public String[] getS(){return texts;}

}