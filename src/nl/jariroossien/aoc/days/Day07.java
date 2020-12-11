package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.*;
import java.util.*;

public class Day07 implements Challenge {

    @Override
    public long solveOne() {
        Bag shinyGold = getBag("shiny gold");
        int bagCount = 0;
        for (Bag b : Bag.currentBags) {
            if (canFitBag(b, shinyGold)) {
                bagCount++;
            }
        }

        return bagCount;
    }

    public boolean canFitBag(Bag bag, Bag bagToFind) {
        if (bag.canContain.containsKey(bagToFind))
            return true;
        else {

            //Loop through each inner bag to see if they can fit the bag.
            for (Bag b : bag.canContain.keySet()) {
                if (canFitBag(b, bagToFind)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public long solveTwo() {
        Bag shinyGold = getBag("shiny gold");
        return getInsideBagCount(shinyGold);
    }

    public int getInsideBagCount(Bag bag) {
        int amount = 0;

        //Loop through all inner bags
        for (Bag innerBag : bag.canContain.keySet()) {
            int amountOfInnerBags = bag.canContain.get(innerBag);

            //Add the bags themselves.
            amount += amountOfInnerBags;

            //Add the amount of bags inside the inner bag, times the amount of bags.
            amount += amountOfInnerBags * getInsideBagCount(innerBag);
        }
        return amount;
    }


    @Override
    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("input/day7.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] array = line.split(" contain ");
                String[] rule_colors = array[0].split(" ");
                Bag ruleBag = getBag(rule_colors[0] + " " + rule_colors[1]);

                String extra_info = array[1];
                String[] containingBags = extra_info.substring(0, extra_info.length() - 1).split(", ");
                for (String s : containingBags) {
                    final String[] temp_bag_info = s.split(" ");

                    // If it's no, there are no bags, we can continue to the next bag entry.
                    if (temp_bag_info[0].equalsIgnoreCase("no")) {
                        break;
                    } else {
                        int count = Integer.parseInt(temp_bag_info[0]);
                        Bag other_bag = getBag(temp_bag_info[1] + " " + temp_bag_info[2]);
                        ruleBag.canContain.put(other_bag, count);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bag getBag(String s) {
        final Optional<Bag> first = Bag.currentBags.stream().filter(bag -> bag.color.equals(s)).findFirst();
        if (first.isPresent())
            return first.get();
        Bag b = new Bag(s);
        Bag.currentBags.add(b);
        return b;
    }

    private static class Bag {

        public static Set<Bag> currentBags = new HashSet<>();

        String color;
        Map<Bag, Integer> canContain = new HashMap<>();

        Bag(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "Bag{" +
                    "color='" + color + '\'' +
                    ", canContain=" + canContain.size() +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Bag bag = (Bag) o;
            return Objects.equals(color, bag.color);
        }

        @Override
        public int hashCode() {
            return Objects.hash(color);
        }
    }
}
