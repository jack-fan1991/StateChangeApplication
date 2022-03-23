package com.example.logger;

import android.util.Log;

import java.util.Locale;

public class Logger {
    public static final String TAG = "GSH";
    private static final String FULL_TAG = Logger.class.getName();
    private static final int PACKAGE_LENGTH = Logger.class.getPackage().getName().length() + 1;  //+1 for end of '.'
    private static final boolean WRITE_TO_FILE = true;

    //前贅定義
    private static String prefixFormat(String fileName, String className, String methodName, int lineNumber) {
        return String.format("(%s:%d): %s.class: %s(): ", fileName, lineNumber, className, methodName);
    }

    private static String formatPrefixByTarget(StackTraceElement[] elements) {
        StackTraceElement target = null;
        int count = elements.length;
        for (int i = 0; i < count; i++) {
            StackTraceElement current = elements[i];
            if (current.getClassName().equals(FULL_TAG)) {
                if (i + 1 < count) {
                    //找到呼叫此方法的stack
                    target = elements[i + 1];
                }
                break;
            }
        }
        if (target == null) {
            return "";
        }
        String className = target.getClassName();
        String fileName = target.getFileName();
        className = className.substring(className.lastIndexOf('.') + 1);  //+1 for
        return prefixFormat(fileName, className, target.getMethodName(), target.getLineNumber());
    }

    private static String formatPrefixByTarget(int targetIdx, StackTraceElement[] elements) {
        StackTraceElement target = null;
        int count = elements.length;
        for (int i = 0; i < count; i++) {
            StackTraceElement current = elements[i];
            if (current.getClassName().equals(FULL_TAG)) {
                if (i + 1 < count) {
                    //找到呼叫此方法的stack
                    target = elements[i + 1 + targetIdx];
                }
                break;
            }
        }
        if (target == null) {
            return "";
        }
        String className = target.getClassName();
        String fileName = target.getFileName();
        className = className.substring(className.lastIndexOf('.') + 1);  //+1 for
        return prefixFormat(fileName, className, target.getMethodName(), target.getLineNumber());
    }


    /**
     * 向上查詢調用者
     *
     * @param stacks 向上查詢深度
     */
    public static void trackingMethod(String format, String targetMethodName, int stacks, Object... parameters) {

        String message = String.format(Locale.US, format, parameters);
        message = "-------" + message + "--tracking  "+targetMethodName  +"------->";
        String call="";
        String result = "";
        String lastCall = "";
        for (int i = 0; i < stacks; i++) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            call+=formatPrefixByTarget(i, elements)+"-->";
            if(i==stacks-1){
                lastCall = formatPrefixByTarget(i, elements);
            }
            if (JUnitUtils.isJUnitTest()) {
                System.out.println(call);
            } else {
                Log.w(TAG, String.format(Locale.US,call ));
            }
        }
        if (JUnitUtils.isJUnitTest()) {
            System.out.println(result);
        } else {
            Log.w(TAG, String.format(Locale.US,lastCall+message ));
        }
    }

    /**
     * 向上查詢調用者
     *
     * @param stacks 向上查詢深度
     */
    public static void trackingMethod( String targetMethodName, int stacks) {

        String message = "-------tracking  "+targetMethodName  +"------->";
        for (int i = 0; i < stacks; i++) {
            String result = "";
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            result += message + formatPrefixByTarget(i, elements);
            if (JUnitUtils.isJUnitTest()) {
                System.out.println(result);
            } else {
                Log.e(TAG, String.format(Locale.US,result ));
            }
        }
    }


    public static void d(String format, Object... parameters) {
        StackTraceElement target = null;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String message = String.format(Locale.US, format, parameters);
        if (JUnitUtils.isJUnitTest()) {
            System.out.println(message);
        } else {
            Log.d(TAG, String.format(Locale.US, formatPrefixByTarget(elements)) + message);
        }

    }

    public static void i(String format, Object... parameters) {
        StackTraceElement target = null;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String message = String.format(Locale.US, format, parameters);
        if (JUnitUtils.isJUnitTest()) {
            System.out.println(message);
        } else {
            Log.i(TAG, String.format(Locale.US, formatPrefixByTarget(elements)) + message);
        }
    }

    public static void w(String format, Object... parameters) {
        StackTraceElement target = null;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String message = String.format(Locale.US, format, parameters);
        if (JUnitUtils.isJUnitTest()) {
            System.out.println(message);
        } else {
            Log.w(TAG, String.format(Locale.US, formatPrefixByTarget(elements)) + message);
        }
    }

    public static void e(String format, Object... parameters) {
        StackTraceElement target = null;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String message = String.format(Locale.US, format, parameters);
        if (JUnitUtils.isJUnitTest()) {
            System.out.println(message);
        } else {
            Log.e(TAG, String.format(Locale.US, formatPrefixByTarget(elements)) + message);
        }
//    }
//    public static void e(String format, Object... parameters) {
//        StackTraceElement target = null;
//        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
//        int count = elements.length;
//        for (int i = 0; i < count; i++) {
//            StackTraceElement current = elements[i];
//            if (current.getClassName().equals(FULL_TAG)) {
//                if (i + 1 < count) {
//                    target = elements[i + 1];
//                }
//                break;
//            }
//        }
//
//        if (target == null) {
//            return;
//        }
//
//        String className = target.getClassName();
//        className = className.substring(className.lastIndexOf('.') + 1);
//        int index = className.indexOf('$');
////        if( index != ConstantService.NOT_FOUND )
////        {
////            className = className.substring(0, index);
////        }
//        className = className.substring(0, index);
//        String message = String.format(Locale.US, format, parameters);
//        String log = String.format(Locale.US,
//                "%s@(%s.java:%d): %s", target.getMethodName(), className, target.getLineNumber(), message);
//        Logger.e( log);

//        if( WRITE_TO_FILE )
//        {
//            try
//            {
//                massStorageDevice.write(log+"\r\n");
//            }
//            catch (Exception e)
//            {
//                Logger.e( "exception: "+e.toString());
//            }
//        }
    }

//    public static void init(@NonNull Context context)
//    {
//        try
//        {
//            massStorageDevice = new MassStorageDevice(context);
//            String fileName = String.format(Locale.US, "%s.log", StringUtility.toDate(new Date()));
//            massStorageDevice.open("exception", fileName);
//        }
//        catch (IOException e)
//        {
//            Logger.e( "(msd)exception: "+e.toString());
//            //massStorageDevice = null;
//        }
//    }
//
//    private static MassStorageDevice massStorageDevice;
}