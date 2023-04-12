package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SquareTests {

    Point p1 = new Point(1, 4);
    Point p2 = new Point(1, 0);

    @Test
    public void testArea(){
        Square square = new Square(5);
        Assert.assertEquals(square.area(), 25);
    }

    @Test
    public void testDistanceBetweenPoints(){
        Assert.assertEquals(CalculateDistanceBetweenPoints.distanceBetweenPoints(p1, p2), 4);
    }

    @Test
    public void testDistanceToPoint(){
        Assert.assertEquals(p1.distanceToPoint(p2), 4);
    }

    @Test
    public void testDistance(){
        Assert.assertEquals(CalculateDistanceBetweenPoints.distanceBetweenPoints(p1, p2), p1.distanceToPoint(p2));
    }

}
