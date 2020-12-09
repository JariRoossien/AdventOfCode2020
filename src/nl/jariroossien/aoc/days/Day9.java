package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day9 implements Challenge {

    final static int OFFSET = 25;
    List<Long> numberSet = new ArrayList<>();

    @Override
    public long solveOne() {

        for (int i = OFFSET; i < numberSet.size(); i++) {
            first:
            for (int j = i - OFFSET; j < i; j++) {
                for (int k = i - OFFSET; k < i; k++) {

                    //They are equal, therefor they cant bypass (j = k) but it
                    // does mean it's at the end of the program.
                    if (j == i - 1 && k == i - 1) {
                        return numberSet.get(i);
                    }
                    if (j == k) continue;

                    //If there is a number, go to next number in the set.
                    if (numberSet.get(j) + numberSet.get(k) == numberSet.get(i)) {
                        break first;
                    }
                    //If we are at the last number and there is still no combination, return the number
                }
            }
        }
        return 0;
    }

    @Override
    public long solveTwo() {
        int indexToSolve = numberSet.indexOf(solveOne());

        // Fuck it, just loop through each item until the unsolved index. And increase the size by 1 when unfound.
        for (int size = 0; size < indexToSolve; size++) {
            for (int j = 0; j < indexToSolve - size; j++) {
                List<Long> longList = numberSet.subList(j, j + size);

                //Check if the sum equals to the number set
                if (longList.stream().mapToLong(Long::longValue).sum() == numberSet.get(indexToSolve)) {
                    //Sort to not do this 2 times for minimum and maximum
                    longList.sort(Long::compareTo);
                    //Make sure it isn't the actual index itself lmao
                    if (longList.size() >= 2) {
                        return longList.get(0) + longList.get(longList.size() - 1);
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public void setup() {
        final File file = new File("input/day9.txt");
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                //TODO Put line into a List.
                numberSet.add(Long.parseLong(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
