package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class Midexp<T> implements Midpnt<T>
{
	public final Doub func(Doub x)
	{
		return Midpnt<T>.funk(-Math.log(x))/x;
	}
	public Midexp(RefObject<T> funcc, Doub aa, Doub bb)
	{
		super(funcc.argvalue, aa, bb);
		Midpnt<T>.a=0.0;
		Midpnt<T>.b=Math.exp(-aa);
	}
}