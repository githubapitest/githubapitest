package com.google.code.numericalrecipes;
public class GlobalMembersGaussj
{
	public static void gaussj(RefObject<MatDoub_IO> a, RefObject<MatDoub_IO> b)
	{
		Int i = new Int();
		Int icol = new Int();
		Int irow = new Int();
		Int j = new Int();
		Int k = new Int();
		Int l = new Int();
		Int ll = new Int();
		Int n = a.argvalue.nrows();
		Int m = b.argvalue.ncols();
		Doub big = new Doub();
		Doub dum = new Doub();
		Doub pivinv = new Doub();
		VecInt indxc = new VecInt(n);
		VecInt indxr = new VecInt(n);
		VecInt ipiv = new VecInt(n);
		for (j = 0;j<n;j++)
			ipiv[j]=0;
		for (i = 0;i<n;i++)
		{
			big = 0.0;
			for (j = 0;j<n;j++)
				if (ipiv[j] != 1)
					for (k = 0;k<n;k++)
					{
						if (ipiv[k] == 0)
						{
							if (Math.abs(a.argvalue[j][k]) >= big)
							{
								big = Math.abs(a.argvalue[j][k]);
								irow = j;
								icol = k;
							}
						}
					}
			++(ipiv[icol]);
			if (irow != icol)
			{
				for (l = 0;l<n;l++)
					SWAP(a.argvalue[irow][l],a.argvalue[icol][l]);
				for (l = 0;l<m;l++)
					SWAP(b.argvalue[irow][l],b.argvalue[icol][l]);
			}
			indxr[i]=irow;
			indxc[i]=icol;
			if (a.argvalue[icol][icol] == 0.0)
				throw("gaussj: Singular Matrix");
			pivinv = 1.0/a.argvalue[icol][icol];
			a.argvalue[icol][icol]=1.0;
			for (l = 0;l<n;l++)
				a.argvalue[icol][l] *= pivinv;
			for (l = 0;l<m;l++)
				b.argvalue[icol][l] *= pivinv;
			for (ll = 0;ll<n;ll++)
				if (ll != icol)
				{
					dum = a.argvalue[ll][icol];
					a.argvalue[ll][icol]=0.0;
					for (l = 0;l<n;l++)
						a.argvalue[ll][l] -= a.argvalue[icol][l]*dum;
					for (l = 0;l<m;l++)
						b.argvalue[ll][l] -= b.argvalue[icol][l]*dum;
				}
		}
		for (l = n-1;l>=0;l--)
		{
			if (indxr[l] != indxc[l])
				for (k = 0;k<n;k++)
					SWAP(a.argvalue[k][indxr[l]],a.argvalue[k][indxc[l]]);
		}
	}

	public static void gaussj(RefObject<MatDoub_IO> a)
	{
		MatDoub b = new MatDoub(a.argvalue.nrows(),0);
	RefObject<MatDoub_IO> tempRefObject = new RefObject<MatDoub_IO>(b);
		gaussj(a, tempRefObject);
		b = tempRefObject.argvalue;
	}
}