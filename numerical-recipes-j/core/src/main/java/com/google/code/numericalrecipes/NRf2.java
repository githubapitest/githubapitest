package com.google.code.numericalrecipes;
public class NRf2
{
	public NRf3 f3 = new NRf3();
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	Doub (*z1)(Doub, Doub);
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	Doub (*z2)(Doub, Doub);
	public NRf2(Doub zz1(Doub, Doub), Doub zz2(Doub, Doub))
	{
		z1 = zz1;
		z2 = zz2;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(Doub y)
	{
		f3.ysav = y;
		return qgaus(f3,z1(f3.xsav,y),z2(f3.xsav,y));
	}
}