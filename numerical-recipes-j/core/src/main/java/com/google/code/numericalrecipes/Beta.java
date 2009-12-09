package com.google.code.numericalrecipes;
public class Beta implements Gauleg18
{
	public static final Int SWITCH = 3000;
	public final Doub EPS = new Doub();
	public final Doub FPMIN = new Doub();

	public final Doub betai(Doub a, Doub b, Doub x)
	{
		Doub bt = new Doub();
		if (a <= 0.0 || b <= 0.0)
			throw("Bad a or b in routine betai");
		if (x < 0.0 || x > 1.0)
			throw("Bad x in routine betai");
		if (x == 0.0 || x == 1.0)
			return x;
		if (a > SWITCH && b > SWITCH)
			return betaiapprox(a, b, x);
		bt = Math.exp(gammln(a+b)-gammln(a)-gammln(b)+a *Math.log(x)+b *Math.log(1.0-x));
		if (x < (a+1.0)/(a+b+2.0))
			return bt *betacf(a, b, x)/a;
		else
			return 1.0-bt *betacf(b, a, 1.0-x)/b;
	}

	public final Doub betacf(Doub a, Doub b, Doub x)
	{
		Int m = new Int();
		Int m2 = new Int();
		Doub aa = new Doub();
		Doub c = new Doub();
		Doub d = new Doub();
		Doub del = new Doub();
		Doub h = new Doub();
		Doub qab = new Doub();
		Doub qam = new Doub();
		Doub qap = new Doub();
		qab = a+b;
		qap = a+1.0;
		qam = a-1.0;
		c = 1.0;
		d = 1.0-qab *x/qap;
		if (Math.abs(d) < FPMIN)
			d = FPMIN;
		d = 1.0/d;
		h = d;
		for (m = 1;m<10000;m++)
		{
			m2 = 2 *m;
			aa = m*(b-m)*x/((qam+m2)*(a+m2));
			d = 1.0+aa *d;
			if (Math.abs(d) < FPMIN)
				d = FPMIN;
			c = 1.0+aa/c;
			if (Math.abs(c) < FPMIN)
				c = FPMIN;
			d = 1.0/d;
			h *= d *c;
			aa = -(a+m)*(qab+m)*x/((a+m2)*(qap+m2));
			d = 1.0+aa *d;
			if (Math.abs(d) < FPMIN)
				d = FPMIN;
			c = 1.0+aa/c;
			if (Math.abs(c) < FPMIN)
				c = FPMIN;
			d = 1.0/d;
			del = d *c;
			h *= del;
			if (Math.abs(del-1.0) <= EPS)
				break;
		}
		return h;
	}

	public final Doub betaiapprox(Doub a, Doub b, Doub x)
	{
		Int j = new Int();
		Doub xu = new Doub();
		Doub t = new Doub();
		Doub sum = new Doub();
		Doub ans = new Doub();
		Doub a1 = a-1.0;
		Doub b1 = b-1.0;
		Doub mu = a/(a+b);
		Doub lnmu = Math.log(mu);
		Doub lnmuc = Math.log(1.-mu);
		t = Math.sqrt(a *b/(SQR(a+b)*(a+b+1.0)));
		if (x > a/(a+b))
		{
			if (x >= 1.0)
				return 1.0;
			xu = MIN(1.,MAX(mu + 10.*t, x + 5.0 *t));
		}
		else
		{
			if (x <= 0.0)
				return 0.0;
			xu = MAX(0.,MIN(mu - 10.*t, x - 5.0 *t));
		}
		sum = 0;
		for (j = 0;j<18;j++)
		{
			t = x + (xu-x)*y[j];
			sum += w[j]*Math.exp(a1*(Math.log(t)-lnmu)+b1*(Math.log(1-t)-lnmuc));
		}
		ans = sum*(xu-x)*Math.exp(a1 *lnmu-gammln(a)+b1 *lnmuc-gammln(b)+gammln(a+b));
		return ans>0.0? 1.0-ans : -ans;
	}

	public final Doub invbetai(Doub p, Doub a, Doub b)
	{
		final Doub EPS = 1.e-8;
		Doub pp = new Doub();
		Doub t = new Doub();
		Doub u = new Doub();
		Doub err = new Doub();
		Doub x = new Doub();
		Doub al = new Doub();
		Doub h = new Doub();
		Doub w = new Doub();
		Doub afac = new Doub();
		Doub a1 = a-1.;
		Doub b1 = b-1.;
		Int j = new Int();
		if (p <= 0.)
			return 0.;
		else if (p >= 1.)
			return 1.;
		else if (a >= 1.&& b >= 1.)
		{
			pp = (p < 0.5)? p : 1.- p;
			t = Math.sqrt(-2.*Math.log(pp));
			x = (2.30753+t *0.27061)/(1.+t*(0.99229+t *0.04481)) - t;
			if (p < 0.5)
				x = -x;
			al = (SQR(x)-3.)/6.;
			h = 2./(1./(2.*a-1.)+1./(2.*b-1.));
			w = (x *Math.sqrt(al+h)/h)-(1./(2.*b-1)-1./(2.*a-1.))*(al+5./6.-2./(3.*h));
			x = a/(a+b *Math.exp(2.*w));
		}
		else
		{
			Doub lna = Math.log(a/(a+b));
			Doub lnb = Math.log(b/(a+b));
			t = Math.exp(a *lna)/a;
			u = Math.exp(b *lnb)/b;
			w = t + u;
			if (p < t/w)
				x = Math.pow(a *w *p,1./a);
			else
				x = 1.- Math.pow(b *w*(1.-p),1./b);
		}
		afac = -gammln(a)-gammln(b)+gammln(a+b);
		for (j = 0;j<10;j++)
		{
			if (x == 0.|| x == 1.)
				return x;
			err = betai(a, b, x) - p;
			t = Math.exp(a1 *Math.log(x)+b1 *Math.log(1.-x) + afac);
			u = err/t;
			x -= (t = u/(1.-0.5 *MIN(1.,u*(a1/x - b1/(1.-x)))));
			if (x <= 0.)
				x = 0.5*(x + t);
			if (x >= 1.)
				x = 0.5*(x + t + 1.);
			if (Math.abs(t) < EPS *x && j > 0)
				break;
		}
		return x;
	}

}