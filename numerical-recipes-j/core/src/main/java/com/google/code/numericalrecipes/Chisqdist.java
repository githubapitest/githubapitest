package com.google.code.numericalrecipes;
public class Chisqdist implements Gamma
{
	public Doub nu = new Doub();
	public Doub fac = new Doub();
	public Chisqdist(Doub nnu)
	{
		nu = nnu;
		if (nu <= 0.)
			throw("bad nu in Chisqdist");
		fac = 0.693147180559945309*(0.5 *nu)+gammln(0.5 *nu);
	}
	public final Doub p(Doub x2)
	{
		if (x2 <= 0.)
			throw("bad x2 in Chisqdist");
		return Math.exp(-0.5*(x2-(nu-2.)*Math.log(x2))-fac);
	}
	public final Doub cdf(Doub x2)
	{
		if (x2 < 0.)
			throw("bad x2 in Chisqdist");
		return gammp(0.5 *nu,0.5 *x2);
	}
	public final Doub invcdf(Doub p)
	{
		if (p < 0.|| p >= 1.)
			throw("bad p in Chisqdist");
		return 2.*invgammp(p,0.5 *nu);
	}
}