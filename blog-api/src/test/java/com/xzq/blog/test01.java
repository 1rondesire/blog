package com.xzq.blog;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class test01 {
    int a;
    @Test
    void test02(){
        String s = DigestUtils.md5Hex(123456 + "mszlu!@#");
        System.out.println(s);
        test03(a);
    }
    void test03(Object o){
        System.out.println(o);
    }
    @Test
    void test04(){
//        Double a = 1.2;
//        Double b = 1.2;
//false
//        int a =12;
//        int b =12;
//true
//        double a =1.2;
//        double b =1.2;
//true
//        Integer a = 200;
//        Integer b = 200;
//flase
//        Integer a = 12;
//        Integer b = 12;
//true
//        Integer a = 12;
//        Integer b = new Integer(12);
//false
//        Boolean a = true;
//        Boolean b = new Boolean(true);
//        Character a = 127;
//        Character b = 127;

        Double a = 1.0;
        Double b =1.00;
        System.out.println(a==b);
        System.out.println(a.equals(b));
        int[] ints = new int[2];
        Map<Integer,Integer> map = new HashMap<>();
    }
}
