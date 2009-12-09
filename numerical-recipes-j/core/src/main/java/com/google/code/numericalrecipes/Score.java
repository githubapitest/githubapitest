package com.google.code.numericalrecipes;
public class Score
{
	public VecDoub f = new VecDoub();
	public Score()
	{
		f = 3;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	VecDoub operator ()(Doub xf, RefObject<VecDoub_I> y)
	{
		for (Int i = 0;i<3;i++)
			f[i]=y.argvalue[i];
		return f;
	}
}