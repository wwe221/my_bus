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
public class Tab3Fragment extends Fragment {
    Document Doc;
    ListView listview ;
    ListViewAdapter adapter;
    String Bus[] ={};
    String texts[] = new String[5];
    URL url;
    public Tab3Fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getBus myb = new getBus();
        myb.execute("100000040");
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
        //new GetXMLTask().execute("100000180");
        try{
            Thread.sleep(800);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        myb.cancel(false);
        Bus=myb.getB();
        texts=myb.getS();
        print();
        return view;
    }
    public void print() {
        for (int i = 0; i < 2; i++) {
            adapter.addItem(Bus[i], texts[i]);
        }
        adapter.notifyDataSetChanged();
    }
}
