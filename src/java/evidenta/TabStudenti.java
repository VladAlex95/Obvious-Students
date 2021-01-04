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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.inject.Inject;

/**
 *
 * @author Alexandru
 */
@Named(value = "tabStudenti")
@SessionScoped
public class TabStudenti implements Serializable {
    String sortare =null,crescDesc=null;
    ArrayList <Student> studenti;
    
    @Inject
    private Conex conex;
    
    @PostConstruct
    public void init(){
        ResultSet rs;
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("sortare" + sortare));
        try {
            Statement smt = conex.con.createStatement();
         if (sortare ==null || sortare.isEmpty())
             rs=smt.executeQuery("Select * from student");
         else if (crescDesc  == null || crescDesc.isEmpty())
                 rs=smt.executeQuery("Select * from student order by " + sortare);
         else 
             rs = smt.executeQuery("select * from student order by " + sortare  + crescDesc);
         studenti = new ArrayList();
         while(rs.next()){
             studenti.add(new Student(rs.getString("id"), rs.getString("nume"),rs.getInt("varsta"),rs.getFloat("media")));
         }
         rs.close();
         smt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(TabStudenti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void operatiiDB(String comandaSQL) throws SQLException{
        Statement smt = conex.con.createStatement();
        System.out.println(comandaSQL);
        smt.executeUpdate(comandaSQL);
        smt.close();
    }
    
    public void adauga(Student s) throws SQLException {
        operatiiDB ("insert student (nume,varsta,media) values ('" + s.nume + "', '" + s.varsta + " ', '" + s.media +  "')");
      s.nume = "";
      s.varsta = 0;
      s.media =0;
      init();
    }
    public void sterge(Student s) throws SQLException{
        operatiiDB("delete from student where id=" + s.id + ";");
        init();
    }
 public void modifica(Student s) throws SQLException{
    operatiiDB("update student set nume ='" + s.nume + "', varsta  ='" + s.varsta + "', media = '" + s.media+ "' where id ='" + s.id + "';");
    
}
    
    public TabStudenti() {
    }

    public String getSortare() {
        return sortare;
    }

    public void setSortare(String sortare) {
        this.sortare = sortare;
    }

    public String getCrescDesc() {
        return crescDesc;
    }

    public void setCrescDesc(String crescDesc) {
        this.crescDesc = crescDesc;
    }

    public ArrayList<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(ArrayList<Student> studenti) {
        this.studenti = studenti;
    }
    
    
}
