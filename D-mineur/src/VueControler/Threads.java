/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControler;

import Modele.Grille;
import java.util.ArrayList;

/**
 *
 * @author theo
 */
public class Threads {
    private ControlerRunnable[] runnables;
    private Thread[] threads;
    private int currentThread;
    private int nbThreads;
    
    public Threads(int n){
        nbThreads = n;
        runnables = new ControlerRunnable[5];
        threads = new Thread[5];
        for (int i=0;i<nbThreads;i++){
            runnables[i] = new ControlerRunnable();
            threads[i] = new Thread(runnables[i]);
            threads[i].setDaemon(true);
        }
        currentThread = 0;
    }
    
    public Threads(){
        this(5);
    }
    
    public void exec(int action, ArrayList options, Grille grille){
        boolean ok=false;
        do{
            currentThread = (currentThread+1)%nbThreads;
            if(threads[currentThread].getState() == Thread.State.NEW){
                runnables[currentThread].setAll( action,  options,  grille);
                threads[currentThread].start();
                ok=true;
            }
            if(threads[currentThread].getState() == Thread.State.WAITING){
                synchronized (runnables[currentThread]) {
                    runnables[currentThread].setAll( action,  options,  grille);
                    runnables[currentThread].notify();
                }
                ok=true;
            }
        }while(!ok);
        
        
   }
}
