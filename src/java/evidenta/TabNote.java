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
@Named(value = "tabNote")
@SessionScoped
public class TabNote implements Serializable {

    ResultSet rs;
    String filtruPersoana, filtruDisciplina, nota, data;
    ArrayList lista;
    float mediaA;
    
    @Inject
    private Conex conex;

    public ArrayList getLista() {
        return lista;
    }
    
    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
    
    public void filtreazaNume(){
        filtruDisciplina=null;
        init();
    }
    public void filtreazaDisciplina(){
        filtruPersoana=null;
        init();
    }
    public void reset(){
        filtruDisciplina=null;
        filtruPersoana=null;
        init();
    }
    public void setFiltruPersoana(String filtruPersoana) {
        this.filtruPersoana = filtruPersoana;
    }

    public void setFiltruDisciplina(String filtruDisciplina) {
        this.filtruDisciplina = filtruDisciplina;
    }

    public String getFiltruPersoana() {
        return filtruPersoana;
    }

    public String getFiltruDisciplina() {
        return filtruDisciplina;
    }
    
    public void calcmedia(){
        
    }
    
    /**
     * Creates a new instance of TabNote
     */
    public TabNote() throws Exception  {
         System.out.println("\n***TabNote");
        java.util.Date d=new java.util.Date();
        data=d.getYear()+1900+"."+(d.getMonth()+1)+"."+d.getDate();
        //init(); apelat cu @PostConstruct nu se poate aici, pt. că nu sun
    }
    public void adaugaNota(){
    //tranzactie adauga nota si recalculeaza media
    Statement smt=null;   
    try{
       conex.con.setAutoCommit(false);
       smt=conex.con.createStatement();
       smt.addBatch("insert note (idstudent, iddisciplina, nota,"
            + " data) values("+filtruPersoana+","+filtruDisciplina+","
            +nota+",'"+data+"');" );
       smt.addBatch("update student set media="
            + "(select sum(nota)/count(nota)"+
            " from note where idstudent="+filtruPersoana+")"
            + " where id="+filtruPersoana+";");
            int [] updateCounts = smt.executeBatch();
            conex.con.commit();
            
       }catch(SQLException e){
            try {
                conex.con.rollback();
            } catch (SQLException ex) {System.out.println(ex);}
        }
        finally{
            try {
                conex.con.setAutoCommit(true);
                if(smt!=null)
                    smt.close();
                init();//împrospateaza tabelul
                nota="";//sterge nota adaugata de pe interfata
            } catch (SQLException ex) {System.out.println(ex);}
         }
    }
    
    @PostConstruct
    public void init(){
        System.out.println("\n++++TabNote Init");
        String selectSQL="select student.id, discipline.id, student.nume,"
                        + " discipline.nume as disciplina, note.data,"
                        + " note.nota from student, discipline, note"
                        + " where note.iddisciplina=discipline.id"
                        + " and student.id=note.idstudent";
        try {
            //Statement smt=conex.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement smt=conex.con.createStatement();
            if(filtruPersoana!=null&&filtruDisciplina!=null)
                rs=smt.executeQuery(selectSQL
                        + " and discipline.id= "+filtruDisciplina
                        + " and student.id= "+filtruPersoana+";");
            else if(filtruPersoana!=null){
                rs=smt.executeQuery(selectSQL
                        + " and student.id= "+filtruPersoana+";");
            }else if(filtruDisciplina!=null)
                rs=smt.executeQuery(selectSQL
                        + " and discipline.id= "+filtruDisciplina+";");
            else//ambele filtre nule
                rs=smt.executeQuery(selectSQL+";");
           
            lista=new ArrayList();
            while (rs.next()){
                lista.add(new Nota(rs.getString("nume"),
                        rs.getString("disciplina"),
                        rs.getDate("data"),rs.getInt("nota")));
            }
            rs.close();
            smt.close();
        } catch (SQLException ex) {
            Logger.getLogger(TabNote.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
