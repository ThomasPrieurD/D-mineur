
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControler;


import Modele.Grille;
import Modele.Timer;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author théo
 */
public class VueControleur extends Application {
    
    private Grille grille;
    private CaseVue[][] cases;
    private final Timer timer = new Timer();
    private final Thread TimerThread = new Thread (timer);
    private final Threads threads = new Threads();
    private int difficulte = 1;
    private int forme = 0;
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        
        this.primaryStage = primaryStage;
        
        // initialisation du modèle que l'on souhaite utiliser
        switch(difficulte){
            case 1 :grille = new Grille(forme,10,10,10);
                break;
            case 2 :grille = new Grille(forme,30,15,70);
                break;
            case 3 :grille = new Grille(forme,40,20,150);
                break;
        }
        
        this.cases = new CaseVue[grille.getDimX()][grille.getDimY()];
        
        
        
        // gestion du placement (permet de palcer le champ Text affichage en haut, et GridPane gPane au centre)
        BorderPane border = new BorderPane();
        
        // permet de placer les diffrents boutons dans une grille
        Pane gPane;
        if(grille.getForme() == 1){
            gPane = new Pane();
            
        }
        else{
            gPane = new GridPane();
        }
        gPane.setStyle("-fx-background-color: #000000;");
        Menu menu = new Menu(this);
        Pane gauche = new Pane();
        gauche.setStyle("-fx-background-color: #000000;");
        
        

        
        // la vue observe les "update" du modèle, et réalise les mises à jour graphiques
        grille.addObserver(new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                CaseVue c;
                Shape layer;
                Text text;
                for(int i=0;i<grille.getDimX();i++){
                    for(int j=0;j<grille.getDimY();j++){
                        layer = (Shape)cases[i][j].getLayer();
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
                            case 4:layer.setFill(Color.BLACK);
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
                    case -1 : menu.dodo();
                        break;
                    case 1 : menu.swag();
                        break;
                    case 2 : menu.sad();
                        break;
                    default : menu.happy();
                        break;
                }
                if(grille.getGameState()>0){
                    timer.pause();
                }
                menu.setNbDrapeau(grille.getMines() - grille.getNbDrapeau());
            }
        });
        
        timer.addObserver(new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                menu.setTime(timer.getMin(),timer.getSec());
            }
        });
        
        // création des cases et placement dans la grille
        for (int i=0;i<grille.getDimX();i++) {
            for(int j=0;j<grille.getDimY();j++){
                cases[i][j] = new CaseVue(grille.getForme(),i,j,this);
                if(forme == 0){
                    ((GridPane)gPane).add(cases[i][j].getStack(), i, j);
                }
                else if (forme == 1){
                    ((Pane )gPane).getChildren().addAll(cases[i][j].getStack());
                }
            }    
        }
        
        
        border.setTop(menu);
        border.setCenter(gPane);
        border.setLeft(gauche);
        //gauche.setMinWidth(50);
        
        if(forme == 0){
            ((GridPane) gPane).setGridLinesVisible(true);
        }
        else {
            gPane.setMinWidth(32*((grille.getDimX()/2))+15);
            gPane.setMinHeight(30*grille.getDimY()+grille.getDimY());
        }
        
        Scene scene = new Scene(border, Color.BLACK);
        menu.setMinWidth(600);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.getIcons().add(new Image("images/mine.png"));
        primaryStage.setTitle("D-mineur");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        menu.placeBouttons((int) gPane.getWidth());
        if(30*grille.getDimX()<600 && this.forme == 0){
            gauche.setMinWidth((600 - 30*grille.getDimX())/2);
        }
        if(32*((grille.getDimX()/2))+15<600 && this.forme == 1){
            gauche.setMinWidth((600 - (32*((grille.getDimX()/2))+15))/2);
        }
        if(TimerThread.getState() == Thread.State.NEW){
            TimerThread.setDaemon(true);
            TimerThread.start();
        }
        else {
            timer.restart();
            synchronized (timer){
                timer.notify();
            }
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
    
    public void restart(int diff,int forme2){
        if(diff == this.difficulte && forme2 == this.forme){
            ArrayList array = new ArrayList();
            array.add(grille.getForme());
            array.add(grille.getDimX());
            array.add(grille.getDimY());
            array.add(grille.getMines());
            threads.exec(2,array,grille);
            timer.restart();
            synchronized (timer){
                timer.notify();
            }
        }
        else{
            this.difficulte = diff;
            this.forme = forme2;
            this.primaryStage.close();
            start(new Stage());
        }
    }

    public int getNbMines() {
        return grille.getMines();
    }

    public int getDifficulte() {
        return difficulte;
    }

    public int getForme() {
        return forme;
    }
    
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
