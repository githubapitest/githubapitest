package com.google.code.numericalrecipes;
public class Gammadev implements Normaldev
{
	public Doub alph = new Doub();
	public Doub oalph = new Doub();
	public Doub bet = new Doub();
	public Doub a1 = new Doub();
	public Doub a2 = new Doub();
	public Gammadev(Doub aalph, Doub bbet, Ullong i)
	{
		super(0., 1., i);
		alph = aalph;
		oalph = aalph;
		bet = bbet;
		if (alph <= 0.)
			throw("bad alph in Gammadev");
		if (alph < 1.)
			alph += 1.;
		a1 = alph-1./3.;
		a2 = 1./Math.sqrt(9.*a1);
	}
	public final Doub dev()
	{
		Doub u = new Doub();
		Doub v = new Doub();
		Doub x = new Doub();
		do
		{
			do
			{
				x = super.dev();
				v = 1.+ a2 *x;
			} while (v <= 0.);
			v = v *v *v;
			u = doub();
		} while (u > 1.- 0.331 *SQR(SQR(x)) && Math.log(u) > 0.5 *SQR(x) + a1*(1.-v+Math.log(v)));
		if (alph == oalph)
			return a1 *v/bet;
		else
		{
			do
				u = doub();
				while (u == 0.);
			return Math.pow(u,1./oalph)*a1 *v/bet;
		}
	}
}