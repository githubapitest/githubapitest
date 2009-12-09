package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class L1, class L2, class R, class S>
public class Shootf<L1, L2, R, S>
{
	public Int nvar = new Int();
	public Int n2 = new Int();
	public Doub x1 = new Doub();
	public Doub x2 = new Doub();
	public Doub xf = new Doub();
	public L1 load1;
	public L2 load2;
	public R d;
	public S score;
	public Doub atol = new Doub();
	public Doub rtol = new Doub();
	public Doub h1 = new Doub();
	public Doub hmin = new Doub();
	public VecDoub y = new VecDoub();
	public VecDoub f1 = new VecDoub();
	public VecDoub f2 = new VecDoub();
	public Shootf(Int nvarr, Int nn2, Doub xx1, Doub xx2, Doub xxf, RefObject<L1> loadd1, RefObject<L2> loadd2, RefObject<R> dd, RefObject<S> scoree)
	{
		nvar = nvarr;
		n2 = nn2;
		x1 = xx1;
		x2 = xx2;
		xf = xxf;
		load1 = loadd1.argvalue;
		load2 = loadd2.argvalue;
		d = dd.argvalue;
		score = scoree.argvalue;
		atol = 1.0e-14;
		rtol = atol;
		hmin = 0.0;
		y = nvar;
		f1 = nvar;
		f2 = nvar;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	VecDoub operator ()(RefObject<VecDoub_I> v)
	{
		VecDoub[] v2 = new VecDoub[n2](nvar-n2, v.argvalue);
		h1 = (x2-x1)/100.0;
		y = load1(x1,v.argvalue);
		Output out = new Output();
		Odeint<StepperDopr853<R> > integ1 = new Odeint<StepperDopr853<R> >(y,x1,xf,atol,rtol,h1,hmin,out,d);
		integ1.integrate();
		f1 = score(xf,y);
		y = load2(x2,v2);
		Odeint<StepperDopr853<R> > integ2 = new Odeint<StepperDopr853<R> >(y,x2,xf,atol,rtol,h1,hmin,out,d);
		integ2.integrate();
		f2 = score(xf,y);
		for (Int i = 0;i<nvar;i++)
			f1[i] -= f2[i];
		return f1;
	}
}