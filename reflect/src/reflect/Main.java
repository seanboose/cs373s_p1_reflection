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
        
        if(args.length < 2){
            System.out.println("Required arguments: package_name, path/to/package");
        }
        mPackage_name = args[0];
        mDirectory = args[1];

        printHeader();
        ArrayList<String> files = getFilesFromString(mDirectory);

        for(String file : files){
            Class<?> cls = getClassFromFilename(file);
            if(cls != null){
                printClassContents(cls);
            }
        }
    }
    
    // Prints header information for output file
    private static void printHeader(){
        System.out.println(":- discontiguous bcClass/3, bcMember/7.");
        System.out.println(":- dynamic bcClass/3, bcMember/7.\n");
    }
    
    // Creates a list of Strings from a directory File
    private static ArrayList<String> getFilesFromString(String directory){
        File dir = new File(directory);
        ArrayList<String> files = new ArrayList<String>(Arrays.asList(dir.list()));
        return files;
    }
    
    // Returns a Class object from a filename
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
    // Prints the contents of a Class object to match desired output for 
    // assignment
    private static void printClassContents(Class<?> cls){
        int classNum = mClasses++;
        
        // Class name
        System.out.println("bcClass(c" + classNum + ",'" + cls.getName() + "','" + formatType(cls.getSuperclass().getName(), false) + "').\n");
        
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
    
    // Prints the contents of a Constructor object to match desired output for 
    // assignment
    private static void printConstructor(Constructor con, int classNum){
        System.out.print("bcMember(");
        System.out.print("m" + mFields++ + ",");
        System.out.print("c" + classNum + ",");
        System.out.print("true,");
        System.out.print("false,");
        System.out.print("'" + con.getName() + "',");
        System.out.print("'',");
        System.out.print("'" + formatConstructorName(con.getName()) + "(");
        printParameters(con.getParameterTypes());
        System.out.println(")').");
    }
    
    // Simplifies fully qualified constructor class name (eg: "yparser.Main"
    // becomes just "Main"
    private static String formatConstructorName(String conName){
        return conName.substring(conName.lastIndexOf('.')+1);
    }
    
    // Takes the parameters of a method and prints properly formatted versions
    // of them
    private static void printParameters(Class<?>[] params){
        if(params.length > 0){
            System.out.print(formatType(params[0].getName(), true));
            for(int i=1; i < params.length; ++i){
                System.out.print(",");
                System.out.print(formatType(params[i].getName(), true));
            }
        }
    }
    
    // Prints the contents of a Field object to match desired output for 
    // assignment
    private static void printField(Field field, int classNum){
        
        Modifier mod = new Modifier();
        int mods = field.getModifiers();
        String type = formatType(field.getType().getName(), false);
        
        System.out.print("bcMember(");
        System.out.print("m" + mFields++ + ",");
        System.out.print("c" + classNum + ",");
        System.out.print(mod.isStatic(mods) + ",");
        System.out.print("true,");
        System.out.print("'" + type + "',");
        
        System.out.print("'");
        printBrackets(field.getType().getName());
        System.out.print("',");
        
        System.out.println("'" + field.getName() + "').");
    }
    
    // Prints the contents of a Method object to match desired output for assignment
    private static void printMethod(Method method, int classNum){
        Modifier mod = new Modifier();
        int mods = method.getModifiers();
        System.out.print("bcMember(");
        System.out.print("m" + mFields++ + ",");
        System.out.print("c" + classNum + ",");
        System.out.print(mod.isStatic(mods) + ",");
        System.out.print("false,");
        System.out.print("'" + formatType(method.getReturnType().getName(), false) + "',");
        
        System.out.print("'");
        printBrackets(method.getReturnType().getName());
        System.out.print("',");
        
        System.out.print("'" + method.getName() + "(");
        printParameters(method.getParameterTypes());
        System.out.println(")').");
    }
    
    private static void printBrackets(String name){
        int arrayDepth = countArray(name);
        for(int i=0; i<arrayDepth; ++i){
            System.out.print("[]");
        }
    }
    
    // Counts the depth of an array from a string containing a type name
    // encoded in the Java Class "[[LObject" format
    private static int countArray(String name){
        int count = 0;
        for(int i=0; i<name.length(); ++i){
            if(name.charAt(i) == '[') ++count;
        }
        return count;
    }
    
    // Given a String containing a type name, removes leading "java.lang." and
    // reformats array indication from the java Class.geName() format .
    // For example, "java.lang.[[LObject" to "Object[][]"
    private static String formatType(String types, boolean addBrackets){
        
        String newType = types.replaceAll("java.lang.","").replaceAll(";","");
        int count = countArray(newType);
        newType = newType.replaceAll("(\\[)+([ZBCLDFIJS])","");
        
        if(addBrackets){
            StringBuilder temp = new StringBuilder(newType);
            while(count > 0){
                temp.append("[]");
                --count;
            }
            newType = temp.toString();
        }

        return newType;
    }
}
