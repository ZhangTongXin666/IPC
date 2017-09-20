// CustomSender.aidl
package com.example.kys_31.study_demo;

// Declare any non-default types here with import statements
import com.example.kys_31.study_demo.CustomData;
import com.example.kys_31.study_demo.CustomReceiver;

interface CustomSender {
   void sendMessage(in CustomData customData);
   void register(CustomReceiver customReceiver);
   void unRegister(CustomReceiver costomReceiver);
}
