import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class AnnotationsDemo {

    /**
     * Returns the default value for a given primitive type or an empty array if the type is an array.
     * For non-primitive types, it returns null.
     *
     * @param type The class type of the parameter
     * @return The default value for the parameter type
     */
    private static Object getDefaultValue(Class<?> type) {
        if (type.isArray()) return Array.newInstance(type.getComponentType(), 0);

        return switch (type.getName()) {
            case "int" -> 0;
            case "boolean" -> false;
            case "double" -> 0.0;
            case "long" -> 0L;
            case "float" -> 0.0f;
            case "short" -> (short) 0;
            case "byte" -> (byte) 0;
            case "char" -> '\u0000';
            default -> null;
        };
    }

    /**
     * Instantiates an object of a given type, or returns the default value for primitive types.
     * If the type is not primitive, the method attempts to use the default constructor to instantiate the object.
     *
     * @param type The class type of the parameter
     * @return An instance of the object, or null if instantiation fails
     */
    private static Object instantiateObject(Class<?> type) {
        try {
            if (type.isPrimitive()) return getDefaultValue(type);

            final var constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            System.out.println("Failed to instantiate parameter of type: " + type.getName());
            return null;
        }
    }

    /**
     * Invokes methods annotated with {@link InvokeTimes} the specified number of times.
     * This method only invokes protected and private static methods.
     *
     * @param clazz The class containing the methods to be invoked
     */
    public static void invokeAnnotatedMethods(Class<?> clazz) {
        for (final var method : clazz.getDeclaredMethods()) {
            // Check if the method is annotated with @InvokeTimes
            if (!method.isAnnotationPresent(InvokeTimes.class)) continue;

            // Skip public methods since only protected or private methods should be invoked
            if (Modifier.isPublic(method.getModifiers())) continue;

            // Create default parameter values for the method
            final var paramValues = Arrays.stream(method.getParameterTypes()).map(AnnotationsDemo::instantiateObject).toArray();

            // Try invoking the method the specified number of times
            try {
                method.setAccessible(true);

                for (var i = 0; i < method.getAnnotation(InvokeTimes.class).value(); i++) {
                    method.invoke(null, paramValues);  // Assuming static methods
                    System.out.println("Invoked: " + method.getName() + " #" + (i + 1));
                }
            } catch (Exception e) {
                System.out.println("Error invoking method: " + method.getName() + " - " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        invokeAnnotatedMethods(TestClass.class);
    }

}

