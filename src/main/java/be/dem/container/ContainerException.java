package be.dem.container;

public class ContainerException extends RuntimeException
{
    public ContainerException()
    {
        super("A ContainerException has been thrown");
    }
    
    public ContainerException(String message)
    {
        super(message);
    }
    
    public ContainerException(String message, Exception ex)
    {
        super(message, ex);
    }
    
    public ContainerException(Exception ex)
    {
        super(ex);
    }
}