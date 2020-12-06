package nl.jariroossien.aoc;


import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (int i = 1; i <= 6; i++) {
            Challenge challenge = (Challenge) Class.forName("nl.jariroossien.aoc.days.Day" + i).getDeclaredConstructor().newInstance();
            challenge.setup();
            System.out.println("Day " + i + " challenge 1: " + challenge.solveOne());
            System.out.println("Day " + i + " challenge 2: " + challenge.solveTwo());
            System.out.println();
        }
    }
}
