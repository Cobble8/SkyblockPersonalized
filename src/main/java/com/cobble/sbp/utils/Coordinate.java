package com.cobble.sbp.utils;

public class Coordinate {

    public int x, z, color;

    public Coordinate() {
        this.x = 0;
        this.z = 0;
        this.color = -1;
    }

    public Coordinate(int x, int z) {
        this.x = x;
        this.z = z;
        this.color = -1;
    }

    public Coordinate(int x, int z, int color) {
        this.x = x;
        this.z = z;
        this.color = color;
    }

    public int distanceFrom(Coordinate coord) {
        int dist = 0;
        dist += Math.abs(this.x - coord.x);
        dist += Math.abs(this.z - coord.z);
        return dist;
    }

    public boolean equals(Coordinate coord) {
        return coord.x == this.x && coord.z == this.z;
    }



}
