package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.company.ClassNameUtil.*;


public class Server {
    private static final Logger logger = LogManager.getLogger(getCurentClassName());
    static Socket socket;
    public static void main(String[] args) throws IOException {
        logger.fatal("fatal");
        logger.warn("warn");
        logger.info("info");
        logger.error("error");
        logger.debug("debug");
        logger.trace("trace");
/*
        int port = 8080;
        new Thread(new KeyboardListener()).start();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                socket = serverSocket.accept();
                new Thread(new SocketProcessor(socket)).start();
            }
        }
*/

    }


    private static class SocketProcessor implements Runnable {
        Socket socket;

        @Override
        public void run() {
            System.out.println("Socket processor is running...");
            try {
                readInputHandler();
                writeResponce(fileToString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("processing is done");
        }

        void readInputHandler() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String str;
/*            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }*/
            while(true) {
                String s = br.readLine();
                stringBuilder.append(s);
                if(s == null || s.trim().length() == 0) {
                    break;
                }
            }
            System.out.println("Input Handler: " + stringBuilder.toString());

        }

        void writeResponce(String s) throws IOException {
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Server: Java/0.1\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            out.write((response + s).getBytes());
            System.out.println("Response sended:");
            out.flush();
//            out.close();
        }

        String fileToString() throws IOException {
            File html = new File("C:\\Users\\Ярослав\\IdeaProjects\\WebEngine\\src\\com\\company\\index.html");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(html));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                sb.append(str + "\n");
            }
            return sb.toString();
        }

        SocketProcessor(Socket socket) {
            this.socket = socket;
        }
    }
    private static class KeyboardListener implements Runnable{
        static BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        @Override
        public void run() {
            logger.info("type \"exit\" to exit");
            String str;

            while (true){
                try {
                    str = stdIn.readLine();
                    if (str.equalsIgnoreCase("exit")){
                        System.exit(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                }
            }

        }
    }



