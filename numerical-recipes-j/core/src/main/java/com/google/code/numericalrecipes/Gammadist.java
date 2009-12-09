package com.google.code.numericalrecipes;
public class Gammadist implements Gamma
{
	public Doub alph = new Doub();
	public Doub bet = new Doub();
	public Doub fac = new Doub();
	public Gammadist(Doub aalph)
	{
		this(aalph, 1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Gammadist(Doub aalph, Doub bbet = 1.) : alph(aalph), bet(bbet)
	public Gammadist(Doub aalph, Doub bbet)
	{
		alph = aalph;
		bet = bbet;
		if (alph <= 0.|| bet <= 0.)
			throw("bad alph,bet in Gammadist");
		fac = alph *Math.log(bet)-gammln(alph);
	}
	public final Doub p(Doub x)
	{
		if (x <= 0.)
			throw("bad x in Gammadist");
		return Math.exp(-bet *x+(alph-1.)*Math.log(x)+fac);
	}
	public final Doub cdf(Doub x)
	{
		if (x < 0.)
			throw("bad x in Gammadist");
		return gammp(alph,bet *x);
	}
	public final Doub invcdf(Doub p)
	{
		if (p < 0.|| p >= 1.)
			throw("bad p in Gammadist");
		return invgammp(p,alph)/bet;
	}
}