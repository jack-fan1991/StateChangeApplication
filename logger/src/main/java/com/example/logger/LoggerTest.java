package com.example.logger;

import org.junit.Test;

public class LoggerTest {

    @Test
    public void testGetMultiMethodName(){
        A a =new A();
        a.test();
    }



    private  static  class A {
        public  void  test(){
            Logger.trackingMethod("TTEr","test()",4);
        }
    }

}