package com.google.code.numericalrecipes;
public class GlobalMembersZrhqr
{
	public static void zrhqr(RefObject<VecDoub_I> a, RefObject<VecComplex_O> rt)
	{
		Int m = a.argvalue.size()-1;
		MatDoub hess = new MatDoub(m,m);
		for (Int k = 0;k<m;k++)
		{
			hess[0][k] = -a.argvalue[m-k-1]/a.argvalue[m];
			for (Int j = 1;j<m;j++)
				hess[j][k]=0.0;
			if (k != m-1)
				hess[k+1][k]=1.0;
		}
		Unsymmeig h = new Unsymmeig(hess, false, true);
		for (Int j = 0;j<m;j++)
			rt.argvalue[j]=h.wri[j];
	}
}