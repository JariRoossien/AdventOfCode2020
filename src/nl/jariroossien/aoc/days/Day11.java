package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day11 implements Challenge {

    char[][] seatLayout;

    @Override
    public long solveOne() {
        char[][] newLayout = seatLayout;
        char[][] oldLayout;
        do {
            oldLayout = getCloned(newLayout);
            newLayout = getNewSeatLayout(oldLayout, 4, false);
        } while (!(isEqualGrid(oldLayout, newLayout)));
        return getSum(oldLayout, '#');
    }

    public boolean isEqualGrid(char[][] old, char[][] newG) {
        if (old.length != newG.length)
            return false;
        for (int i = 0; i < old.length; i++) {
            if (old[i].length != newG[i].length)
                return false;
            for (int j = 0; j < old[i].length; j++) {
                if (old[i][j] != newG[i][j])
                    return false;
            }
        }
        return true;
    }
    public int getSum(char[][] seats, char c) {
        int counter = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == c)
                    counter++;
            }
        }
        return counter;
    }
    public void printSeats(char[][] seats) {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j]);
            }
            System.out.println();
        }

    }

    public char[][] getCloned(char[][] list) {
        char[][] cloned = new char[list.length][list[0].length];
        for (int i = 0; i < list.length; i++) {
            cloned[i] = list[i].clone();
        }
        return cloned;
    }

    public boolean isSeat(char[][] oldSeats, int x, int y, int xOff, int yOff) {
        try {
            return oldSeats[x+xOff][y+yOff] == '#' || oldSeats[x+xOff][y+yOff] == 'L';
        } catch (Exception ignored) { return false; }
    }

    public char[][] getNewSeatLayout(char[][] oldSeats, int required, boolean partTwo) {
        char[][] cloned = getCloned(oldSeats);
        for (int i = 0; i < oldSeats.length; i++) {
            for (int j = 0; j < oldSeats[i].length; j++) {
                if (oldSeats[i][j] == '.') continue;

                //Check if there's no seat occupied in a direct circle.
                int seatCounter = 0;
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) continue;
                        if (partTwo) {
                            char seat = getFirstSeatInDirection(oldSeats, i, j, x, y);
                            if (seat == '#') {
                                seatCounter++;
                            }
                        } else {
                            if (isOccuppied(oldSeats, i, j, x, y)) {
                                seatCounter++;
                            }
                        }
                    }
                }
                if (seatCounter == 0) {
                    cloned[i][j] = '#';
                }
                if (seatCounter >= required && oldSeats[i][j] == '#') {
                    cloned[i][j] = 'L';
                }
            }
        }

        return cloned;
    }

    private char getFirstSeatInDirection(char[][] oldSeats, int i, int j, int x, int y) {
        char found = 0;
        try {
            int xOffset = 0;
            int yOffset = 0;
            while (found == 0) {
                xOffset += x;
                yOffset += y;
                if (oldSeats[i+xOffset][j+yOffset] == '#' || oldSeats[i+xOffset][j+yOffset] == 'L')
                    found = oldSeats[i+xOffset][j+yOffset];
            }
        } catch (Exception e) {
            return 0;
        }
        return found;
    }

    private boolean isInGrid(char[][] oldSeats, int x, int y, int xOffset, int yOffset) {
        try {
            return oldSeats[x+xOffset][y+yOffset] != 'M';
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isOccuppied(char[][] seats, int x, int y, int xOffset, int yOffset) {
        try {
            char seatType = seats[x + xOffset][y + yOffset];
            return (seatType == '#');
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public long solveTwo() {
        char[][] newLayout = seatLayout;
        char[][] oldLayout;
        do {
            oldLayout = getCloned(newLayout);
            newLayout = getNewSeatLayout(oldLayout, 5, true);
        } while (!(isEqualGrid(oldLayout, newLayout)));
        return getSum(oldLayout, '#');

    }

    @Override
    public void setup() {
        final File file = new File("input/day11.txt");
        seatLayout = new char[97][90];
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            int counter = 0;
            while ((line = in.readLine()) != null) {
                for (int i = 0; i < line.getBytes().length; i++) {
                    seatLayout[counter][i] = line.charAt(i);
                }
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
