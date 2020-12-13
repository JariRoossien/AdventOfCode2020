package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day01 extends Day {

    List<Integer> numbers  = new ArrayList<>();

    public Day01() {
        setup();
    }

    @Override
    public long solveOne() {
        int sum;
        int lOffset = 0;
        int rightOffset = numbers.size() - 1;
        do {
            sum = numbers.get(lOffset) + numbers.get(rightOffset);
            if (sum > 2020) {
                rightOffset -= 1;
            } else if (sum < 2020) {
                lOffset += 1;
            }
        } while (sum != 2020 && lOffset < rightOffset);

        return numbers.get(lOffset) * numbers.get(rightOffset);

    }

    @Override
    public long solveTwo() {
        int n1 = 0;
        int n2 = 0;
        int n3 = 0;

        for (Integer i : numbers) {
            for (Integer j : numbers) {
                for (Integer k : numbers) {
                    if (i + j + k == 2020) {
                        n1 = i;
                        n2 = j;
                        n3 = k;
                    }
                }
            }
        }

        return n1*n2*n3;
    }

    @Override
    public void setup() {
        super.setup();
        for (String s : input) {
            numbers.add(Integer.parseInt(s));
        }
        numbers.sort(Integer::compareTo);
    }
}
