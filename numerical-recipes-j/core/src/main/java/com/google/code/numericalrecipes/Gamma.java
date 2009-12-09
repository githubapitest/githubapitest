package com.google.code.numericalrecipes;
public class Gamma implements Gauleg18
{
	public static final Int ASWITCH = 100;
	public final Doub EPS = new Doub();
	public final Doub FPMIN = new Doub();
	public Doub gln = new Doub();

	public final Doub gammp(Doub a, Doub x)
	{
		if (x < 0.0 || a <= 0.0)
			throw("bad args in gammp");
		if (x == 0.0)
			return 0.0;
		else if ((Int)a >= ASWITCH)
			return gammpapprox(a, x, 1);
		else if (x < a+1.0)
			return gser(a, x);
		else
			return 1.0-gcf(a, x);
	}

	public final Doub gammq(Doub a, Doub x)
	{
		if (x < 0.0 || a <= 0.0)
			throw("bad args in gammq");
		if (x == 0.0)
			return 1.0;
		else if ((Int)a >= ASWITCH)
			return gammpapprox(a, x, 0);
		else if (x < a+1.0)
			return 1.0-gser(a, x);
		else
			return gcf(a, x);
	}

	public final Doub gser(Doub a, Doub x)
	{
		Doub sum = new Doub();
		Doub del = new Doub();
		Doub ap = new Doub();
		gln = gammln(a);
		ap = a;
		del = sum = 1.0/a;
		for (;;)
		{
			++ap;
			del *= x/ap;
			sum += del;
			if (Math.abs(del) < Math.abs(sum)*EPS)
			{
				return sum *Math.exp(-x+a *Math.log(x)-gln);
			}
		}
	}

	public final Doub gcf(Doub a, Doub x)
	{
		Int i = new Int();
		Doub an = new Doub();
		Doub b = new Doub();
		Doub c = new Doub();
		Doub d = new Doub();
		Doub del = new Doub();
		Doub h = new Doub();
		gln = gammln(a);
		b = x+1.0-a;
		c = 1.0/FPMIN;
		d = 1.0/b;
		h = d;
		for (i = 1;;i++)
		{
			an = -i*(i-a);
			b += 2.0;
			d = an *d+b;
			if (Math.abs(d) < FPMIN)
				d = FPMIN;
			c = b+an/c;
			if (Math.abs(c) < FPMIN)
				c = FPMIN;
			d = 1.0/d;
			del = d *c;
			h *= del;
			if (Math.abs(del-1.0) <= EPS)
				break;
		}
		return Math.exp(-x+a *Math.log(x)-gln)*h;
	}

	public final Doub gammpapprox(Doub a, Doub x, Int psig)
	{
		Int j = new Int();
		Doub xu = new Doub();
		Doub t = new Doub();
		Doub sum = new Doub();
		Doub ans = new Doub();
		Doub a1 = a-1.0;
		Doub lna1 = Math.log(a1);
		Doub sqrta1 = Math.sqrt(a1);
		gln = gammln(a);
		if (x > a1)
			xu = MAX(a1 + 11.5 *sqrta1, x + 6.0 *sqrta1);
		else
			xu = MAX(0.,MIN(a1 - 7.5 *sqrta1, x - 5.0 *sqrta1));
		sum = 0;
		for (j = 0;j<ngau;j++)
		{
			t = x + (xu-x)*y[j];
			sum += w[j]*Math.exp(-(t-a1)+a1*(Math.log(t)-lna1));
		}
		ans = sum*(xu-x)*Math.exp(a1*(lna1-1.)-gln);
		return (psig != null?(ans>0.0? 1.0-ans:-ans):(ans>=0.0? ans:1.0+ans));
	}

	public final Doub invgammp(Doub p, Doub a)
	{
		Int j = new Int();
		Doub x = new Doub();
		Doub err = new Doub();
		Doub t = new Doub();
		Doub u = new Doub();
		Doub pp = new Doub();
		Doub lna1 = new Doub();
		Doub afac = new Doub();
		Doub a1 = a-1;
		final Doub EPS = 1.e-8;
		gln = gammln(a);
		if (a <= 0.)
			throw("a must be pos in invgammap");
		if (p >= 1.)
			return MAX(100.,a + 100.*Math.sqrt(a));
		if (p <= 0.)
			return 0.0;
		if (a > 1.)
		{
			lna1 = Math.log(a1);
			afac = Math.exp(a1*(lna1-1.)-gln);
			pp = (p < 0.5)? p : 1.- p;
			t = Math.sqrt(-2.*Math.log(pp));
			x = (2.30753+t *0.27061)/(1.+t*(0.99229+t *0.04481)) - t;
			if (p < 0.5)
				x = -x;
			x = MAX(1.e-3,a *Math.pow(1.-1./(9.*a)-x/(3.*Math.sqrt(a)),3));
		}
		else
		{
			t = 1.0 - a*(0.253+a *0.12);
			if (p < t)
				x = Math.pow(p/t,1./a);
			else
				x = 1.-Math.log(1.-(p-t)/(1.-t));
		}
		for (j = 0;j<12;j++)
		{
			if (x <= 0.0)
				return 0.0;
			err = gammp(a, x) - p;
			if (a > 1.)
				t = afac *Math.exp(-(x-a1)+a1*(Math.log(x)-lna1));
			else
				t = Math.exp(-x+a1 *Math.log(x)-gln);
			u = err/t;
			x -= (t = u/(1.-0.5 *MIN(1.,u*((a-1.)/x - 1))));
			if (x <= 0.)
				x = 0.5*(x + t);
			if (Math.abs(t) < EPS *x)
				break;
		}
		return x;
	}

}