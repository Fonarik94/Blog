package com.fonarik94.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

public class WOL extends HttpServlet {
    private static final Logger log = LogManager.getLogger(getCurentClassName());
    private String mac;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        mac = request.getParameter("MAC");
        if (mac != null) {
            log.debug(">> Wake up PC with MAC: " + mac);
            wake(mac);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/wol.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mac = request.getParameter("MAC");
        if (mac != null) {
            log.debug(">> Wake up PC with MAC: " + mac);
            wake(mac);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/wol.jsp");
        dispatcher.forward(request, response);
    }

    private void wake(String mac) throws IOException {
        /**
         * Send wake-on-lan package
         * @param mac
         * @throws IOException
         */
        if (mac != null) {
            InetAddress localaddr = InetAddress.getLocalHost();
            log.debug(">> LocalHost: " + localaddr);
            String[] localHost = localaddr.toString().split("/");
            log.debug(">> Host name: " + localHost[0] + ";  host ip: " + localHost[1]);
            String[] localHostOctets = localHost[1].split("\\.");
            String broadcastIP = localHostOctets[0] + "." + localHostOctets[1] + "." + localHostOctets[2] + "." + "255";
            log.debug(">> Broadcast address: " + broadcastIP);
            DatagramPacket udpPacket = this.getWolPacket(mac, broadcastIP);
            DatagramSocket udpSocket = new DatagramSocket();
            for (int i = 0; i < 10; i++) {
                udpSocket.send(udpPacket);
            }
            udpSocket.close();
            log.info("Wake-on-lan package sent");
        }
    }

    private DatagramPacket getWolPacket(String mac, String broadcastIP) throws UnknownHostException {
        /**
         * Returns an array of bytes, ready for sending wake-on-lan packet over UDP
         *
         * @param mac - string MAC address like AA:BB:CC:DD:EE:FF or AA-BB-CC-DD-EE-FF
         * @return DatagramPacket, like FF x 6 and MAC x 16
         */
        InetAddress address = InetAddress.getByName(broadcastIP);
        int wolPort = 9;

        byte[] wolPacket = new byte[102];
        for (int i = 0; i < 6; i++) {
            wolPacket[i] = (byte) 0xFF;
        }
        byte[] macBytes = new byte[6];
        String[] macBytesStrArr = mac.split("(:|-)");
        if (macBytesStrArr.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address");
        } else {
            for (int i = 0; i < 6; i++) {
                macBytes[i] = (byte) Integer.parseInt(macBytesStrArr[i], 16);
            }
        }
        int pos = 6;
        for (int i = 0; i < 16; i++) {
            System.arraycopy(macBytes, 0, wolPacket, pos, 6);
            pos += 6;
        }
        return new DatagramPacket(wolPacket, wolPacket.length, address, wolPort);
    }
}

