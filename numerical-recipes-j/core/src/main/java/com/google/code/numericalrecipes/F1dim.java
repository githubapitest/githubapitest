package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class F1dim<T>
{
	public final VecDoub p;
	public final VecDoub xi;
	public Int n = new Int();
	public T func;
	public VecDoub xt = new VecDoub();
	public F1dim(RefObject<VecDoub_I> pp, RefObject<VecDoub_I> xii, RefObject<T> funcc)
	{
		p = pp.argvalue;
		xi = xii.argvalue;
		n = pp.argvalue.size();
		func = funcc.argvalue;
		xt = n;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(Doub x)
	{
		for (Int j = 0;j<n;j++)
			xt[j]=p[j]+x *xi[j];
		return func(xt);
	}
}