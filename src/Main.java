import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * Created by semihd on 9.03.2017.
 */
public class Main {

    public static void main(String[] args) {

        // Creating Stream Resources
        Stream<String> empty = Stream.empty(); // count = 0
        Stream<Integer> singleElement = Stream.of(1); // count = 1
        Stream<Integer> fromArray = Stream.of(1, 2, 3); // count = 2
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> fromList = list.stream();
        Stream<String> fromListParallel = list.parallelStream();
        Stream<Double> randoms = Stream.generate(Math::random);
        Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);


        // This example shows calling count() on a finite stream:
        Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
        System.out.println(s.count()); // 3

        // This example finds the animal with the fewest letters in its name:
        Stream<String> ss = Stream.of("monkey", "ape", "bonobo");
        Optional<String> min = ss.min((s1, s2) -> s1.length() - s2.length());
        min.ifPresent(System.out::println); // ape

        // Lets look at an empty stream:
        Optional<?> minEmpty = Stream.empty().min((s1, s2) -> 0);
        System.out.println(minEmpty.isPresent()); // false

        // This example finds an animal:
        Stream<String> streamAnimal = Stream.of("monkey", "gorilla", "bonobo");
        Stream<String> infinite = Stream.generate(() -> "chimp");
        streamAnimal.findAny().ifPresent(System.out::println); // monkey
        infinite.findAny().ifPresent(System.out::println); // chimp

        // This example checks whether animal names begin with letters:
        List<String> listAnim = Arrays.asList("monkey", "2", "chimp");
        Stream<String> infiniteAnim = Stream.generate(() -> "chimp");
        Predicate<String> pred = x -> Character.isLetter(x.charAt(0));
        System.out.println(listAnim.stream().anyMatch(pred)); // true
        System.out.println(listAnim.stream().allMatch(pred)); // false
        System.out.println(listAnim.stream().noneMatch(pred)); // false
        System.out.println(infiniteAnim.anyMatch(pred)); // true

        // Foreach on streams
        Stream<String> streamFor = Stream.of("Monkey", "Gorilla", "Bonobo");
        streamFor.forEach(System.out::print); // MonkeyGorillaBonobo
        // Stream streamForNot = Stream.of(1);
        // for (Integer i: streamForNot) {} // DOES NOT COMPILE

        // Reduction on Streams
        Stream<String> stream = Stream.of("w", "o", "l", "f");
        String word = stream.reduce("", (a, c) -> a + c);
        System.out.println(word); // wolf

        // Concatanate
        Stream<String> streamConc = Stream.of("w", "o", "l", "f");
        String wordConc = streamConc.reduce("", String::concat);
        System.out.println(wordConc); // wolf

        // Multiply all of the Integer objects in a stream
        Stream<Integer> streamMultiply = Stream.of(3, 5, 6);
        System.out.println(streamMultiply.reduce(1, (a, b) -> a * b));


        // Our wolf example from reduce can be converted to use collect():
        Stream<String> streamCollect = Stream.of("w", "o", "l", "f");
        StringBuilder wordCollect = streamCollect.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);


        // This filters all elements that begin with the letter m :
        Stream<String> streamFilter = Stream.of("monkey", "gorilla", "bonobo");
        streamFilter.filter(x -> x.startsWith("m")).forEach(System.out::print); // monkey

        // The distinct() method returns a stream with duplicate values removed
        Stream<String> streamDistinct = Stream.of("duck", "duck", "duck", "goose");
        streamDistinct.distinct().forEach(System.out::print); // duckgoose

        // The limit() and skip() methods make a Stream smaller.
        Stream<Integer> streamLimitSkip = Stream.iterate(1, n -> n + 1);
        streamLimitSkip.skip(5).limit(2).forEach(System.out::print); // 67

        //The map() method creates a one-to-one mapping from the elements
        // in the stream to the elements of the next step in the stream.
        Stream<String> streamMap = Stream.of("monkey", "gorilla", "bonobo");
        streamMap.map(String::length).forEach(System.out::print); // 676

        // The flatMap() method takes each element in the stream and makes
        // any elements it contains top-level elements in a single stream.
        List<String> zero = Arrays.asList();
        List<String> one = Arrays.asList("Bonobo");
        List<String> two = Arrays.asList("Mama Gorilla", "Baby Gorilla");
        Stream<List<String>> animals = Stream.of(zero, one, two);
        animals.flatMap(l -> l.stream()).forEach(System.out::println);
        // Output
        // Bonobo
        // Mama Gorilla
        // Baby Gorilla


        // The sorted() method returns a stream with the elements sorted.
        Stream<String> streamSorted = Stream.of("brown-", "bear-");
        streamSorted.sorted().forEach(System.out::print); // bear-brown-
        Stream<String> streamReverseSorted = Stream.of("brown bear-", "grizzly-");
        streamReverseSorted.sorted(Comparator.reverseOrder()).forEach(System.out::print); // grizzly-brown bear-

        // The peek() method is useful for debugging because it allows us to
        // perform a stream operation without actually changing the stream.
        Stream<String> streamPeek = Stream.of("black bear", "brown bear", "grizzly");
        long count = streamPeek.filter(sss -> sss.startsWith("g")).peek(System.out::println).count(); // grizzly
        System.out.println(count); // 1


        // Putting Together the Pipeline
        List<String> listPipe = Arrays.asList("Toby", "Anna", "Leroy", "Alex");
        listPipe.stream().filter(n -> n.length() == 4).sorted().limit(2).forEach(System.out::println);

        // Creating Primitive Streams
        DoubleStream oneValue = DoubleStream.of(3.14);
        DoubleStream varargs = DoubleStream.of(1.0, 1.1, 1.2);
        oneValue.forEach(System.out::println);
        System.out.println();
        varargs.forEach(System.out::println);

    }


}
