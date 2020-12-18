package nl.jariroossien.aoc.days;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Day17 extends Day {
    Grid g = new Grid();
    Grid gridPartTwo = new Grid();

    @Override
    public long solveOne() {
        return getTotalActiveCubes(g);
    }

    @Override
    public long solveTwo() {
        return getTotalActiveCubes(gridPartTwo);
    }

    private long getTotalActiveCubes(Grid gridPartTwo) {
        Grid nextGen = gridPartTwo;
        for (int i = 0; i < 6; i++) {
            nextGen = getNextIteration(nextGen);
        }
        return nextGen.cubeSet.stream().filter(Cube::isActive).count();
    }

    @Override
    public void setup() {
        super.setup();
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).toCharArray().length; j++) {
                if (input.get(i).toCharArray()[j] == '#') {
                    g.cubeSet.add(new Cube(true, j, i, 0, false));
                    gridPartTwo.cubeSet.add(new Cube(true, j, i, 0, 0));
                }
            }
        }
    }

    public Grid getNextIteration(Grid oldGrid) {
        Grid newGrid = new Grid();
        Set<Cube> checkedCubes = new HashSet<>();

        //Loop through each cube and get the nearby 9 cubes to check if it needs updating.
        oldGrid.cubeSet.forEach(cube -> {
            if (!checkedCubes.contains(cube)) {
                Set<Cube> getNearbyCubes = cube.getNearbyCubes(oldGrid);
                final long totalActive = getNearbyCubes.stream().filter(cube1 -> cube1.active).count();
                if (totalActive == 2 || totalActive == 3) {
                    newGrid.cubeSet.add(cube.clone());
                }

            }

            //Loop through all the other items.
            for (int tempX = -1; tempX <= 1; tempX++) {
                for (int tempY = -1; tempY <= 1; tempY++) {
                    for (int tempZ = -1; tempZ <= 1; tempZ++) {
                        if (cube.partTwo) {
                            for (int tempW = -1; tempW <= 1; tempW++) {
                                Cube toCheck = new Cube(false, cube.x + tempX, cube.y + tempY, cube.z + tempZ, cube.w + tempW);
                                if (checkedCubes.contains(toCheck)) {
                                    continue;
                                }

                                if (oldGrid.cubeSet.contains(toCheck)) {
                                    Cube finalToCheck = toCheck;
                                    toCheck = oldGrid.cubeSet.stream().filter(cube1 -> cube1.equals(finalToCheck)).findFirst().get();
                                }
                                long totalTempActive = toCheck.getNearbyCubes(oldGrid).stream().filter(Cube::isActive).count();
                                if (totalTempActive == 3) {
                                    toCheck.active = true;
                                    newGrid.cubeSet.add(toCheck);
                                } else if (totalTempActive == 2) {
                                    if (toCheck.active) {
                                        newGrid.cubeSet.add(toCheck);
                                    }
                                } else {
                                    toCheck.active = false;
                                }
                                checkedCubes.add(toCheck);

                            }
                        } else {
                            Cube toCheck = new Cube(false, cube.x + tempX, cube.y + tempY, cube.z + tempZ, false);
                            if (checkedCubes.contains(toCheck)) {
                                continue;
                            }

                            if (oldGrid.cubeSet.contains(toCheck)) {
                                Cube finalToCheck = toCheck;
                                toCheck = oldGrid.cubeSet.stream().filter(cube1 -> cube1.equals(finalToCheck)).findFirst().get();
                            }
                            long totalTempActive = toCheck.getNearbyCubes(oldGrid).stream().filter(Cube::isActive).count();
                            if (totalTempActive == 3) {
                                toCheck.active = true;
                                newGrid.cubeSet.add(toCheck);
                            } else if (totalTempActive == 2) {
                                if (toCheck.active) {
                                    newGrid.cubeSet.add(toCheck);
                                }
                            } else {
                                toCheck.active = false;
                            }
                            checkedCubes.add(toCheck);

                        }
                    }
                }
            }
        });
        return newGrid;
    }

    private static class Grid {
        Set<Cube> cubeSet = new HashSet<>();
    }

    private static class Cube {

        boolean active;
        boolean partTwo;

        int x;
        int y;
        int z;
        int w;

        public Cube(boolean isActive, int x, int y, int z, boolean partTwo) {
            this.active = isActive;
            this.x = x;
            this.y = y;
            this.z = z;
            this.partTwo = partTwo;
        }

        public Cube(boolean active, int x, int y, int z, int w) {
            this(active, x, y, z, true);
            this.w = w;
        }

        public boolean isActive() {
            return active;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cube cube = (Cube) o;
            if (cube.partTwo) {
                return x == cube.x &&
                        y == cube.y &&
                        z == cube.z &&
                        w == cube.w;
            } else {
                return x == cube.x &&
                        y == cube.y &&
                        z == cube.z;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z, partTwo);
        }

        public Set<Cube> getNearbyCubes(Grid grid) {
            Set<Cube> tempSet = new HashSet<>();

            for (int tempX = x - 1; tempX <= x + 1; tempX++) {
                for (int tempY = y - 1; tempY <= y + 1; tempY++) {
                    for (int tempZ = z - 1; tempZ <= z + 1; tempZ++) {
                        if (partTwo) {
                            for (int tempW = w - 1; tempW <= w + 1; tempW++) {
                                if (tempX == x && tempY == y && tempZ == z && tempW == w)
                                    continue;
                                final Cube toCheck = new Cube(false, tempX, tempY, tempZ, tempW);
                                if (grid.cubeSet.contains(toCheck)) {
                                    toCheck.active = true;
                                    tempSet.add(toCheck);
                                }
                            }
                        } else {
                            if (tempX == x && tempY == y && tempZ == z)
                                continue;
                            final Cube toCheck = new Cube(false, tempX, tempY, tempZ, false);
                            if (grid.cubeSet.contains(toCheck)) {
                                toCheck.active = true;
                                tempSet.add(toCheck);
                            }
                        }
                    }
                }
            }
            return tempSet;
        }

        protected Cube clone() {
            if (partTwo) {
                return new Cube(active, x, y, z, w);
            } else {
                return new Cube(active, x, y, z, false);
            }
        }
    }
}
