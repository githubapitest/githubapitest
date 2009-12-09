package com.google.code.numericalrecipes;
public class Cholesky
{
	public Int n = new Int();
	public MatDoub el = new MatDoub();
	public Cholesky(RefObject<MatDoub_I> a)
	{
		n = a.argvalue.nrows();
		el = a.argvalue;
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		VecDoub tmp = new VecDoub();
		Doub sum = new Doub();
		if (el.ncols() != n)
			throw("need square matrix");
		for (i = 0;i<n;i++)
		{
			for (j = i;j<n;j++)
			{
				for (sum = el[i][j],k = i-1;k>=0;k--)
					sum -= el[i][k]*el[j][k];
				if (i == j)
				{
					if (sum <= 0.0)
						throw("Cholesky failed");
					el[i][i]=Math.sqrt(sum);
				}
				else
					el[j][i]=sum/el[i][i];
			}
		}
		for (i = 0;i<n;i++)
			for (j = 0;j<i;j++)
				el[j][i] = 0.;
	}
	public final void solve(RefObject<VecDoub_I> b, RefObject<VecDoub_O> x)
	{
		Int i = new Int();
		Int k = new Int();
		Doub sum = new Doub();
		if (b.argvalue.size() != n || x.argvalue.size() != n)
			throw("bad lengths in Cholesky");
		for (i = 0;i<n;i++)
		{
			for (sum = b.argvalue[i],k = i-1;k>=0;k--)
				sum -= el[i][k]*x.argvalue[k];
			x.argvalue[i]=sum/el[i][i];
		}
		for (i = n-1;i>=0;i--)
		{
			for (sum = x.argvalue[i],k = i+1;k<n;k++)
				sum -= el[k][i]*x.argvalue[k];
			x.argvalue[i]=sum/el[i][i];
		}
	}
	public final void elmult(RefObject<VecDoub_I> y, RefObject<VecDoub_O> b)
	{
		Int i = new Int();
		Int j = new Int();
		if (b.argvalue.size() != n || y.argvalue.size() != n)
			throw("bad lengths");
		for (i = 0;i<n;i++)
		{
			b.argvalue[i] = 0.;
			for (j = 0;j<=i;j++)
				b.argvalue[i] += el[i][j]*y.argvalue[j];
		}
	}
	public final void elsolve(RefObject<VecDoub_I> b, RefObject<VecDoub_O> y)
	{
		Int i = new Int();
		Int j = new Int();
		Doub sum = new Doub();
		if (b.argvalue.size() != n || y.argvalue.size() != n)
			throw("bad lengths");
		for (i = 0;i<n;i++)
		{
			for (sum = b.argvalue[i],j = 0; j<i; j++)
				sum -= el[i][j]*y.argvalue[j];
			y.argvalue[i] = sum/el[i][i];
		}
	}
	public final void inverse(RefObject<MatDoub_O> ainv)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Doub sum = new Doub();
		ainv.argvalue.resize(n,n);
		for (i = 0;i<n;i++)
			for (j = 0;j<=i;j++)
			{
			sum = (i == j != null? 1.: 0.);
			for (k = i-1;k>=j;k--)
				sum -= el[i][k]*ainv.argvalue[j][k];
			ainv.argvalue[j][i]= sum/el[i][i];
		}
		for (i = n-1;i>=0;i--)
			for (j = 0;j<=i;j++)
			{
			sum = (i<j != null? 0.: ainv.argvalue[j][i]);
			for (k = i+1;k<n;k++)
				sum -= el[k][i]*ainv.argvalue[j][k];
			ainv.argvalue[i][j] = ainv.argvalue[j][i] = sum/el[i][i];
		}
	}
	public final Doub logdet()
	{
		Doub sum = 0.;
		for (Int i = 0; i<n; i++)
			sum += Math.log(el[i][i]);
		return 2.*sum;
	}
}