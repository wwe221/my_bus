package com.ebookfrenzy.tablayoutdemo;


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



/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2Fragment extends Fragment {
    Document Doc;
    ListView listview ;
    ListViewAdapter adapter;
    String s = "";
    String bus;
    String Bus[] ={"1","2","3","4","5"};
    String texts[] = new String[5];

    public Tab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        listview = (ListView) view.findViewById(R.id.listview1);
        adapter = (ListViewAdapter)new ListViewAdapter() ;
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;
                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                // TODO : use item data.
            }
        }) ;
        new GetXMLTask2().execute();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return view;
    }
    private class GetXMLTask2 extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try{
                Log.i("@@@@","로그인");
                String q="100000180";
                // 100000180 상명대 정문
                // 100000040 상명대 입구 7018
                // 100000189 상명대 입구
                // 100000021 경복궁역
                url = new URL("http://ws.bus.go.kr/api/rest/arrive/getLowArrInfoByStId?ServiceKey=ZUpYRcYG5aEM7ugMSQoNmjtAc0leLgZoJJqH2Z5feXJGJkYa1EGV3Ag6evY6QU%2FKczL6URU8QkG7lO%2FO%2FAkZCA%3D%3D&" +
                        "stId=100000180");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Doc = db.parse(new InputSource(url.openStream()));
                Doc.getDocumentElement().normalize();
            } catch (Exception e) {
                Log.i("try","wrong");
            }
            return Doc;
        }
        @Override
        protected void onPostExecute(Document doc) {
            Log.i("Root element :",doc.getDocumentElement().getNodeName());// root tag
            NodeList nodeList = doc.getElementsByTagName("itemList");
            Log.i("파싱할 리스트 수 : ",nodeList.getLength()+"개");
            for(int i = 0; i< nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                Element eElement = (Element) node;
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                            bus=getTagValue("rtNm", eElement);
                            s = "이번: " + getTagValue("arrmsg1", eElement) + "     ";
                            s += "탑승: " + getTagValue("reride_Num1", eElement) + "명" + "\n";
                            s += "다음: " + getTagValue("arrmsg2", eElement) + "     ";
                            s += "탑승: " + getTagValue("reride_Num2", eElement) + "명" + "\n";
                            Bus[0] = bus;
                            texts[0] = s;
                            Log.i("@@@",bus);

                }
            }
            print();
            super.onPostExecute(doc);
        }

    }
    public void print(){
            adapter.addItem(Bus[0],texts[0]);
            adapter.notifyDataSetChanged();
    }
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }

}
