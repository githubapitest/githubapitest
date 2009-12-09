package com.google.code.numericalrecipes;
public class Betadist implements Beta
{
	public Doub alph = new Doub();
	public Doub bet = new Doub();
	public Doub fac = new Doub();
	public Betadist(Doub aalph, Doub bbet)
	{
		alph = aalph;
		bet = bbet;
		if (alph <= 0.|| bet <= 0.)
			throw("bad alph,bet in Betadist");
		fac = gammln(alph+bet)-gammln(alph)-gammln(bet);
	}
	public final Doub p(Doub x)
	{
		if (x <= 0.|| x >= 1.)
			throw("bad x in Betadist");
		return Math.exp((alph-1.)*Math.log(x)+(bet-1.)*Math.log(1.-x)+fac);
	}
	public final Doub cdf(Doub x)
	{
		if (x < 0.|| x > 1.)
			throw("bad x in Betadist");
		return betai(alph,bet,x);
	}
	public final Doub invcdf(Doub p)
	{
		if (p < 0.|| p > 1.)
			throw("bad p in Betadist");
		return invbetai(p,alph,bet);
	}
}