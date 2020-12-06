package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day5 implements Challenge {
    int highestval = 0;
    List<Integer> seatIds = new ArrayList<>();

    @Override
    public long solveOne() {
        seatIds.sort(Integer::compareTo);
        return seatIds.get(seatIds.size() - 1);
    }

    @Override
    public long solveTwo() {
        for (int i = seatIds.get(0); i < seatIds.get(seatIds.size() - 1); i++) {
            if (seatIds.get(i) +2 == seatIds.get(i + 1))
                return seatIds.get(i) + 1;
        }
        return 0;
    }

    @Override
    public void setup() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("input/day5.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                int row = getInt(0, 127, line.substring(0, 7));
                int seat = getInt(0, 7, line.substring(7));
                int seatId = row * 8 + seat;
                seatIds.add(seatId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getInt(int low, int high, String remaining) {
        int range = high - low;
        if (remaining.charAt(0) == 'F' || remaining.charAt(0) == 'L') {
            if (remaining.length() == 1) {
                return low;
            } else {
                return getInt(low, low + range/2, remaining.substring(1));
            }
        } else {
            if (remaining.length() == 1) {
                return high;
            } else {
                return getInt(high - range/2, high, remaining.substring(1));
            }
        }
    }

}
