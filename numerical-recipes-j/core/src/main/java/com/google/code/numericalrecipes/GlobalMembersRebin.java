package com.google.code.numericalrecipes;
public class GlobalMembersRebin
{
	public static void rebin(Doub rc, Int nd, RefObject<VecDoub_I> r, RefObject<VecDoub_O> xin, RefObject<MatDoub_IO> xi, Int j)
	{
		Int i = new Int();
		Int k = 0;
		Doub dr = 0.0;
		Doub xn = 0.0;
		Doub xo = 0.0;

		for (i = 0;i<nd-1;i++)
		{
			while (rc > dr)
				dr += r.argvalue[(++k)-1];
			if (k > 1)
				xo = xi.argvalue[j][k-2];
			xn = xi.argvalue[j][k-1];
			dr -= rc;
			xin.argvalue[i]=xn-(xn-xo)*dr/r.argvalue[k-1];
		}
		for (i = 0;i<nd-1;i++)
			xi.argvalue[j][i]=xin.argvalue[i];
		xi.argvalue[j][nd-1]=1.0;
	}
}