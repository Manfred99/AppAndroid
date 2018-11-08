package com.aplicacion.appandroid;

import android.app.Activity;
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

public class ViewProcess extends Activity {

    public static ArrayList<String> procesos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_process);

        fillExpandibleList();
        final ListView listView = findViewById(R.id.list_viewProcess);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "";
                message+=listView.getItemAtPosition(position).toString();
                String itemSelectedInterests="";
                int startPosition = 10;
                while(startPosition<message.length()-1){
                    itemSelectedInterests+=message.charAt(startPosition);
                    startPosition++;
                }
                Toast.makeText(ViewProcess.this, "Has seleccionado: "+itemSelectedInterests, Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void fillExpandibleList() {

        List<Map<String, String>> interestsList = new ArrayList<>();
        int conta=0;
        List_Process list = new List_Process();
        String values = list.listOfProcess;
        //procesos = new ArrayList<>(values.length());
        String nameP="";
        for(int i =0; i < values.length();i++){
            char letter = values.charAt(i);
            if(conta==2){
                interestsList.add(createInterest("interest", nameP));
                conta=0;
                nameP=""+letter;
            }else{

                if(letter=='\n'){
                    conta++;
                    nameP+="  ";
                }else
                    nameP+=letter;
            }
        }

        SimpleAdapter simpleAdpterForListView =
                new SimpleAdapter(this, interestsList, android.R.layout.simple_list_item_checked,
                        new String[] {"interest"}, new int[] {android.R.id.text1});

        ListView lv = (ListView) findViewById(R.id.list_viewProcess);

        lv.setAdapter(simpleAdpterForListView);

    }


    private HashMap<String, String> createInterest(String key, String name) {
        HashMap<String, String> interest = new HashMap<String, String>();
        interest.put(key, name);
        return interest;

    }
}
