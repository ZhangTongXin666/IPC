// ISecurityCenter.aidl
package com.example.kys_31.study_demo;

// Declare any non-default types here with import statements

interface ISecurityCenter {
  String encrypt(String content);
  String decrypt(String password);
}
