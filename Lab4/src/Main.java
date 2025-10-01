import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // Пункт 1
    public static OptionalDouble average(List<Integer> numbers){
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average();
    }

    // Пункт 2
    public static List<String> transformStrings(List<String> strings) {
        return strings.stream()
                .map(s -> "_new_" + s.toUpperCase())
                .collect(Collectors.toList());
    }

    // Пункт 3
    public static List<Integer> uniqueSquares(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> Collections.frequency(numbers, n) == 1)
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    // Пункт 4
    public static <T> T getLastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Collection is empty"));
    }

    // Пункт 5
    public static int sumOfEvenNumbers(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    // Пункт 6
    public static Map<Character, String> stringsToMap(List<String> strings) {
        return strings.stream()
                .collect(Collectors.toMap(
                        s -> s.charAt(0),
                        s -> s.substring(1),
                        (existing, replacement) -> existing));
    }

    // Проверка работы
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(average(numbers).orElse(Double.NaN));

        List<String> strings = Arrays.asList("lower", "case", "text");
        System.out.println(transformStrings(strings));

        List<Integer> numbersWithDuplicates = Arrays.asList(3, 4, 4, 5, 6, 7, 7);
        System.out.println(uniqueSquares(numbersWithDuplicates));

        List<String> elements = Arrays.asList("first", "mid", "last");
        try {
            System.out.println(getLastElement(elements));
        }
        catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        int[] array = {4, 6, 8, 3, 5, 7};
        System.out.println(sumOfEvenNumbers(array));

        List<String> words = Arrays.asList("aelement", "bto", "cmap");
        System.out.println(stringsToMap(words));
    }
}