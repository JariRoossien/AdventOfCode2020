package nl.jariroossien.aoc.days;

import nl.jariroossien.aoc.Challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day04 implements Challenge {

    Set<Passport> passports = new HashSet<>();

    @Override
    public long solveOne() {
        return passports.size();
    }

    @Override
    public long solveTwo() {
        return (int) passports.stream().filter(Passport::isValid).count();
    }

    @Override
    public void setup() {
        File file = new File("input/day4.txt");
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            Map<String, String> tempMap = new HashMap<>();
            while ((line = in.readLine()) != null) {
                //Go to new passport if there's an empty line.
                if (line.length() == 0) {
                    passports.add(Passport.fromMap(Map.copyOf(tempMap)));
                    tempMap.clear();
                    continue;
                }

                for (String s : line.split(" ")) {
                    String[] info = s.split(":");
                    tempMap.put(info[0], info[1]);
                }
            }
            passports.add(Passport.fromMap(Map.copyOf(tempMap)));
            tempMap.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class Passport {

        String byr, iyr, eyr, hgt, hcl, ecl, cid, pid;

        public static Passport fromMap(Map<String, String> map) {
            Passport temp = new Passport();
            temp.byr = map.getOrDefault("byr", "");
            temp.iyr = map.getOrDefault("iyr", "");
            temp.eyr = map.getOrDefault("eyr", "");
            temp.hgt = map.getOrDefault("hgt", "");
            temp.hcl = map.getOrDefault("hcl", "");
            temp.ecl = map.getOrDefault("ecl", "");
            temp.cid = map.getOrDefault("cid", "");
            temp.pid = map.getOrDefault("pid", "");
            return temp;
        }

        public boolean isValid() {
            return byr.matches("^(200[0-2]|19[2-9][0-9])$") &&
                    iyr.matches("^(2020|201[0-9])$") &&
                    eyr.matches("^(2030|202[0-9])$") &&
                    hgt.matches("^((1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-6])in))$") &&
                    hcl.matches("^(#[0-9a-f]{6})$") &&
                    ecl.matches("^(amb|blu|brn|gry|grn|hzl|oth)$") &&
                    pid.matches("^[0-9]{9}$");
        }

    }

}
