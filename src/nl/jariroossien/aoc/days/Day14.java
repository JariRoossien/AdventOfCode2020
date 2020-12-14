package nl.jariroossien.aoc.days;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 extends Day {
    Map<Integer, String> memory = new HashMap<>();
    char[] mask = new char[36];
    Map<BigInteger, Long> newMemory = new HashMap<>();

    @Override
    public long solveOne() {
        for (String line : input) {
            if (line.startsWith("mask")) {
                mask = line.split(" = ")[1].toCharArray();
            } else {
                String[] info = line.split(" = ");
                int memoryInteger = Integer.parseInt(info[0].replace("mem[", "")
                        .replace("]", ""));

                //Make sure all numbers are 36 bit integers.
                char[] numbers = "000000000000000000000000000000000000".toCharArray();
                char[] inputBinary = Integer.toBinaryString(Integer.parseInt(info[1])).toCharArray();
                for (int i = 0; i < inputBinary.length; i++) {
                    numbers[35 - i] = inputBinary[inputBinary.length - 1 - i];
                }

                char[] memoryInput = memory.getOrDefault(memoryInteger, "000000000000000000000000000000000000").toCharArray();

                //Write values to memory.
                for (int i = 35; i >= 0; i--) {
                    // 35 -> 3
                    // 34 -> 2
                    // 33 -> 1
                    // etc.
                    if (i - (36 - numbers.length) >= 0) {
                        memoryInput[i] = numbers[i - (36 - numbers.length)];
                    }

                    //Apply mask if necessary.
                    if (mask.length - i > 0) {
                        if (mask[i] != 'X')
                            memoryInput[i] = mask[i];
                    }
                }
                memory.put(memoryInteger, String.valueOf(memoryInput));
            }
        }
        long total = 0;
        for (String s : memory.values()) {
            //Integer parseInt won't work, so old school math.
            for (int i = 1; i <= s.length(); i++) {
                if (s.charAt(s.length() - i) == '1') {
                    total += Math.pow(2, i - 1);
                }
            }
        }
        return total;
    }

    @Override
    public long solveTwo() {
        for (String line : input) {
            if (line.startsWith("mask")) {
                mask = line.split(" = ")[1].toCharArray();
            } else {
                String[] info = line.split(" = ");
                int memoryInteger = Integer.parseInt(info[0].replace("mem[", "")
                        .replace("]", ""));

                //Find all possible addresses with the current Mask.
                Set<BigInteger> addresses = findAllAdresses(memoryInteger, mask);
                for (BigInteger integer : addresses)
                    newMemory.put(integer, Long.parseLong(info[1]));
            }
        }

        return newMemory.values().stream().mapToLong(integer -> integer).sum();
    }

    private Set<BigInteger> findAllAdresses(int memoryInteger, char[] mask) {

        //Replaces the numbers to a 36 integer binary.
        char[] numbers = "000000000000000000000000000000000000".toCharArray();
        char[] inputBinary = Integer.toBinaryString(memoryInteger).toCharArray();
        List<Integer> xIndexes = new ArrayList<>();

        for (int i = 0; i < 36; i++) {
            if (inputBinary.length - 1 - i >= 0)
                numbers[35 - i] = inputBinary[inputBinary.length - 1 - i];

            if (mask[i] != '0') {
                numbers[i] = mask[i];
            }
            if (mask[i] == 'X') {
                xIndexes.add(i);
            }
        }

        List<String> permutations = new ArrayList<>();
        permutations.add(String.valueOf(numbers));

        //Go through each thing and change the X to a 0 or 1.
        for (int i : xIndexes) {
            permutations = permutations.stream().flatMap(string -> {
                String first = string.substring(0, i) + "0" + string.substring(i + 1);
                String second = string.substring(0, i) + "1" + string.substring(i + 1);
                return Stream.of(first, second);
            }).collect(Collectors.toList());
        }
        return permutations.stream().map(s -> new BigInteger(s, 2)).collect(Collectors.toSet());

    }
}
