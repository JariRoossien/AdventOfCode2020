package nl.jariroossien.aoc.days;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

public class Day13 extends Day {

    int time = 0;
    List<Bus> busList = new ArrayList<>();

    @Override
    public long solveOne() {
        Bus firstBus = null;
        int counter = time;
        do {
            counter++;
            for (Bus bus : busList) {
                if (counter % bus.id == 0) {
                    firstBus = bus;
                }
            }

        } while (firstBus == null);

        return (counter - time) * firstBus.id;
    }


    @Override
    public long solveTwo() {
        long[] n = busList.stream().mapToLong(bus -> bus.id).toArray();
        long[] a = busList.stream().mapToLong(bus -> (bus.id - bus.position) % bus.id).toArray();

        return chineseRemainder(n, a);
    }

    // Vies gestolen van het internet. Ik ga ook geen moeite doen om het uit te leggen.
    // https://rosettacode.org/wiki/Chinese_remainder_theorem#Java
    public static long chineseRemainder(long[] n, long[] a) {
        long prod = stream(n).reduce(1, (i, j) -> i * j);

        long p, sm = 0;
        for (int i = 0; i < n.length; i++) {
            p = prod / n[i];
            sm += a[i] * mulInv(p, n[i]) * p;
        }
        return sm % prod;
    }

    private static long mulInv(long a, long b) {
        long b0 = b;
        long x0 = 0;
        long x1 = 1;

        if (b == 1)
            return 1;

        while (a > 1) {
            long q = a / b;
            long amb = a % b;
            a = b;
            b = amb;
            long xqx = x1 - q * x0;
            x1 = x0;
            x0 = xqx;
        }

        if (x1 < 0)
            x1 += b0;

        return x1;
    }
    @Override
    public void setup() {
        super.setup();
        time = Integer.parseInt(input.get(0));
        final String[] split = input.get(1).split(",");
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("x")) {
                busList.add(new Bus(Integer.parseInt(split[i]), i));
            }
        }
    }

    public static class Bus {
        int id;
        int position;
        public Bus(int id, int position) {
            this.id = id;
            this.position = position;
        }

    }
}
