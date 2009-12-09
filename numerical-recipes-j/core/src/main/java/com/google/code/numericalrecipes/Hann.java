package com.google.code.numericalrecipes;
public class Hann
{
	public Int nn = new Int();
	public VecDoub win = new VecDoub();
	public Hann(Int n)
	{
		nn = n;
		win = n;
		Doub twopi = 8.*Math.atan(1.);
		for (Int i = 0;i<nn;i++)
			win[i] = 0.5*(1.-Math.cos(i *twopi/(nn-1.)));
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(Int j, Int n)
	{
		if (n != nn)
			throw("incorrect n for this Hann");
		return win[j];
	}
}