package nl.jariroossien.aoc.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06 extends Day {

    List<Group> groupList = new ArrayList<>();

    @Override
    public long solveOne() {
        return groupList.stream().mapToInt(group -> group.questions.keySet().size()).sum();
    }

    @Override
    public long solveTwo() {
        return groupList.stream()
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
        super.setup();
        Group temp = new Group();
        for (String line : input) {
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

    }

    private static class Group {
        public Map<Character, Integer> questions = new HashMap<>();
        public int playercount;
    }

}
