package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class NRfdjac<T>
{
	public final Doub EPS = new Doub();
	public T func;
	public NRfdjac(RefObject<T> funcc)
	{
		EPS = 1.0e-8;
		func = funcc.argvalue;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	MatDoub operator ()(RefObject<VecDoub_I> x, RefObject<VecDoub_I> fvec)
	{
		Int n = x.argvalue.size();
		MatDoub df = new MatDoub(n,n);
		VecDoub xh = x.argvalue;
		for (Int j = 0;j<n;j++)
		{
			Doub temp = xh[j];
			Doub h = EPS *Math.abs(temp);
			if (h == 0.0)
				h = EPS;
			xh[j]=temp+h;
			h = xh[j]-temp;
			VecDoub f = func(xh);
			xh[j]=temp;
			for (Int i = 0;i<n;i++)
				df[i][j]=(f[i]-fvec.argvalue[i])/h;
		}
		return df;
	}
}