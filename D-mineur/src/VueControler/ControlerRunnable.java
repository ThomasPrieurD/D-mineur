/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControler;

import Modele.Grille;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author theo
 */
public class ControlerRunnable implements Runnable{
    private int action;//0->clique gauche , 1->clique droit
    private ArrayList options;
    private Grille grille;

    public void setAll(int action, ArrayList options, Grille grille) {
        this.action = action;
        this.options = options;
        this.grille = grille;
    }
    
    @Override
    public void run() {
        synchronized(this){
            while(true){
                switch(action){
                    case 0:clicG();
                        break;
                    case 1:clicD();
                        break;
                }

                try {
                    this.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ControlerRunnable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void clicG(){
        int i = (int)options.get(0);
        int j = (int)options.get(1);
        CaseVue c = (CaseVue)options.get(2);
        grille.clicG((int)options.get(0),(int)options.get(1));
    }
    
    public void clicD(){
        int i = (int)options.get(0);
        int j = (int)options.get(1);
        CaseVue c = (CaseVue)options.get(2);
        grille.clicD(i,j);
    }
    
    public void sleep(){
        synchronized(this){
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ControlerRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
