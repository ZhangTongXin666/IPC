package com.example.kys_31.study_demo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * Created by kys_31 on 2017/9/16.
 */

public class BookManagerActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private  IBookManager iBookManager;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iBookManager = IBookManager.Stub.asInterface(iBinder);
            try {
                List<Book> list = iBookManager.getBookList();
                Log.e("TAG","服务端返回的数据(增加数据前)："+list.size());
                iBookManager.addBook(2, "pyhson");
                list = iBookManager.getBookList();
                Log.e("TAG","服务端返回的数据（增加数据后）："+list.size());
                iBookManager.registerListener(newBookArrivedListener);
                Log.e("TAG", "客户端对象地址："+newBookArrivedListener.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iBookManager = null;
            Log.e("TAG", "断开连接");
        }
    };
    private IOnNewBookAArrivedListener newBookArrivedListener = new IOnNewBookAArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            handler.obtainMessage(0,newBook).sendToTarget();
        }
    };

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Log.e("TAG", "新书到啦："+msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iBookManager != null && iBookManager.asBinder().isBinderAlive()){
            try {
                iBookManager.unRegisterListener(newBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(serviceConnection);


    }
}
