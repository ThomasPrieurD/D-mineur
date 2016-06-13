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
    private int etat; // 0: non cliquée; 1: cliquée; 2: drapeau; 3: mine cliquée;4:mine
    private boolean mine;
    private int nbMineVois;

    public Case(int etat, boolean mine) {
        this.etat = etat;
        this.mine = mine;
        this.nbMineVois = 0;
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

    public int getNbMineVois() {
        return nbMineVois;
    }

    public void setNbMineVois(int nbMineVois) {
        this.nbMineVois = nbMineVois;
    }
    
    
    
}
