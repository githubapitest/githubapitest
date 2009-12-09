package com.google.code.numericalrecipes;
public class Bessik
{
	public static final Doub[] i0p = new Doub[14];
	public static final Doub[] i0q = new Doub[5];
	public static final Doub[] i0pp = new Doub[5];
	public static final Doub[] i0qq = new Doub[6];
	public static final Doub[] i1p = new Doub[14];
	public static final Doub[] i1q = new Doub[5];
	public static final Doub[] i1pp = new Doub[5];
	public static final Doub[] i1qq = new Doub[6];
	public static final Doub[] k0pi = new Doub[5];
	public static final Doub[] k0qi = new Doub[3];
	public static final Doub[] k0p = new Doub[5];
	public static final Doub[] k0q = new Doub[3];
	public static final Doub[] k0pp = new Doub[8];
	public static final Doub[] k0qq = new Doub[8];
	public static final Doub[] k1pi = new Doub[5];
	public static final Doub[] k1qi = new Doub[3];
	public static final Doub[] k1p = new Doub[5];
	public static final Doub[] k1q = new Doub[3];
	public static final Doub[] k1pp = new Doub[8];
	public static final Doub[] k1qq = new Doub[8];
	public Doub y = new Doub();
	public Doub z = new Doub();
	public Doub ax = new Doub();
	public Doub term = new Doub();

	public final Doub i0(Doub x)
	{
		if ((ax = Math.abs(x)) < 15.0)
		{
			y = x *x;
			return poly(i0p, 13, y)/poly(i0q, 4, 225.-y);
		}
		else
		{
			z = 1.0-15.0/ax;
			return Math.exp(ax)*poly(i0pp, 4, z)/(poly(i0qq, 5, z)*Math.sqrt(ax));
		}
	}

	public final Doub i1(Doub x)
	{
		if ((ax = Math.abs(x)) < 15.0)
		{
			y = x *x;
			return x *poly(i1p, 13, y)/poly(i1q, 4, 225.-y);
		}
		else
		{
			z = 1.0-15.0/ax;
			Doub ans = Math.exp(ax)*poly(i1pp, 4, z)/(poly(i1qq, 5, z)*Math.sqrt(ax));
			return x > 0.0 ? ans : -ans;
		}
	}

	public final Doub k0(Doub x)
	{
		if (x <= 1.0)
		{
			z = x *x;
			term = poly(k0pi, 4, z)*Math.log(x)/poly(k0qi, 2, 1.-z);
			return poly(k0p, 4, z)/poly(k0q, 2, 1.-z)-term;
		}
		else
		{
			z = 1.0/x;
			return Math.exp(-x)*poly(k0pp, 7, z)/(poly(k0qq, 7, z)*Math.sqrt(x));
		}
	}

	public final Doub k1(Doub x)
	{
		if (x <= 1.0)
		{
			z = x *x;
			term = poly(k1pi, 4, z)*Math.log(x)/poly(k1qi, 2, 1.-z);
			return x*(poly(k1p, 4, z)/poly(k1q, 2, 1.-z)+term)+1./x;
		}
		else
		{
			z = 1.0/x;
			return Math.exp(-x)*poly(k1pp, 7, z)/(poly(k1qq, 7, z)*Math.sqrt(x));
		}
	}

	public final Doub in(Int n, Doub x)
	{
		final Doub ACC = 200.0;
		final Int IEXP = numeric_limits<Doub>.max_exponent/2;
		Int j = new Int();
		Int k = new Int();
		Doub bi = new Doub();
		Doub bim = new Doub();
		Doub bip = new Doub();
		Doub dum = new Doub();
		Doub tox = new Doub();
		Doub ans = new Doub();
		if (n == 0)
			return i0(x);
		if (n == 1)
			return i1(x);
		if (x *x <= 8.0 *numeric_limits<Doub>.min())
			return 0.0;
		else
		{
			tox = 2.0/Math.abs(x);
			bip = ans = 0.0;
			bi = 1.0;
			for (j = 2*(n+(Int)(Math.sqrt(ACC *n)));j>0;j--)
			{
				bim = bip+j *tox *bi;
				bip = bi;
				bi = bim;
				dum = frexp(bi, k);
				if (k > IEXP)
				{
					ans = ldexp(ans,-IEXP);
					bi = ldexp(bi,-IEXP);
					bip = ldexp(bip,-IEXP);
				}
				if (j == n)
					ans = bip;
			}
			ans *= i0(x)/bi;
			return x < 0.0 && (n & 1) ? -ans : ans;
		}
	}

	public final Doub kn(Int n, Doub x)
	{
		Int j = new Int();
		Doub bk = new Doub();
		Doub bkm = new Doub();
		Doub bkp = new Doub();
		Doub tox = new Doub();
		if (n == 0)
			return k0(x);
		if (n == 1)
			return k1(x);
		tox = 2.0/x;
		bkm = k0(x);
		bk = k1(x);
		for (j = 1;j<n;j++)
		{
			bkp = bkm+j *tox *bk;
			bkm = bk;
			bk = bkp;
		}
		return bk;
	}

	public final Doub poly(Doub[] cof, Int n, Doub x)
	{
		Doub ans = cof[n];
		for (Int i = n-1;i>=0;i--)
			ans = ans *x+cof[i];
		return ans;
	}
}