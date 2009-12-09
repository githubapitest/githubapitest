package com.google.code.numericalrecipes;
public class GlobalMembersDelaunay
{
	public static Doub incircle(Point<2> d, Point<2> a, Point<2> b, Point<2> c)
	{
		Circle cc = circumcircle(a,b,c);
		Doub radd = SQR(d.x[0]-cc.center.x[0]) + SQR(d.x[1]-cc.center.x[1]);
		return (SQR(cc.radius) - radd);
	}
	public static final Doub Delaunay.fuzz = 1.0e-6;
	public static final Doub Delaunay.bigscale = 1000.0;
}