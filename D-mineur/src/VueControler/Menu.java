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
import javafx.scene.control.ComboBox;
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
    private ImageView smiley = new ImageView(happy);
    private Button newGame = new Button("",smiley);
    
    private Text nbDrapeau;
    private ComboBox dif;
    
    public Menu(VueControleur controleur) {
        //images
        smiley.setFitHeight(50);
        smiley.setPreserveRatio(true);
        drapeau.setFitHeight(30);
        drapeau.setPreserveRatio(true);
        
        this.setPadding(new Insets(10, 0, 10, 0));
        
        //bouttons
        newGame.setPadding(new Insets(10, 10, 10, 10));
        
        //texts
        this.nbDrapeau = new Text(": "+controleur.getNbMines());
        this.nbDrapeau.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        this.nbDrapeau.setFill(Color.WHITE);
        
        dif = new ComboBox();
        dif.getItems().addAll(
                    "facile",//10 * 10 et 10 mines
                    "moyen",//20*20 et 50 mines
                    "difficile"//20*40 et 100 mines 
        );
        dif.setValue("facile");
        
        this.setStyle("-fx-background-color: #336699;");
        this.getChildren().addAll(newGame,drapeau,nbDrapeau,dif);
        
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                controleur.restart();
            }
        });
        
    }
    
    public void placeBouttons(int width){
        newGame.setTranslateX((width-60)/2);
        newGame.setTranslateY(10);
        
        drapeau.setTranslateX(width/2 + 100);
        drapeau.setTranslateY((this.getHeight())/2-15);
        nbDrapeau.setTranslateX(width/2 + 130);
        nbDrapeau.setTranslateY((this.getHeight())/2+5);
        
        dif.setTranslateX(width/2 - 270);
        dif.setTranslateY((this.getHeight())/2-20);
        
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
