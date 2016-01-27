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

        RegTest.Utility.redirectStdOut("outYparser.txt");  // redirects standard out to file "out.txt"
        String[] args1 = {"yparser", 
            "/Users/terriBoose/repos/cs373s_softwaredesign/p1_reflection/otherclasses/yparser"};
        Main.main(args1);
        RegTest.Utility.validate("outYparser.txt", "correctOutYparser.txt", false); // test passes if files are equal

        RegTest.Utility.redirectStdOut("outReflect.txt");  // redirects standard out to file "out.txt"
        String[] args2 = {"reflect", 
            "/Users/terriBoose/repos/cs373s_softwaredesign/p1_reflection/reflect/build/classes/reflect/"};
        RegTest.Utility.validate("outReflect.txt", "correctOutReflect.txt", false);
    }
    
}
