/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author theo
 */
public class Grille {
    private HashMap<Integer,Case> grille;
    private int forme;
    private int[] dim;

    public Grille(HashMap<Integer, Case> grille, int forme, int[] dim) {
        this.grille = grille;
        this.forme = forme;
        this.dim = dim;
        int k=0;
        for(int i=0;i<dim.length;i++){
            for(int j=0;j<dim[i];j++){
                grille.put(k, new Case());
                k++;
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
}
