package ru.stqa.pft.sandbox;

public class m1p {
	public static void main(String[] args) {
		Square square = new Square(5);
		System.out.println("Площадь квадрата со стороной " + square.l + " = " + square.area());

		Rectangle rectangle = new Rectangle(4, 6);
		System.out.println("Площадь прямоугольника со сторонами " + rectangle.a + " и " + rectangle.b + " = " + rectangle.area());
	}
}