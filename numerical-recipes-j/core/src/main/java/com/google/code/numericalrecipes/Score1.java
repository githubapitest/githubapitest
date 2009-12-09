package com.google.code.numericalrecipes;
public class Score
{
	public Int n = new Int();
	public Int m = new Int();
	public VecDoub f = new VecDoub();
	public Score(Int nn, Int mm)
	{
		n = nn;
		m = mm;
		f = 1;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	VecDoub operator ()(Doub xf, RefObject<VecDoub_I> y)
	{
		f[0]=((n-m & 1) != 0 ? y.argvalue[0] : y.argvalue[1]);
		return f;
	}
}