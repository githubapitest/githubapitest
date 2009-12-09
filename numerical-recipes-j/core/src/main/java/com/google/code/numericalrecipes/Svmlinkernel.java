package com.google.code.numericalrecipes;
public class Svmlinkernel implements Svmgenkernel
{
	public Int n = new Int();
	public VecDoub mu = new VecDoub();
	public Svmlinkernel(RefObject<MatDoub_I> ddata, RefObject<VecDoub_I> yy)
	{
		super(yy, ddata);
		n = data.ncols();
		mu = n;
		Int i = new Int();
		Int j = new Int();
		for (j = 0;j<n;j++)
			mu[j] = 0.;
		for (i = 0;i<m;i++)
			for (j = 0;j<n;j++)
				mu[j] += data[i][j];
		for (j = 0;j<n;j++)
			mu[j] /= m;
		fill();
	}
	public final Doub kernel(Doub[] xi, Doub[] xj)
	{
		Doub dott = 0.;
		for (Int k = 0; k<n; k++)
			dott += (xi[k]-mu[k])*(xj[k]-mu[k]);
		return dott;
	}
}