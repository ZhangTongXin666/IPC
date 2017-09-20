package com.example.kys_31.study_demo;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.security.Permission;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by kys_31 on 2017/9/16.
 */

public class BookManagerService extends Service {

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>(); //支撑并发读/写，自动实现线程同步
    private RemoteCallbackList<IOnNewBookAArrivedListener> mListenerList = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(int bookId, String bookName) throws RemoteException {
            Book book = new Book(bookId,bookName);
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookAArrivedListener regiter) throws RemoteException {
            Log.e("TAG", "连接服务端对象地址："+regiter.asBinder().toString());
            mListenerList.register(regiter);
            Log.e("TAG", "这个客户成功注册监听");
        }

        @Override
        public void unRegisterListener(IOnNewBookAArrivedListener unRegister) throws RemoteException {
            Log.e("TAG", "解除连接服务端对象地址："+unRegister.asBinder().toString());
            mListenerList.unregister(unRegister);
                Log.e("TAG", "这个客户成功解除监听");
        }
    };

    @Override
    public void onCreate(){
        super.onCreate();
        mBookList.add(new Book(0, "android"));
        mBookList.add(new Book(1, "IOS"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(5000);
                        int bookID = mBookList.size()+1;
                        String bookName = "newBook#"+bookID;
                        Book book = new Book(bookID, bookName);
                        onNewBookArrived(book);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        int size = mListenerList.beginBroadcast();
        for (int i = 0; i < size; i++){
            IOnNewBookAArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null)
            listener.onNewBookArrived(book);
        }
        mListenerList.finishBroadcast();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.example.kys_31.study_demo.permission.ACCESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED)return null;
        return mBinder;
    }

}
