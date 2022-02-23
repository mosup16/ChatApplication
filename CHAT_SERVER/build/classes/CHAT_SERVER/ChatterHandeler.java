/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHAT_SERVER;

import java.io.PrintWriter;

/**
 *
 * @author بلال جلال
 */
public class ChatterHandeler {

    private String address;
    private int port;
    private PrintWriter out;
    String name;

    public ChatterHandeler(String name, PrintWriter out) {
        this.address = address;
        this.port = port;
        this.out = out;
        this.name = name;
    }

    public boolean matches(String name) {
        if (name.equals(this.name)) {
            return true;
        } else {
            return false;
        }
    }

    public void sendmsg(String msg) {
        out.println(msg);
    }

    public String getName() {
        return name;
    }

}
