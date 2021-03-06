
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private final Threads threads = new Threads();
    private int difficulte = 1;
    private int forme = 0;
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        
        this.primaryStage = primaryStage;
        
        // initialisation du modèle que l'on souhaite utiliser
        switch(difficulte){
            case 1 :grille = new Grille(forme,10,10,10,-1);
                break;
            case 2 :grille = new Grille(forme,30,15,70,-1);
                break;
            case 3 :grille = new Grille(forme,40,20,150,-1);
                break;
            case 4 :grille = new Grille(forme,40,20,150,300);
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
        if(grille.getTime()>0){
            int tempsRest = grille.getTime() - (60*grille.getTimer().getMin() + grille.getTimer().getSec());
            menu.setTime(tempsRest/60,tempsRest%60);
        }
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
                            case 2:layer.setFill(Color.DARKGRAY);
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
                menu.setNbDrapeau(grille.getMines() - grille.getNbDrapeau());
            }
        });
        
        grille.getTimer().addObserver(new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                if(grille.getTime()>0){
                    int tempsRest = grille.getTime() - (60*grille.getTimer().getMin() + grille.getTimer().getSec());
                    menu.setTime(tempsRest/60,tempsRest%60);
                    if(tempsRest/60==0 && tempsRest%60==0){
                        menu.colorTimer(Color.RED);
                    }
                    else {
                        menu.colorTimer(Color.WHITE);
                    }
                } else {
                    menu.setTime(grille.getTimer().getMin(),grille.getTimer().getSec());
                }
            }
        });
        
        // création des cases et placement dans la grille
        for (int i=0;i<grille.getDimX();i++) {
            for(int j=0;j<grille.getDimY();j++){
                cases[i][j] = new CaseVue(grille.getForme(),i,j,this);
                if(forme == 0){
                    ((GridPane)gPane).add(cases[i][j].getStack(), i, j);
                }
                else if (forme > 0){
                    ((Pane )gPane).getChildren().addAll(cases[i][j].getStack());
                }
            }    
        }
        
        
        border.setTop(menu);
        border.setCenter(gPane);
        border.setLeft(gauche);
        
        switch (forme) {
            case 0:
                ((GridPane) gPane).setGridLinesVisible(true);
                break;
            case 1:
                gPane.setMinWidth(32*((grille.getDimX()/2))+15);
                gPane.setMinHeight(30*grille.getDimY()+grille.getDimY());
                break;
            case 2:
                gPane.setMinWidth(23*(grille.getDimX()-1)+31);
                gPane.setMinHeight(30*grille.getDimY()+grille.getDimY()+15);
                break;
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
        if(this.forme == 0 && 30*grille.getDimX()<600){
            gauche.setMinWidth((600 - 30*grille.getDimX())/2);
        }
        if(this.forme == 1 && 32*((grille.getDimX()/2))+15<600){
            gauche.setMinWidth((600 - (32*((grille.getDimX()/2))+15))/2);
        }
        if(this.forme == 2 && (23*(grille.getDimX()-1)+31)<600){
            gauche.setMinWidth((600 - ((23*grille.getDimX()-1)+31))/2);
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
            
        }
        else{
            this.difficulte = diff;
            this.forme = forme2;
            this.grille.closeTimer();
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

    public CaseVue[][] getCases() {
        return cases;
    }
    
    public int getDimX(){
        return grille.getDimX();
    }
    
    public int getDimY(){
        return grille.getDimY();
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
