package com.company;

import java.io.*;

/**
 * Created by Ярослав on 25.10.2016.
 */
public class FileTest {
    public static void main(String[] args) {
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        File file = new File("D:\\java.txt");

        try {
//            dataInputStream = new DataInputStream(new FileInputStream("D:\\java.txt"));
            dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            dataOutputStream.writeUTF("Hello1");
            dataOutputStream.flush();
            dataOutputStream.close();
            String string = null;
/*            while ((dataInputStream.available()) > 0) {
                string = dataInputStream.readUTF();
            }*/
dataInputStream = new DataInputStream(new FileInputStream(file));
            System.out.println(dataInputStream.readUTF());
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        }
    }
