package com.google.code.numericalrecipes;
public class GlobalMembersPoly
{
	public static void ddpoly(RefObject<VecDoub_I> c, Doub x, RefObject<VecDoub_O> pd)
	{
		Int nnd = new Int();
		Int j = new Int();
		Int i = new Int();
		Int nc = c.argvalue.size()-1;
		Int nd = pd.argvalue.size()-1;
		Doub cnst = 1.0;
		pd.argvalue[0]=c.argvalue[nc];
		for (j = 1;j<nd+1;j++)
			pd.argvalue[j]=0.0;
		for (i = nc-1;i>=0;i--)
		{
			nnd = (nd < (nc-i) ? nd : nc-i);
			for (j = nnd;j>0;j--)
				pd.argvalue[j]=pd.argvalue[j]*x+pd.argvalue[j-1];
			pd.argvalue[0]=pd.argvalue[0]*x+c.argvalue[i];
		}
		for (i = 2;i<nd+1;i++)
		{
			cnst *= i;
			pd.argvalue[i] *= cnst;
		}
	}
	public static void poldiv(RefObject<VecDoub_I> u, RefObject<VecDoub_I> v, RefObject<VecDoub_O> q, RefObject<VecDoub_O> r)
	{
		Int k = new Int();
		Int j = new Int();
		Int n = u.argvalue.size()-1;
		Int nv = v.argvalue.size()-1;
		while (nv >= 0 && v.argvalue[nv] == 0.)
			nv--;
		if (nv < 0)
			throw("poldiv divide by zero polynomial");
		r.argvalue = u.argvalue;
		q.argvalue.assign(u.argvalue.size(),0.);
		for (k = n-nv;k>=0;k--)
		{
			q.argvalue[k]=r.argvalue[nv+k]/v.argvalue[nv];
			for (j = nv+k-1;j>=k;j--)
				r.argvalue[j] -= q.argvalue[k]*v.argvalue[j-k];
		}
		for (j = nv;j<=n;j++)
			r.argvalue[j]=0.0;
	}
}