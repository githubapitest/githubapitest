package com.google.code.numericalrecipes;
public class Stiel
{
	public static class pp
	{
		public Stiel st;
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
		Doub operator ()(Doub x, Doub del)
		{
			Doub pval = st.p(x);
			return pval*(st.wt1)(x,del)*pval;
		}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
		Doub operator ()(Doub t)
		{
			Doub x = (st.fx)(t);
			Doub pval = st.p(x);
			return pval*(st.wt2)(x)*(st.fdxdt)(t)*pval;
		}
	}
	public static class ppx
	{
		public Stiel st;
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
		Doub operator ()(Doub x, Doub del)
		{
			return st.ppfunc(x,del)*x;
		}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
		Doub operator ()(Doub t)
		{
			return st.ppfunc(t)*(st.fx)(t);
		}
	}
	public Int j = new Int();
	public Int n = new Int();
	public Doub aa = new Doub();
	public Doub bb = new Doub();
	public Doub hmax = new Doub();
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	Doub (*wt1)(const Doub x, const Doub del);
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	Doub (*wt2)(const Doub x);
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	Doub (*fx)(const Doub t);
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	Doub (*fdxdt)(const Doub t);
	public VecDoub a = new VecDoub();
	public VecDoub b = new VecDoub();
	public Quadrature s1;
	public Quadrature s2;
	public final Doub p(Doub x)
	{
		Doub pval = new Doub();
		Doub pj = new Doub();
		Doub pjm1 = new Doub();
		if (j == 0)
			return 1.0;
		else
		{
			pjm1 = 0.0;
			pj = 1.0;
			for (Int i = 0;i<j;i++)
			{
				pval = (x-a[i])*pj-b[i]*pjm1;
				pjm1 = pj;
				pj = pval;
			}
		}
		return pval;
	}
	public pp ppfunc = new pp();
	public ppx ppxfunc = new ppx();
	public Stiel(Int nn, Doub aaa, Doub bbb, Doub hmaxx, Doub wwt1(Doub,Doub))
	{
		n = nn;
		aa = aaa;
		bb = bbb;
		hmax = hmaxx;
		wt1 = wwt1;
		a = nn;
		b = nn;
		ppfunc.st = this;
		ppxfunc.st = this;
		s1 = new DErule<pp>(ppfunc,aa,bb,hmax);
		s2 = new DErule<ppx>(ppxfunc,aa,bb,hmax);
	}
	public Stiel(Int nn, Doub aaa, Doub bbb, Doub wwt2(Doub), Doub ffx(Doub), Doub ffdxdt(Doub))
	{
		n = nn;
		aa = aaa;
		bb = bbb;
		a = nn;
		b = nn;
		wt2 = wwt2;
		fx = ffx;
		fdxdt = ffdxdt;
		ppfunc.st = this;
		ppxfunc.st = this;
		s1 = new Trapzd<pp>(ppfunc,aa,bb);
		s2 = new Trapzd<ppx>(ppxfunc,aa,bb);
	}
	public final Doub quad(RefObject<Quadrature> s)
	{
		final Doub EPS = 3.0e-11;
		final Doub MACHEPS = numeric_limits<Doub>.epsilon();
		final Int NMAX = 11;
		Doub olds = new Doub();
		Doub sum = new Doub();
		s.argvalue.n=0;
		for (Int i = 1;i<=NMAX;i++)
		{
			sum = s.argvalue.next();
			if (i > 3)
				if (Math.abs(sum-olds) <= EPS *Math.abs(olds))
					return sum;
			if (i == NMAX)
				if (Math.abs(sum) <= MACHEPS && Math.abs(olds) <= MACHEPS)
					return 0.0;
			olds = sum;
		}
		throw("no convergence in quad");
		return 0.0;
	}
	public final void get_weights(RefObject<VecDoub_O> x, RefObject<VecDoub_O> w)
	{
		Doub amu0 = new Doub();
		Doub c = new Doub();
		Doub oldc = 1.0;
		if (n != x.argvalue.size())
			throw("bad array size in Stiel");
		for (Int i = 0;i<n;i++)
		{
			j = i;
			RefObject<Quadrature> tempRefObject = new RefObject<Quadrature>(s1);
			c = quad(tempRefObject);
			s1 = tempRefObject.argvalue;
			b[i]=c/oldc;
			RefObject<Quadrature> tempRefObject2 = new RefObject<Quadrature>(s2);
			a[i]=quad(tempRefObject2)/c;
			s2 = tempRefObject2.argvalue;
			oldc = c;
		}
		amu0 = b[0];
		gaucof(a,b,amu0,x.argvalue,w.argvalue);
	}
}