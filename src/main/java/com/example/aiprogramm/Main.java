package com.example.aiprogramm;

import java.net.URL;

public class Main {

    public static void main(String[] args) {


        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL resource = cl.getResource("native/ortextensions.dll");

        if (resource != null) {
            System.out.println("DLL найден в classpath: " + resource.getPath());
        } else {
            System.out.println("DLL НЕ найден в classpath");
        }
        }
    }

