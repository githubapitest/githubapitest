package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class NRfmin<T>
{
	public VecDoub fvec = new VecDoub();
	public T func;
	public Int n = new Int();
	public NRfmin(RefObject<T> funcc)
	{
		func = funcc.argvalue;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(RefObject<VecDoub_I> x)
	{
		n = x.argvalue.size();
		Doub sum = 0;
		fvec = func(x.argvalue);
		for (Int i = 0;i<n;i++)
			sum += SQR(fvec[i]);
		return 0.5 *sum;
	}
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
