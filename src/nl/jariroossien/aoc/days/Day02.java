package nl.jariroossien.aoc.days;

import java.util.ArrayList;
import java.util.List;

public class Day02 extends Day {
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
        super.setup();
        passwords = new ArrayList<>();
        for (String line : input) {
            String[] lineArray = line.split(" ");
            passwords.add(new Password(Integer.parseInt(lineArray[0].split("-")[0]),
                    Integer.parseInt(lineArray[0].split("-")[1]),
                    lineArray[1].charAt(0),
                    lineArray[2])
            );
        }
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
