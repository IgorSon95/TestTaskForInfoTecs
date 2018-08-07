package com.gmail.starguest95;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by isv on 20.09.17.
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        IplirEngine iplirEngine = (IplirEngine) context.getBean("iplirEngine");
        iplirEngine.search();
    }

}

