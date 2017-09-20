package com.example.kys_31.study_demo;

import android.os.Environment;
import android.os.IBinder;
import android.os.IInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.kys_31.study_demo.binder.Binder;
import com.example.kys_31.study_demo.binder.IPlus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "mainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            User user = new User(0,"jake",true);
            File file = new File(Environment.getExternalStorageDirectory(),"/cache.txt");
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(user);
            out.close();

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            User readUser = (User) in.readObject();
            in.close();
            Log.i(TAG,readUser.toString());
        } catch (IOException e) {
            Log.i(TAG,e.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG,"2");
        }

        IInterface plus = new IPlus() {
            @Override
            public int add(int a, int b) {
                return a+b;
            }

            @Override
            public IBinder asBinder() {
                return null;
            }
        };

        Binder binder = new Binder();
        binder.attachInterface(plus,"PLUS TWO INT");
    }

}
