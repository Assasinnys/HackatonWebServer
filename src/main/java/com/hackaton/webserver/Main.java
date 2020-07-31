package com.hackaton.webserver;

public class Main {
    public static void main(String[] args) {
        WebServerMainThread server = new WebServerMainThread();
        try {
            server.start();
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
