package com.google.code.numericalrecipes;
public class KSdist
{
	public final Doub pks(Doub z)
	{
		if (z < 0.)
			throw("bad z in KSdist");
		if (z == 0.)
			return 0.;
		if (z < 1.18)
		{
			Doub y = Math.exp(-1.23370055013616983/SQR(z));
			return 2.25675833419102515 *Math.sqrt(-Math.log(y)) *(y + Math.pow(y,9) + Math.pow(y,25) + Math.pow(y,49));
		}
		else
		{
			Doub x = Math.exp(-2.*SQR(z));
			return 1.- 2.*(x - Math.pow(x,4) + Math.pow(x,9));
		}
	}
	public final Doub qks(Doub z)
	{
		if (z < 0.)
			throw("bad z in KSdist");
		if (z == 0.)
			return 1.;
		if (z < 1.18)
			return 1.-pks(z);
		Doub x = Math.exp(-2.*SQR(z));
		return 2.*(x - Math.pow(x,4) + Math.pow(x,9));
	}
	public final Doub invqks(Doub q)
	{
		Doub y = new Doub();
		Doub logy = new Doub();
		Doub yp = new Doub();
		Doub x = new Doub();
		Doub xp = new Doub();
		Doub f = new Doub();
		Doub ff = new Doub();
		Doub u = new Doub();
		Doub t = new Doub();
		if (q <= 0.|| q > 1.)
			throw("bad q in KSdist");
		if (q == 1.)
			return 0.;
		if (q > 0.3)
		{
			f = -0.392699081698724155 *SQR(1.-q);
			y = GlobalMembersKsdist.invxlogx(f);
			do
			{
				yp = y;
				logy = Math.log(y);
				ff = f/SQR(1.+ Math.pow(y,4)+ Math.pow(y,12));
				u = (y *logy-ff)/(1.+logy);
				y = y - (t = u/MAX(0.5,1.-0.5 *u/(y*(1.+logy))));
			} while (Math.abs(t/y)>1.e-15);
			return 1.57079632679489662/Math.sqrt(-Math.log(y));
		}
		else
		{
			x = 0.03;
			do
			{
				xp = x;
				x = 0.5 *q+Math.pow(x,4)-Math.pow(x,9);
				if (x > 0.06)
					x += Math.pow(x,16)-Math.pow(x,25);
			} while (Math.abs((xp-x)/x)>1.e-15);
			return Math.sqrt(-0.5 *Math.log(x));
		}
	}
	public final Doub invpks(Doub p)
	{
		return invqks(1.-p);
	}
}