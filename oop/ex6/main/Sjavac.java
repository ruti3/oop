package oop.ex6.main;



import oop.ex6.scopes.GeneralScope;
import oop.ex6.scopes.MethodScope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Ruthi on 11/06/2017.
 */
public class Sjavac {

    public static void main(String[] args) {
        Path sourceFilePath = Paths.get(args[0]);
        List<String> fileLines;
        LinkedList<String> listOfMethods = null;
        MethodScope newMethod;
        GeneralScope generalScope;
        MethodScope mainScope;

        try {
            fileLines = Files.readAllLines(sourceFilePath);
            // Create main block
            mainScope = new MethodScope(0, fileLines, true);
            mainScope.check();
        } catch (IOException e){
            throw new Error();
        } catch (Exception e2){
            System.err.println(0);
            System.err.close();
        }



    }

}
