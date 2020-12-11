package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06 implements Challenge {

    List<Group> groupList = new ArrayList<>();

    @Override
    public long solveOne() {
        return groupList.stream().mapToInt(group -> group.questions.keySet().size()).sum();
    }

    @Override
    public long solveTwo() {
        return  groupList.stream()
                .mapToInt(group -> (int) group
                        .questions
                        .entrySet()
                        .stream()
                        .filter(
                                entry -> entry.getValue() == group.playercount)
                        .count()
                )
                .sum();
    }

    @Override
    public void setup() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("input/day6.txt"));
            String line;
            Group temp = new Group();
            while ((line = in.readLine()) != null) {
                if (line.length() == 0) {
                    groupList.add(temp);
                    temp = new Group();
                    continue;
                }

                for (char a : line.toCharArray()) {
                    temp.questions.merge(a, 1, Integer::sum);
                }
                temp.playercount++;

            }
            groupList.add(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static class Group {
        public Map<Character, Integer> questions = new HashMap<>();
        public int playercount;
    }

}
