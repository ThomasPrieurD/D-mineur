
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
import javafx.geometry.Insets;
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
import javafx.scene.layout.StackPane;
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
    private Grille grille;
    private CaseVue[][] cases;
    private int dimX;
    private int dimY;
    
    @Override
    public void start(Stage primaryStage) {
        
        this.dimX=20;
        this.dimY=20;
        this.cases = new CaseVue[this.dimX][this.dimY];
        // initialisation du modèle que l'on souhaite utiliser
        grille = new Grille(0,this.dimX,this.dimY,50);
        
        // gestion du placement (permet de palcer le champ Text affichage en haut, et GridPane gPane au centre)
        BorderPane border = new BorderPane();
        
        // permet de placer les diffrents boutons dans une grille
        GridPane gPane = new GridPane();
        
        int column = 0;
        int row = 0;
        
        
        
        
        // la vue observe les "update" du modèle, et réalise les mises à jour graphiques
        grille.addObserver(new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                Rectangle layer;
                Text text;
                for(int i=0;i<dimX;i++){
                    for(int j=0;j<dimY;j++){
                        layer = (Rectangle)cases[i][j].getShape();
                        text = (Text)cases[i][j].getText();
                        switch(grille.getCase(i, j).getEtat()){
                            case 0: layer.setFill(Color.GREY);
                                break;
                            case 1:layer.setFill(Color.WHITE);
                                break;
                            case 2:layer.setFill(Color.BLACK);
                                break;
                            case 3:layer.setFill(Color.RED);
                                break;
                            default:layer.setFill(Color.GREY);
                                break;
                        }
                        if(grille.getCase(i,j).getNbMineVois()>0){
                            text.setText(Integer.toString(grille.getCase(i,j).getNbMineVois()));
                       }
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
        for (int i=0;i<dimX;i++) {
            for(int j=0;j<dimY;j++){
                StackPane stack  = new StackPane();
                Rectangle layer2 = new Rectangle(30, 30, Color.GREY);
                layer2.setArcWidth(10);
                layer2.setArcHeight(10);
                layer2.setX(i);
                layer2.setY(j);
                final Text text = new Text(0, 0, "");
                text.setFill(Color.DARKBLUE);
                stack.getChildren().addAll(layer2, text);
                StackPane.setMargin(text, null);
                gPane.add(stack, i, j);
                cases[i][j] = new CaseVue(layer2,text);

                // un controleur (EventHandler) par bouton écoute et met à jour le champ affichage
                layer2.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        int indiceX = (int)layer2.getX();
                        int indiceY = (int)layer2.getY();
                        if(event.getButton()==MouseButton.PRIMARY){
                            grille.clicG(indiceX,indiceY);
                        }
                        else{

                            grille.clicD(indiceX,indiceY);
                        }
                    }

                });
            }    
        }
        
        gPane.setGridLinesVisible(true);
        
        border.setCenter(gPane);
        
        Scene scene = new Scene(border, Color.BLACK);
        
        primaryStage.setTitle("D-mineur");
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
