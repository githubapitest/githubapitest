package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class Midinf<T> implements Midpnt<T>
{
	public final Doub func(Doub x)
	{
		return Midpnt<T>.funk(1.0/x)/(x *x);
	}
	public Midinf(RefObject<T> funcc, Doub aa, Doub bb)
	{
		super(funcc.argvalue, aa, bb);
		Midpnt<T>.a=1.0/bb;
		Midpnt<T>.b=1.0/aa;
	}
}