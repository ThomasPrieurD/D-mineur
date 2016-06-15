/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private int minesRest;
    
    private int gameState;
    private int time;
    

    public Grille(int forme, int dimX,int dimY, int mines) {
        this.gameState = -1; // -1:pas démarré ;0: en cours; 1: victoire; 2: défaite
        this.time = 0;
        this.cases = new Case[dimX][dimY];
        this.forme = forme;
        this.dimX = dimX;
        this.dimY = dimY;
        this.mines = min(mines,dimX*dimY-9);
        this.minesRest = mines;
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
        this.gameState = 0;
    }
    
    public Position voisins(int x,int y,int numVoisin){
        Position vois;
        switch(forme){
            case 0: vois = voisinsRect(x,y,numVoisin);
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
    
    
    public void clicG(int x,int y){
        if(this.getGameState() == -1){
            this.poseMines(x, y);
        }
        if(this.getGameState() == 0){
            Position pos;
            if(cases[x][y].getEtat() == 0){
                if (cases[x][y].isMine()){
                    this.gameState = 2;
                    afficheMines();
                    
                    this.cases[x][y].setEtat(3);
                    this.gameState = 2;
                }
                else{
                    clicGRec(x,y);

                }
                updateEtat();
                setChanged();
                notifyObservers();
            }
        }
    }
    
    public void clicGRec(int x,int y){
        this.cases[x][y].setEtat(1);
        Position pos;
        int mineVois = 0;
        for (int i=0; i<8; i++){
            if(voisins(x,y,i) != null){
                pos = voisins(x,y,i);
                if (cases[pos.getX()][pos.getY()].isMine())
                    mineVois++;
            }
        }
        cases[x][y].setNbMineVois(mineVois);
        if (mineVois == 0){
            for (int i=0; i<8; i++){
                if(voisins(x,y,i) != null){
                    pos = voisins(x,y,i);
                    if (cases[pos.getX()][pos.getY()].getEtat() == 0 ){
                        clicGRec(pos.getX(),pos.getY());
                    }
                }
            }
        }
    }
    
    public void clicD(int x,int y){
        if(this.getGameState() == 0){
            if (this.cases[x][y].getEtat() == 0){
                this.cases[x][y].setEtat(2);
                this.minesRest--;
            }
            else{
                if (this.cases[x][y].getEtat() == 2){
                    this.cases[x][y].setEtat(0);
                    this.minesRest++;
                }
            }
            updateEtat();
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
            for(int j=0;j<this.getDimX();j++){
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
            int nbMineTrouvee = 0;
            int nbCasesCliquee = 0;
            for(int i=0;i<this.dimX;i++){
                for(int j=0;j<this.dimX;j++){
                    if(this.cases[i][j].isMine() && this.cases[i][j].getEtat() == 2){
                        nbMineTrouvee++;
                    }
                    if(this.cases[i][j].getEtat() != 0 && this.cases[i][j].getEtat() != 2){
                        nbCasesCliquee++;
                    }
                }
            }
            if(nbCasesCliquee == dimX*dimY-mines || nbMineTrouvee == mines){
                this.gameState = 1;
                afficheMines();
            }
        }
    }
}
