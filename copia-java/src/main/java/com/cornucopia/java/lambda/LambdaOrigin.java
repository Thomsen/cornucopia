package com.cornucopia.java.lambda;

import java.time.Period;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LambdaOrigin {

    public static void main(String[] args) {

        originOne();

        originTwo();

        originThree();

        originFour();
    }

    private static void originOne() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("origin runnable");
            }
        }).start();

        // () -> {}  anonymous class
        new Thread(() -> System.out.println("lambda runnable"))
                .start();
    }


    private static void originTwo() {
        List<String> features = Arrays.asList("alpha", "beta", "gamma", "delta", "epsilon");

        for (String f : features) {
            System.out.println("origin " + f);
        }

        // iterative expression
        features.forEach(f -> System.out.println("lambda " + f));

        features.forEach(System.out::print);
    }

    private static void originThree() {
        List<String> features = Arrays.asList("alpha", "beta", "gamma", "delta", "epsilon");

        System.out.println("\nlanguages with start with g : ");
        filterOrigin(features, (s) -> s.startsWith("g"));

        System.out.println("languages with start with g : ");
        filterLambda(features, (s) -> s.startsWith("g"));

        System.out.println("languages with end with a and length=4 : ");
        filterLambda(features, (s) -> s.endsWith("a"), (s) -> s.length() == 4);

    }

    // Predicate must T, Cannot resolve method 'startsWith(java.lang.String)'
    private static void filterOrigin(List<String> values, Predicate<String> condition) {
        for (String v : values) {
            if (condition.test(v)) {
                System.out.println(v + "");
            }
        }
    }

    // function stream programming
    private static void filterLambda(List<String> values, Predicate<String> condition) {
        values.stream().filter((value) -> (condition.test(value)))
                .forEach((value) -> {
                    System.out.println(value + " s ");
                });
    }

    private static void filterLambda(List<String> values, Predicate<String> conditionOne,
                                     Predicate<String> conditionTwo) {
        values.stream().filter(conditionOne.and(conditionTwo))
                .forEach((value) -> {
                    System.out.println(value + " s ");
                });
    }

    private static void originFour() {

        List<Integer> costPrice = Arrays.asList(100, 200, 300, 400, 500);
        for (Integer cost : costPrice) {
            double price = cost + (0.12 * cost);
            System.out.println("origin " + price);
        }

        costPrice.stream().map((cost) -> cost + (0.12 * cost))
                .forEach(System.out::println);

        double bill = costPrice.stream().map((cost) -> cost + (0.12 * cost))
                .reduce((sum, cost) -> sum + cost).get();
        System.out.println("total: " + bill);

        List<Integer> afterPrice = costPrice.stream().filter((cost) -> cost > 200)
                .collect(Collectors.toList()); // distinct()
        afterPrice.stream().forEach(System.out::println);

        String afterPriceStr = costPrice.stream().filter((cost) -> cost > 300)
                .map((cost) -> String.valueOf(cost))
                .collect(Collectors.joining(", " ));
        System.out.println("after price: " + afterPriceStr);

        IntSummaryStatistics statistics = costPrice.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("highest " + statistics.getMax());
        System.out.println("lowest " + statistics.getMin());
        System.out.println("sum of price " + statistics.getSum());
        System.out.println("average of price " + statistics.getAverage());

    }


}
