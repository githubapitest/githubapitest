package com.google.code.numericalrecipes;
public class NRf1
{
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	Doub (*y1)(Doub);
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	Doub (*y2)(Doub);
	public NRf2 f2 = new NRf2();
	public NRf1(Doub yy1(Doub), Doub yy2(Doub), Doub z1(Doub, Doub), Doub z2(Doub, Doub))
	{
		y1 = yy1;
		y2 = yy2;
		f2 = new NRf2(z1,z2);
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(Doub x)
	{
		f2.f3.xsav = x;
		return qgaus(f2,y1(x),y2(x));
	}
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
