package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day10 implements Challenge {

    List<Long> longDataList = new ArrayList<>();

    // {1 size, 2 size, 3 size}
    int[] countingArray = {0, 0, 0};
    @Override
    public long solveOne() {
        longDataList.add(0L);
        longDataList.sort(Long::compareTo);
        int[] results = getTotalJolts(longDataList, 0, countingArray);

        return results[0] * results[2];
    }

    public int[] getTotalJolts(List<Long> longList, int index, int[] result) {
        //Return if size equals
        if (longList.size() - 1 == index) {
            //Add the final 3 adapter from the build inside.
            result[2] += 1;
            return result;
        }


        /*
         * This is the newly written code after I took a look at it.
         * The old one is still commented under this.
         */
        long difference = longList.get(index + 1) - longList.get(index);
        result[(int) (difference - 1)] += 1;
        return getTotalJolts(longList, ++index, result);

        //Check to see if there's any jolt from 1, then 2, then 3.
        //This might be fucking ugly as fuck but it's 6 am and I cant be bothered Honestly.
/*        switch ((int) (longList.get(index + 1) - longList.get(index))) {
            case 1 -> {
                result[0] += 1;
                return getTotalJolts(longList, ++index, result);
            }
            case 2 -> {
                result[1] += 1;
                return getTotalJolts(longList, ++index, result);
            }
            case 3 -> {
                result[2] += 1;
                return getTotalJolts(longList, ++index, result);
            }
        }

        return new int[]{0, 0, 0};
 */
    }

    @Override
    public long solveTwo() {
        longDataList.sort(Long::compareTo);

        return getTotalWays(longDataList, 0);
    }

    //Use isChecked as a cache.
    Map<Integer, Long> isChecked = new HashMap<>();

    public long getTotalWays(List<Long> data, int index) {
        if (data.size() - 1 == index)
            return 1;
        //If it's outside the index, return 0.
        if (data.size() - 1 < index) {
            return 0;
        }

        //If it's already been checked, return the checked value.
        if (isChecked.containsKey(index)) {
            return isChecked.get(index);
        }
        long count = 0;
        for (int i = 1; i <= 3; i++) {
            if (data.size() > index + i && data.get(index + i) - data.get(index) > 3)
                break;
            final long totalWays = getTotalWays(data, index + i);
            isChecked.put(index + i, totalWays);
            count += totalWays;

        }

        return count;
    }
    @Override
    public void setup() {
        final File file = new File("input/day10.txt");
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                longDataList.add(Long.valueOf(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
