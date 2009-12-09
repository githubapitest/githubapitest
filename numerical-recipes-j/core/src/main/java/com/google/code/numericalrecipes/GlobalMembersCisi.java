package com.google.code.numericalrecipes;
public class GlobalMembersCisi
{
	public static Complex cisi(Doub x)
	{
		final Int MAXIT = 100;
		final Doub EULER = 0.577215664901533;
		final Doub PIBY2 = 1.570796326794897;
		final Doub TMIN = 2.0;
		final Doub EPS = numeric_limits<Doub>.epsilon();
		final Doub FPMIN = numeric_limits<Doub>.min()*4.0;
		final Doub BIG = numeric_limits<Doub>.max()*EPS;
		Int i = new Int();
		Int k = new Int();
		Bool odd = new Bool();
		Doub a = new Doub();
		Doub err = new Doub();
		Doub fact = new Doub();
		Doub sign = new Doub();
		Doub sum = new Doub();
		Doub sumc = new Doub();
		Doub sums = new Doub();
		Doub t = new Doub();
		Doub term = new Doub();
		Complex h = new Complex();
		Complex b = new Complex();
		Complex c = new Complex();
		Complex d = new Complex();
		Complex del = new Complex();
		Complex cs = new Complex();
		if ((t = Math.abs(x)) == 0.0)
			return -BIG;
		if (t > TMIN)
		{
			b = Complex(1.0,t);
			c = Complex(BIG,0.0);
			d = h = 1.0/b;
			for (i = 1;i<MAXIT;i++)
			{
				a = -i *i;
				b += 2.0;
				d = 1.0/(a *d+b);
				c = b+a/c;
				del = c *d;
				h *= del;
				if (Math.abs(real(del)-1.0)+Math.abs(imag(del)) <= EPS)
					break;
			}
			if (i >= MAXIT)
				throw("cf failed in cisi");
			h = Complex(Math.cos(t),-Math.sin(t))*h;
			cs = -conj(h)+Complex(0.0,PIBY2);
		}
		else
		{
			if (t < Math.sqrt(FPMIN))
			{
				sumc = 0.0;
				sums = t;
			}
			else
			{
				sum = sums = sumc = 0.0;
				sign = fact = 1.0;
				odd = true;
				for (k = 1;k<=MAXIT;k++)
				{
					fact *= t/k;
					term = fact/k;
					sum += sign *term;
					err = term/Math.abs(sum);
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
					if (err < EPS)
						break;
					odd = !odd;
				}
				if (k > MAXIT)
					throw("maxits exceeded in cisi");
			}
			cs = Complex(sumc+Math.log(t)+EULER,sums);
		}
		if (x < 0.0)
			cs = conj(cs);
		return cs;
	}
}