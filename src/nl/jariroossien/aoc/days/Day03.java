package nl.jariroossien.aoc.days;

import java.util.List;

public class Day03 extends Day {

    final static char TREE = '#';

    @Override
    public long solveOne() {
        return calculateTreeEncounters(input, 3, 1);
    }

    @Override
    public long solveTwo() {
        long threeRight = calculateTreeEncounters(input, 3, 1);
        long twoDown = calculateTreeEncounters(input, 1, 2);
        long oneRight = calculateTreeEncounters(input, 1, 1);
        long fiveRight = calculateTreeEncounters(input, 5, 1);
        long sevenRight = calculateTreeEncounters(input, 7, 1);
        return (threeRight * twoDown * oneRight * fiveRight * sevenRight);
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
