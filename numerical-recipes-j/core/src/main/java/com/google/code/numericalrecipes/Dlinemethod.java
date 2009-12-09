package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class Dlinemethod<T>
{
	public VecDoub p = new VecDoub();
	public VecDoub xi = new VecDoub();
	public T func;
	public Int n = new Int();
	public Dlinemethod(RefObject<T> funcc)
	{
		func = funcc.argvalue;
	}
	public final Doub linmin()
	{
		Doub ax = new Doub();
		Doub xx = new Doub();
		Doub xmin = new Doub();
		n = p.size();
		Df1dim<T> df1dim = new Df1dim<T>(p,xi,func);
		ax = 0.0;
		xx = 1.0;
		Dbrent dbrent = new Dbrent();
		dbrent.bracket(ax,xx,df1dim);
		RefObject<T> tempRefObject = new RefObject<T>(df1dim);
		xmin = dbrent.minimize(tempRefObject);
		df1dim = tempRefObject.argvalue;
		for (Int j = 0;j<n;j++)
		{
			xi[j] *= xmin;
			p[j] += xi[j];
		}
		return dbrent.fmin;
	}
}