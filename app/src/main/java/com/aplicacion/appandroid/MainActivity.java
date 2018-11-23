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
/**
 En esta actividad es para redirigirme a las dos opciones que tengo de FTP
 Con esto traigo los directorios y archivos del servidor
 Esto se pone en lugares independientes a las clases donde las voy a utilizar
 asi me evito de errores por tiempos de consulta en el server
 **/
        new AsyncTask<Integer, Void, Void>(){
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    SSHActivities list = new SSHActivities();
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
                    SSHActivities list = new SSHActivities();
                    list.lista("ls /home/transferftp/ftp/files");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);


        final Button goToExplorerPath = findViewById(R.id.btn_GoExplorerPath);
        goToExplorerPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( MainActivity.this, ExplorerActivity.class);
                startActivity(intent);
            }
        });
        final Button goToFilesServerFTP = findViewById(R.id.btn_sawTheServer);
        goToFilesServerFTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( MainActivity.this, SearchinserverActivity.class);
                startActivity(intent);
            }
        });
    }
}
