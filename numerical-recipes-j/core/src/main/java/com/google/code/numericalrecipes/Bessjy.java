package com.google.code.numericalrecipes;
public class Bessjy
{
	public final Doub xj00 = new Doub();
	public final Doub xj10 = new Doub();
	public final Doub xj01 = new Doub();
	public final Doub xj11 = new Doub();
	public final Doub twoopi = new Doub();
	public final Doub pio4 = new Doub();
	public static final Doub[] j0r = new Doub[7];
	public static final Doub[] j0s = new Doub[7];
	public static final Doub[] j0pn = new Doub[5];
	public static final Doub[] j0pd = new Doub[5];
	public static final Doub[] j0qn = new Doub[5];
	public static final Doub[] j0qd = new Doub[5];
	public static final Doub[] j1r = new Doub[7];
	public static final Doub[] j1s = new Doub[7];
	public static final Doub[] j1pn = new Doub[5];
	public static final Doub[] j1pd = new Doub[5];
	public static final Doub[] j1qn = new Doub[5];
	public static final Doub[] j1qd = new Doub[5];
	public static final Doub[] y0r = new Doub[9];
	public static final Doub[] y0s = new Doub[9];
	public static final Doub[] y0pn = new Doub[5];
	public static final Doub[] y0pd = new Doub[5];
	public static final Doub[] y0qn = new Doub[5];
	public static final Doub[] y0qd = new Doub[5];
	public static final Doub[] y1r = new Doub[8];
	public static final Doub[] y1s = new Doub[8];
	public static final Doub[] y1pn = new Doub[5];
	public static final Doub[] y1pd = new Doub[5];
	public static final Doub[] y1qn = new Doub[5];
	public static final Doub[] y1qd = new Doub[5];
	public Doub nump = new Doub();
	public Doub denp = new Doub();
	public Doub numq = new Doub();
	public Doub denq = new Doub();
	public Doub y = new Doub();
	public Doub z = new Doub();
	public Doub ax = new Doub();
	public Doub xx = new Doub();

	public final Doub j0(Doub x)
	{
		if ((ax = Math.abs(x)) < 8.0)
		{
			rat(x, j0r, j0s, 6);
			return nump*(y-xj00)*(y-xj10)/denp;
		}
		else
		{
			asp(j0pn, j0pd, j0qn, j0qd, 1.);
			return Math.sqrt(twoopi/ax)*(Math.cos(xx)*nump/denp-z *Math.sin(xx)*numq/denq);
		}
	}

	public final Doub j1(Doub x)
	{
		if ((ax = Math.abs(x)) < 8.0)
		{
			rat(x, j1r, j1s, 6);
			return x *nump*(y-xj01)*(y-xj11)/denp;
		}
		else
		{
			asp(j1pn, j1pd, j1qn, j1qd, 3.);
			Doub ans = Math.sqrt(twoopi/ax)*(Math.cos(xx)*nump/denp-z *Math.sin(xx)*numq/denq);
			return x > 0.0 ? ans : -ans;
		}
	}

	public final Doub y0(Doub x)
	{
		if (x < 8.0)
		{
			Doub j0x = j0(x);
			rat(x, y0r, y0s, 8);
			return nump/denp+twoopi *j0x *Math.log(x);
		}
		else
		{
			ax = x;
			asp(y0pn, y0pd, y0qn, y0qd, 1.);
			return Math.sqrt(twoopi/x)*(Math.sin(xx)*nump/denp+z *Math.cos(xx)*numq/denq);
		}
	}

	public final Doub y1(Doub x)
	{
		if (x < 8.0)
		{
			Doub j1x = j1(x);
			rat(x, y1r, y1s, 7);
			return x *nump/denp+twoopi*(j1x *Math.log(x)-1.0/x);
		}
		else
		{
			ax = x;
			asp(y1pn, y1pd, y1qn, y1qd, 3.);
			return Math.sqrt(twoopi/x)*(Math.sin(xx)*nump/denp+z *Math.cos(xx)*numq/denq);
		}
	}

	public final Doub jn(Int n, Doub x)
	{
		final Doub ACC = 160.0;
		final Int IEXP = numeric_limits<Doub>.max_exponent/2;
		Bool jsum = new Bool();
		Int j = new Int();
		Int k = new Int();
		Int m = new Int();
		Doub ax = new Doub();
		Doub bj = new Doub();
		Doub bjm = new Doub();
		Doub bjp = new Doub();
		Doub dum = new Doub();
		Doub sum = new Doub();
		Doub tox = new Doub();
		Doub ans = new Doub();
		if (n == 0)
			return j0(x);
		if (n == 1)
			return j1(x);
		ax = Math.abs(x);
		if (ax *ax <= 8.0 *numeric_limits<Doub>.min())
			return 0.0;
		else if (ax > Doub(n))
		{
			tox = 2.0/ax;
			bjm = j0(ax);
			bj = j1(ax);
			for (j = 1;j<n;j++)
			{
				bjp = j *tox *bj-bjm;
				bjm = bj;
				bj = bjp;
			}
			ans = bj;
		}
		else
		{
			tox = 2.0/ax;
			m = 2*((n+(Int)(Math.sqrt(ACC *n)))/2);
			jsum = false;
			bjp = ans = sum = 0.0;
			bj = 1.0;
			for (j = m;j>0;j--)
			{
				bjm = j *tox *bj-bjp;
				bjp = bj;
				bj = bjm;
				dum = frexp(bj, k);
				if (k > IEXP)
				{
					bj = ldexp(bj,-IEXP);
					bjp = ldexp(bjp,-IEXP);
					ans = ldexp(ans,-IEXP);
					sum = ldexp(sum,-IEXP);
				}
				if (jsum != null)
					sum += bj;
				jsum = !jsum;
				if (j == n)
					ans = bjp;
			}
			sum = 2.0 *sum-bj;
			ans /= sum;
		}
		return x < 0.0 && (n & 1) ? -ans : ans;
	}

	public final Doub yn(Int n, Doub x)
	{
		Int j = new Int();
		Doub by = new Doub();
		Doub bym = new Doub();
		Doub byp = new Doub();
		Doub tox = new Doub();
		if (n == 0)
			return y0(x);
		if (n == 1)
			return y1(x);
		tox = 2.0/x;
		by = y1(x);
		bym = y0(x);
		for (j = 1;j<n;j++)
		{
			byp = j *tox *by-bym;
			bym = by;
			by = byp;
		}
		return by;
	}

	public final void rat(Doub x, Doub[] r, Doub[] s, Int n)
	{
		y = x *x;
		z = 64.0-y;
		nump = r[n];
		denp = s[n];
		for (Int i = n-1;i>=0;i--)
		{
			nump = nump *z+r[i];
			denp = denp *y+s[i];
		}
	}

	public final void asp(Doub[] pn, Doub[] pd, Doub[] qn, Doub[] qd, Doub fac)
	{
		z = 8.0/ax;
		y = z *z;
		xx = ax-fac *pio4;
		nump = pn[4];
		denp = pd[4];
		numq = qn[4];
		denq = qd[4];
		for (Int i = 3;i>=0;i--)
		{
			nump = nump *y+pn[i];
			denp = denp *y+pd[i];
			numq = numq *y+qn[i];
			denq = denq *y+qd[i];
		}
	}
}