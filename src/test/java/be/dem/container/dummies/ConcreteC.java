package be.dem.container.dummies;

public class ConcreteC
{
    private InterfaceB b;
    private ConcreteA a;

    public ConcreteC(InterfaceB concreteB, ConcreteA concreteA)
    {
        this.b = concreteB;
        this.a = concreteA;
    }
    
    public boolean hasA() 
    {
        return a != null;
    }
    
    public boolean hasB() 
    {
        return b != null;
    }
}
