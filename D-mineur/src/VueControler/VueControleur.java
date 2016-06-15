
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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private int nbMines;
    private int forme;
    
    private Threads threads = new Threads();
    
    @Override
    public void start(Stage primaryStage) {
        
        this.dimX=10;
        this.dimY=10;
        this.nbMines = 10;
        this.forme=0;
        this.cases = new CaseVue[this.dimX][this.dimY];
        // initialisation du modèle que l'on souhaite utiliser
        grille = new Grille(this.forme,this.dimX,this.dimY,this.nbMines);
        
        // gestion du placement (permet de palcer le champ Text affichage en haut, et GridPane gPane au centre)
        BorderPane border = new BorderPane();
        
        // permet de placer les diffrents boutons dans une grille
        GridPane gPane = new GridPane();
        gPane.setStyle("-fx-background-color: #000000;");
        Menu menu = new Menu(this);
        Pane gauche = new Pane();
        gauche.setStyle("-fx-background-color: #000000;");
        
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        
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
                                text.setText("");
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
                switch(grille.getGameState()){
                    case 1 : menu.swag();
                        break;
                    case 2 : menu.sad();
                        break;
                    default : menu.happy();
                        break;
                }
                menu.setNbDrapeau(grille.getNbDrapeau());
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
        border.setTop(menu);
        border.setCenter(gPane);
        border.setLeft(gauche);
        //gauche.setMinWidth(50);
        
        Scene scene = new Scene(border, Color.BLACK);
        menu.setMinWidth(600);
        primaryStage.getIcons().add(new Image("images/mine.png"));
        
        primaryStage.setTitle("D-mineur");
        primaryStage.setScene(scene);
        primaryStage.show();
        menu.placeBouttons((int) gPane.getWidth());
        if(30*dimX<600){
            gauche.setMinWidth((600 - 30*dimX)/2);
        }
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
    
    public void restart(){
        ArrayList array = new ArrayList();
        array.add(forme);
        array.add(dimX);
        array.add(dimY);
        array.add(nbMines);
        threads.exec(2,array,grille);
    }

    public int getNbMines() {
        return nbMines;
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
