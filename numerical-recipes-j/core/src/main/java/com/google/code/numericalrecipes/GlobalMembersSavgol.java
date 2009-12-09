package com.google.code.numericalrecipes;
public class GlobalMembersSavgol
{
	public static void savgol(RefObject<VecDoub_O> c, Int np, Int nl, Int nr, Int ld, Int m)
	{
		Int j = new Int();
		Int k = new Int();
		Int imj = new Int();
		Int ipj = new Int();
		Int kk = new Int();
		Int mm = new Int();
		Doub fac = new Doub();
		Doub sum = new Doub();
		if (np < nl+nr+1 || nl < 0 || nr < 0 || ld > m || nl+nr < m)
			throw("bad args in savgol");
		VecInt indx = new VecInt(m+1);
		MatDoub a = new MatDoub(m+1,m+1);
		VecDoub b = new VecDoub(m+1);
		for (ipj = 0;ipj<=(m << 1);ipj++)
		{
			sum = (ipj != null ? 0.0 : 1.0);
			for (k = 1;k<=nr;k++)
				sum += Math.pow(Doub(k),Doub(ipj));
			for (k = 1;k<=nl;k++)
				sum += Math.pow(Doub(-k),Doub(ipj));
			mm = MIN(ipj,2 *m-ipj);
			for (imj = -mm;imj<=mm;imj+=2)
				a[(ipj+imj)/2][(ipj-imj)/2]=sum;
		}
		LUdcmp alud = new LUdcmp(a);
		for (j = 0;j<m+1;j++)
			b[j]=0.0;
		b[ld]=1.0;
		alud.solve(b,b);
		for (kk = 0;kk<np;kk++)
			c.argvalue[kk]=0.0;
		for (k = -nl;k<=nr;k++)
		{
			sum = b[0];
			fac = 1.0;
			for (mm = 1;mm<=m;mm++)
				sum += b[mm]*(fac *= k);
			kk = (np-k) % np;
			c.argvalue[kk]=sum;
		}
	}
}