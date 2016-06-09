/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Observable;
import src.mvc.libInterpreteurExpr.Node;

/**
 *
 * @author théo
 */
public class Modele extends Observable {

    private double lastValue;
    private boolean err = false;
    private Byte[][] GameBoard;
    private int nbmines;

    public Modele(int height,int width,int nbMines){
        
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                //GameBoard[i][j]=0;
            }
        }
    }
    
    
    
    public void calc(String expr) {
        StringBuffer input;
        StringBuffer output;
        Node toto;

        input = new StringBuffer(expr);
        output = new StringBuffer(255);

        toto = Node.Construct_Tree(input, input.length(), 0);
        if (toto != null) {
            toto.Write_Tree(output);
            lastValue = toto.Compute_Tree(0, 0, 0);
            err = false;
        } else {
            err = true;
        }
        
        // notification de la vue, suite à la mise à jour du champ lastValue
        setChanged();
        notifyObservers();
    }
    
    public boolean getErr() {
        return err;
    }
    
    public double getValue() {
        return lastValue;
    }

    public Byte[][] getGameBoard() {
        return GameBoard;
    }
    
    

}