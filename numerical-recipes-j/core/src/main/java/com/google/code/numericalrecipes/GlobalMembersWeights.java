package com.google.code.numericalrecipes;
public class GlobalMembersWeights
{
	public static void weights(Doub z, RefObject<VecDoub_I> x, RefObject<MatDoub_O> c)
	{
		Int n = c.argvalue.nrows()-1;
		Int m = c.argvalue.ncols()-1;
		Doub c1 = 1.0;
		Doub c4 = x.argvalue[0]-z;
		for (Int k = 0;k<=m;k++)
			for (Int j = 0;j<=n;j++)
				c.argvalue[j][k]=0.0;
		c.argvalue[0][0]=1.0;
		for (Int i = 1;i<=n;i++)
		{
			Int mn = MIN(i,m);
			Doub c2 = 1.0;
			Doub c5 = c4;
			c4 = x.argvalue[i]-z;
			for (Int j = 0;j<i;j++)
			{
				Doub c3 = x.argvalue[i]-x.argvalue[j];
				c2 = c2 *c3;
				if (j == i-1)
				{
					for (Int k = mn;k>0;k--)
						c.argvalue[i][k]=c1*(k *c.argvalue[i-1][k-1]-c5 *c.argvalue[i-1][k])/c2;
					c.argvalue[i][0]=-c1 *c5 *c.argvalue[i-1][0]/c2;
				}
				for (Int k = mn;k>0;k--)
					c.argvalue[j][k]=(c4 *c.argvalue[j][k]-k *c.argvalue[j][k-1])/c3;
				c.argvalue[j][0]=c4 *c.argvalue[j][0]/c3;
			}
			c1 = c2;
		}
	}
}