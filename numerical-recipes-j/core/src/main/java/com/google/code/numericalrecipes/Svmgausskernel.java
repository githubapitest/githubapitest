package com.google.code.numericalrecipes;
public class Svmgausskernel implements Svmgenkernel
{
	public Int n = new Int();
	public Doub sigma = new Doub();
	public Svmgausskernel(RefObject<MatDoub_I> ddata, RefObject<VecDoub_I> yy, Doub ssigma)
	{
		super(yy, ddata);
		n = data.ncols();
		sigma = ssigma;
		fill();
	}
	public final Doub kernel(Doub[] xi, Doub[] xj)
	{
		Doub dott = 0.;
		for (Int k = 0; k<n; k++)
			dott += SQR(xi[k]-xj[k]);
		return Math.exp(-0.5 *dott/(sigma *sigma));
	}
}