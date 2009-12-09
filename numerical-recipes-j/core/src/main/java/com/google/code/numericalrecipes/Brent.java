package com.google.code.numericalrecipes;
public class Brent implements Bracketmethod
{
	public Doub xmin = new Doub();
	public Doub fmin = new Doub();
	public final Doub tol = new Doub();
	public Brent()
	{
		this(3.0e-8);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Brent(const Doub toll=3.0e-8) : tol(toll)
	public Brent(Doub toll)
	{
		tol = toll;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public final <T> Doub minimize(RefObject<T> func)
	{
		final Int ITMAX = 100;
		final Doub CGOLD = 0.3819660;
		final Doub ZEPS = numeric_limits<Doub>.epsilon()*1.0e-3;
		Doub a = new Doub();
		Doub b = new Doub();
		Doub d = 0.0;
		Doub etemp = new Doub();
		Doub fu = new Doub();
		Doub fv = new Doub();
		Doub fw = new Doub();
		Doub fx = new Doub();
		Doub p = new Doub();
		Doub q = new Doub();
		Doub r = new Doub();
		Doub tol1 = new Doub();
		Doub tol2 = new Doub();
		Doub u = new Doub();
		Doub v = new Doub();
		Doub w = new Doub();
		Doub x = new Doub();
		Doub xm = new Doub();
		Doub e = 0.0;

		a = (ax < cx != null ? ax : cx);
		b = (ax > cx != null ? ax : cx);
		x = w = v = bx;
		fw = fv = fx = func.argvalue(x);
		for (Int iter = 0;iter<ITMAX;iter++)
		{
			xm = 0.5*(a+b);
			tol2 = 2.0*(tol1 = tol *Math.abs(x)+ZEPS);
			if (Math.abs(x-xm) <= (tol2-0.5*(b-a)))
			{
				fmin = fx;
				return xmin = x;
			}
			if (Math.abs(e) > tol1)
			{
				r = (x-w)*(fx-fv);
				q = (x-v)*(fx-fw);
				p = (x-v)*q-(x-w)*r;
				q = 2.0*(q-r);
				if (q > 0.0)
					p = -p;
				q = Math.abs(q);
				etemp = e;
				e = d;
				if (Math.abs(p) >= Math.abs(0.5 *q *etemp) || p <= q*(a-x) || p >= q*(b-x))
					d = CGOLD*(e = (x >= xm != null ? a-x : b-x));
				else
				{
					d = p/q;
					u = x+d;
					if (u-a < tol2 || b-u < tol2)
						d = SIGN(tol1,xm-x);
				}
			}
			else
			{
				d = CGOLD*(e = (x >= xm != null ? a-x : b-x));
			}
			u = (Math.abs(d) >= tol1 != null ? x+d : x+SIGN(tol1,d));
			fu = func.argvalue(u);
			if (fu <= fx)
			{
				if (u >= x)
					a = x;
					else
						b = x;
				shft3(v,w,x,u);
				shft3(fv,fw,fx,fu);
			}
			else
			{
				if (u < x)
					a = u;
					else
						b = u;
				if (fu <= fw || w == x)
				{
					v = w;
					w = u;
					fv = fw;
					fw = fu;
				}
				else if (fu <= fv || v == x || v == w)
				{
					v = u;
					fv = fu;
				}
			}
		}
		throw("Too many iterations in brent");
	}
}