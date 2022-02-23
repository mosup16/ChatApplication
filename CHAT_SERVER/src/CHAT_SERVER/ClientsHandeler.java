/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHAT_SERVER;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author بلال جلال
 */
class ClientsHandeler {

    ArrayList clients;
    String address;
    int port;
    PrintWriter out;
    String whoStart = "32@3#$";

    public ClientsHandeler() {
        clients = new ArrayList();
    }

    public void addPerson(String name, PrintWriter out) {
        this.port =port;
        this.out = out;
//        this.address = address;
        clients.add(new ChatterHandeler(name, out));
    }

    public void delPerson(String name) {
        ChatterHandeler c;
        for (int i = 0; i < clients.size(); i++) {
            c = (ChatterHandeler) clients.get(i);
            if (c.matches(name)) {
                clients.remove(i);
            }
        }
    }

    public ChatterHandeler getPerson(String name) {
        ChatterHandeler c = null;
        for (int i = 0; i < clients.size(); i++) {
            if (((ChatterHandeler) clients.get(i)).matches(name)) {
                c = (ChatterHandeler) clients.get(i);
//                System.out.println("get the Chater matches "+ name);
            }
        }
        return c;
    }
    
    public String getWho(){
        String people = whoStart+"/" ;
        for(int i = 0; i<clients.size();i++){
           ChatterHandeler c = (ChatterHandeler) clients.get(i);
          people+= c.getName()+"/";
          System.err.println("(ClientsHandeler) : "+people);
        }
        return people;
    }
    public ArrayList getPeopleSet(){
    return clients;
    }

}
