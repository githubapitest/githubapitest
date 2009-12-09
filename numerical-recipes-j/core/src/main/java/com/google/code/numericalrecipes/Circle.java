package com.google.code.numericalrecipes;
public class Circle
{
	public Point<2> center = new Point<2>();
	public Doub radius = new Doub();
	public Circle(Point<2> cen, Doub rad)
	{
		center = cen;
		radius = rad;
	}
}