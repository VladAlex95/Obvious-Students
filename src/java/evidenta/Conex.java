/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evidenta;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandru
 */
@Named(value = "conex")
@SessionScoped
public class Conex implements Serializable {
Connection con;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
   
    
    public Conex() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Connector 5
//             Class.forName("com.mysql.cj.jdbc.Driver");//Connector 8.0
            con = DriverManager.getConnection("jdbc:mysql://localhost/test","root", "" );//Connector 5
             //con = DriverManager.getConnection("jdbc:mysql://localhost/evidenta?autoReconnect=true&useSSL=false","root", "" ); //Connector 8.0
            System.out.println("--conexiune");
        } catch (Exception ex) {
        Logger.getLogger(Conex.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    public void finalize(){
        try{
            con.close();
            System.out.println("inchide conexiunea");
        } catch (SQLException ex) {
        Logger.getLogger(Conex.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
}
