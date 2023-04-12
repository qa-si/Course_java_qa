package ru.stqa.pft.sandbox;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String pointCoordinates() {
        return "(" + this.x + ";" + this.y + ")";
    }

    public double distanceToPoint(Point p){
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }
}
