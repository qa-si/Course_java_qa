package ru.stqa.pft.sandbox;

public class CalculateDistanceBetweenPoints {

    public static void main(String[] args) {

        Point p1 = new Point(1, 0);
        Point p2 = new Point(3, 0);

        System.out.println("Расстояние между точками " + p1.pointCoordinates() + " и " + p2.pointCoordinates() + " = " + distanceBetweenPoints(p1, p2));
        System.out.println("Расстояние от точки " + p1.pointCoordinates() + " до точки " + p2.pointCoordinates() + " = " + p1.distanceToPoint(p2.x, p2.y));
    }

    public static double distanceBetweenPoints(Point p1, Point p2){
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) +Math.pow(p2.y - p1.y, 2));
    }

}
