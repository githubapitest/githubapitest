package com.google.code.numericalrecipes;
public class Bandec
{
	public Int n = new Int();
	public Int m1 = new Int();
	public Int m2 = new Int();
	public MatDoub au = new MatDoub();
	public MatDoub al = new MatDoub();
	public VecInt indx = new VecInt();
	public Doub d = new Doub();
	public Bandec(RefObject<MatDoub_I> a, Int mm1, Int mm2)
	{
		n = a.argvalue.nrows();
		au = a.argvalue;
		m1 = mm1;
		m2 = mm2;
		al = new MatDoub(n,m1);
		indx = n;
		final Doub TINY = 1.0e-40;
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int l = new Int();
		Int mm = new Int();
		Doub dum = new Doub();
		mm = m1+m2+1;
		l = m1;
		for (i = 0;i<m1;i++)
		{
			for (j = m1-i;j<mm;j++)
				au[i][j-l]=au[i][j];
			l--;
			for (j = mm-l-1;j<mm;j++)
				au[i][j]=0.0;
		}
		d = 1.0;
		l = m1;
		for (k = 0;k<n;k++)
		{
			dum = au[k][0];
			i = k;
			if (l<n)
				l++;
			for (j = k+1;j<l;j++)
			{
				if (Math.abs(au[j][0]) > Math.abs(dum))
				{
					dum = au[j][0];
					i = j;
				}
			}
			indx[k]=i+1;
			if (dum == 0.0)
				au[k][0]=TINY;
			if (i != k)
			{
				d = -d;
				for (j = 0;j<mm;j++)
					SWAP(au[k][j],au[i][j]);
			}
			for (i = k+1;i<l;i++)
			{
				dum = au[i][0]/au[k][0];
				al[k][i-k-1]=dum;
				for (j = 1;j<mm;j++)
					au[i][j-1]=au[i][j]-dum *au[k][j];
				au[i][mm-1]=0.0;
			}
		}
	}
	public final void solve(RefObject<VecDoub_I> b, RefObject<VecDoub_O> x)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int l = new Int();
		Int mm = new Int();
		Doub dum = new Doub();
		mm = m1+m2+1;
		l = m1;
		for (k = 0;k<n;k++)
			x.argvalue[k] = b.argvalue[k];
		for (k = 0;k<n;k++)
		{
			j = indx[k]-1;
			if (j!=k)
				SWAP(x.argvalue[k],x.argvalue[j]);
			if (l<n)
				l++;
			for (j = k+1;j<l;j++)
				x.argvalue[j] -= al[k][j-k-1]*x.argvalue[k];
		}
		l = 1;
		for (i = n-1;i>=0;i--)
		{
			dum = x.argvalue[i];
			for (k = 1;k<l;k++)
				dum -= au[i][k]*x.argvalue[k+i];
			x.argvalue[i]=dum/au[i][0];
			if (l<mm)
				l++;
		}
	}
	public final Doub det()
	{
		Doub dd = d;
		for (int i = 0;i<n;i++)
			dd *= au[i][0];
		return dd;
	}
}