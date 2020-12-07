package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day3 implements Challenge {

    final static char TREE = '#';
    List<String> rows = new ArrayList<>();

    @Override
    public long solveOne() {
        return calculateTreeEncounters(rows, 3, 1);
    }

    @Override
    public long solveTwo() {
        long threeRight = calculateTreeEncounters(rows, 3, 1);
        long twoDown = calculateTreeEncounters(rows, 1, 2);
        long oneRight = calculateTreeEncounters(rows, 1, 1);
        long fiveRight = calculateTreeEncounters(rows, 5, 1);
        long sevenRight = calculateTreeEncounters(rows, 7, 1);
        return (threeRight * twoDown * oneRight * fiveRight * sevenRight);
    }

    @Override
    public void setup() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(new File("input/day3.txt")));
            String line;
            while ((line = bf.readLine()) != null) {
                rows.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int calculateTreeEncounters(List<String> list, int right, int down) {
        int position = -1;
        int row = 0;
        int encountered = 0;
        int offset = 0;
        while (row < list.size()) {
            String line = list.get(row);
            position++;
            if (line.length() <= position * right - offset) {
                offset = line.length() - (position - 1) * right + offset;
                position = 1;
            }

            char c = line.charAt(position * right - offset);
            if (c == TREE)
                encountered++;

            row += down;
        }


        return encountered;

    }
}
