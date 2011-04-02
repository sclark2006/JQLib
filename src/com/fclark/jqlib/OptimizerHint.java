package com.fclark.jqlib;
/**
 * 
 * @author Frederick Clark
 * @version 0.2 Feb.17 2011. Modificada para heredar la clase SelectModifier 
 */

public class OptimizerHint extends SelectModifier {
    
    public OptimizerHint(String modifier) {
        super(modifier);
    }

    @Override
    public String toString() {
        return "/*+" + this.modifier + "*/\n";
    }
}
