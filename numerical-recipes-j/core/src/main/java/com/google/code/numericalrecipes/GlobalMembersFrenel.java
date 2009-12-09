package com.google.code.numericalrecipes;
public class GlobalMembersFrenel
{
	public static Complex frenel(Doub x)
	{
		final Int MAXIT = 100;
		final Doub PI = 3.141592653589793238;
		final Doub PIBY2 = (PI/2.0);
		final Doub XMIN = 1.5;
		final Doub EPS = numeric_limits<Doub>.epsilon();
		final Doub FPMIN = numeric_limits<Doub>.min();
		final Doub BIG = numeric_limits<Doub>.max()*EPS;
		Bool odd = new Bool();
		Int k = new Int();
		Int n = new Int();
		Doub a = new Doub();
		Doub ax = new Doub();
		Doub fact = new Doub();
		Doub pix2 = new Doub();
		Doub sign = new Doub();
		Doub sum = new Doub();
		Doub sumc = new Doub();
		Doub sums = new Doub();
		Doub term = new Doub();
		Doub test = new Doub();
		Complex b = new Complex();
		Complex cc = new Complex();
		Complex d = new Complex();
		Complex h = new Complex();
		Complex del = new Complex();
		Complex cs = new Complex();
		if ((ax = Math.abs(x)) < Math.sqrt(FPMIN))
		{
			cs = ax;
		}
		else if (ax <= XMIN)
		{
			sum = sums = 0.0;
			sumc = ax;
			sign = 1.0;
			fact = PIBY2 *ax *ax;
			odd = true;
			term = ax;
			n = 3;
			for (k = 1;k<=MAXIT;k++)
			{
				term *= fact/k;
				sum += sign *term/n;
				test = Math.abs(sum)*EPS;
				if (odd != null)
				{
					sign = -sign;
					sums = sum;
					sum = sumc;
				}
				else
				{
					sumc = sum;
					sum = sums;
				}
				if (term < test)
					break;
				odd = !odd;
				n += 2;
			}
			if (k > MAXIT)
				throw("series failed in frenel");
			cs = Complex(sumc,sums);
		}
		else
		{
			pix2 = PI *ax *ax;
			b = Complex(1.0,-pix2);
			cc = BIG;
			d = h = 1.0/b;
			n = -1;
			for (k = 2;k<=MAXIT;k++)
			{
				n += 2;
				a = -n*(n+1);
				b += 4.0;
				d = 1.0/(a *d+b);
				cc = b+a/cc;
				del = cc *d;
				h *= del;
				if (Math.abs(real(del)-1.0)+Math.abs(imag(del)) <= EPS)
					break;
			}
			if (k > MAXIT)
				throw("cf failed in frenel");
			h *= Complex(ax,-ax);
			cs = Complex(0.5,0.5) *(1.0-Complex(Math.cos(0.5 *pix2),Math.sin(0.5 *pix2))*h);
		}
		if (x < 0.0)
			cs = -cs;
		return cs;
	}
}