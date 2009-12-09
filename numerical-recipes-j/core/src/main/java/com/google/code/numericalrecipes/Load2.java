package com.google.code.numericalrecipes;
public class Load2
{
	public Int m = new Int();
	public Doub c2 = new Doub();
	public VecDoub y = new VecDoub();
	public Load2(Int mm, Doub cc2)
	{
		m = mm;
		c2 = cc2;
		y = 3;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	VecDoub operator ()(Doub x2, RefObject<VecDoub_I> v2)
	{
		y[2]=v2.argvalue[1];
		y[0]=v2.argvalue[0];
		y[1]=(y[2]-c2)*y[0]/(2*(m+1));
		return y;
	}
}