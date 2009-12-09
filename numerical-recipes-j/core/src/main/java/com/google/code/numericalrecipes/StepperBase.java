package com.google.code.numericalrecipes;
public class StepperBase
{
	public Doub x;
	public Doub xold = new Doub();
	public VecDoub y;
	public VecDoub dydx;
	public Doub atol = new Doub();
	public Doub rtol = new Doub();
	public boolean dense;
	public Doub hdid = new Doub();
	public Doub hnext = new Doub();
	public Doub EPS = new Doub();
	public Int n = new Int();
	public Int neqn = new Int();
	public VecDoub yout = new VecDoub();
	public VecDoub yerr = new VecDoub();
	public StepperBase(RefObject<VecDoub_IO> yy, RefObject<VecDoub_IO> dydxx, RefObject<Doub> xx, Doub atoll, Doub rtoll, boolean dens)
	{
		x = xx.argvalue;
		y = yy.argvalue;
		dydx = dydxx.argvalue;
		atol = atoll;
		rtol = rtoll;
		dense = dens;
		n = y.size();
		neqn = n;
		yout = n;
		yerr = n;
	}
}