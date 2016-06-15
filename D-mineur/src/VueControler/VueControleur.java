
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControler;


import Modele.Grille;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Text;

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
    
    private Threads threads = new Threads();
    
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
        
        // la vue observe les "update" du modèle, et réalise les mises à jour graphiques
        grille.addObserver(new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                CaseVue c;
                Rectangle layer;
                Text text;
                for(int i=0;i<dimX;i++){
                    for(int j=0;j<dimY;j++){
                        layer = (Rectangle)cases[i][j].getLayer();
                        text = (Text)cases[i][j].getText();
                        c = cases[i][j];
                        switch(grille.getCase(i, j).getEtat()){
                            case 0: layer.setFill(Color.GREY);
                                c.setVisibleMineR(false);
                                c.setVisibleMine(false);
                                c.setVisibleDrapeau(false);
                                break;
                            case 1:layer.setFill(Color.WHITE);
                                cases[i][j].colorTXt(grille.getCase(i,j).getNbMineVois());
                                text.setText(Integer.toString(grille.getCase(i,j).getNbMineVois()));
                                c.setVisibleMineR(false);
                                c.setVisibleMine(false);
                                c.setVisibleDrapeau(false);
                                break;
                            case 2:layer.setFill(Color.BLACK);
                                c.setVisibleMineR(false);
                                c.setVisibleMine(false);
                                c.setVisibleDrapeau(true);
                                break;
                            case 3:layer.setFill(Color.RED);
                                c.setVisibleMineR(true);
                                c.setVisibleMine(false);
                                c.setVisibleDrapeau(false);
                                break;
                            case 4:layer.setFill(Color.RED);
                                c.setVisibleMineR(false);
                                c.setVisibleMine(true);
                                c.setVisibleDrapeau(false);
                                break;
                            case 5:layer.setFill(Color.DARKBLUE);
                                c.setVisibleMineR(false);
                                c.setVisibleMine(true);
                                c.setVisibleDrapeau(false);
                                break;
                            default:layer.setFill(Color.GREY);
                                break;
                        }
                    }
                }
            }
        });
        
        // création des cases et placement dans la grille
        for (int i=0;i<dimX;i++) {
            for(int j=0;j<dimY;j++){
                cases[i][j] = new CaseVue(i,j,this);
                gPane.add(cases[i][j].getStack(), i, j);
            }    
        }
        
        gPane.setGridLinesVisible(true);
        
        border.setCenter(gPane);
        
        Scene scene = new Scene(border, Color.BLACK);
        
        primaryStage.getIcons().add(new Image("images/mine.png"));
        
        primaryStage.setTitle("D-mineur");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void clicG(int i,int j){
        ArrayList array = new ArrayList();
        array.add(i);
        array.add(j);
        array.add(cases[i][j]);
        threads.exec(0,array,grille);
    }
    
    public void clicD(int i,int j){
        ArrayList array = new ArrayList();
        array.add(i);
        array.add(j);
        array.add(cases[i][j]);
        threads.exec(1,array,grille);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}