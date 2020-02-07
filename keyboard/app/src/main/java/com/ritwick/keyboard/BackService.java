
package com.ritwick.keyboard;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

public class BackService extends Service {

    dbmaker db;
    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("BACKGROUND","SERVICE CREATED");
         db = new dbmaker(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent!=null)
        {
            Log.e("BACKGROUND"," "+ intent.getStringExtra("Character"));

            writeToSDFile((intent.getStringExtra("Character")));

        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void writeToSDFile(String data){


    boolean done = db.insertdata(data);
    if(done==true)
        Log.e("VALUES DB","INSERTED");
    else
        Log.e("VALUES DB","FAAILED");
    Cursor c = db.getData();
    if(c.getCount()==0) {
        Log.e("DATABASE","EMOPTY");
        return;
    }
    while(c.moveToNext()){
        Log.e("VALUES",c.getString(0));
    }

    }

}