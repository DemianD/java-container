package be.dem.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container
{
    private final Map<Class, Object> mapping;
    private final Map<Class, Object> mappingSingleton;

    private static Container instance;

    private Container() 
    {
        this.mapping = new HashMap<>();
        this.mappingSingleton = new HashMap<>();
    }
    
    private static Container getInstance()
    {
        // TODO: Used Pattern: Singleton
        if (instance == null) {
            instance = new Container();
        }
        
        return instance;
    }
    
    public static <T> void bind(Class from, T to)
    {
        if (to instanceof Class) {
            to = (T) resolve((Class) to);
        }

        if ( ! from.isInterface()) {
            throw new ContainerException(String.format("%s is not an interface.", from.getName()));
        }

        if ( ! from.isAssignableFrom(to.getClass())) {
            throw new ContainerException(String.format("The concrete class %s must implement the interface %s.", to.getClass().getName(), from.getName()));
        }

        getInstance().mapping.put(from, to);
    }

    public static <T> void bindSingleton(Class from)
    {
        getInstance().mappingSingleton.put(from, null);
    }


    private static <T> T resolveInterface(Class<T> toBeResolved)
    {
        if(toBeResolved.isInterface()) {
            T result = (T) getInstance().mapping.get(toBeResolved);

            if (result != null) {
                return result;
            }

            throw new ContainerException(String.format("There is no binding specified for the interface %s", toBeResolved.getName()));
        }

        return null;
    }

    private static <T> T resolveSingleton(Class<T> toBeResolved, Object[] params) {

        boolean isSingleton = getInstance().mappingSingleton.containsKey(toBeResolved);

        if(isSingleton) {
            T result = (T) getInstance().mappingSingleton.get(toBeResolved);

            if(result == null) {
                result = createClass(toBeResolved, params);
                getInstance().mappingSingleton.replace(toBeResolved, result);
            }

            return result;
        }

        return null;
    }

    public static <T> T resolve(Class<T> toBeResolved, Object... params)
    {
        T result;

        result = resolveInterface(toBeResolved);
        if (result != null)
            return result;

        result = resolveSingleton(toBeResolved, params);
        if(result != null)
            return result;

        return createClass(toBeResolved, params);
    }

    private static <T> T createClass(Class<T> toBeResolved, Object[] params) {
        try
        {
            Constructor constructor = getInstance().getConstructor(toBeResolved);

            List<Object> parameters = new ArrayList<>();

            Class[] parameterTypes = constructor.getParameterTypes();

            for(Object param : params) {
                parameters.add(param);
            }

            for (int i = parameters.size(); i < parameterTypes.length; i++) {
                parameters.add(resolve(parameterTypes[i]));
            }

            return (T) constructor.newInstance(parameters.toArray());
        }
        catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            throw new ContainerException("An Exception has been thrown when making a Class", ex);
        }
    }

    public static void clear()
    {
        getInstance().mapping.clear();
    }

    private <T> Constructor getConstructor(Class<T> toBeResolved)
    {
        Constructor[] constructors = toBeResolved.getConstructors();

        if(constructors.length > 1)
            throw new ContainerException(String.format("The %s class should only have one constructor: ", toBeResolved.getName()));

        return constructors[0];
    }
}
