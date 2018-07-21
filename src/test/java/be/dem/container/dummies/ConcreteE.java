package be.dem.container.dummies;

public class ConcreteE
{
    private ConcreteA concreteA;
    private ConcreteC concreteC;
    private InterfaceB concreteB;

    public ConcreteE(ConcreteA concreteA, ConcreteC concreteC, InterfaceB concreteB) {
        this.concreteA = concreteA;
        this.concreteC = concreteC;
        this.concreteB = concreteB;
    }

    public ConcreteA getConcreteA() {
        return concreteA;
    }

    public ConcreteC getConcreteC() {
        return concreteC;
    }

    public InterfaceB getConcreteB() {
        return concreteB;
    }
}
