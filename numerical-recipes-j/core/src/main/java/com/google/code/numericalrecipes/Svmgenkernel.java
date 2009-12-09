package com.google.code.numericalrecipes;
public abstract class Svmgenkernel
{
	public Int m = new Int();
	public Int kcalls = new Int();
	public MatDoub ker = new MatDoub();
	public VecDoub_I y;
	public MatDoub_I data;
	public Svmgenkernel(RefObject<VecDoub_I> yy, RefObject<MatDoub_I> ddata)
	{
		m = yy.argvalue.size();
		kcalls = 0;
		ker = new MatDoub(m,m);
		y = yy.argvalue;
		data = ddata.argvalue;
	}
	public abstract Doub kernel(Doub xi, Doub xj);
	public final Doub kernel(Int i, RefObject<Doub> xj)
	{
		return kernel(data[i][0], xj);
	}
	public final void fill()
	{
		Int i = new Int();
		Int j = new Int();
		for (i = 0;i<m;i++)
			for (j = 0;j<=i;j++)
			{
			ker[i][j] = ker[j][i] = kernel(data[i][0], data[j][0]);
		}
	}
}