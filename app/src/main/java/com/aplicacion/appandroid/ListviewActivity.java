package com.aplicacion.appandroid;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListviewActivity extends AppCompatActivity {
    private List<String> listNombreArchivos;
    private List<String> listRutaArchivos;
    private ArrayAdapter<String> adaptador;
    private String directorioRaiz;
    private TextView carpetaActual;
    List<Map<String, String>> interestsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        carpetaActual = findViewById(R.id.rutaActual);

        directorioRaiz = Environment.getExternalStorageDirectory().getPath();
        //directorioRaiz = getFilesDir().getPath();
        //directorioRaiz="/data/emmulated";
        verArchivoDirectorio(directorioRaiz);
        //fillExpandibleList();
        final ListView lv = findViewById(R.id.lv_Files);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                String message="";
                message+=lv.getItemAtPosition(position).toString();
                String itemSelected="";
                int startPosition =10;
                while (startPosition<message.length()-1){
                    itemSelected+=message.charAt(startPosition);
                    startPosition++;
                }
                Toast.makeText(ListviewActivity.this, "Has seleccionado: "+itemSelected, Toast.LENGTH_SHORT).show();
                */
                File archivo = new File(listRutaArchivos.get(position));
                if(archivo.isFile()){
                    String ubicacion = archivo.getAbsolutePath();
                    Intent i = new Intent(ListviewActivity.this,ExplorerActivity.class);
                    i.putExtra("ubicacion", ubicacion);
                    startActivity(i);
                }else{
                    verArchivoDirectorio(listRutaArchivos.get(position));
                }
            }
        });

    }
    public void fillExpandibleList() {

        List<Map<String, String>> interestsList = new ArrayList<Map<String,String>>();
        interestsList.add(createInterest("interest", "Sports"));
        interestsList.add(createInterest("interest", "Video Games"));
        interestsList.add(createInterest("interest", "Studying"));
        interestsList.add(createInterest("interest", "Volunteering"));

        SimpleAdapter simpleAdpterForListView =
                new SimpleAdapter(this, interestsList, android.R.layout.simple_list_item_checked,
                        new String[] {"interest"}, new int[] {android.R.id.text1});

        ListView lv = (ListView) findViewById(R.id.lv_Files);

        lv.setAdapter(simpleAdpterForListView);

    }
    private HashMap<String, String> createInterest(String key, String name) {
        HashMap<String, String> interest = new HashMap<String, String>();
        interest.put(key, name);
        return interest;

    }

    private void verArchivoDirectorio(String rutaDirectorio){
        carpetaActual.setText("Estas en: "+ rutaDirectorio);
        listNombreArchivos = new ArrayList<String>();
        listRutaArchivos = new ArrayList<String>();
        interestsList = new ArrayList<Map<String,String>>();
        File directorioActual = new File(rutaDirectorio);
        File[] listaArchivos = directorioActual.listFiles();

        int x = 0;
        if(!rutaDirectorio.equals(directorioRaiz)){
            listNombreArchivos.add("../");
            listRutaArchivos.add(directorioActual.getParent());
            x = 1;
        }
        for (File archivo : listaArchivos){
            listRutaArchivos.add(archivo.getPath());
        }
        Collections.sort(listRutaArchivos, String.CASE_INSENSITIVE_ORDER);
        for(int i = x; i<listRutaArchivos.size(); i++){
            File archivo = new File(listRutaArchivos.get(i));
            if(archivo.isFile()){
                listNombreArchivos.add(archivo.getName());
            }else{
                listNombreArchivos.add("/"+archivo.getName());
            }
        }
        if(listaArchivos.length<1){
            listNombreArchivos.add("No hay ningun archivo");
            listRutaArchivos.add(rutaDirectorio);
        }
        fillList();
    }
    private void fillList(){
        for(int i =0; i<listNombreArchivos.size(); i++){
            interestsList.add(createInterest("interest", listNombreArchivos.get(i)));
        }
        SimpleAdapter simpleAdpterForListView =
                new SimpleAdapter(this, interestsList, android.R.layout.simple_list_item_checked,
                        new String[] {"interest"}, new int[] {android.R.id.text1});

        ListView lv = (ListView) findViewById(R.id.lv_Files);

        lv.setAdapter(simpleAdpterForListView);
    }
    /*
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        File archivo = new File(listRutaArchivos.get(position));
        if(archivo.isFile()){
            String ubicacion = archivo.getAbsolutePath();
            Intent i = new Intent(this,ExplorerActivity.class);
            i.putExtra("ubicacion", ubicacion);
            startActivity(i);
        }else{
            verArchivoDirectorio(listRutaArchivos.get(position));
        }
    }
    */
}
