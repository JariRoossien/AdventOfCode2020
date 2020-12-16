package nl.jariroossien.aoc.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day16 extends Day {

    public static List<Rule> rules = new ArrayList<>();
    public static List<Ticket> tickets = new ArrayList<>();
    public Ticket ownTicket;

    @Override
    public long solveOne() {
        return tickets.stream().mapToInt(Ticket::isValid).sum();
    }

    @Override
    public long solveTwo() {
        List<Ticket> validTickets = tickets.stream().filter(ticket -> ticket.isValid() == 0).collect(Collectors.toList());
        for (Ticket t : validTickets) {
            for (int i = 0; i < t.values.length; i++) {
                for (Rule r : rules) {
                    if (!r.isValid(t.values[i])) {
                        r.onInvalid(i);
                    }
                }
            }
        }

        //Sort the rules from lowest amount of equal fields to the highest.
        //This way we can filter out these fields on the others since some have multiple fields positive.
        //Example A {0},  B{0,1} Take A first, remove the value and you are only left with 1 value at B.
        rules.sort(Comparator.comparing(rule -> rule.getEqualFields().length));
        for (int i = 0; i < rules.size(); i++) {
            int invalidRowForOthers = rules.get(i).getEqualFields()[0];
            for (int j = i + 1; j < rules.size(); j++) {
                rules.get(j).onInvalid(invalidRowForOthers);
            }
        }

        return rules.stream()
                //Take the only departure values.
                .filter(rule -> rule.name.startsWith("departure"))
                //Get the values that are on your own ticket.
                .mapToLong(rule -> ownTicket.values[rule.getEqualFields()[0]])
                //Reduce by multiplying the values.
                .reduce((a, b) -> a * b)
                //return value.
                .getAsLong();
    }

    @Override
    public void setup() {
        super.setup();
        boolean passedOwn = false;
        boolean passedNearby = false;
        for (String line : input) {
            if (line.equals("your ticket:")) {
                passedOwn = true;
                continue;
            }

            if (line.equals("nearby tickets:")) {
                passedNearby = true;
                continue;
            }
            if (line.length() == 0) {
                continue;
            }
            if (passedNearby) {
                tickets.add(new Ticket(line));
                continue;
            }
            if (passedOwn) {
                ownTicket = new Ticket(line);
                continue;
            }

            rules.add(new Rule(line));
        }
    }

    private static class Rule {

        String name;

        //Use range[0-1] for lower, range [2-3] for higher
        int[] ranges;
        int[] equalFields;

        public Rule(String input) {
            this.name = input.split(": ")[0];

            this.ranges = new int[]{0, 0, 0, 0};
            String[] split = input.split(": ")[1].split(" or ");
            for (int i = 0; i < split.length; i++) {
                ranges[i * 2] = Integer.parseInt(split[i].split("-")[0]);
                ranges[i * 2 + 1] = Integer.parseInt(split[i].split("-")[1]);
            }

            this.equalFields = new int[20];
            for (int i = 0; i < 20; i++) {
                equalFields[i] = i;
            }
        }

        public int[] getEqualFields() {
            return Arrays.stream(equalFields).filter(value -> value != -1).toArray();
        }

        public void onInvalid(int row) {
            equalFields[row] = -1;
        }

        public boolean isValid(int value) {
            return (value >= ranges[0] && value <= ranges[1]) || (value >= ranges[2] && value <= ranges[3]);
        }
    }

    private static class Ticket {
        int[] values;

        public Ticket(String input) {
            values = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
        }

        public int isValid() {
            int invalid = 0;
            outer:
            for (int v : values) {
                for (Rule r : rules) {
                    if ((v >= r.ranges[0] && v <= r.ranges[1]) || (v >= r.ranges[2] && v <= r.ranges[3])) {
                        continue outer;
                    }
                }
                invalid += v;
            }
            return invalid;
        }
    }
}
