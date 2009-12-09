package com.google.code.numericalrecipes;
public class GlobalMembersPcshft
{
	public static void pcshft(Doub a, Doub b, RefObject<VecDoub_IO> d)
	{
		Int k = new Int();
		Int j = new Int();
		Int n = d.argvalue.size();
		Doub cnst = 2.0/(b-a);
		Doub fac = cnst;
		for (j = 1;j<n;j++)
		{
			d.argvalue[j] *= fac;
			fac *= cnst;
		}
		cnst = 0.5*(a+b);
		for (j = 0;j<=n-2;j++)
			for (k = n-2;k>=j;k--)
				d.argvalue[k] -= cnst *d.argvalue[k+1];
	}
	public static void ipcshft(Doub a, Doub b, RefObject<VecDoub_IO> d)
	{
		pcshft(-(2.+b+a)/(b-a), (2.-b-a)/(b-a), d);
	}
}