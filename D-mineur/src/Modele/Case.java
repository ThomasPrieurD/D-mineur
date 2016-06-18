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
    private int etat; // 0: non cliquée; 1: cliquée; 2: drapeau; 3: mine cliquée;4:mine;5:mine marquée
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
    
    public void drapeau(Grille grille){
        if (this.getEtat() == 0){
            this.setEtat(2);
            grille.addNbDrapeau(1);
        }
        else{
            if (this.getEtat() == 2){
                this.setEtat(0);
                grille.addNbDrapeau(-1);
            }
        }
    }
    
    public void libereCases(Grille grille,int x,int y){
        this.setEtat(1);
        Position pos;
        int mineVois = 0;
        for (int i=0; i<grille.getNbVoisins(); i++){
            if(grille.voisins(x,y,i) != null){
                pos = grille.voisins(x,y,i);
                if (grille.getCases()[pos.getX()][pos.getY()].isMine())
                    mineVois++;
            }
        }
        grille.getCases()[x][y].setNbMineVois(mineVois);
        if (mineVois == 0){
            for (int i=0; i<grille.getNbVoisins(); i++){
                if(grille.voisins(x,y,i) != null){
                    pos = grille.voisins(x,y,i);
                    if (grille.getCases()[pos.getX()][pos.getY()].getEtat() == 0 ){
                        grille.getCases()[pos.getX()][pos.getY()].libereCases(grille, pos.getX(), pos.getY());
                    }
                }
            }
        }
    }

    
}
