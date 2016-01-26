/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflect;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author terriBoose
 */
public class Reflect {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String package_name = args[0];
        String directory = args[1];
        System.out.println("package name: " + package_name);
        System.out.println("directory: " + directory);
        
        System.out.println("CLASSPATH:");
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();
        for(URL url : urls){
            System.out.println(url.getFile());
        }
        System.out.println("CLASSPATHFORM2: " + System.getProperty("java.class.path"));
        
        Class<?> c = null;
        try { 
            c = Class.forName(package_name);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reflect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(c.toString());
        Class<?>[] c2 = c.getClasses();
        for(Class<?> cls : c2){
            System.out.println(cls);
        }
        
    }
    
}
