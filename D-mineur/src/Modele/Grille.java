/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;

/**
 *
 * @author theo
 */
public class Grille  extends Observable{
    private HashMap<Integer,Case> cases;
    private int forme; // 0: classique
    private int[] dim;
    private int mines;

    public Grille(int forme, int[] dim, int mines) {
        this.cases = new HashMap<>();
        this.forme = forme;
        this.dim = dim;
        this.mines = mines;
        int k=0;
        int l = 1;
        for(int i=0;i<dim.length;i++){
            l *= dim[i];
        }
        for(int i=0;i<l;i++){
            this.cases.put(k, new Case());
            k++;
        }
        int minesAPlacer = this.mines;
        Random rand = new Random();
        int test;
        while (minesAPlacer > 0){
            test = rand.nextInt(this.cases.size());
            if (!this.cases.get(test).isMine()){
                this.cases.get(test).placeMine();
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
                    vois.add((x+i)*dim[1]+(y+j));
                }
            }
        }
        return vois;
    }
    
    
    public void clicG(int k){
        if (cases.get(k).isMine()){
            //gameOver();
        }
        else{
            ArrayList<Integer> vois = voisins(k);
            int mineVois = 0;
            for (int i=0; i<vois.size(); i++){
                if (cases.get(vois.get(i)).isMine())
                    mineVois++;
            }
            cases.get(k).setNbMineVois(mineVois);
            //update(k,mineVois); // met à jour l'affichage de la case k
            if (mineVois == 0)
                for (int i=0; i<vois.size(); i++)
                    clicG(vois.get(i));
        }
        setChanged();
        notifyObservers();
    }
    
    public void clicD(int k){
        System.out.println(this.cases.get(k).getEtat());
        if (this.cases.get(k).getEtat() == 0)
            this.cases.get(k).setEtat(2);
        if (this.cases.get(k).getEtat() == 2)
            this.cases.get(k).setEtat(0);
        this.cases.get(k).setEtat(2);
        setChanged();
        notifyObservers();
    }

    public int[] getDim() {
        return dim;
    }

    public HashMap<Integer, Case> getCases() {
        return cases;
    }
    
    public Case getCase(int i){
        return cases.get(i);
    }
}
