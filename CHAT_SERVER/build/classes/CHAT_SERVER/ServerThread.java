/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHAT_SERVER;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author بلال جلال
 */
public class ServerThread extends Thread {

    PrintWriter out;
    BufferedReader in;
    ClientsHandeler ch;

    String whoStart = "32@3#$";
    String sepmsg = "*/@#*/";
    Socket client;
    String adress;
    private boolean done = false;
    private String msg;
    String sendedMsgCode = "@#41";
    String sendedMsgSep = "#25/";
    private String byeMsg = "#@124$%3";
    String senderSep = "$!!$";
    private String hiMsg = "#$%#";
    String name;

    public ServerThread(Socket client, String adress) {
        this.client = client;
        this.adress = adress;
    }

    ServerThread(Socket client, ClientsHandeler c, String adress, PrintWriter out, BufferedReader in) {
        this.client = client;
        this.adress = adress;
        this.in = in;
        this.out = out;
        this.ch = c;
    }

    public void run() {
        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    client.getInputStream()));
//
//            PrintWriter out = new PrintWriter(
//                    client.getOutputStream(), true);

            System.out.println("thread running :" + adress);
//            out.println("@#41 mos #25/ hi" + "\n");       //to Test The Messaging Machinism
//            out.println(whoStart + "/ mosup16" + " / mo" + " / mos " + " / mosu /");   //to Test The Who Message machinism
            processmsg(in, out);
            client.close();
            doBye(name);
            System.out.println("Client (" + name + ") connection closed\n");

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void processmsg(BufferedReader in, PrintWriter out) {
        try {
            while (!done) {
//                msg = in.readLine();

                if ((msg = in.readLine()) == null) {
                    done = true;
                    client.close();
                    System.err.println("null msg");
                    break;
                } else {
                    if (msg.trim().startsWith(byeMsg)) {
                        System.out.println(msg);
                        done = true;
                        doBye(msg.trim().substring(byeMsg.length()));
                        client.close();
                        break;
                    } else {
                        dorequest(msg, out);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println(ex + "\n" + ex.toString());
            try {
                client.close();
            } catch (Exception ex1) {
                System.err.println(ex1 + "\nCoud not to close the Stream");
            }
        } finally {
            try {
                client.close();
            } catch (Exception ex1) {
                System.err.println(ex1 + "\nCoud not to close the Stream again");
            }
        }
    }

    private void dorequest(String line, PrintWriter out) {
        System.out.println(line);
        if (line.trim().startsWith(sendedMsgCode)) {
            String msgLine = line.trim().substring(4);
            defineMsg(msgLine, out);
        }
        if (msg.trim().startsWith(hiMsg)) {
            addChatter(msg.trim());
        }
    }

    private void defineMsg(String line, PrintWriter out) {
        int i = line.indexOf(sendedMsgSep);
        String reciver = line.substring(0, i);
        int x = line.indexOf(senderSep);
        String sender = line.substring(i + 4, x);
        String mymsg = line.substring(x + 4);
//        System.out.println(line + " to: " + reciver + " :from: " + sender + " the msg: " + mymsg);
        sendMsg(reciver, sender, mymsg, out);

    }

    private void sendMsg(String reciver, String sender, String msg, PrintWriter out) {
        ChatterHandeler c = ch.getPerson(reciver);
        if (c != null) {
            c.sendmsg(sendedMsgCode + sender + sendedMsgSep + msg);
//            System.out.println("working");
        } else {
            System.out.println("Send Massege Not Working");
        }
    }

    private void addChatter(String line) {
        name = line.substring(4);
        ch.addPerson(name, out);
        sendWhoMsg(ch, out);
//        System.err.println("addperson : " + name);
    }

    private void sendWhoMsg(ClientsHandeler ch, PrintWriter out) {
        String msg = ch.getWho();
        //out.println(msg);
        ArrayList clients = ch.getPeopleSet();
        for (int i = 0; i < clients.size(); i++) {
            ChatterHandeler c = (ChatterHandeler) clients.get(i);
            c.sendmsg(msg);
        }
    }

    private void doBye(String name) {
        ch.delPerson(name);
        sendWhoMsg(ch, out);
    }

}
