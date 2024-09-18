/**
 * A test class containing methods with and without the {@link InvokeTimes} annotation.
 */
public final class TestClass {

    static public void publicNotAnnotated(String str) {}

    @InvokeTimes(100)
    static public void publicAnnotated(int min, int max) {}

    static protected void protectedNotAnnotated(double value) {}

    @InvokeTimes(2)
    static protected void protectedAnnotated(String first, String second) {}

    static private void privateNotAnnotated(int value, String str) {}

    @InvokeTimes(3)
    static private void privateAnnotated(double value, String first, String second) {}

}
