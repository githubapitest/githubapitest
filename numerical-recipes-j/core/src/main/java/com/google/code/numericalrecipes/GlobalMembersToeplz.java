package com.google.code.numericalrecipes;
public class GlobalMembersToeplz
{
	public static void toeplz(RefObject<VecDoub_I> r, RefObject<VecDoub_O> x, RefObject<VecDoub_I> y)
	{
		Int j = new Int();
		Int k = new Int();
		Int m = new Int();
		Int m1 = new Int();
		Int m2 = new Int();
		Int n1 = new Int();
		Int n = y.argvalue.size();
		Doub pp = new Doub();
		Doub pt1 = new Doub();
		Doub pt2 = new Doub();
		Doub qq = new Doub();
		Doub qt1 = new Doub();
		Doub qt2 = new Doub();
		Doub sd = new Doub();
		Doub sgd = new Doub();
		Doub sgn = new Doub();
		Doub shn = new Doub();
		Doub sxn = new Doub();
		n1 = n-1;
		if (r.argvalue[n1] == 0.0)
			throw("toeplz-1 singular principal minor");
		x.argvalue[0]=y.argvalue[0]/r.argvalue[n1];
		if (n1 == 0)
			return;
		VecDoub g = new VecDoub(n1);
		VecDoub h = new VecDoub(n1);
		g[0]=r.argvalue[n1-1]/r.argvalue[n1];
		h[0]=r.argvalue[n1+1]/r.argvalue[n1];
		for (m = 0;m<n;m++)
		{
			m1 = m+1;
			sxn = -y.argvalue[m1];
			sd = -r.argvalue[n1];
			for (j = 0;j<m+1;j++)
			{
				sxn += r.argvalue[n1+m1-j]*x.argvalue[j];
				sd += r.argvalue[n1+m1-j]*g[m-j];
			}
			if (sd == 0.0)
				throw("toeplz-2 singular principal minor");
			x.argvalue[m1]=sxn/sd;
			for (j = 0;j<m+1;j++)
				x.argvalue[j] -= x.argvalue[m1]*g[m-j];
			if (m1 == n1)
				return;
			sgn = -r.argvalue[n1-m1-1];
			shn = -r.argvalue[n1+m1+1];
			sgd = -r.argvalue[n1];
			for (j = 0;j<m+1;j++)
			{
				sgn += r.argvalue[n1+j-m1]*g[j];
				shn += r.argvalue[n1+m1-j]*h[j];
				sgd += r.argvalue[n1+j-m1]*h[m-j];
			}
			if (sgd == 0.0)
				throw("toeplz-3 singular principal minor");
			g[m1]=sgn/sgd;
			h[m1]=shn/sd;
			k = m;
			m2 = (m+2) >> 1;
			pp = g[m1];
			qq = h[m1];
			for (j = 0;j<m2;j++)
			{
				pt1 = g[j];
				pt2 = g[k];
				qt1 = h[j];
				qt2 = h[k];
				g[j]=pt1-pp *qt2;
				g[k]=pt2-pp *qt1;
				h[j]=qt1-qq *pt2;
				h[k--]=qt2-qq *pt1;
			}
		}
		throw("toeplz - should not arrive here!");
	}
}