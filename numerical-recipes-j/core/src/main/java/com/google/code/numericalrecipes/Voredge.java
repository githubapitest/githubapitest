package com.google.code.numericalrecipes;
public class Voredge
{
	public Point<2>[] p = new Point[2];
	public Int nearpt = new Int();
	public Voredge()
	{
	}
	public Voredge(Point<2> pa, Point<2> pb, Int np)
	{
		nearpt = np;
		p[0] = pa;
		p[1] = pb;
	}
}