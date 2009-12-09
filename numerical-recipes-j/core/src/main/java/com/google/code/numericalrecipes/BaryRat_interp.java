package com.google.code.numericalrecipes;
public class BaryRat_interp implements Base_interp
{
	public VecDoub w = new VecDoub();
	public Int d = new Int();
	public BaryRat_interp(RefObject<VecDoub_I> xv, RefObject<VecDoub_I> yv, Int dd)
	{
		super(xv, yv.argvalue[0], xv.argvalue.size());
		w = n;
		d = dd;
		if (n<=d)
			throw("d too large for number of points in BaryRat_interp");
		for (Int k = 0;k<n;k++)
		{
			Int imin = MAX(k-d,0);
			Int imax = k >= n-d != null ? n-d-1 : k;
			Doub temp = imin & 1 ? -1.0 : 1.0;
			Doub sum = 0.0;
			for (Int i = imin;i<=imax;i++)
			{
				Int jmax = MIN(i+d,n-1);
				Doub term = 1.0;
				for (Int j = i;j<=jmax;j++)
				{
					if (j == k)
						continue;
					term *= (xx[k]-xx[j]);
				}
				term = temp/term;
				temp = -temp;
				sum += term;
			}
			w[k]=sum;
		}
	}
	public final Doub rawinterp(Int jl, Doub x)
	{
		Doub num = 0;
		Doub den = 0;
		for (Int i = 0;i<n;i++)
		{
			Doub h = x-xx[i];
			if (h == 0.0)
			{
				return yy[i];
			}
			else
			{
				Doub temp = w[i]/h;
				num += temp *yy[i];
				den += temp;
			}
		}
		return num/den;
	}
	public final Doub interp(Doub x)
	{
		return rawinterp(1, x);
	}
}