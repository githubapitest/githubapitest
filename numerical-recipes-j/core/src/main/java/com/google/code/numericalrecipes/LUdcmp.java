package com.google.code.numericalrecipes;
public class LUdcmp
{
	public Int n = new Int();
	public MatDoub lu = new MatDoub();
	public VecInt indx = new VecInt();
	public Doub d = new Doub();
	public LUdcmp(RefObject<MatDoub_I> a)
	{
		n = a.argvalue.nrows();
		lu = a.argvalue;
		aref = a.argvalue;
		indx = n;
		final Doub TINY = 1.0e-40;
		Int i = new Int();
		Int imax = new Int();
		Int j = new Int();
		Int k = new Int();
		Doub big = new Doub();
		Doub temp = new Doub();
		VecDoub vv = new VecDoub(n);
		d = 1.0;
		for (i = 0;i<n;i++)
		{
			big = 0.0;
			for (j = 0;j<n;j++)
				if ((temp = Math.abs(lu[i][j])) > big)
					big = temp;
			if (big == 0.0)
				throw("Singular matrix in LUdcmp");
			vv[i]=1.0/big;
		}
		for (k = 0;k<n;k++)
		{
			big = 0.0;
			for (i = k;i<n;i++)
			{
				temp = vv[i]*Math.abs(lu[i][k]);
				if (temp > big)
				{
					big = temp;
					imax = i;
				}
			}
			if (k != imax)
			{
				for (j = 0;j<n;j++)
				{
					temp = lu[imax][j];
					lu[imax][j]=lu[k][j];
					lu[k][j]=temp;
				}
				d = -d;
				vv[imax]=vv[k];
			}
			indx[k]=imax;
			if (lu[k][k] == 0.0)
				lu[k][k]=TINY;
			for (i = k+1;i<n;i++)
			{
				temp = lu[i][k] /= lu[k][k];
				for (j = k+1;j<n;j++)
					lu[i][j] -= temp *lu[k][j];
			}
		}
	}
	public final void solve(RefObject<VecDoub_I> b, RefObject<VecDoub_O> x)
	{
		Int i = new Int();
		Int ii = 0;
		Int ip = new Int();
		Int j = new Int();
		Doub sum = new Doub();
		if (b.argvalue.size() != n || x.argvalue.size() != n)
			throw("LUdcmp::solve bad sizes");
		for (i = 0;i<n;i++)
			x.argvalue[i] = b.argvalue[i];
		for (i = 0;i<n;i++)
		{
			ip = indx[i];
			sum = x.argvalue[ip];
			x.argvalue[ip]=x.argvalue[i];
			if (ii != 0)
				for (j = ii-1;j<i;j++)
					sum -= lu[i][j]*x.argvalue[j];
			else if (sum != 0.0)
				ii = i+1;
			x.argvalue[i]=sum;
		}
		for (i = n-1;i>=0;i--)
		{
			sum = x.argvalue[i];
			for (j = i+1;j<n;j++)
				sum -= lu[i][j]*x.argvalue[j];
			x.argvalue[i]=sum/lu[i][i];
		}
	}
	public final void solve(RefObject<MatDoub_I> b, RefObject<MatDoub_O> x)
	{
		int i;
		int j;
		int m = b.argvalue.ncols();
		if (b.argvalue.nrows() != n || x.argvalue.nrows() != n || b.argvalue.ncols() != x.argvalue.ncols())
			throw("LUdcmp::solve bad sizes");
		VecDoub xx = new VecDoub(n);
		for (j = 0;j<m;j++)
		{
			for (i = 0;i<n;i++)
				xx[i] = b.argvalue[i][j];
			RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(xx);
			RefObject<VecDoub_O> tempRefObject2 = new RefObject<VecDoub_O>(xx);
			solve(tempRefObject, tempRefObject2);
			xx = tempRefObject.argvalue;
			xx = tempRefObject2.argvalue;
			for (i = 0;i<n;i++)
				x.argvalue[i][j] = xx[i];
		}
	}
	public final void inverse(RefObject<MatDoub_O> ainv)
	{
		Int i = new Int();
		Int j = new Int();
		ainv.argvalue.resize(n,n);
		for (i = 0;i<n;i++)
		{
			for (j = 0;j<n;j++)
				ainv.argvalue[i][j] = 0.;
			ainv.argvalue[i][i] = 1.;
		}
		RefObject<MatDoub_I> tempRefObject = new RefObject<MatDoub_I>(ainv);
		RefObject<MatDoub_O> tempRefObject2 = new RefObject<MatDoub_O>(ainv);
		solve(tempRefObject, tempRefObject2);
		ainv.argvalue = tempRefObject.argvalue;
		ainv.argvalue = tempRefObject2.argvalue;
	}
	public final Doub det()
	{
		Doub dd = d;
		for (Int i = 0;i<n;i++)
			dd *= lu[i][i];
		return dd;
	}
	public final void mprove(RefObject<VecDoub_I> b, RefObject<VecDoub_IO> x)
	{
		Int i = new Int();
		Int j = new Int();
		VecDoub r = new VecDoub(n);
		for (i = 0;i<n;i++)
		{
			Ldoub sdp = -b.argvalue[i];
			for (j = 0;j<n;j++)
				sdp += (Ldoub)aref[i][j] * (Ldoub)x.argvalue[j];
			r[i]=sdp;
		}
		RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(r);
		RefObject<VecDoub_O> tempRefObject2 = new RefObject<VecDoub_O>(r);
		solve(tempRefObject, tempRefObject2);
		r = tempRefObject.argvalue;
		r = tempRefObject2.argvalue;
		for (i = 0;i<n;i++)
			x.argvalue[i] -= r[i];
	}
	public MatDoub_I aref;
}