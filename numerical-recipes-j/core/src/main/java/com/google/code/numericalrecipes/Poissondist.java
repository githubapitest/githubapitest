package com.google.code.numericalrecipes;
public class Poissondist implements Gamma
{
	public Doub lam = new Doub();
	public Poissondist(Doub llam)
	{
		lam = llam;
		if (lam <= 0.)
			throw("bad lam in Poissondist");
	}
	public final Doub p(Int n)
	{
		if (n < 0)
			throw("bad n in Poissondist");
		return Math.exp(-lam + n *Math.log(lam) - gammln(n+1.));
	}
	public final Doub cdf(Int n)
	{
		if (n < 0)
			throw("bad n in Poissondist");
		if (n == 0)
			return 0.;
		return gammq((Doub)n,lam);
	}
	public final Int invcdf(Doub p)
	{
		Int n = new Int();
		Int nl = new Int();
		Int nu = new Int();
		Int inc = 1;
		if (p <= 0.|| p >= 1.)
			throw("bad p in Poissondist");
		if (p < Math.exp(-lam))
			return 0;
		n = (Int)MAX(Math.sqrt(lam),5.);
		if (p < cdf(n))
		{
			do
			{
				n = MAX(n-inc,0);
				inc *= 2;
			} while (p < cdf(n));
			nl = n;
			nu = n + inc/2;
		}
		else
		{
			do
			{
				n += inc;
				inc *= 2;
			} while (p > cdf(n));
			nu = n;
			nl = n - inc/2;
		}
		while (nu-nl>1)
		{
			n = (nl+nu)/2;
			if (p < cdf(n))
				nu = n;
			else
				nl = n;
		}
		return nl;
	}
}