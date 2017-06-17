package oop.ex6.variables;

/**
 * Created by Ruthi on 12/06/2017.
 */
public class VariableFactory {
    BooleanVariable booleanVariable = new BooleanVariable();
    IntVariable intVariable = new IntVariable();
    StringVariable stringVariable = new StringVariable();
    CharVariable charVariable = new CharVariable();
    DoubleVariable doubleVariable = new DoubleVariable();

    public VariablesStrategy createVariable (String typeOfVariable){
        return null;
    }
}
