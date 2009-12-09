package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class Midsql<T> implements Midpnt<T>
{
	public Doub aorig = new Doub();
	public final Doub func(Doub x)
	{
		return 2.0 *x *Midpnt<T>.funk(aorig+x *x);
	}
	public Midsql(RefObject<T> funcc, Doub aa, Doub bb)
	{
		super(funcc.argvalue, aa, bb);
		aorig = aa;
		Midpnt<T>.a=0;
		Midpnt<T>.b=Math.sqrt(bb-aa);
	}
}