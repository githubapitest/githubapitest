package com.google.code.numericalrecipes;
public class Poly
{
	public VecDoub c;
	public Poly(RefObject<VecDoub> cc)
	{
		c = cc.argvalue;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(Doub x)
	{
		Int j = new Int();
		Doub p = c[j = c.size()-1];
		while (j>0)
			p = p *x + c[--j];
		return p;
	}
}