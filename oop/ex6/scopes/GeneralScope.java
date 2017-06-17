package oop.ex6.scopes;

import oop.ex6.variables.VariableFactory;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

import java.lang.instrument.IllegalClassFormatException;
import java.util.*;

/**
 * Created by Ruthi on 15/06/2017.
 */
public abstract class GeneralScope {
    boolean isMain;
    int firstLine;
    private int lastLineNum;
    static  List<String> fileList;
    static int currentLine;
    HashMap<String, VariableFactory> mapOfVariables; // TODO static?
    static HashMap<String, MethodScope> mapOfMethods = new HashMap<String, MethodScope>();
    private boolean returnIndicator = false;


    public boolean check(List<String> fileListInput) throws Exception{
        fileList = fileListInput;
        forLoop:
        for (int i = firstLine; i < fileList.size(); i++)
            try {
                String[] line = fileList.get(i).trim().split("\\s+");

                switch (line[0]) {
                    case "void":
                        if (isMain) {
                            MethodScope newMethod = new MethodScope(i + 1, false,
                                    mapOfVariables);
                            System.out.println(newMethod.getName());
                            newMethod.check();
                            // TODO agregar metodo a map
                            i = newMethod.lastMethodLine();
                        } else {    // Exception if there's a method inside another method
                            throw new IllegalClassFormatException();
                        }
                        break;
                    case "while":
                    case "if":
                        if (!isMain) {
                            System.out.println(line[0] + " scope");
                            FunctionScope newFunction = new FunctionScope(mapOfVariables, i + 1, fileList);
                            newFunction.check();
                            i = newFunction.lastMethodLine();

                        } else {    // Throw exception if there's a function outside a method
                            throw new IllegalClassFormatException();
                        }
                        break;
                    case "int":
                    case "double":
                    case "String":
                    case "char":
                    case "boolean":
                        System.out.println(line[0] + " variable");
                        break;
                    case "}":
                        if (!returnIndicator) {
                            throw new MissingFormatArgumentException("return;");
                        }
                        lastLineNum = i;
                        return true;
                    case "return;":
                        returnIndicator = true;
                        break;
                    default:
                        switch (line[0]) { // First word of the line

                            case "":
                                break;
                            default: // TODO tengo que ver el caso que reciba por ej. a=3
                                throw new NoSuchElementException();
                        }
                        break;
                }
            } catch (MissingFormatArgumentException e) {
                System.err.println("Missing return line");
                System.err.println(0);
                System.err.close();
                break forLoop;

            } catch (IllegalClassFormatException e1) {
                System.err.println("Scope error");
            } catch (NoSuchElementException e2) {
                System.err.println("Non valid format");
            } catch (Exception e3){
                System.err.println("Method error "+e3);
            }

        return true;
    }


    public int lastMethodLine() {
        return lastLineNum;
    }
}