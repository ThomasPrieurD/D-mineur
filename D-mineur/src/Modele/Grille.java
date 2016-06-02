/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

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
    
    
}
