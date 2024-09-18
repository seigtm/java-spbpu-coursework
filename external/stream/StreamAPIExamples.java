import java.util.*;
import java.util.stream.Collectors;

/**
 * This class provides utility methods that demonstrate the use of the Java
 * Stream API for common data processing tasks such as calculating averages,
 * transforming collections, filtering unique values, and more.
 */
public final class StreamAPIExamples {

    /**
     * Returns the average of a list of integers.
     *
     * @param numbers the list of integers to calculate the average from.
     * @return the average value of the list as a double.
     * @throws IllegalArgumentException if the list is empty.
     */
    public static double averageValue(List<Integer> numbers) throws IllegalArgumentException {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("List is empty"));
    }

    /**
     * Converts all strings in the provided list to uppercase and adds the prefix
     * "_new_" to each string.
     *
     * @param strings the list of strings to transform.
     * @return a list of strings, each transformed to uppercase and prefixed with.
     */
    public static List<String> convertToUpperCaseWithPrefix(List<String> strings) {
        return strings.stream()
                .map(str -> "_new_" + str.toUpperCase())
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of squares of the elements that appear only once in the
     * provided list.
     *
     * @param numbers the list of integers to filter and transform.
     * @return a list of integers representing the squares of the unique elements.
     */
    public static List<Integer> uniqueSquares(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> Collections.frequency(numbers, n) == 1)
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    /**
     * Returns the last element in a collection.
     *
     * @param <T>        the type of elements in the collection.
     * @param collection the collection from which to retrieve the last element.
     * @return the last element of the collection.
     * @throws NoSuchElementException if the collection is empty.
     */
    public static <T> T getLastElement(Collection<T> collection) throws NoSuchElementException {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Collection is empty"));
    }

    /**
     * Returns the sum of all even numbers in an array of integers.
     *
     * @param numbers the array of integers to process.
     * @return the sum of even numbers, or 0 if there are no even numbers.
     */
    public static int sumOfEvens(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    /**
     * Transforms a list of strings into a {@code Map} where the first character of
     * each string is the key, and the remaining characters form the value.
     *
     * @param strings the list of strings to transform.
     * @return a map where each key is the first character of a string, and the
     *         value is the remaining part of the string.
     */
    public static Map<Character, String> transformToMap(List<String> strings) {
        return strings.stream()
                .collect(Collectors.toMap(
                        str -> str.charAt(0),
                        str -> str.substring(1),
                        (existing, replacement) -> existing // Retain first occurrence in case of duplicate keys.
                ));
    }

    /**
     * Helper method to test exceptions thrown by a method.
     *
     * @param methodName the name of the method being tested.
     * @param action     a lambda expression that calls the method.
     */
    private static void testException(String methodName, Runnable action) {
        try {
            action.run();

            // This should not be reached if the method throws an exception.
            System.out.println("TEST FAILED: " + methodName + " did not throw an exception.");
        } catch (Exception e) {
            System.out.println("TEST PASSED: " + methodName + " threw exception: " + e.getMessage() + '.');
        }
    }

    public static void main(String[] args) {
        System.err.println("---- Stream API Examples ----");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 2, 3, 4);
        List<String> strings = Arrays.asList("car", "ball", "cat", "dog", "apple");
        System.err.println("-- Testing methods on numbers=" + numbers + " and strings=" + strings + " --");

        System.out.println("\n---- Testing methods ----");
        System.out.println("Average:                    " + averageValue(numbers));
        System.out.println("Converted strings:          " + convertToUpperCaseWithPrefix(strings));
        System.out.println("Unique squares:             " + uniqueSquares(numbers));
        System.out.println("Last element:               " + getLastElement(numbers));
        System.out.println("Sum of evens:               " + sumOfEvens(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }));
        System.out.println("Sum of evens (empty array): " + sumOfEvens(new int[] {}));
        System.out.println("Transformed map:            " + transformToMap(strings));

        System.out.println("\n---- Testing exceptions  ----");
        testException("getLastElement", () -> getLastElement(new ArrayList<>()));
        testException("averageValue", () -> averageValue(new ArrayList<>()));

        System.out.println("\n---- Testing \'testException\' itself just for fun :)  ----");
        System.out.println("-- WARNING: all tests should fail after this line! --");
        testException("getLastElement", () -> getLastElement(numbers));
        testException("averageValue", () -> averageValue(numbers));
    }

}
