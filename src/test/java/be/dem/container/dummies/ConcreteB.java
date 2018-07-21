package be.dem.container.dummies;

public class ConcreteB implements InterfaceB
{
    private ConcreteA concreteA = null;
    
    public ConcreteB(ConcreteA concreteA)
    {
        this.concreteA = concreteA;
    }
    
    public boolean hasConcreteA() 
    {
        return concreteA != null;
    }

    public ConcreteA getConcreteA() {
        return concreteA;
    }
}
