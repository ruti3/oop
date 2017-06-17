package oop.ex6.scopes;

import java.lang.instrument.IllegalClassFormatException;

/**
 * Created by Ruthi on 12/06/2017.
 */
public class ScopeFactory extends GeneralScope {
    static MethodScope methodScope;
    static FunctionScope FunctionScope;

    public GeneralScope createScope (String[] line, boolean isMain){
        try {
            switch(line[0]) { // First word of the line
                case "void":
                    if (isMain) {
                        methodScope = new MethodScope(currentLine, false, mapOfVariables);

                        return methodScope;
                    } else {
                        throw new IllegalClassFormatException();
                    }
                case "if":
                    System.out.println("if scope");
                    break;
                case "while":
                    System.out.println("while scope");
                    break;
            }

        } catch (IllegalClassFormatException e){
            System.err.println("Format is not valid");
            System.err.println(0);
            System.out.close();
        }
        return null;
    }
}
