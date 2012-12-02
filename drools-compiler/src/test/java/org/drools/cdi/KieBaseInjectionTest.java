package org.drools.cdi;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.KnowledgeBase;
import org.kie.cdi.KBase;

import static org.junit.Assert.*;

@RunWith(CDITestRunner.class)
public class KieBaseInjectionTest {

    @Inject @KBase("org.kie.kbase1" )
    private KnowledgeBase kBase1;
    
    @Test 
    public void test1() {
        assertNotNull( kBase1 );
        assertEquals( 1, kBase1.getKnowledgePackage( "org.kie.kbase1" ).getRules().size() );
    }
    
}
