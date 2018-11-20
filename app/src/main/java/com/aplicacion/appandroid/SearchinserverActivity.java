package com.aplicacion.appandroid;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
    MemoryShare ms;
    String answer;
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
                new AsyncTask<Integer, Void, Void>(){
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                            downloadFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
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
    private void downloadFile(){
        FTPClient client = new FTPClient();
        String sFTP = "192.168.1.13";//direccion del servidor
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
                client.setFileType(FTP.BINARY_FILE_TYPE);
                //client.
                client.enterLocalPassiveMode();
                // APPROACH #1: using retrieveFile(String, OutputStream)
                String remoteFile1 = "/home/transferftp/ftp/files/"+itemSelectedInterests;
                File downloadFile1 = new File("/storage/emulated/0/DownloadsServer/"+itemSelectedInterests);
                OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
                boolean success = client.retrieveFile(remoteFile1, outputStream1);
                outputStream1.close();

                if (success) {
                    System.out.println("File #1 has been downloaded successfully.");
                }else{
                    System.out.println("############################Error");
                }
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
