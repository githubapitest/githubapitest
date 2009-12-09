package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class L, class R, class S>
public class Shoot<L, R, S>
{
	public Int nvar = new Int();
	public Doub x1 = new Doub();
	public Doub x2 = new Doub();
	public L load;
	public R d;
	public S score;
	public Doub atol = new Doub();
	public Doub rtol = new Doub();
	public Doub h1 = new Doub();
	public Doub hmin = new Doub();
	public VecDoub y = new VecDoub();
	public Shoot(Int nvarr, Doub xx1, Doub xx2, RefObject<L> loadd, RefObject<R> dd, RefObject<S> scoree)
	{
		nvar = nvarr;
		x1 = xx1;
		x2 = xx2;
		load = loadd.argvalue;
		d = dd.argvalue;
		score = scoree.argvalue;
		atol = 1.0e-14;
		rtol = atol;
		hmin = 0.0;
		y = nvar;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	VecDoub operator ()(RefObject<VecDoub_I> v)
	{
		h1 = (x2-x1)/100.0;
		y = load(x1,v.argvalue);
		Output out = new Output();
		Odeint<StepperDopr853<R> > integ = new Odeint<StepperDopr853<R> >(y,x1,x2,atol,rtol,h1,hmin,out,d);
		integ.integrate();
		return score(x2,y);
	}
}