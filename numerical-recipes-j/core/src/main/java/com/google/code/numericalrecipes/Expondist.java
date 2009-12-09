package com.google.code.numericalrecipes;
public class Expondist
{
	public Doub bet = new Doub();
	public Expondist(Doub bbet)
	{
		bet = bbet;
		if (bet <= 0.)
			throw("bad bet in Expondist");
	}
	public final Doub p(Doub x)
	{
		if (x < 0.)
			throw("bad x in Expondist");
		return bet *Math.exp(-bet *x);
	}
	public final Doub cdf(Doub x)
	{
		if (x < 0.)
			throw("bad x in Expondist");
		return 1.-Math.exp(-bet *x);
	}
	public final Doub invcdf(Doub p)
	{
		if (p < 0.|| p >= 1.)
			throw("bad p in Expondist");
		return -Math.log(1.-p)/bet;
	}
}