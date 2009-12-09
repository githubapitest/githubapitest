package com.google.code.numericalrecipes;
public class GlobalMembersGauss_wgts2
{
	public static void gaucof(RefObject<VecDoub_IO> a, RefObject<VecDoub_IO> b, Doub amu0, RefObject<VecDoub_O> x, RefObject<VecDoub_O> w)
	{
		Int n = a.argvalue.size();
		for (Int i = 0;i<n;i++)
			if (i != 0)
				b.argvalue[i]=Math.sqrt(b.argvalue[i]);
		Symmeig sym = new Symmeig(a.argvalue,b.argvalue);
		for (Int i = 0;i<n;i++)
		{
			x.argvalue[i]=sym.d[i];
			w.argvalue[i]=amu0 *sym.z[0][i]*sym.z[0][i];
		}
	}
	public static void radau(RefObject<VecDoub_IO> a, RefObject<VecDoub_IO> b, Doub amu0, Doub x1, RefObject<VecDoub_O> x, RefObject<VecDoub_O> w)
	{
		Int n = a.argvalue.size();
		if (n == 1)
		{
			x.argvalue[0]=x1;
			w.argvalue[0]=amu0;
		}
		else
		{
			Doub p = x1-a.argvalue[0];
			Doub pm1 = 1.0;
			Doub p1 = p;
			for (Int i = 1;i<n-1;i++)
			{
				p = (x1-a.argvalue[i])*p1-b.argvalue[i]*pm1;
				pm1 = p1;
				p1 = p;
			}
			a.argvalue[n-1]=x1-b.argvalue[n-1]*pm1/p;
			gaucof(a, b, amu0, x, w);
		}
	}
	public static void lobatto(RefObject<VecDoub_IO> a, RefObject<VecDoub_IO> b, Doub amu0, Doub x1, Doub xn, RefObject<VecDoub_O> x, RefObject<VecDoub_O> w)
	{
		Doub det = new Doub();
		Doub pl = new Doub();
		Doub pr = new Doub();
		Doub p1l = new Doub();
		Doub p1r = new Doub();
		Doub pm1l = new Doub();
		Doub pm1r = new Doub();
		Int n = a.argvalue.size();
		if (n <= 1)
			throw("n must be bigger than 1 in lobatto");
		pl = x1-a.argvalue[0];
		pr = xn-a.argvalue[0];
		pm1l = 1.0;
		pm1r = 1.0;
		p1l = pl;
		p1r = pr;
		for (Int i = 1;i<n-1;i++)
		{
			pl = (x1-a.argvalue[i])*p1l-b.argvalue[i]*pm1l;
			pr = (xn-a.argvalue[i])*p1r-b.argvalue[i]*pm1r;
			pm1l = p1l;
			pm1r = p1r;
			p1l = pl;
			p1r = pr;
		}
		det = pl *pm1r-pr *pm1l;
		a.argvalue[n-1]=(x1 *pl *pm1r-xn *pr *pm1l)/det;
		b.argvalue[n-1]=(xn-x1)*pl *pr/det;
		gaucof(a, b, amu0, x, w);
	}
}