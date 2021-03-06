/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 *
 * @author theo
 */
public class Grille  extends Observable{
    private Case[][] cases;
    private int forme; // 0: classique
    private int dimX;
    private int dimY;
    private int mines;
    private int nbDrapeau;
    private int nbVoisins;  //nombre de voisins d'une case
    
    private int gameState;
    
    private int time;
    
    private final Timer timer;
    private final Thread TimerThread;
    

    public Grille(int forme, int dimX,int dimY, int mines,int time) {
        this.gameState = -1; // -1:pas démarré ;0: en cours; 1: victoire; 2: défaite
        this.nbDrapeau = 0;
        this.cases = new Case[dimX][dimY];
        this.forme = forme;
        this.time=time;
        timer = new Timer(this);
        TimerThread = new Thread (timer);
        TimerThread.setDaemon(true);
        switch(forme){
            case 1: nbVoisins = 12;
                break;
            case 2: nbVoisins = 6;
                break;
            default: nbVoisins = 8;
                break;
        }
        this.dimX = dimX;
        this.dimY = dimY;
        this.mines = min(mines,dimX*dimY-9);
        for(int i = 0;i<this.dimX;i++){
            for(int j = 0;j<this.dimY;j++){
                this.cases[i][j] = new Case();
            }
        }
        
    }
    
    public void poseMines(int x,int y){
        int minesAPlacer = this.mines;
        ArrayList<Case> casesLibre = new ArrayList<>();
        for(int i=0;i<this.dimX;i++){
            for(int j=0;j<this.dimY;j++){
                if((i>x+1 || i<x-1) || (j>y+1 || j<y-1)){
                    casesLibre.add(this.getCase(i,j));
                }
            }
        }
        Random rand = new Random();
        int test;
        while (minesAPlacer > 0 && casesLibre.size()>0){
            
            test = rand.nextInt(casesLibre.size());
            if (!casesLibre.get(test).isMine()){
                
                casesLibre.get(test).placeMine();
                casesLibre.remove(test);
                minesAPlacer--;
            }
        }
        if(TimerThread.getState() == Thread.State.NEW){
            TimerThread.start();
        }
        else {
            timer.start();
            synchronized (timer){
                timer.notify();
            }
        }
       
        this.gameState = 0;
    }
    
    public Position voisins(int x,int y,int numVoisin){
        Position vois;
        switch(forme){
            case 0: vois = voisinsRect(x,y,numVoisin);
                break;
            case 1: vois = voisinsTri(x,y,numVoisin);
                break;
            case 2: vois = voisinsHexa(x,y,numVoisin);
                break;
            default : vois = null;
        }
        return vois;
    }
    
    
    public Position voisinsRect(int x,int y,int numVoisin){
        int posX,posY;
        switch(numVoisin){
            case 0: posX = x-1;
                    posY = y-1;
                break;
            case 1: posX = x;
                    posY = y-1;
                break;
            case 2: posX = x+1;
                    posY = y-1;
                break;
            case 3: posX = x+1;
                    posY = y;
                break;
            case 4: posX = x+1;
                    posY = y+1;
                break;
            case 5: posX = x;
                    posY = y+1;
                break;
            case 6: posX = x-1;
                    posY = y+1;
                break;
            case 7: posX = x-1;
                    posY = y;
                break;
            default : posX = -1;
                      posY = -1;
                break;
            
        }
        if(posX<0 || posY<0 || posX>=this.dimX || posY>=this.dimY )
            return null;
        else return new Position(posX,posY);
    }
    
    public Position voisinsHexa(int x,int y,int numVoisin){
        int posX,posY;
        int j;
        if(x%2 == 0) j=-1;
        else j=1;
        switch(numVoisin){
            case 0: posX = x-1;
                    posY = y;
                break;
            case 1: posX = x+1;
                    posY = y;
                break;
            case 2: posX = x;
                    posY = y+1;
                break;
            case 3: posX = x;
                    posY = y-1;
                break;
            case 4: posX = x-1;
                    posY = y+j;
                break;
            case 5: posX = x+1;
                    posY = y+j;
                break;
            default : posX = -1;
                      posY = -1;
                break;
            
        }
        if(posX<0 || posY<0 || posX>=this.dimX || posY>=this.dimY )
            return null;
        else return new Position(posX,posY);
    }
    
