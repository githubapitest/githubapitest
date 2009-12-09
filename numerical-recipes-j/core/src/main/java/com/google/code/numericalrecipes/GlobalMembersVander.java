package com.google.code.numericalrecipes;
public class GlobalMembersVander
{
	public static void vander(RefObject<VecDoub_I> x, RefObject<VecDoub_O> w, RefObject<VecDoub_I> q)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int n = q.argvalue.size();
		Doub b = new Doub();
		Doub s = new Doub();
		Doub t = new Doub();
		Doub xx = new Doub();
		VecDoub c = new VecDoub(n);
		if (n == 1)
			w.argvalue[0]=q.argvalue[0];
		else
		{
			for (i = 0;i<n;i++)
				c[i]=0.0;
			c[n-1] = -x.argvalue[0];
			for (i = 1;i<n;i++)
			{
				xx = -x.argvalue[i];
				for (j = (n-1-i);j<(n-1);j++)
					c[j] += xx *c[j+1];
				c[n-1] += xx;
			}
			for (i = 0;i<n;i++)
			{
				xx = x.argvalue[i];
				t = b = 1.0;
				s = q.argvalue[n-1];
				for (k = n-1;k>0;k--)
				{
					b = c[k]+xx *b;
					s += q.argvalue[k-1]*b;
					t = xx *t+b;
				}
				w.argvalue[i]=s/t;
			}
		}
	}
}