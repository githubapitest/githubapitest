package com.google.code.numericalrecipes;
public class Fdist implements Beta
{
	public Doub nu1 = new Doub();
	public Doub nu2 = new Doub();
	public Doub fac = new Doub();
	public Fdist(Doub nnu1, Doub nnu2)
	{
		nu1 = nnu1;
		nu2 = nnu2;
		if (nu1 <= 0.|| nu2 <= 0.)
			throw("bad nu1,nu2 in Fdist");
		fac = 0.5*(nu1 *Math.log(nu1)+nu2 *Math.log(nu2))+gammln(0.5*(nu1+nu2)) -gammln(0.5 *nu1)-gammln(0.5 *nu2);
	}
	public final Doub p(Doub f)
	{
		if (f <= 0.)
			throw("bad f in Fdist");
		return Math.exp((0.5 *nu1-1.)*Math.log(f)-0.5*(nu1+nu2)*Math.log(nu2+nu1 *f)+fac);
	}
	public final Doub cdf(Doub f)
	{
		if (f < 0.)
			throw("bad f in Fdist");
		return betai(0.5 *nu1,0.5 *nu2,nu1 *f/(nu2+nu1 *f));
	}
	public final Doub invcdf(Doub p)
	{
		if (p <= 0.|| p >= 1.)
			throw("bad p in Fdist");
		Doub x = invbetai(p,0.5 *nu1,0.5 *nu2);
		return nu2 *x/(nu1*(1.-x));
	}
}