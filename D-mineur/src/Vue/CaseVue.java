/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Grille;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author theo
 */
public class CaseVue {
    
    private final ImageView drapeau = new ImageView(new Image("images/drapeau.png"));
    private final ImageView mine = new ImageView(new Image("images/mine.png"));
    private final ImageView mineR = new ImageView(new Image("images/mineR.png"));
    
    private Rectangle layer;
    private Text text;
    private StackPane stack  = new StackPane();

    public CaseVue(Rectangle layer, Text text,int i,int j,Grille grille) {
        drapeau.setFitHeight(25);
        drapeau.setPreserveRatio(true);
        mine.setFitHeight(25);
        mine.setPreserveRatio(true);
        mineR.setFitHeight(25);
        mineR.setPreserveRatio(true);
        this.layer = layer;
        this.text = text;
        this.text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        this.layer.setArcWidth(10);
        this.layer.setArcHeight(10);
        this.layer.setX(i);
        this.layer.setY(j);
        this.text.setFill(Color.DARKBLUE);
        stack.getChildren().addAll(this.layer, this.text,drapeau,mine,mineR);
        StackPane.setMargin(this.text, null);
        drapeau.setVisible(false);
        mine.setVisible(false);
        mineR.setVisible(false);
        
        this.getLayer().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                int indiceX = (int) getLayer().getX();
                int indiceY = (int)getLayer().getY();
                if(event.getButton()==MouseButton.PRIMARY){
                    grille.clicG(indiceX,indiceY);
                    if(grille.getCase(i, j).getEtat()==3){
                        mineR.setVisible(true);
                    }
                }
                else{
                    grille.clicD(indiceX,indiceY);
                    if(grille.getCase(indiceX, indiceY).getEtat() != 1)
                        drapeau.setVisible(!drapeau.isVisible());
                }
            }

        });
        
    }
    
    public void mine(){
        drapeau.setVisible(false);
        mine.setVisible(true);
    }
    
    public void colorTXt(int number){
        switch(number){
            case 1:this.text.setFill(Color.DARKBLUE);
                break;
            case 2:this.text.setFill(Color.DARKGREEN);
                break;
            case 3:this.text.setFill(Color.RED);
                break;
            case 4:this.text.setFill(Color.PURPLE);
                break;
            default:this.text.setFill(Color.BLACK);
                break;
        }
    }
    
    public CaseVue(int i,int j,Grille grille){
        this(new Rectangle(30, 30, Color.GREY),new Text(0, 0, ""),i,j,grille);
    }
    
    public Rectangle getLayer() {
        return layer;
    }

    public Text getText() {
        return text;
    }

    public StackPane getStack() {
        return stack;
    }
    

    public void setLayer(Rectangle layer) {
        this.layer = layer;
    }

    public void setText(Text text) {
        this.text = text;
    }
    
}
