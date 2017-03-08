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
import java.util.Arrays;

import static com.fonarik94.utils.ClassNameUtil.getCurentClassName;

/**
 * Created by Ярослав on 01.03.2017.
 */
public class WOL extends HttpServlet {
    private static final Logger log = LogManager.getLogger(getCurentClassName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(">> MAC: " + request.getParameter("MAC"));
        wake(request.getParameter("MAC"));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/wol.jsp");
        dispatcher.forward(request, response);
    }

    private void wake(String mac) throws IOException {
        if (mac != null) {
            int wolPort = 9;
            InetAddress localaddr = InetAddress.getLocalHost();
            log.debug(">> getLocalHost: " + localaddr);
            String[] localHost = localaddr.toString().split("/");
            log.debug(">> host name: " + localHost[0] + ";  host ip: " + localHost[1]);
            String[] localHostOctets = localHost[1].split("\\.");
            log.debug(">> local host octets: " + Arrays.toString(localHostOctets));
            String br = localHostOctets[0] + "." + localHostOctets[1] + "." + localHostOctets[2] + "." + "255";
            log.debug("broadcast address: " + br);

            String broadcastIP = "10.10.25.255";
            InetAddress address = InetAddress.getByName(broadcastIP);
            DatagramPacket udpPacket = this.getWolPacket(mac);
            DatagramSocket udpSocket = new DatagramSocket(wolPort, address);
            for (int i = 0; i < 10; i++) {
                udpSocket.send(udpPacket);
            }
            udpSocket.close();
        }
    }

    /**
     * Returns an array of bytes, ready for sending like wake-on-lan packet
     *
     * @param mac - string MAC address like AA:BB:CC:DD:EE:FF or AA-BB-CC-DD-EE-FF
     * @return byte array, like FF x 6 and MAC x 16
     */
    private DatagramPacket getWolPacket(String mac) {

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
        return new DatagramPacket(wolPacket, wolPacket.length);
    }
}

