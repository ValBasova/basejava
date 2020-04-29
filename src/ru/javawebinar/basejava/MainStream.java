package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));

        System.out.println(oddOrEven(new ArrayList<>(Arrays.asList(1, 2, 3, 3, 2, 3))));
        System.out.println(oddOrEven(new ArrayList<>(Arrays.asList(8, 9))));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).
                distinct().
                sorted().
                reduce(0, (partial, a) -> partial * 10 + a);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> oddList = new ArrayList<>();
        List<Integer> evenList = new ArrayList<>();
        integers.stream().collect(Collectors.partitioningBy(x -> ((x % 2 == 0) ? evenList : oddList).add(x)));
        //      integers.stream().map(x ->((x % 2 == 0) ? evenList : oddList).add(x)).collect(Collectors.toList());

        return (oddList.size() % 2 != 0) ? oddList : evenList;
    }
}