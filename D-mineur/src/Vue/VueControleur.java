
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;


import Modele.Grille;
import Modele.Modele;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseButton;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author théo
 */
public class VueControleur extends Application {
    
    // modèle : ce qui réalise le calcule de l'expression
    Grille grille;
    ArrayList cases = new ArrayList();
    
    @Override
    public void start(Stage primaryStage) {
        
        // initialisation du modèle que l'on souhaite utiliser
        int[] dimension = {20,20};
        grille = new Grille(0,dimension,20);
        
        // gestion du placement (permet de palcer le champ Text affichage en haut, et GridPane gPane au centre)
        BorderPane border = new BorderPane();
        
        // permet de placer les diffrents boutons dans une grille
        GridPane gPane = new GridPane();
        
        int column = 0;
        int row = 0;
        
        
        /*affichage = new Text("");
        affichage.setFont(Font.font ("Verdana", 20));
        affichage.setFill(Color.RED);
        border.setTop(affichage);*/
        
        // la vue observe les "update" du modèle, et réalise les mises à jour graphiques
        grille.addObserver(new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                for(int i=0;i<grille.getDim()[0]*grille.getDim()[1];i++){
                    Rectangle layer = (Rectangle)cases.get(i);
                    switch(grille.getCase(i).getEtat()){
                        case 0: layer.setFill(Color.GREY);
                            break;
                        case 1:layer.setFill(Color.BLUE);
                            break;
                        case 2:layer.setFill(Color.RED);
                            break;
                        case 3:layer.setFill(Color.BLUE);
                            break;
                        default:layer.setFill(Color.GREY);
                            break;
                    }
                }
            }
        });
        
        // on efface affichage lors du clic
        /*m.getGameBoard().setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                //affichage.setText("");
            }
            
        });
        */
        // création des bouton et placement dans la grille
        int l = 1;
        for(int i=0;i<grille.getDim().length;i++){
            l *= grille.getDim()[i];
        }
        for (int i=0;i<l;i++) {
            Rectangle layer2 = new Rectangle(30, 30, Color.GREY);
            layer2.setArcWidth(10);
            layer2.setArcHeight(10);
            layer2.setX(i);
            cases.add(layer2);
            gPane.add(layer2, column++, row);
            
            if (column > 19) {
                column = 0;
                row++;
                
            }
            
            // un controleur (EventHandler) par bouton écoute et met à jour le champ affichage
            layer2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                
                @Override
                public void handle(MouseEvent event) {
                    int indice = (int)layer2.getX();
                    if(event.getButton()==MouseButton.PRIMARY){
                        grille.clicG(indice);
                    }
                    else{
                        
                        grille.clicD(indice);
                    }
                }
                
            });
            
            
            
        }
        
        gPane.setGridLinesVisible(true);
        
        border.setCenter(gPane);
        
        Scene scene = new Scene(border, Color.BLACK);
        
        primaryStage.setTitle("Calc FX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
