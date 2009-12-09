package com.google.code.numericalrecipes;
public class Erf
{
	public static final Int ncof = 28;
	public static final Doub[] cof = new Doub[28];

	public final Doub erf(Doub x)
	{
		if (x >=0.)
			return 1.0 - erfccheb(x);
		else
			return erfccheb(-x) - 1.0;
	}

	public final Doub erfc(Doub x)
	{
		if (x >= 0.)
			return erfccheb(x);
		else
			return 2.0 - erfccheb(-x);
	}

	public final Doub erfccheb(Doub z)
	{
		Int j = new Int();
		Doub t = new Doub();
		Doub ty = new Doub();
		Doub tmp = new Doub();
		Doub d = 0.;
		Doub dd = 0.;
		if (z < 0.)
			throw("erfccheb requires nonnegative argument");
		t = 2./(2.+z);
		ty = 4.*t - 2.;
		for (j = ncof-1;j>0;j--)
		{
			tmp = d;
			d = ty *d - dd + cof[j];
			dd = tmp;
		}
		return t *Math.exp(-z *z + 0.5*(cof[0] + ty *d) - dd);
	}

	public final Doub inverfc(Doub p)
	{
		Doub x = new Doub();
		Doub err = new Doub();
		Doub t = new Doub();
		Doub pp = new Doub();
		if (p >= 2.0)
			return -100.;
		if (p <= 0.0)
			return 100.;
		pp = (p < 1.0)? p : 2.- p;
		t = Math.sqrt(-2.*Math.log(pp/2.));
		x = -0.70711*((2.30753+t *0.27061)/(1.+t*(0.99229+t *0.04481)) - t);
		for (Int j = 0;j<2;j++)
		{
			err = erfc(x) - pp;
			x += err/(1.12837916709551257 *Math.exp(-SQR(x))-x *err);
		}
		return (p < 1.0? x : -x);
	}

	public final Doub inverf(Doub p)
	{
		return inverfc(1.-p);
	}

}