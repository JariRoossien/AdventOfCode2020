package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day02 implements Challenge {
    List<Password> passwords;
    public Day02() {
        setup();
    }

    @Override
    public long solveOne() {
        return (int) passwords.stream().filter(Password::isValidForOne).count();
    }

    @Override
    public long solveTwo() {
        return (int) passwords.stream().filter(Password::isValidForTwo).count();
    }

    @Override
    public void setup() {
        final File file = new File("input/day2.txt");
        passwords = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                String[] lineArray = line.split(" ");
                passwords.add(new Password(Integer.parseInt(lineArray[0].split("-")[0]),
                        Integer.parseInt(lineArray[0].split("-")[1]),
                        lineArray[1].charAt(0),
                        lineArray[2])
                );
            }
        } catch (IOException ignored) {}

    }

    private static class Password {

        int karakterMin;
        int karakterMax;
        char karakter;
        String password;

        public Password(int karakterMin, int karakterMax, char karakter, String password) {
            this.karakterMin = karakterMin;
            this.karakterMax = karakterMax;
            this.karakter = karakter;
            this.password = password;
        }


        public boolean isValidForOne() {
            int charCount = 0;
            for (char a : password.toCharArray()) {
                if (a == karakter) {
                    charCount++;
                }
            }
            return (charCount >= karakterMin && charCount <= karakterMax);
        }

        public boolean isValidForTwo() {
            return (password.charAt(karakterMin - 1) == karakter ^ password.charAt(karakterMax - 1) == karakter);
        }

    }

}
