package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day8 implements Challenge {

    private final List<Line> actions = new ArrayList<>();
    private final Set<Line> executedActions = new HashSet<>();

    @Override
    public long solveOne() {
        int counter = 0;
        for (int i = 0; i < actions.size(); i++) {
            Line actionLine = actions.get(i);

            //If the line has already been executed once, stop the loop and return the current value.
            if (executedActions.contains(actionLine)) {
                return counter;
            }

            //Perform actions based on String
            if (actionLine.getAction().equals("acc")) {
                counter += actionLine.getAmount();
            } else if (actionLine.getAction().equals("jmp")) {
                i += actionLine.getAmount() - 1;
            }
            executedActions.add(actionLine);

        }
        return 0;
    }

    @Override
    public long solveTwo() {
        //Only loop through the lines that have jmp or nop.
        List<Line> changedLines = actions.stream().filter(action -> action.getAction().equals("jmp") || action.getAction().equals("nop")).collect(Collectors.toList());

        for (Line changedLine : changedLines) {
            String oldAction = changedLine.getAction();

            //Switch action of line.
            changedLine.setAction(changedLine.getAction().equalsIgnoreCase("jmp") ? "nop" : "jmp");

            //Check for a loop and return if the program doesn't loop.
            if (checkForInfiniteLoops(actions)) {
                return getTotalCount(actions);
            }

            //Reset action.
            changedLine.setAction(oldAction);
        }

        return 0;
    }

    public int getTotalCount(List<Line> actions) {
        int counter = 0;
        for (int i = 0; i < actions.size(); i++) {
            Line actionLine = actions.get(i);
            if (actionLine.getAction().equals("acc")) {
                counter += actionLine.getAmount();
            } else if (actionLine.getAction().equals("jmp")) {
                i += actionLine.getAmount() - 1;
            }
        }
        return counter;

    }

    public boolean checkForInfiniteLoops(List<Line> actions) {
        Set<Line> executedLines = new HashSet<>();
        for (int i = 0; i < actions.size(); i++) {
            Line line = actions.get(i);
            if (executedLines.contains(line)) {
                return false;
            }
            if (line.getAction().equals("jmp")) {
                i += line.getAmount() - 1;
            }
            executedLines.add(line);
        }
        return true;
    }

    @Override
    public void setup() {
        final File file = new File("input/day8.txt");
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                actions.add(new Line(line.split(" ")[0], Integer.parseInt(line.split(" ")[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static class Line {
        private static int linecounter = 0;
        private final int id;

        private String action;
        private final int amount;

        public Line(String action, int amount) {
            id = linecounter++;
            this.action = action;
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            return id == line.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        @Override
        public String toString() {
            return "Line{" +
                    "id=" + id +
                    ", action='" + action + '\'' +
                    ", amount=" + amount +
                    '}';
        }
    }
}
