package com.google.code.numericalrecipes;
public class Svmpolykernel implements Svmgenkernel
{
	public Int n = new Int();
	public Doub a = new Doub();
	public Doub b = new Doub();
	public Doub d = new Doub();
	public Svmpolykernel(RefObject<MatDoub_I> ddata, RefObject<VecDoub_I> yy, Doub aa, Doub bb, Doub dd)
	{
		super(yy, ddata);
		n = data.ncols();
		a = aa;
		b = bb;
		d = dd;
		fill();
	}
	public final Doub kernel(Doub[] xi, Doub[] xj)
	{
		Doub dott = 0.;
		for (Int k = 0; k<n; k++)
			dott += xi[k]*xj[k];
		return Math.pow(a *dott+b,d);
	}
}