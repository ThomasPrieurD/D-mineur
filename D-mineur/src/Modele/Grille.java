/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author theo
 */
public class Grille {
    private HashMap<Integer,Case> grille;
    private int forme; // 0: classique
    private int[] dim;
    private int mines;

    public Grille(HashMap<Integer, Case> grille, int forme, int[] dim, int mines) {
        this.grille = grille;
        this.forme = forme;
        this.dim = dim;
        this.mines = mines;
        int k=0;
        for(int i=0;i<dim.length;i++){
            for(int j=0;j<dim[i];j++){
                grille.put(k, new Case());
                k++;
            }
        }
        
        int minesAPlacer = this.mines;
        Random rand = new Random();
        int test;
        while (minesAPlacer > 0){
            test = rand.nextInt(this.grille.size());
            if (!this.grille.get(test).isMine()){
                this.grille.get(test).placeMine();
                minesAPlacer--;
            }
        }
    }
    
    public ArrayList<Integer> voisins(int k){
        ArrayList<Integer> vois = new ArrayList<Integer>();
        switch(forme){
            case 0: vois = voisinsRect(k);
        }
        return vois;
    }
    
    
    public ArrayList<Integer> voisinsRect(int k){
        ArrayList<Integer> vois = new ArrayList<Integer>();
        int y = k/dim[0];
        int x = (k-y)/dim[0];
        for (int i = -1; i <=1; i++){
            for (int j = -1; j <=1; j++){
                if ((x+i>=0) && (x+i<dim[0]) && (y+j>=0) && (y+j<dim[1]) && 
                        !((i==0) && (j==0))){
                    vois.add((x+i)*dim[0]+(y+j));
                }
            }
        }
        return vois;
    }
    
    
    public void clicG(int k){
        if (grille.get(k).isMine()){
            //gameOver();
        }
        else{
            ArrayList<Integer> vois = voisins(k);
            int mineVois = 0;
            for (int i=0; i<vois.size(); i++){
                if (grille.get(vois.get(i)).isMine())
                    mineVois++;
            }
            grille.get(k).setNbMineVois(mineVois);
            //update(k,mineVois); // met à jour l'affichage de la case k
            if (mineVois == 0)
                for (int i=0; i<vois.size(); i++)
                    clicG(vois.get(i));
        }
    }
    
    public void clicD(int k){
        if (this.grille.get(k).getEtat() == 0)
            this.grille.get(k).setEtat(2);
        if (this.grille.get(k).getEtat() == 2)
            this.grille.get(k).setEtat(0);
    }
}
