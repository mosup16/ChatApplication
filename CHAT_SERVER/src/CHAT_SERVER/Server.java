package CHAT_SERVER;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.*;
import java.net.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author بلال جلال
 */
public class Server extends JFrame {

    ServerSocket server;
    int port = 14450;
    ClientsHandeler clients;
    private boolean connected = false;
    private String adress;
    String addresses ="";

    public Server() {
        super("Server");
        try {
            this.setSize(340, 640 / 12 * 9);
            this.setResizable(false);
            this.setVisible(true);
            this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
            server = new ServerSocket(port);
            clients = new ClientsHandeler();
            while (true) {
                System.out.println("waiting for the conction");
                Socket client = server.accept();
                adress = client.getInetAddress().getHostAddress();
                System.out.println("conected from :" + adress);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        client.getInputStream()));

                PrintWriter out = new PrintWriter(
                        client.getOutputStream(), true);

                connected = true;
                addresses +="\n"+adress;
                this.repaint();
                //clients.addPerson(adress,adress, port, out);
                new ServerThread(client, clients, adress, out, in).start();
            }

        } catch (Exception e) {
            System.err.println(e + "\n" + e.getClass());
        }
    }

    public static void main(String[] args) {
//    JOptionPane.showMessageDialog(null, "Server is Starting","Massege",JOptionPane.INFORMATION_MESSAGE);
        new Server();
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        ImageIcon pic = new ImageIcon("Background 9.png");
        g.drawImage(pic.getImage(), 0, 0, this);
        g.setColor(Color.black);
        Font f = new Font("Arial", Font.ITALIC | Font.BOLD, 15);
        g.setFont(f);
        g.drawString("Server is Waiting for Clients...", (this.getWidth() / 2) - 100, (this.getHeight() / 2));
        if (connected) {
            g.clearRect(0, 0, this.getWidth(), getHeight());
            g.drawImage(pic.getImage(), 0, 0, this);
            g.drawString("Connected From : "+addresses, (this.getWidth() / 2) - 100, (this.getHeight() / 2));
            connected = false;
        }
//        if (!connected) {
//            g.clearRect(0, 0, this.getWidth(), getHeight());
//            g.drawImage(pic.getImage(), 0, 0, this);
//            g.drawString("Server is Waiting for Clients...", (this.getWidth() / 2) - 100, (this.getHeight() / 2));
//            connected = false;
//        }
    }

}
