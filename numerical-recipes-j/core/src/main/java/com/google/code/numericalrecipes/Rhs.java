package com.google.code.numericalrecipes;
public class Rhs
{
	public Int m = new Int();
	public Doub c2 = new Doub();
	public Rhs(Int mm, Doub cc2)
	{
		m = mm;
		c2 = cc2;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	void operator ()(Doub x, RefObject<VecDoub_I> y, RefObject<VecDoub_O> dydx)
	{
		dydx.argvalue[0]=y.argvalue[1];
		dydx.argvalue[1]=(2.0 *x*(m+1.0)*y.argvalue[1]-(y.argvalue[2]-c2 *x *x)*y.argvalue[0])/(1.0-x *x);
		dydx.argvalue[2]=0.0;
	}
}