/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Case;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author theo
 */
public class CaseVue {
    
    private Shape shape;
    private Text text;

    public CaseVue(Shape shape, Text text) {
        this.shape = shape;
        this.text = text;
    }

    public Shape getShape() {
        return shape;
    }

    public Text getText() {
        return text;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setText(Text text) {
        this.text = text;
    }
    
}
