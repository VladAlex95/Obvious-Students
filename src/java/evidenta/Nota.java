/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evidenta;

import java.io.Serializable;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Alexandru
 */
@Named(value = "nota")
@RequestScoped
public class Nota implements Serializable {

   String nume, disciplina;
    Date data;
    int nota;
    public Nota() {}
    public Nota(String nume, String disciplina, Date data, int nota) {
        this.nume = nume;
        this.disciplina = disciplina;
        this.data = data;
        this.nota = nota;
    }

    public String getNume() {
        return nume;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public Date getData() {
        return data;
    }

    public int getNota() {
        return nota;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
   
    
}
