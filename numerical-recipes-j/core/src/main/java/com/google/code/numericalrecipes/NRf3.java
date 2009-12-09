package com.google.code.numericalrecipes;
public class NRf3
{
	public Doub xsav = new Doub();
	public Doub ysav = new Doub();
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	Doub (*func3d)(const Doub, const Doub, const Doub);
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(Doub z)
	{
		return func3d(xsav,ysav,z);
	}
}