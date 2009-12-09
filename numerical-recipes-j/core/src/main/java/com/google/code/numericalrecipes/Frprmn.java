package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class Frprmn<T> implements Linemethod<T>
{
	public Int iter = new Int();
	public Doub fret = new Doub();
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using Linemethod<T>.func;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using Linemethod<T>.linmin;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using Linemethod<T>.p;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using Linemethod<T>.xi;
	public final Doub ftol = new Doub();
	public Frprmn(RefObject<T> funcd)
	{
		this(funcd, 3.0e-8);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Frprmn(T &funcd, const Doub ftoll=3.0e-8) : Linemethod<T>(funcd), ftol(ftoll)
	public Frprmn(RefObject<T> funcd, Doub ftoll)
	{
		super(funcd.argvalue);
		ftol = ftoll;
	}
	public final VecDoub minimize(RefObject<VecDoub_I> pp)
	{
		final Int ITMAX = 200;
		final Doub EPS = 1.0e-18;
		final Doub GTOL = 1.0e-8;
		Doub gg = new Doub();
		Doub dgg = new Doub();
		Int n = pp.argvalue.size();
		p=pp.argvalue;
		VecDoub g = new VecDoub(n);
		VecDoub h = new VecDoub(n);
		xi.resize(n);
		Doub fp = func(p);
		func.df(p,xi);
		for (Int j = 0;j<n;j++)
		{
			g[j] = -xi[j];
			xi[j]=h[j]=g[j];
		}
		for (Int its = 0;its<ITMAX;its++)
		{
			iter = its;
			fret = linmin();
			if (2.0 *Math.abs(fret-fp) <= ftol*(Math.abs(fret)+Math.abs(fp)+EPS))
				return p;
			fp = fret;
			func.df(p,xi);
			Doub test = 0.0;
			Doub den = MAX(fp,1.0);
			for (Int j = 0;j<n;j++)
			{
				Doub temp = Math.abs(xi[j])*MAX(Math.abs(p[j]),1.0)/den;
				if (temp > test)
					test = temp;
			}
			if (test < GTOL)
				return p;
			dgg = gg = 0.0;
			for (Int j = 0;j<n;j++)
			{
				gg += g[j]*g[j];
//			  dgg += xi[j]*xi[j];
				dgg += (xi[j]+g[j])*xi[j];
			}
			if (gg == 0.0)
				return p;
			Doub gam = dgg/gg;
			for (Int j = 0;j<n;j++)
			{
				g[j] = -xi[j];
				xi[j]=h[j]=g[j]+gam *h[j];
			}
		}
		throw("Too many iterations in frprmn");
	}
}