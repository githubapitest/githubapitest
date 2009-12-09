package com.google.code.numericalrecipes;
public class Triel
{
	public Point<2> pts;
	public Int[] p = new Int[3];
	public Int[] d = new Int[3];
	public Int stat = new Int();
	public final void setme(Int a, Int b, Int c, RefObject<Point<2>> ptss)
	{
		pts = ptss.argvalue;
		p[0] = a;
		p[1] = b;
		p[2] = c;
		d[0] = d[1] = d[2] = -1;
		stat = 1;
	}
	public final Int contains(Point<2> point)
	{
		Doub d = new Doub();
		Int i = new Int();
		Int j = new Int();
		Int ztest = 0;
		for (i = 0; i<3; i++)
		{
			j = (i+1) %3;
			d = (pts[p[j]].x[0]-pts[p[i]].x[0])*(point.x[1]-pts[p[i]].x[1]) - (pts[p[j]].x[1]-pts[p[i]].x[1])*(point.x[0]-pts[p[i]].x[0]);
			if (d < 0.0)
				return -1;
			if (d == 0.0)
				ztest = 1;
		}
		return (ztest != null? 0 : 1);
	}
}