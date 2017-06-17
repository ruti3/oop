package oop.ex6.scopes;


import com.sun.deploy.util.StringUtils;
import oop.ex6.variables.VariableFactory;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ruthi on 12/06/2017.
 */
public class MethodScope  extends GeneralScope {
    private int FIRST_LINE = 0;
    static final Pattern METHOD_PARAMETERS = Pattern.compile("(\\((.*?)\\)(.*?))");
    static Pattern methodNamePattern = Pattern.compile("[\\w\\d_]*[(]");
    static final Pattern METHOD_NAME = Pattern.compile("^([A-Za-z][A-Za-z0-9\\_]*)((?=\\()|$)"); // TODO caso de "name sf("
    static final String TRIM_PATTERN = "^\\s+|\\s+$";
    private Map<String, VariableFactory>  listOfVariables;
    private Matcher matcher;
    private String openingLine;



    public MethodScope(int firstLineNum, List<String> listOfLines, boolean isMainBlock) {
        firstLine = firstLineNum;
        isMain = isMainBlock;
        fileList = listOfLines;
        if (!isMain) {
            openingLine = fileList.get(firstLine - 1);
        }
    }

    public MethodScope(int firstLineNum, boolean isMainBlock, Map<String, VariableFactory> variables) {
        firstLine = firstLineNum;
        isMain = isMainBlock;
        listOfVariables = variables;
        if (!isMain) {
            openingLine = fileList.get(firstLine - 1);
        }
    }

    public boolean check () throws Exception{
        if (!isMain) {
            String methodName = getName();
            if (mapOfMethods != null) {

                // Check for duplicate methods
                if (mapOfMethods.containsKey(methodName)) {
                    throw new Exception();
                } else {
                    mapOfMethods.put(methodName, this);
                }
            } else {
                mapOfMethods.put(methodName, this);
            }
        }

        // Check the method's content
        super.check(fileList);

        // Check and extract the parameters
        try {
            matcher = METHOD_PARAMETERS.matcher(fileList.get(firstLine));
            if (matcher.find()){
                checkParameters(matcher.group(1)); //TODO aca me quedeeeeeeeeeeeeeeeeeeeeee chequear
            } else {
                throw new Exception(); // TODO ver exception
            }
        } catch (Exception e) {
            System.out.println(0);
            System.out.close();

        }
        return false;
    }

    /**
     * Checks if the method's name is legal.
     * @param name the method's name
     * @return the method's name if it is legal, throws exception otherwise
     * @throws IllegalArgumentException if name is illegal
     */
    private boolean checkName(String name) throws IllegalArgumentException {
        matcher = METHOD_NAME.matcher(name);
        if (matcher.find()){
            return true;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Check the parameters of a method.
     * @param parametersLine String that contains the method's parameters
     * @return Linked list that contain all the parameters.
     */
    private LinkedList<String[]> checkParameters (String parametersLine) throws Exception {
        LinkedList<String[]> parametersLinkedList = new LinkedList<>();
        String[] arrayOfParameters = parametersLine.split(",\\s+|\\(|\\)");

        for (String parameter : arrayOfParameters){
            if (!parameter.equals("")){
                String[] parameterArray = parameter.split(" ");
                String type = parameterArray[0];
                String name = parameterArray[1];
                if (checkParameterType(type) && checkParameterName(name)){
                    System.out.println(type+" "+name);
                    parametersLinkedList.add(parameterArray);
                } else {
                    throw new Exception(); // TODO catch expression aca?
                }
            }
        }
        return parametersLinkedList;

    }

    /**
     * Checks whether the parameter's name is legal
     * @param name name of the parameter
     * @return True if it is legal, false otherwise.
     */
    private boolean checkParameterName (String name){
        Pattern nameRegex = Pattern.compile("((^[A-Za-z]|(^_{2,}))[A-Za-z0-9\\_]*)|((^_+)[A-Za-z0-9\\_]+)");
        Matcher matcher = nameRegex.matcher(name);

        return matcher.find();
    }

    /**
     * Checks whether the type of a parameter is legal.
     * @param type type of the parameter
     * @return True if it is legal, false otherwise.
     */
    private boolean checkParameterType (String type){
        switch (type){
            case "double":
                return true;
            case "int":
                return true;
            case "boolean":
                return true;
            case "String":
                return true;
        }
        return false;
    }

    /**
     *  Returns the method's name.
     * @return method's name
     */
    public String getName(){
        String[] noSpacesLine = openingLine.trim().split("\\s+");
        int endIndex = noSpacesLine[1].length();
        if (noSpacesLine[1].contains("(")){
            endIndex = noSpacesLine[1].indexOf("(");
        }
        String methodName = noSpacesLine[1].substring(0, endIndex);
        if (checkName(methodName)){
            return methodName;
        }
        return null;
    }
}
