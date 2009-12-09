package com.google.code.numericalrecipes;
public class Load1
{
	public Int n = new Int();
	public Int m = new Int();
	public Doub gmma = new Doub();
	public Doub c2 = new Doub();
	public Doub dx = new Doub();
	public VecDoub y = new VecDoub();
	public Load1(Int nn, Int mm, Doub gmmaa, Doub cc2, Doub dxx)
	{
		n = nn;
		m = mm;
		gmma = gmmaa;
		c2 = cc2;
		dx = dxx;
		y = 3;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	VecDoub operator ()(Doub x1, RefObject<VecDoub_I> v1)
	{
		Doub y1 = ((n-m & 1) != 0 ? -gmma : gmma);
		y[2]=v1.argvalue[0];
		y[1] = -(y[2]-c2)*y1/(2*(m+1));
		y[0]=y1+y[1]*dx;
		return y;
	}
}