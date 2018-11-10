package com.aplicacion.appandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;

public class ExplorerActivity extends AppCompatActivity {
    Button searchButton;
    Button doneButton;
    TextView txtUbicacion;
    String ubicacion = "";
    MemoryShare ms;
    String answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);
        ms = new MemoryShare();
        answer = "";
        searchButton = findViewById(R.id.btn_Search);
        doneButton = findViewById(R.id.btn_Done);
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
        if(ubicacion!=""){
            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AsyncTask<Integer, Void, Void>(){
                        @Override
                        protected Void doInBackground(Integer... params) {
                            try {
                                uploadFile();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute(1);

                     //answer =  ms.getAnswerServer();
                     //System.out.println("#############"+answer);
                    Toast.makeText(ExplorerActivity.this, "Exito", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }
    private void abrirArchivo(){
        try{
            txtUbicacion.setText(ubicacion);
            Toast.makeText(ExplorerActivity.this, ubicacion, Toast.LENGTH_SHORT).show();
        }catch(Exception e){

        }
    }
    private void uploadFile(){
        FTPClient client = new FTPClient();
        String sFTP = "192.168.1.14";//direccion del servidor
        String sUser = "transferftp";//usuario
        String sPassword = "FTPDEV";//contraseña
        boolean getIn=false;
        try {
            client.connect(sFTP, 21);
            boolean login = client.login(sUser, sPassword);
            System.out.println(login);
            client.changeWorkingDirectory("files");
            int reply = client.getReplyCode();
            System.out.println("Reply "+reply);

            if(FTPReply.isPositiveCompletion(reply)){
                File file = new File(ubicacion);
                FileInputStream input = new FileInputStream(file);
                client.setFileType(FTP.BINARY_FILE_TYPE);
                client.enterLocalPassiveMode();
                System.out.println("Subio correctamente el archivo "+file.getName());
                if(!client.storeFile(file.getName(), input)){
                    System.out.println("Subida fallida");
                    getIn=true;
                }
                System.out.println("Replay "+client.getReplyCode());
                input.close();
            }
            client.logout();
            client.disconnect();
        } catch (Exception ioe) {
            System.out.println("no funciono mier");
            getIn=true;
        }
        String salida = "";
        if(getIn==true){
            salida = "Fallido";
        }else {
            salida = "Exito";
        }
        ms.setAnswerServer(salida);
        answer = salida;
    }
}
