package nl.jariroossien.aoc;


import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (int i = 1; i <= 13; i++) {

            String dayNumber;
            if (i <= 9) {
                dayNumber = "0" + i;
            } else {
                dayNumber = String.valueOf(i);
            }

            Challenge challenge = (Challenge) Class.forName("nl.jariroossien.aoc.days.Day" + dayNumber).getDeclaredConstructor().newInstance();
            challenge.setup();
            long timer = System.nanoTime();
            long lOne = challenge.solveOne();
            long challOneSolveTime = System.nanoTime() - timer;

            System.out.println("Day " + i + " challenge 1: " + lOne + ". solved in " + challOneSolveTime / 1000000 + "ms");


            timer = System.nanoTime();
            long lTwo = challenge.solveTwo();
            long challengeSolveTime = System.nanoTime() - timer;
            System.out.println("Day " + i + " challenge 2: " + lTwo + ". solved in " + challengeSolveTime / 1000000 + "ms");
            System.out.println();
        }


    }
}
