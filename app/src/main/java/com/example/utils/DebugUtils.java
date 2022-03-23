package com.example.utils;

public class DebugUtils {
    public static String listenerTrace(Object  listener, Object state){
        String listenerName = listener.getClass().toString().split("\\.")[listener.getClass().toString().split("\\.").length-1];
        String stateName = state.getClass().toString().split("\\.")[state.getClass().toString().split("\\.").length-1];
        return  stateName + " , 立刻通知" +listenerName;
    }
}
