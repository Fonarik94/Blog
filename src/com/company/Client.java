package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Fonar on 15.10.2016.
 */
public class Client {
    public static void main(String[] args) {
        String adress = "localhost";
        int port = 1025;
        Socket socket;
        InputStream in;
        OutputStream out;

        try {
            InetAddress ip = InetAddress.getByName(adress);
            socket = new Socket(ip, port);
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
