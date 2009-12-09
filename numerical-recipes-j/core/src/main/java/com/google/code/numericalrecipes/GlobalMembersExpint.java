package com.google.code.numericalrecipes;
public class GlobalMembersExpint
{
	public static Doub expint(Int n, Doub x)
	{
		final Int MAXIT = 100;
		final Doub EULER = 0.577215664901533;
		final Doub EPS = numeric_limits<Doub>.epsilon();
		final Doub BIG = numeric_limits<Doub>.max()*EPS;
		Int i = new Int();
		Int ii = new Int();
		Int nm1 = n-1;
		Doub a = new Doub();
		Doub b = new Doub();
		Doub c = new Doub();
		Doub d = new Doub();
		Doub del = new Doub();
		Doub fact = new Doub();
		Doub h = new Doub();
		Doub psi = new Doub();
		Doub ans = new Doub();
		if (n < 0 || x < 0.0 || (x == 0.0 && (n == 0 || n == 1)))
			throw("bad arguments in expint");
		if (n == 0)
			ans = Math.exp(-x)/x;
		else
		{
			if (x == 0.0)
				ans = 1.0/nm1;
			else
			{
				if (x > 1.0)
				{
					b = x+n;
					c = BIG;
					d = 1.0/b;
					h = d;
					for (i = 1;i<=MAXIT;i++)
					{
						a = -i*(nm1+i);
						b += 2.0;
						d = 1.0/(a *d+b);
						c = b+a/c;
						del = c *d;
						h *= del;
						if (Math.abs(del-1.0) <= EPS)
						{
							ans = h *Math.exp(-x);
							return ans;
						}
					}
					throw("continued fraction failed in expint");
				}
				else
				{
					ans = (nm1!=0 ? 1.0/nm1 : -Math.log(x)-EULER);
					fact = 1.0;
					for (i = 1;i<=MAXIT;i++)
					{
						fact *= -x/i;
						if (i != nm1)
							del = -fact/(i-nm1);
						else
						{
							psi = -EULER;
							for (ii = 1;ii<=nm1;ii++)
								psi += 1.0/ii;
							del = fact*(-Math.log(x)+psi);
						}
						ans += del;
						if (Math.abs(del) < Math.abs(ans)*EPS)
							return ans;
					}
					throw("series failed in expint");
				}
			}
		}
		return ans;
	}
	public static Doub ei(Doub x)
	{
		final Int MAXIT = 100;
		final Doub EULER = 0.577215664901533;
		final Doub EPS = numeric_limits<Doub>.epsilon();
		final Doub FPMIN = numeric_limits<Doub>.min()/EPS;
		Int k = new Int();
		Doub fact = new Doub();
		Doub prev = new Doub();
		Doub sum = new Doub();
		Doub term = new Doub();
		if (x <= 0.0)
			throw("Bad argument in ei");
		if (x < FPMIN)
			return Math.log(x)+EULER;
		if (x <= -Math.log(EPS))
		{
			sum = 0.0;
			fact = 1.0;
			for (k = 1;k<=MAXIT;k++)
			{
				fact *= x/k;
				term = fact/k;
				sum += term;
				if (term < EPS *sum)
					break;
			}
			if (k > MAXIT)
				throw("Series failed in ei");
			return sum+Math.log(x)+EULER;
		}
		else
		{
			sum = 0.0;
			term = 1.0;
			for (k = 1;k<=MAXIT;k++)
			{
				prev = term;
				term *= k/x;
				if (term < EPS)
					break;
				if (term < prev)
					sum += term;
				else
				{
					sum -= prev;
					break;
				}
			}
			return Math.exp(x)*(1.0+sum)/x;
		}
	}
}