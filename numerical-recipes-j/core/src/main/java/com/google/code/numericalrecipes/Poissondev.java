package com.google.code.numericalrecipes;
public class Poissondev implements Ran
{
	public Doub lambda = new Doub();
	public Doub sqlam = new Doub();
	public Doub loglam = new Doub();
	public Doub lamexp = new Doub();
	public Doub lambold = new Doub();
	public VecDoub logfact = new VecDoub();
	public Int swch = new Int();
	public Poissondev(Doub llambda, Ullong i)
	{
		super(i);
		lambda = llambda;
		logfact = new VecDoub(1024,-1.);
		lambold = -1.;
	}
	public final Int dev()
	{
		Doub u = new Doub();
		Doub u2 = new Doub();
		Doub v = new Doub();
		Doub v2 = new Doub();
		Doub p = new Doub();
		Doub t = new Doub();
		Doub lfac = new Doub();
		Int k = new Int();
		if (lambda < 5.)
		{
			if (lambda != lambold)
				lamexp = Math.exp(-lambda);
			k = -1;
			t = 1.;
			do
			{
				++k;
				t *= doub();
			} while (t > lamexp);
		}
		else
		{
			if (lambda != lambold)
			{
				sqlam = Math.sqrt(lambda);
				loglam = Math.log(lambda);
			}
			for (;;)
			{
				u = 0.64 *doub();
				v = -0.68 + 1.28 *doub();
				if (lambda > 13.5)
				{
					v2 = SQR(v);
					if (v >= 0.)
					{
						if (v2 > 6.5 *u*(0.64-u)*(u+0.2))
							continue;
					}
					else
					{
						if (v2 > 9.6 *u*(0.66-u)*(u+0.07))
							continue;
					}
				}
				k = (Int)(Math.floor(sqlam*(v/u)+lambda+0.5));
				if (k < 0)
					continue;
				u2 = SQR(u);
				if (lambda > 13.5)
				{
					if (v >= 0.)
					{
						if (v2 < 15.2 *u2*(0.61-u)*(0.8-u))
							break;
					}
					else
					{
						if (v2 < 6.76 *u2*(0.62-u)*(1.4-u))
							break;
					}
				}
				if (k < 1024)
				{
					if (logfact[k] < 0.)
						logfact[k] = gammln(k+1.);
					lfac = logfact[k];
				}
				else
					lfac = gammln(k+1.);
				p = sqlam *Math.exp(-lambda + k *loglam - lfac);
				if (u2 < p)
					break;
			}
		}
		lambold = lambda;
		return k;
	}
	public final Int dev(Doub llambda)
	{
		lambda = llambda;
		return dev();
	}
}