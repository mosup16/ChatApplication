/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHAT_CLIENT;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.PrintWriter;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author بلال جلال
 */
public class clientThread extends Thread {

    PrintWriter out;
    BufferedReader in;
    private String msg;
    Client ci;
    String whoStart = "32@3#$";
    String sepmsg = "*/@#*/";
    private boolean done = false;
    JTextArea ta;
    JComboBox cb;
    String sendedMsgCode = "@#41";
    String sendedMsgSep = "#25/";
    boolean searching = true;

    public clientThread(PrintWriter out, BufferedReader in, Client ci) {
        this.in = in;
        this.out = out;
        this.ci = ci;
        ta = ci.jTextArea1;
        cb = ci.jClientsBox;
    }

    @Override
    public void run() {
        try {
            while (!done) {
                if (!ci.closed) {
                    msg = in.readLine();
                } else {
                    done = true;
                    break;
                }

                if (msg == null) {
                    done = true;
                    break;
                } else {
                    processmsg(msg);
                    System.out.println(msg.trim());
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(
                    null,
                    e.toString()+
                    "\nUnavilable ServerProblem\nPlease Check your internet connection",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(22);
        }

    }

    private void processmsg(String line) {
        try {

            if (line.trim().startsWith(whoStart)) {
                String Wline = line.trim().substring(6).trim();
                readWho(Wline);
            } else {
                if (line.trim().startsWith(sendedMsgCode)) {
                    String msgLine = line.trim().substring(4);
                    defineMsg(msgLine);
                }
            }

        } catch (Exception e) {
            System.err.println(e + "\n" + e.toString());
        }
    }

    private void readWho(String line) {
//        int end = 0;
//        while(searching){
//          int i = line.indexOf("/",0);
//           if(i == -1){
//               searching = false;
//               break;
//           }
//           end = line.indexOf("/",i+1);
//           if(end == -1){
//               searching = false;
//               break;
//           }
//           String cn = line.substring(i+1,end);
//    //       end = end+6;
//           //cb.addItem(cn);
//           System.out.println(cn+" : Start :- "+i+":  end:- "+end);
//        }
        //  byte[] data = line.getBytes();
        int length = line.length();
        int Start, endT, num = 0;
        cb.removeAllItems();
        System.err.println("Invoked");
        for (int i = 0; i < length; i++) {
            if (line.charAt(i) == '/') {
                //Start = i;
                for (int x = i + 1; x < length; x++) {
                    if (line.charAt(x) == '/') {
                        //endT = x;
                        cb.addItem(line.substring(i + 1, x).trim());
//                        System.out.println(line.substring(i + 1, x) + " : Start :- " + i + ":  end:- " + x);
//                             to Test The machinism that is used in Reading Who Messages
                        break;
                    }
                }
            }
        }
    }

    private void defineMsg(String line) {
        int i = line.indexOf(sendedMsgSep);
        String sender = line.substring(0, i);
        String msg = line.substring(i + 4);
        if (!ci.closed) {
//            ta.setForeground(Color.CYAN);
            ta.append("from(" + sender + " ) : " + msg + "\n");
        }
    }
}