    public Position voisinsTri(int x,int y,int numVoisin){
        int posX,posY;
        if (x%2 == y%2){
            switch(numVoisin){
                case 0: posX = x-1;
                        posY = y-1;
                    break;
                case 1: posX = x;
                        posY = y-1;
                    break;
                case 2: posX = x+1;
                        posY = y-1;
                    break;
                case 3: posX = x+1;
                        posY = y;
                    break;
                case 4: posX = x+2;
                        posY = y;
                    break;
                case 5: posX = x+2;
                        posY = y+1;
                    break;
                case 6: posX = x+1;
                        posY = y+1;
                    break;
                case 7: posX = x;
                        posY = y+1;
                    break;
                case 8: posX = x-1;
                        posY = y+1;
                    break;
                case 9: posX = x-2;
                        posY = y+1;
                    break;
                case 10: posX = x-2;
                        posY = y;
                    break;
                case 11: posX = x-1;
                        posY = y;
                    break;
                default : posX = -1;
                          posY = -1;
                    break;
            }
        }
        else{
            switch(numVoisin){
                case 0: posX = x-2;
                        posY = y-1;
                    break;
                case 1: posX = x-1;
                        posY = y-1;
                    break;
                case 2: posX = x;
                        posY = y-1;
                    break;
                case 3: posX = x+1;
                        posY = y-1;
                    break;
                case 4: posX = x+2;
                        posY = y-1;
                    break;
                case 5: posX = x+2;
                        posY = y;
                    break;
                case 6: posX = x+1;
                        posY = y;
                    break;
                case 7: posX = x+1;
                        posY = y+1;
                    break;
                case 8: posX = x;
                        posY = y+1;
                    break;
                case 9: posX = x-1;
                        posY = y+1;
                    break;
                case 10: posX = x-1;
                        posY = y;
                    break;
                case 11: posX = x-2;
                        posY = y;
                    break;
                default : posX = -1;
                          posY = -1;
                    break;
            }
        }
        if(posX<0 || posY<0 || posX>=this.dimX || posY>=this.dimY )
            return null;
        else return new Position(posX,posY);
    }
    
    
    public void clicG(int x,int y){
        if(this.getGameState() == -1){
            this.poseMines(x, y);
        }
        if(this.getGameState() == 0){
            if(cases[x][y].getEtat() == 0){
                if (cases[x][y].isMine()){
                    this.gameState = 2;
                    afficheMines();
                    this.cases[x][y].setEtat(3);
                }
                else{
                    this.cases[x][y].libereCases(this, x, y);

                }
                updateEtat();
                if(this.gameState!=0){
                    timer.pause();
                }
                setChanged();
                notifyObservers();
            }
        }
    }
    
    public void clicD(int x,int y){
        if(this.getGameState() == 0){
            this.cases[x][y].drapeau(this);
            setChanged();
            notifyObservers();
        }
    }

    public int getDimX() {
        return dimX;
    }

    public int getDimY() {
        return dimY;
    }

    public int getNbDrapeau() {
        return nbDrapeau;
    }

    public int getNbVoisins() {
        return nbVoisins;
    }
    
    

    public Case[][] getCases() {
        return cases;
    }
    
    public Case getCase(int x,int y){
        return cases[x][y];
    }
    
    public int getGameState(){
        return this.gameState;
    }
    
    public void afficheMines(){
        for(int i=0;i<this.getDimX();i++){
            for(int j=0;j<this.getDimY();j++){
                if(this.cases[i][j].isMine()){
                    if(this.cases[i][j].getEtat()==2){
                        this.cases[i][j].setEtat(5);
                    }
                    else this.cases[i][j].setEtat(4);
                }
            }
        }
    }
    
    public void updateEtat(){
        if(this.gameState==0){
            int nbCasesCliquee = 0;
            for(int i=0;i<this.dimX;i++){
                for(int j=0;j<this.dimY;j++){
                    if(this.cases[i][j].getEtat() != 0 && this.cases[i][j].getEtat() != 2){
                        nbCasesCliquee++;
                    }
                }
            }
            if(nbCasesCliquee == dimX*dimY-mines){
                this.gameState = 1;
                afficheMines();
            }
        }
    }
    
    public void restart() {
        this.gameState = -1; // -1:pas démarré ;0: en cours; 1: victoire; 2: défaite
        this.cases = new Case[dimX][dimY];
        this.nbDrapeau = 0;
        this.mines = min(mines,dimX*dimY-9);
        for(int i = 0;i<this.dimX;i++){
            for(int j = 0;j<this.dimY;j++){
                this.cases[i][j] = new Case();
            }
        }
        timer.restart();
        synchronized (timer){
            timer.notify();
        }
        setChanged();
        notifyObservers();
    }

    public int getMines() {
        return mines;
    }

    public int getForme() {
        return forme;
    }

    public Timer getTimer() {
        return timer;
    }

    public int getTime() {
        return time;
    }
    
    public void timeOut(){
        this.gameState = 2;
        timer.pause();
        setChanged();
        notifyObservers();
    }
    
    public void closeTimer(){
        this.timer.stop();
        synchronized (timer){
            timer.notify();
        }
    }
    
    public void addNbDrapeau(int n){
        this.nbDrapeau+=n;
    }
}
