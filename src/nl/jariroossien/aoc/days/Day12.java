package nl.jariroossien.aoc.days;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day12 extends Day {
    Boat b = new Boat();


    @Override
    public long solveOne() {
        for (String s : input) {
            b.move(s, false);
        }

        return Math.abs(b.coords[0]) + Math.abs(b.coords[1]);
    }

    @Override
    public long solveTwo() {
        b = new Boat();
        for (String s : input) {
            b.move(s, true);
        }
        return Math.abs(b.coords[0]) + Math.abs(b.coords[1]);
    }

    private static class Boat {
        int[] coords = {0, 0};

        int[] relativeWaypoint = {1, 10};
        int angle = 0;

        public Boat() {
        }

        //I'm sure there's some way to not have to use a boolean partTwo but I'm too tired for that.
        //At least in the rotation the angle wouldn't be necessary to save.
        public void move(String direction, boolean partTwo) {
            char a = direction.charAt(0);
            int amount = Integer.parseInt(direction.substring(1));
            switch (a) {
                case 'N':
                    if (partTwo)
                        relativeWaypoint[0] += amount;
                    else
                        coords[0] += amount;
                    break;
                case 'S':
                    if (partTwo)
                        relativeWaypoint[0] -= amount;
                    else
                        coords[0] -= amount;
                    break;
                case 'W':
                    if (partTwo)
                        relativeWaypoint[1] -= amount;
                    else
                        coords[1] -= amount;
                    break;
                case 'E':
                    if (partTwo)
                        relativeWaypoint[1] += amount;
                    else
                        coords[1] += amount;
                    break;
                case 'R':
                    angle -= amount;
                    if (partTwo) {
                        rotateRelative(Math.toRadians(amount));
                    }
                    break;
                case 'L':
                    angle += amount;
                    if (partTwo) {
                        rotateRelative(-Math.toRadians(amount));
                    }
                    break;
                case 'F':
                    int yDir = (int) Math.cos(Math.toRadians(angle));
                    int xDir = (int) Math.sin(Math.toRadians(angle));
                    if (partTwo) {
                        xDir = relativeWaypoint[0];
                        yDir = relativeWaypoint[1];
                    }
                    coords[0] += xDir * amount;
                    coords[1] += yDir * amount;
                    break;
            }
        }

        private void rotateRelative(double currentAngle) {
            int x = relativeWaypoint[0];
            int y = relativeWaypoint[1];
            relativeWaypoint[0] = (int) Math.round(x * Math.cos(currentAngle) - y * Math.sin(currentAngle));
            relativeWaypoint[1] = (int) Math.round(x * Math.sin(currentAngle) + y * Math.cos(currentAngle));
        }

        public int[] getCoords() {
            return coords;
        }

    }
}
