package com.aplicacion.appandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ExplorerActivity extends AppCompatActivity {
    Button searchButton;
    TextView txtUbicacion;
    String ubicacion = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);
        searchButton = findViewById(R.id.btn_Search);
        txtUbicacion = findViewById(R.id.txt_PathFile);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( ExplorerActivity.this, ListviewActivity.class);
                startActivity(intent);
            }
        });
        try{
            Intent i = getIntent();
            ubicacion = i.getExtras().getString("ubicacion");
        }catch(Exception e){

        }
        if(ubicacion!="")
            abrirArchivo();

    }
    private void abrirArchivo(){
        try{
            txtUbicacion.setText(ubicacion);
            Toast.makeText(ExplorerActivity.this, ubicacion, Toast.LENGTH_SHORT).show();
        }catch(Exception e){

        }
    }
}
