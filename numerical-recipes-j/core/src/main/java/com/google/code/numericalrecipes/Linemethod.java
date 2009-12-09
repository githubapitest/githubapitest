package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class Linemethod<T>
{
	public VecDoub p = new VecDoub();
	public VecDoub xi = new VecDoub();
	public T func;
	public Int n = new Int();
	public Linemethod(RefObject<T> funcc)
	{
		func = funcc.argvalue;
	}
	public final Doub linmin()
	{
		Doub ax = new Doub();
		Doub xx = new Doub();
		Doub xmin = new Doub();
		n = p.size();
		F1dim<T> f1dim = new F1dim<T>(p,xi,func);
		ax = 0.0;
		xx = 1.0;
		Brent brent = new Brent();
		brent.bracket(ax,xx,f1dim);
		RefObject<T> tempRefObject = new RefObject<T>(f1dim);
		xmin = brent.minimize(tempRefObject);
		f1dim = tempRefObject.argvalue;
		for (Int j = 0;j<n;j++)
		{
			xi[j] *= xmin;
			p[j] += xi[j];
		}
		return brent.fmin;
	}
}