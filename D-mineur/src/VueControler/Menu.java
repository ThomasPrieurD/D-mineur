/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControler;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;



/**
 *
 * @author p1303175
 */
public class Menu extends Pane{
    
    private Image happy = new Image("images/happy.png");
    private Image sad = new Image("images/sad.png");
    private Image swag = new Image("images/swag.png");
    private ImageView drapeau = new ImageView(new Image("images/drapeau.png"));
    private ImageView mine = new ImageView(new Image("images/mine.png"));
    private ImageView smiley = new ImageView(happy);
    private Button newGame = new Button("",smiley);
    
    private Text nbMines;
    private Text nbDrapeau;
    
    public Menu(VueControleur controleur) {
        //images
        smiley.setFitHeight(50);
        smiley.setPreserveRatio(true);
        drapeau.setFitHeight(30);
        drapeau.setPreserveRatio(true);
        mine.setFitHeight(30);
        mine.setPreserveRatio(true);
        
        this.setPadding(new Insets(10, 0, 10, 0));
        
        //bouttons
        newGame.setPadding(new Insets(10, 10, 10, 10));
        
        //texts
        this.nbMines = new Text(": "+controleur.getNbMines());
        this.nbMines.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        this.nbMines.setFill(Color.WHITE);
        
        this.nbDrapeau = new Text(": "+0);
        this.nbDrapeau.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        this.nbDrapeau.setFill(Color.WHITE);
        
        this.setStyle("-fx-background-color: #336699;");
        this.getChildren().addAll(newGame,mine,nbMines,drapeau,nbDrapeau);
        
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                controleur.restart();
            }
        });
        
    }
    
    public void placeBouttons(int width){
        newGame.setTranslateX((width-60)/2);
        newGame.setTranslateY(10);
        
        mine.setTranslateX(width/2 + 100);
        mine.setTranslateY((this.getHeight())/2 -45);
        nbMines.setTranslateX(width/2 + 130);
        nbMines.setTranslateY((this.getHeight())/2-25);
        
        drapeau.setTranslateX(width/2 + 100);
        drapeau.setTranslateY((this.getHeight())/2);
        nbDrapeau.setTranslateX(width/2 + 130);
        nbDrapeau.setTranslateY((this.getHeight())/2+20);
        
    }
    
    public void happy(){
        smiley.setImage(happy);
    }
    public void sad(){
        smiley.setImage(sad);
    }
    
    public void swag(){
        smiley.setImage(swag);
    }
    
    public void setNbDrapeau(int n){
        this.nbDrapeau.setText(": "+n);
    }
}
