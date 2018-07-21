package be.dem.container;

import be.dem.container.dummies.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ContainerTest
{

    @Before
    public void before()
    {
        Container.clear();
    }

    @Test
    public void testItCanBindAnInterfaceToAConcreteRepresentation()
    {
        Class from = List.class;
        List to = new ArrayList<>();

        to.add("a");
        to.add("b");
        to.add("c");

        Container.bind(from, to);

        assertSame(to, Container.resolve(from));
        assertEquals(3, ((List) Container.resolve(from)).size());
    }

    @Test
    public void testWhenBindingTheConcreteClassImplementsTheGivenInterface()
    {
        Class from = List.class;
        List to = new ArrayList<>();

        Container.bind(from, to);
        assertSame(to, Container.resolve(from));
    }


    @Test(expected = ContainerException.class)
    public void testWhenBindingTheConcreteClassMustImplementTheGivenInterface()
    {
        Class from = List.class;
        Set to = new HashSet<>();

        Container.bind(from, to);
    }

    @Test(expected = ContainerException.class)
    public void testWhenBindingTheFirstArgumentMustBeAnInterface()
    {
        Container.bind(ArrayList.class, null); // ArrayList instead of List
    }

    @Test(expected = ContainerException.class)
    public void testItThrowsAnExceptionWhenNoBindingsExist()
    {
        Container.resolve(List.class);
    }

    @Test
    public void testWhenBindingTheSecondArgumentCanBeAClassObject()
    {
        Container.bind(InterfaceB.class, ConcreteB.class);
    }
    
    @Test
    public void testItCanResolveConcreteClassesWithoutBinding()
    {
        ConcreteA concreteA = Container.resolve(ConcreteA.class);
        
        assertNotNull(concreteA);
        assertEquals("This is a concrete A class", concreteA.toString());
    }
    
    @Test
    public void testItCanRecursiveInjectConcreteDependenciesInTheConstructor()
    {
        ConcreteB concreteB = Container.resolve(ConcreteB.class);
        
        assertNotNull(concreteB);
        assertTrue(concreteB.hasConcreteA());
    }
    
    @Test
    public void testItCanRecursiveInjectConcreteAndInterfaceDependenciesInTheConstructor()
    {
        Container.bind(InterfaceB.class, Container.resolve(ConcreteB.class));
        
        ConcreteC concreteC = Container.resolve(ConcreteC.class);
        
        assertNotNull(concreteC);
        assertTrue(concreteC.hasA());
        assertTrue(concreteC.hasB());
    }
    
    @Test(expected = ContainerException.class)
    public void testThereCanOnlyBeOneConstructor()
    {
        Container.resolve(ConcreteD.class);
    }

    @Test
    public void testYouCanPassAdditionalParametersToResolveMethod()
    {
        ConcreteA a = new ConcreteA();

        ConcreteB b = Container.resolve(ConcreteB.class, a);

        assertSame(a, b.getConcreteA());
    }

    @Test
    public void testYouCanPassAdditionalParametersToResolveMethodAndKeepResolvingOtherParameters()
    {
        Container.bind(InterfaceB.class, Container.resolve(ConcreteB.class));

        ConcreteA a = new ConcreteA();
        ConcreteE e = Container.resolve(ConcreteE.class, a);

        assertSame(a, e.getConcreteA());
        assertNotNull(e.getConcreteB());
        assertNotNull(e.getConcreteC());
    }

    @Test
    public void testSingleton_TwoResolves_OneInstance()
    {
        Container.bindSingleton(ConcreteA.class);

        ConcreteA a_1 = Container.resolve(ConcreteA.class);
        ConcreteA a_2 = Container.resolve(ConcreteA.class);

        assertSame(a_1, a_2);
    }

    @Test
    public void testSingleton_ThreeResolves_OneInstance()
    {
        Container.bindSingleton(ConcreteA.class);

        ConcreteA a_1 = Container.resolve(ConcreteA.class);
        ConcreteA a_2 = Container.resolve(ConcreteA.class);
        ConcreteA a_3 = Container.resolve(ConcreteA.class);

        assertSame(a_1, a_2);
        assertSame(a_2, a_3);
        assertSame(a_3, a_1);
    }
}

