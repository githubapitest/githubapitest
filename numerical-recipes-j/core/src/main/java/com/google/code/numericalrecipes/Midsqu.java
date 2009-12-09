package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class Midsqu<T> implements Midpnt<T>
{
	public Doub borig = new Doub();
	public final Doub func(Doub x)
	{
		return 2.0 *x *Midpnt<T>.funk(borig-x *x);
	}
	public Midsqu(RefObject<T> funcc, Doub aa, Doub bb)
	{
		super(funcc.argvalue, aa, bb);
		borig = bb;
		Midpnt<T>.a=0;
		Midpnt<T>.b=Math.sqrt(bb-aa);
	}
}