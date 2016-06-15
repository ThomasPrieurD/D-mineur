/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author theo
 */
public class Timer extends Observable implements Runnable{
    private int sec;
    private int min;
    private boolean active;
    
    @Override
    public void run() {
        
        this.sec = 0;
        this.min = 0;
        this.active=true;

        while(true) {
            try {
                // pause
                Thread.sleep(999);
            }
            catch (InterruptedException ex) {}
            incr();
            if(!active){
                synchronized (this){
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            setChanged();
            notifyObservers();
        }
    }
    
    public void incr(){
        this.sec++;
        if(this.sec > 59){
            this.min++;
            this.sec -= 60;
        }
    }

    public int getSec() {
        return sec;
    }

    public int getMin() {
        return min;
    }
    
    public void restart(){
        this.sec = 0;
        this.min = 0;
        this.active = true;
        setChanged();
        notifyObservers();
    }
    
    public void pause(){
        this.active = false;
    }
}
