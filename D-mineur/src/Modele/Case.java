/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author theo
 */
public class Case {
    private int etat;
    private boolean mine;

    public Case(int etat, boolean mine) {
        this.etat = etat;
        this.mine = mine;
    }
    
    public Case() {
        this(0,false);
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void placeMine() {
        this.mine = true;
    }

    public int getEtat() {
        return etat;
    }

    public boolean isMine() {
        return mine;
    }
    
    
    
}
