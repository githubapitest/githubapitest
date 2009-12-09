package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class Funcd<T>
{
	public Doub EPS = new Doub();
	public T func;
	public Doub f = new Doub();
	public Funcd(RefObject<T> funcc)
	{
		EPS = 1.0e-8;
		func = funcc.argvalue;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(RefObject<VecDoub_I> x)
	{
		return f = func(x.argvalue);
	}

	public final void df(RefObject<VecDoub_I> x, RefObject<VecDoub_O> df)
	{
		Int n = x.argvalue.size();
		VecDoub xh = x.argvalue;
		Doub fold = f;
		for (Int j = 0;j<n;j++)
		{
			Doub temp = x.argvalue[j];
			Doub h = EPS *Math.abs(temp);
			if (h == 0.0)
				h = EPS;
			xh[j]=temp+h;
			h = xh[j]-temp;
			Doub fh = operator ()(xh);
			xh[j]=temp;
			df.argvalue[j]=(fh-fold)/h;
		}
	}
}