package com.google.code.numericalrecipes;
public class GlobalMembersBanded
{
	public static void banmul(RefObject<MatDoub_I> a, Int m1, Int m2, RefObject<VecDoub_I> x, RefObject<VecDoub_O> b)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int tmploop = new Int();
		Int n = a.argvalue.nrows();
		for (i = 0;i<n;i++)
		{
			k = i-m1;
			tmploop = MIN(m1+m2+1,(Int)(n-k));
			b.argvalue[i]=0.0;
			for (j = MAX(0,-k);j<tmploop;j++)
				b.argvalue[i] += a.argvalue[i][j]*x.argvalue[j+k];
		}
	}
}