package oop.ex6.scopes;

import oop.ex6.variables.VariableFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Ruthi on 12/06/2017.
 */
public class FunctionScope extends GeneralScope{
    private String[] line;
    private Map<String, VariableFactory> mapOfVariables;
    private List<String> fileLines;
    private GeneralScope generalScope;
    private List<String> scope;



    public FunctionScope (Map<String, VariableFactory> variables, int firstLineNum, List<String> listOfLines){
        scope = listOfLines;
        mapOfVariables = variables;
        firstLine = firstLineNum;

    }

    public boolean check(){
        try {
            super.check(scope);
        }catch (Exception e){
            System.err.close();
        }
        return false;
    }


}
