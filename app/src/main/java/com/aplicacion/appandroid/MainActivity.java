package com.aplicacion.appandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button process;

        process = (Button) findViewById(R.id.btn_process);

        new AsyncTask<Integer, Void, Void>(){
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    List_Process list = new List_Process();
                    list.lista();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);

        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( MainActivity.this, ViewProcess.class);

                Bundle bundle=new Bundle();
                bundle.putString("NAME", "Procesos");

                //coloca el mensaje que la actividad va a transmitir
                intent.putExtras(bundle);


                //hace el paso de actividades

                startActivity(intent);
                Toast.makeText(MainActivity.this, "Process", Toast.LENGTH_LONG).show();
            }
        });
        final Button goToExplorerPath = findViewById(R.id.btn_GoExplorerPath);
        goToExplorerPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( MainActivity.this, ExplorerActivity.class);
                startActivity(intent);
            }
        });
    }
}
