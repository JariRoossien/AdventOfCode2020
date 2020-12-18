package nl.jariroossien.aoc.days;

import java.util.*;

public class Day15 extends Day {

    @Override
    public long solveOne() {
        List<Integer> numbers = new ArrayList<>(2020);
        Arrays.stream(input.get(0).split(",")).forEach(string -> numbers.add(Integer.parseInt(string)));
        do {
            int toBeChecked = numbers.remove(numbers.size() - 1);
            if (!numbers.contains(toBeChecked)) {
                numbers.add(toBeChecked);
                numbers.add(0);
            } else {
                int lastCalled = numbers.lastIndexOf(toBeChecked);
                numbers.add(toBeChecked);
                numbers.add(numbers.size() - 1 - lastCalled);
            }
        } while (numbers.size() < 2020);

        return numbers.get(2019);
    }

    @Override
    public long solveTwo() {
        Map<Integer, Integer> numbers = new HashMap<>(3700000);
        int[] lastNumbers = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        for (int i : lastNumbers) {
            numbers.put(i, numbers.size());
        }
        numbers.remove(lastNumbers[lastNumbers.length - 1]);
        int nextNumber = lastNumbers[lastNumbers.length - 1];
        int index = numbers.size();
        do {
            if (!numbers.containsKey(nextNumber)) {
                numbers.put(nextNumber, index++);
                nextNumber = 0;
            } else {
                int lastCalled = numbers.get(nextNumber);
                numbers.put(nextNumber, index);
                nextNumber = index - lastCalled;
                index++;
            }
        } while (index < 29999999);
        return nextNumber;
    }
}
