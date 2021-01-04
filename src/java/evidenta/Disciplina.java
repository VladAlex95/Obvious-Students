/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evidenta;

/**
 *
 * @author Alexandru
 */
public class Disciplina {
    String nume;
    String id;

    public Disciplina() {}

    public Disciplina(String id, String nume) {
        this.nume = nume;
        this.id = id;
    }
    
    

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
