package com.google.code.numericalrecipes;
public class GlobalMembersPade
{
	public static Ratfn pade(RefObject<VecDoub_I> cof)
	{
		final Doub BIG = 1.0e99;
		Int j = new Int();
		Int k = new Int();
		Int n = (cof.argvalue.size()-1)/2;
		Doub sum = new Doub();
		MatDoub q = new MatDoub(n,n);
		MatDoub qlu = new MatDoub(n,n);
		VecInt indx = new VecInt(n);
		VecDoub x = new VecDoub(n);
		VecDoub y = new VecDoub(n);
		VecDoub num = new VecDoub(n+1);
		VecDoub denom = new VecDoub(n+1);
		for (j = 0;j<n;j++)
		{
			y[j]=cof.argvalue[n+j+1];
			for (k = 0;k<n;k++)
				q[j][k]=cof.argvalue[j-k+n];
		}
		LUdcmp lu = new LUdcmp(q);
		lu.solve(y,x);
		for (j = 0;j<4;j++)
			lu.mprove(y,x);
		for (k = 0;k<n;k++)
		{
			for (sum = cof.argvalue[k+1],j = 0;j<=k;j++)
				sum -= x[j]*cof.argvalue[k-j];
			y[k]=sum;
		}
		num[0] = cof.argvalue[0];
		denom[0] = 1.;
		for (j = 0;j<n;j++)
		{
			num[j+1]=y[j];
			denom[j+1] = -x[j];
		}
		return Ratfn(num,denom);
	}
}