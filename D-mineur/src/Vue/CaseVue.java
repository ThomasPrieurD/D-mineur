/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Case;

/**
 *
 * @author theo
 */
public class CaseVue {
    
    private Case c;

    public CaseVue(Case c) {
        this.c = c;
    }
    
    public CaseVue(){
        this(new Case());
    }
}
