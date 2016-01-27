/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflect;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
        mPackage_name = args[0];
        mDirectory = args[1];
        System.out.println("package name: " + mPackage_name);
        System.out.println("directory: " + mDirectory);
        
        printHeader();
        ArrayList<String> files = getFilesFromString(mDirectory);

        int count = 0;
        for(String file : files){
            Class<?> cls = getClassFromFilename(file);
            if(cls != null){
                printClassContents(cls, count++);
            }
        }
    }
    
    private static void printHeader(){
        System.out.println(":- discontiguous bcClass/3, bcMember/7.");
        System.out.println(":- dynamic bcClass/3, bcMember/7.\n");
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
    
    private static void printClassContents(Class<?> cls, int clsNum){

        // Keeps track of all members' index
        int memNum = 0;
        
        // Class name
        System.out.println("bcClass(c" + clsNum + ",'" + cls.getName() + "','" + cls.getSuperclass().getName().replaceAll("java.lang.","") + "').\n");
        
        // Public Constructors
        System.out.println("/* public Constructors */");
        Constructor[] constructors = cls.getConstructors();
        
        // Public Fields
        System.out.println("/* public Fields */");
        Field[] fields = cls.getFields();
        
        // Public Methods
        System.out.println("/* public Methods */");
        Method[] methods = cls.getMethods();
        
        //Final text in formatting
        System.out.println("/*-------------*/");
    }
}
