/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflect;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author terriBoose
 */
public class Main {
    
    static String mPackage_name = null;
    static String mDirectory = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        mPackage_name = args[0];
        mDirectory = args[1];
        System.out.println("package name: " + mPackage_name);
        System.out.println("directory: " + mDirectory);
        
        printHeader();
        ArrayList<String> files = getFilesFromString(mDirectory);

        for(String file : files){
                
                Class<?> cls = getClassFromFilename(file);
                if(cls != null){
                    System.out.println(cls);
                }
        }
    }
    
    private static void printHeader(){
        System.out.println(":- discontiguous bcClass/3, bcMember/7.");
        System.out.println(":- dynamic bcClass/3, bcMember/7.");
    }
    
    private static ArrayList<String> getFilesFromString(String directory){
        File dir = new File(directory);
        ArrayList<String> files = new ArrayList<String>(Arrays.asList(dir.list()));
        return files;
    }
    
    // Returns null if class is not found
    private static Class<?> getClassFromFilename(String file) {
        Class<?> c = null;
        // Ignore non-.class files
        if(file.endsWith(".class")){
            
            // Chop off the ".class" part of filename
            file = file.substring(0, file.length() - 6);
            
            // Get the file!
            try {
                c = Class.forName(mPackage_name + "." + file);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return c;
    }
}
