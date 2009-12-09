package com.google.code.numericalrecipes;
public class GlobalMembersPointbox
{
	public static <Int DIM> Doub dist(Point<DIM> p, Point<DIM> q)
	{
		Doub dd = 0.0;
		for (Int j = 0; j<DIM; j++)
			dd += SQR(q.x[j]-p.x[j]);
		return Math.sqrt(dd);
	}
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
	public static <Int DIM> Doub dist(Box<DIM> b, Point<DIM> p)
	{
		Doub dd = 0;
		for (Int i = 0; i<DIM; i++)
		{
			if (p.x[i]<b.lo.x[i])
				dd += SQR(p.x[i]-b.lo.x[i]);
			if (p.x[i]>b.hi.x[i])
				dd += SQR(p.x[i]-b.hi.x[i]);
		}
		return Math.sqrt(dd);
	}
}