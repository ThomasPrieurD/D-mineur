/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControler;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
    
    private Shape layer;
    private Text text;
    private StackPane stack  = new StackPane();
    
    int X;
    int Y;
    int forme;
    public CaseVue(int forme, Text text,int i,int j,VueControleur vuecontrol) {
        
        drapeau.setPreserveRatio(true);
        mine.setPreserveRatio(true);
        mineR.setPreserveRatio(true);
        
        this.X = i;
        this.Y = j;
        this.forme = forme;
        
        this.text = text;
        this.text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        
        //carrés
        if(forme == 0){
            this.layer = new Rectangle(29, 29, Color.GREY);
            ((Rectangle) this.layer).setArcWidth(10);
            ((Rectangle) this.layer).setArcHeight(10);
            drapeau.setFitHeight(25);
            mine.setFitHeight(25);
            mineR.setFitHeight(25);
        }
        
        //triangles
        if(forme == 1){
            this.layer = new Polygon();
            if(X%2 == Y%2){
                ((Polygon) layer).getPoints().addAll(new Double[]{
                    (double)15, (double)0,
                    (double)0, (double)30,
                    (double)30, (double)30 });
            }
            else {
                ((Polygon) layer).getPoints().addAll(new Double[]{
                    (double)-15, (double)0,
                    (double)15, (double)0,
                    (double)0, (double)30 });
            }
            this.stack.setTranslateX(30*X-14*X);
            this.stack.setTranslateY(30*Y+Y);
            ((Polygon) layer).setFill(Color.GREY);
            //les stackpane se chevauche, il faut transmettre les évenements souris.
            if(X>0){
                Polygon prevLayer = new Polygon();
                if(X%2 == Y%2){
                    prevLayer.getPoints().addAll(new Double[]{
                        (double)0, (double)0,
                        (double)14, (double)0,
                        (double)0, (double)30 });
                }
                else {
                    prevLayer.getPoints().addAll(new Double[]{
                    (double)0, (double)0,
                    (double)0, (double)30,
                    (double)15, (double)30 });
                }
                prevLayer.setFill(Color.TRANSPARENT);
                prevLayer.setTranslateX(-8);
                stack.getChildren().add(prevLayer);
                prevLayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getButton()==MouseButton.PRIMARY){

                            vuecontrol.clicG(X-1, Y);
                        }
                        else{
                            vuecontrol.clicD(X-1, Y);
                        }
                    }

                });
                prevLayer.setOnMouseEntered(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent me){
                        Shape l = vuecontrol.getCases()[X-1][Y].getLayer();
                        if(l.getFill() == Color.GREY)
                            l.setFill(Color.LIGHTGREY);
                    }
                });
                prevLayer.setOnMouseExited(new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent me){
                        Shape l = vuecontrol.getCases()[X-1][Y].getLayer();
                        if(l.getFill() == Color.LIGHTGREY)
                            l.setFill(Color.GREY);
                    }
                });
            }
            
            drapeau.setFitHeight(15);
            mine.setFitHeight(15);
            mineR.setFitHeight(15);
        }
        
        //hexagones
        if(forme == 2){
            this.layer = new Polygon();
            ((Polygon) layer).getPoints().addAll(new Double[]{
                (double)8, (double)0,
                (double)22, (double)0,
                (double)30, (double)15,
                (double)22, (double)30,
                (double)8, (double)30,
                (double)0, (double)15 });
            this.stack.setTranslateX(23*X);
            if(X%2==0){
                this.stack.setTranslateY(30*Y+Y);
            } else {
                this.stack.setTranslateY(30*Y+Y+15);
            }
            ((Polygon) layer).setFill(Color.GREY);
             if(X>0){
                 if(Y>0 || X%2 != 0){
                    Polygon prevLayer = new Polygon();
                    prevLayer.getPoints().addAll(new Double[]{
                        (double)0, (double)0,
                        (double)8, (double)0,
                        (double)0, (double)15 });
                    prevLayer.setFill(Color.TRANSPARENT);
                    stack.getChildren().add(prevLayer);
                    prevLayer.setTranslateX(-11);
                    prevLayer.setTranslateY(-8.5);
                    prevLayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            int x,y;
                            x=X-1;
                            if(X%2 == 0) y=Y-1;
                            else y=Y;
                            if(event.getButton()==MouseButton.PRIMARY){

                                vuecontrol.clicG(x, y);
                            }
                            else{
                                vuecontrol.clicD(x, y);
                            }
                        }

                    });
                    prevLayer.setOnMouseEntered(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent me){
                            int x,y;
                            x=X-1;
                            if(X%2 == 0) y=Y-1;
                            else y=Y;
                            Shape l = vuecontrol.getCases()[x][y].getLayer();
                            if(l.getFill() == Color.GREY)
                                l.setFill(Color.LIGHTGREY);
                        }
                    });
                    prevLayer.setOnMouseExited(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent me){
                            int x,y;
                            x=X-1;
                            if(X%2 == 0) y=Y-1;
                            else y=Y;
                            Shape l = vuecontrol.getCases()[x][y].getLayer();
                            if(l.getFill() == Color.LIGHTGREY)
                                l.setFill(Color.GREY);
                        }
                    });
                }
                if(Y<vuecontrol.getDimY()-1 || X%2 == 0){
                    Polygon prevLayer = new Polygon();
                    prevLayer.getPoints().addAll(new Double[]{
                        (double)0, (double)15,
                        (double)8, (double)30,
                        (double)0, (double)30 });
                    prevLayer.setFill(Color.TRANSPARENT);
                    stack.getChildren().add(prevLayer);
                    prevLayer.setTranslateX(-11);
                    prevLayer.setTranslateY(8.5);
                    prevLayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            int x,y;
                            x=X-1;
                            if(X%2 != 0) y=Y+1;
                            else y=Y;
                            if(event.getButton()==MouseButton.PRIMARY){

                                vuecontrol.clicG(x, y);
                            }
                            else{
                                vuecontrol.clicD(x, y);
                            }
                        }
                    });
                    prevLayer.setOnMouseEntered(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent me){
                            int x,y;
                            x=X-1;
                            if(X%2 != 0) y=Y+1;
                            else y=Y;
                            Shape l = vuecontrol.getCases()[x][y].getLayer();
                            if(l.getFill() == Color.GREY)
                                l.setFill(Color.LIGHTGREY);
                        }
                    });
                    prevLayer.setOnMouseExited(new EventHandler<MouseEvent>(){
                        @Override
                        public void handle(MouseEvent me){
                            int x,y;
                            x=X-1;
                            if(X%2 != 0) y=Y+1;
                            else y=Y;
                            Shape l = vuecontrol.getCases()[x][y].getLayer();
                            if(l.getFill() == Color.LIGHTGREY)
                                l.setFill(Color.GREY);
                        }
                    });
                }
            }
            drapeau.setFitHeight(20);
            mine.setFitHeight(20);
            mineR.setFitHeight(20);
        }
        
        this.text.setFill(Color.DARKBLUE);
        stack.getChildren().addAll(this.layer, this.text,drapeau,mine,mineR);
        StackPane.setMargin(this.text, null);
        drapeau.setVisible(false);
        mine.setVisible(false);
        mineR.setVisible(false);
        adMouseEvents(drapeau,vuecontrol);
        adMouseEvents(this.layer,vuecontrol);
        adMouseEvents(this.text,vuecontrol);
    }
    
    public CaseVue(int forme,int i,int j,VueControleur vuecontrol){
        this(forme,new Text(0, 0, ""),i,j,vuecontrol);
    }
    
    public void colorTXt(int number){
        switch(number){
            case 0:this.text.setFill(Color.WHITE);
                break;
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
    
    public void adMouseEvents(Node node,VueControleur vuecontrol){
        node.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton()==MouseButton.PRIMARY){
                    
                    vuecontrol.clicG(X, Y);
                }
                else{
                    vuecontrol.clicD(X, Y);
                }
            }

        });
        
        node.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent me){
                if(layer.getFill() == Color.GREY)
                    layer.setFill(Color.LIGHTGREY);
            }
        });
        node.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent me){
                if(layer.getFill() == Color.LIGHTGREY)
                    layer.setFill(Color.GREY);
            }
        });
    }
    
    
    
    public Shape getLayer() {
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
    
    public void setVisibleMineR(boolean bool){
        mineR.setVisible(bool);
    }
    
    public void setVisibleMine(boolean bool){
        mine.setVisible(bool);
    }
    
    public void setVisibleDrapeau(boolean bool){
        drapeau.setVisible(bool);
    }
}
