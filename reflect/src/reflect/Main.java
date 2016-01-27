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
import java.lang.reflect.Modifier;
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
    static int mClasses = 0;
    static int mFields = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        mPackage_name = args[0];
        mDirectory = args[1];
//        System.out.println("package name: " + mPackage_name);
//        System.out.println("directory: " + mDirectory);
        
        printHeader();
        ArrayList<String> files = getFilesFromString(mDirectory);

//        int classNum = 0;
//        int memNum = 0;
        for(String file : files){
            Class<?> cls = getClassFromFilename(file);
            if(cls != null){
                printClassContents(cls);
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
    
    private static void printClassContents(Class<?> cls){
        int classNum = mClasses++;
        
        // Class name
        System.out.println("bcClass(c" + classNum + ",'" + cls.getName() + "','" + cls.getSuperclass().getName().replaceAll("java.lang.","") + "').\n");
        
        // Public Constructors
        System.out.println("/* public Constructors */");
        Constructor[] constructors = cls.getDeclaredConstructors();
        for(Constructor con : constructors){
            printConstructor(con, classNum);
        }
        System.out.println();
        
        // Public Fields
        System.out.println("/* public Fields */");
        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields){
            printField(field, classNum);
        }
        System.out.println();
        
        // Public Methods
        System.out.println("/* public Methods */");
        Method[] methods = cls.getDeclaredMethods();
        for(Method method : methods){
            printMethod(method, classNum);
        }
        System.out.println();
        
        //Final text in formatting
        System.out.println("/*-------------*/");
    }
    
    private static void printConstructor(Constructor con, int classNum){
        System.out.print("bcMember(");
        System.out.print("m" + mFields++ + ",");
        System.out.print("c" + classNum + ",");
        System.out.print("true,");
        System.out.print("false,");
        System.out.print(con.getName() + ",");
        System.out.print("<ARRAY>,");
        System.out.print(con.getName() + "(");
        printParameters(con.getParameterTypes());
        System.out.println(")').");
        
        

    }
    
    private static void printParameters(Class<?>[] params){
        if(params.length > 0){
            System.out.print(params[0].getName().replaceAll("java.lang.",""));
            for(int i=1; i < params.length; ++i){
                System.out.print(",");
                System.out.print(params[i].getName().replaceAll("java.lang.",""));
            }
        }
    }
    
    private static void printField(Field field, int classNum){
        
        Modifier mod = new Modifier();
        int mods = field.getModifiers();
        String type = field.getType().getName().replaceAll("java.lang.","");
        
        System.out.print("bcMember(");
        System.out.print("m" + mFields++ + ",");
        System.out.print("c" + classNum + ",");
        System.out.print(mod.isStatic(mods) + ",");
        System.out.print("true,");
        System.out.print(type + ",");
        System.out.print("<ARRAY>,");
        System.out.println(field.getName() + ").");

    }
    
    private static void printMethod(Method method, int classNum){
        Modifier mod = new Modifier();
        int mods = method.getModifiers();
        System.out.print("bcMember(");
        System.out.print("m" + mFields++ + ",");
        System.out.print("c" + classNum + ",");
        System.out.print(mod.isStatic(mods) + ",");
        System.out.print("false,");
        System.out.print(method.getReturnType().getName().replaceAll("java.lang.","") + ",");
        System.out.print("<ARRAY>,");
        System.out.print(method.getName() + "(");
        printParameters(method.getParameterTypes());
        System.out.println(")').");

        
    }
}
