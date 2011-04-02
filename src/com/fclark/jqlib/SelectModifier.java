package com.fclark.jqlib;

/**
 * Clase utilizada para enviar modificadores de la clausula SELECT de SQL tales
 * como DISTINCT, UNIQUE, indicaciones de optimización (Oracle Optimizer Hints),
 * etc.
 * 
 * @author Frederick Clark
 * @version 0.1 Feb. 17, 2011
 * 
 */
public class SelectModifier {

    protected String modifier;

    public SelectModifier(String modifier) {
        this.modifier = modifier;
    }

    public SelectModifier add(String newMod) {
        return new SelectModifier(this.toString() + " " + newMod);
    }
    
    public SelectModifier add(SelectModifier newMod) {
        return new SelectModifier(this.toString() + " " + newMod.toString());
    }

    @Override
    public String toString() {
        return this.modifier;
    }

}
