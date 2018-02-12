package com.fonarik94.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
@Slf4j
public class WakeOnLan {

    /**
     * Sending Wake-on-lan packet to broadcast addresses over all network interfaces
     *
     * @param mac - MAC address of computer, which need to wakeup
     * @throws IOException
     */
    public static void wake(String mac) {
        if (!mac.equals("")) {
            try (DatagramSocket udpSocket = new DatagramSocket()) {
                List<InetAddress> broadcastIP = getBroadcastIP();
                for (InetAddress ip : broadcastIP) {
                    log.info(">> Sending WOL packet with MAC address " + mac + " on broadcast ip: " + ip.toString());
                    DatagramPacket udpPacket = getWolPacket(mac, ip);
                    for (int i = 0; i < 10; i++) {
                        udpSocket.send(udpPacket);
                    }
                }
            } catch (SocketException e) {
                log.error(">> Cant create new datagram socket" + e.toString());
            } catch (IOException e){
                log.error(">> Cant send UDP packet: " + e.toString());
            }
        }
        else {
            log.info(">> MAC address is null. Nothing to do.");
        }
    }

    /**
     * Returns DatagramPacket instance, which maked with MAC address and InetAddress instance
     *
     * @param mac                  - MAC address of PC
     * @param broadcastInetAddress - broadcast inet address
     * @return datagram packet with mac and broadcast address and wake on lan port 9
     * @throws UnknownHostException
     */
    private static DatagramPacket getWolPacket(String mac, InetAddress broadcastInetAddress){
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
        int position = 6;
        for (int i = 0; i < 16; i++) {
            System.arraycopy(macBytes, 0, wolPacket, position, 6);
            position += 6;
        }
        return new DatagramPacket(wolPacket, 0, wolPacket.length, broadcastInetAddress, wolPort);
    }

    /**
     * Returns List of InetAddresses of all network interfaces
     *
     * @return List of InetAddresses
     * @throws SocketException
     * @throws UnknownHostException
     */
    private static List<InetAddress> getBroadcastIP() {
        List<InetAddress> broadcastInetAddresses = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfacesEnumeration = NetworkInterface.getNetworkInterfaces();
            while (networkInterfacesEnumeration.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfacesEnumeration.nextElement();
                List<InterfaceAddress> interfaceAddressList = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress e : interfaceAddressList) {
                    if (e.getBroadcast() != null) {
                        broadcastInetAddresses.add(InetAddress.getByName(e.getBroadcast().toString().replaceAll("/", "")));
                    }
                }
            }
        } catch (SocketException e) {
            log.error(">> Cant get network interfaces: " + e.toString());
        } catch (UnknownHostException e) {
            log.error(">> Unknown host: " + e.toString());
        }
        return broadcastInetAddresses;
    }

}

