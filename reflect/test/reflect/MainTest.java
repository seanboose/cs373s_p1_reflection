/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflect;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author terriBoose
 */
public class MainTest {
    
    public MainTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testMain() {
        RegTest.Utility.redirectStdOut("out.txt");  // redirects standard out to file "out.txt"
        System.out.println("main");
        String[] args = {"yparser", "/Users/terriBoose/repos/cs373s_softwaredesign/p1_reflection/otherclasses/yparser"};
        Main.main(args);
        // TODO review the generated test code and remove the default call to fail.
        RegTest.Utility.validate("out.txt", "correctOut.txt", false); // test passes if files are equal
    }
    
}
