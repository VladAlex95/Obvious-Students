/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evidenta;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author Alexandru
 */
@Named(value = "tabDiscipline")
@SessionScoped
public class TabDiscipline implements Serializable {

    ResultSet rs;
    ArrayList lista;

    @Inject
    private Conex conex;

    public ArrayList getLista() {
        return lista;
    }
    

    public TabDiscipline()throws Exception  {
        System.out.println("\n***TabDiscipline");
        //init();nu e bine asa, pt ca nu apucă să injecteze dependențele
        //si nu va avea acces la beanul conex
    }
    
    @PostConstruct//apelată automat după injectarea dependențelor
    public void init(){
        try {
            //Statement smt=conex.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement smt=conex.con.createStatement();
            rs=smt.executeQuery("select * from discipline order by nume;");
            lista=new ArrayList();
            
            while (rs.next()){
                lista.add(new Disciplina(rs.getString("id"), rs.getString("nume")));
            }
            rs.close();
            smt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(TabDiscipline.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
    
}
