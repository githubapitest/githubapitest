package com.google.code.numericalrecipes;
public class Adapt
{
	public Doub TOL = new Doub();
	public Doub toler = new Doub();
	public final Doub alpha = new Doub();
	public final Doub beta = new Doub();
	public final Doub x1 = new Doub();
	public final Doub x2 = new Doub();
	public final Doub x3 = new Doub();
	public static final Doub[] x = new Doub[12];
	public boolean terminate;
	public boolean out_of_tolerance;
	public Adapt(Doub tol)
	{
		TOL = tol;
		terminate = true;
		out_of_tolerance = false;
		final Doub EPS = numeric_limits<Doub>.epsilon();
		if (TOL < 10.0 *EPS)
			TOL = 10.0 *EPS;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public final <T> Doub integrate(RefObject<T> func, Doub a, Doub b)
	{
		Doub m = new Doub();
		Doub h = new Doub();
		Doub fa = new Doub();
		Doub fb = new Doub();
		Doub i1 = new Doub();
		Doub i2 = new Doub();
		Doub is = new Doub();
		Doub erri1 = new Doub();
		Doub erri2 = new Doub();
		Doub r = new Doub();
		Doub[] y = new Doub[13];
		m = 0.5*(a+b);
		h = 0.5*(b-a);
		fa = y[0] =func.argvalue(a);
		fb = y[12] =func.argvalue(b);
		for (Int i = 1;i<12;i++)
			y[i] =func.argvalue(m+x[i]*h);
		i2 = (h/6.0)*(y[0]+y[12]+5.0*(y[4]+y[8]));
		i1 = (h/1470.0)*(77.0*(y[0]+y[12])+432.0*(y[2]+y[10])+ 625.0*(y[4]+y[8])+672.0 *y[6]);
		is = h*(0.0158271919734802*(y[0]+y[12])+0.0942738402188500* (y[1]+y[11])+0.155071987336585*(y[2]+y[10])+ 0.188821573960182*(y[3]+y[9])+0.199773405226859* (y[4]+y[8])+0.224926465333340*(y[5]+y[7])+ 0.242611071901408 *y[6]);
		erri1 = Math.abs(i1-is);
		erri2 = Math.abs(i2-is);
		r = (erri2 != 0.0) ? erri1/erri2 : 1.0;
		toler = (r > 0.0 && r < 1.0) ? TOL/r : TOL;
		if (is == 0.0)
			is = b-a;
		is = Math.abs(is);
		return adaptlob(func, a, b, fa, fb, is);
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public final <T> Doub adaptlob(RefObject<T> func, Doub a, Doub b, Doub fa, Doub fb, Doub is)
	{
		Doub m = new Doub();
		Doub h = new Doub();
		Doub mll = new Doub();
		Doub ml = new Doub();
		Doub mr = new Doub();
		Doub mrr = new Doub();
		Doub fmll = new Doub();
		Doub fml = new Doub();
		Doub fm = new Doub();
		Doub fmrr = new Doub();
		Doub fmr = new Doub();
		Doub i1 = new Doub();
		Doub i2 = new Doub();
		m = 0.5*(a+b);
		h = 0.5*(b-a);
		mll = m-alpha *h;
		ml = m-beta *h;
		mr = m+beta *h;
		mrr = m+alpha *h;
		fmll = func.argvalue(mll);
		fml = func.argvalue(ml);
		fm = func.argvalue(m);
		fmr = func.argvalue(mr);
		fmrr = func.argvalue(mrr);
		i2 = h/6.0*(fa+fb+5.0*(fml+fmr));
		i1 = h/1470.0*(77.0*(fa+fb)+432.0*(fmll+fmrr)+625.0*(fml+fmr)+672.0 *fm);
		if (Math.abs(i1-i2) <= toler *is || mll <= a || b <= mrr)
		{
			if ((mll <= a || b <= mrr) && terminate)
			{
				out_of_tolerance = true;
				terminate = false;
			}
			return i1;
		}
		else
			return adaptlob(func, a, mll, fa, fmll, is)+ adaptlob(func, mll, ml, fmll, fml, is)+ adaptlob(func, ml, m, fml, fm, is)+ adaptlob(func, m, mr, fm, fmr, is)+ adaptlob(func, mr, mrr, fmr, fmrr, is)+ adaptlob(func, mrr, b, fmrr, fb, is);
	}
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
