/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControler;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
    private Image dodo = new Image("images/dodo.png");
    private Image sad = new Image("images/sad.png");
    private Image swag = new Image("images/swag.png");
    private ImageView drapeau = new ImageView(new Image("images/drapeau.png"));
    private ImageView time = new ImageView(new Image("images/horloge.jpg"));
    private ImageView smiley = new ImageView(dodo);
    private Button newGame = new Button("",smiley);
    
    private Text nbDrapeau;
    private Text timer;
    private ComboBox dif;
    private ComboBox forme;
    
    public Menu(VueControleur controleur) {
        //images
        smiley.setFitHeight(50);
        smiley.setPreserveRatio(true);
        drapeau.setFitHeight(30);
        drapeau.setPreserveRatio(true);
        time.setFitHeight(30);
        time.setPreserveRatio(true);
        
        //bouttons
        newGame.setPadding(new Insets(10, 10, 10, 10));
        
        //texts
        this.nbDrapeau = new Text(": "+controleur.getNbMines());
        this.nbDrapeau.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        this.nbDrapeau.setFill(Color.WHITE);
        
        this.timer = new Text(0+" : "+0);
        this.timer.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        this.timer.setFill(Color.WHITE);
        
        dif = new ComboBox();
        dif.getItems().addAll(
                    "facile",//10 * 10 et 10 mines
                    "moyen",//20*20 et 50 mines
                    "difficile",//20*40 et 100 mines 
                    "hardcore"//difficile + temps limité(3 minutes);
        );
        switch(controleur.getDifficulte()){
            case 1 : dif.setValue("facile");
                break;
            case 2 : dif.setValue("moyen");
                break;
            case 3 : dif.setValue("difficile");
                break;
            case 4 : dif.setValue("hardcore");
                break;
        }
        
        forme = new ComboBox();
        forme.getItems().addAll(
                    "carré",
                    "triangle"
        );
        switch(controleur.getForme()){
             case 1 : forme.setValue("triangle");
                break;
            default : forme.setValue("carré");
                break;
        }
        
        
        this.setStyle("-fx-background-color: #336699;");
        this.getChildren().addAll(newGame,drapeau,nbDrapeau,time,timer,dif,forme);
        
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int forme2 = 0;
                switch((String)forme.getValue()){
                    case "carré": forme2=0;
                        break;
                    case "triangle": forme2=1;
                        break;
                }
                switch((String)dif.getValue()){
                    case "facile": controleur.restart(1,forme2);
                        break;
                    case "moyen": controleur.restart(2,forme2);
                        break;
                    case "difficile": controleur.restart(3,forme2);
                        break;
                    case "hardcore": controleur.restart(4,forme2);
                        break;
                }
            }
        });
        
    }
    
    public void placeBouttons(int width){
        newGame.setTranslateX((width-60)/2);
        newGame.setTranslateY(10);
        
        drapeau.setTranslateX(width/2 + 70);
        drapeau.setTranslateY((this.getHeight())/2-15);
        nbDrapeau.setTranslateX(width/2 + 100);
        nbDrapeau.setTranslateY((this.getHeight())/2+5);
        
        time.setTranslateX(width/2 + 170);
        time.setTranslateY((this.getHeight())/2-15);
        timer.setTranslateX(width/2 + 210);
        timer.setTranslateY((this.getHeight())/2+5);
        
        dif.setTranslateX(width/2 - 75 - forme.getWidth() - dif.getWidth());
        dif.setTranslateY((this.getHeight())/2-20);
        
        forme.setTranslateX(width/2 - 50 - forme.getWidth());
        forme.setTranslateY((this.getHeight())/2-20);
        
    }
    
    public void happy(){
        smiley.setImage(happy);
    }
    public void sad(){
        smiley.setImage(sad);
    }
    
    public void dodo(){
        smiley.setImage(dodo);
    }
    
    public void swag(){
        smiley.setImage(swag);
    }
    
    public void setNbDrapeau(int n){
        this.nbDrapeau.setText(": "+n);
    }
    
    public void setTime(int min, int sec){
        this.timer.setText(min+" : "+sec);
    }
    
    public void colorTimer(Color color){
        this.timer.setFill(color);
    }
}
