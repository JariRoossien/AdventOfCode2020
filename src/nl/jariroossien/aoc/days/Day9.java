package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day9 implements Challenge {

    @Override
    public long solveOne() {
        return 0;
    }

    @Override
    public long solveTwo() {
        return 0;
    }

    @Override
    public void setup() {
        final File file = new File("input/day8.txt");
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                //TODO Put line into a List.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
