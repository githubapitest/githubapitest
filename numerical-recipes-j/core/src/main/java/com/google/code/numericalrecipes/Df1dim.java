package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class Df1dim<T>
{
	public final VecDoub p;
	public final VecDoub xi;
	public Int n = new Int();
	public T funcd;
	public VecDoub xt = new VecDoub();
	public VecDoub dft = new VecDoub();
	public Df1dim(RefObject<VecDoub_I> pp, RefObject<VecDoub_I> xii, RefObject<T> funcdd)
	{
		p = pp.argvalue;
		xi = xii.argvalue;
		n = pp.argvalue.size();
		funcd = funcdd.argvalue;
		xt = n;
		dft = n;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(Doub x)
	{
		for (Int j = 0;j<n;j++)
			xt[j]=p[j]+x *xi[j];
		return funcd(xt);
	}
	public final Doub df(Doub x)
	{
		Doub df1 = 0.0;
		funcd.df(xt,dft);
		for (Int j = 0;j<n;j++)
			df1 += dft[j]*xi[j];
		return df1;
	}
}