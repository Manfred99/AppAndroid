package com.aplicacion.appandroid;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class SearchinserverActivity extends AppCompatActivity {
    public  String listOfFiles1 = null;
    List<Map<String, String>> interestsList;
    private List<String> listNombreArchivos;
    String itemSelectedInterests="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchinserver);

        new AsyncTask<Integer, Void, Void>(){
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    List_Process list = new List_Process();
                    list.lista("ls /home/transferftp/ftp/files");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);
        new AsyncTask<Integer, Void, Void>(){
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    List_Process list = new List_Process();
                    list.lista("ls /home/transferftp/ftp/files");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);
        final ListView listView = findViewById(R.id.ls_FilesFTP);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "";
                message+=listView.getItemAtPosition(position).toString();
                itemSelectedInterests="";
                int startPosition = 10;
                while(startPosition<message.length()-1){
                    itemSelectedInterests+=message.charAt(startPosition);
                    startPosition++;
                }
                Toast.makeText(SearchinserverActivity.this, "Has seleccionado: "+itemSelectedInterests, Toast.LENGTH_SHORT).show();
            }
        });
        listOfFiles1 = List_Process.listOfProcess;
        interestsList = new ArrayList<Map<String,String>>();
        listNombreArchivos = new ArrayList<String>();
        fillListNameFiles();
        fillList();
        Toast.makeText(SearchinserverActivity.this, "Hello", Toast.LENGTH_SHORT).show();
    }
    private void fillList(){
        for(int i =0; i<listNombreArchivos.size(); i++){
            interestsList.add(createInterest("interest", listNombreArchivos.get(i)));
        }
        SimpleAdapter simpleAdpterForListView =
                new SimpleAdapter(this, interestsList, android.R.layout.simple_list_item_checked,
                        new String[] {"interest"}, new int[] {android.R.id.text1});

        ListView lv = (ListView) findViewById(R.id.ls_FilesFTP);

        lv.setAdapter(simpleAdpterForListView);
    }
    private HashMap<String, String> createInterest(String key, String name) {
        HashMap<String, String> interest = new HashMap<String, String>();
        interest.put(key, name);
        return interest;

    }
    private void fillListNameFiles(){
        StringTokenizer st = new StringTokenizer(listOfFiles1, "\n");
        while (st.hasMoreTokens()){
            listNombreArchivos.add(st.nextToken());
        }
    }
}
