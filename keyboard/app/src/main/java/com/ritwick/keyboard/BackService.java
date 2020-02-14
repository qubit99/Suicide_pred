package com.ritwick.keyboard;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.HashSet;

import static com.ritwick.keyboard.notif.CHANNEL_ID;

public class BackService extends Service {

    private NotificationManagerCompat notifmanager;

    private StringBuffer sb = new StringBuffer();
    dbmaker db;
    int c=1;
    StringBuffer s = new StringBuffer();
    float pred[][] ;
    HashSet<String> keywords = new HashSet<>();
    Classifier cf;



    public static final String TAG = "THread";
    @Override
    public void onCreate() {
        super.onCreate();
        notifmanager = NotificationManagerCompat.from(this);
        Log.e("BACKGROUND","SERVICE CREATED");
        db = new dbmaker(this);
        createHash();
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    String testData = "I cannot take it anymore i want to kill myself. I have got testicular cancer";
                    cf  = new Classifier(testData);
                    Thread.sleep(5000);
                    //Toast.makeText(BackService.this, "Hello From Back service", Toast.LENGTH_SHORT).show();
                    //createNotif("HELLO");
                    Log.e(TAG, "Running Classifier");
                    pred=cf.classify("I cannot take it anymore i want to kill myself. I have got testicular cancer");
                    if(pred[0][0]>0.5)
                        createNotif("SUICIDAL");
                    else
                        createNotif("NOT SUICIDAL");

                } catch (Exception e) {
                    Log.e("Error", e.getMessage(), e);
                }
            }

        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent!=null) {
            String character = intent.getStringExtra("Character");

            Log.e("BACKGROUND"," "+ character);



            writeToSDFile(character);



        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void writeToSDFile(String data){
        char a = data.charAt(0);
        int asc = (int)a;

        //Log.e("Ascii value",Integer.toString(asc));
        if(asc==65531&&sb.length()>1){
            sb.delete(sb.length()-2,sb.length()-1);

        }
        else if(asc!=65531){
            if(sb.length()==20&&c>0) {
                store();
            }
            else{
                sb.append(data);
            }
        }
        Cursor c = db.getData();
        if(c.getCount()==0) {
            Log.e("DATABASE","EMPTY");
            return;
        }

        int counter = 0;
        while(c.moveToNext()){
            s.append(c.getString(0));
            Log.e("VALUES",c.getString(0));
            if (++counter == 1)
                break;
        }
    }

    public void store(){
        String[] words = sb.toString().split("\\s");
        for(String w : words){
            //Log.e("WORDS",w);
            if(keywords.contains(w)){
                boolean done = db.insertData(sb.toString());
                c= 5;
                if (done)
                    Log.e("VALUES DB", "INSERTED");
                else
                    Log.e("VALUES DB", "FAILED");
                break;
            }
            else{
                if(c>1){
                    db.insertData(sb.toString());
                    c--;
                }
                else
                    c=1;
            }

        }
        sb = new StringBuffer();
    }
    void createNotif(String x){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(this,CHANNEL_ID).setContentTitle("HEY There").setContentText(x).setSmallIcon(R.drawable.arrow).build();
            notifmanager.notify(1,notification);
        }
        Log.e("IN FUNCTION","BAckSPACE");
    }
    void createHash(){

        keywords.add("kill me");
        keywords.add("suicide");
        keywords.add("suicidal");
        keywords.add("my friend is suicidal");
        keywords.add("feel like dying");
        keywords.add("depressed");
        keywords.add("killing myself");
        keywords.add("self harm");
        keywords.add("ready to end my life");
        keywords.add("raped");
        keywords.add("live anymore");
        keywords.add("my child is having suicidal intent");
        keywords.add("son is suicidal");
        keywords.add("daughter is suicidal");
        keywords.add("ignores my problems");
        keywords.add("fucking die");
        keywords.add("lost my job");
        keywords.add("no reason to live");
        keywords.add("cut my body");
        keywords.add("kill myself");
        keywords.add("cheated on me");
        keywords.add("i cry");
        keywords.add("life sucks");

    }

}
