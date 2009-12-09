package com.google.code.numericalrecipes;
public class Dbrent implements Bracketmethod
{
	public Doub xmin = new Doub();
	public Doub fmin = new Doub();
	public final Doub tol = new Doub();
	public Dbrent()
	{
		this(3.0e-8);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Dbrent(const Doub toll=3.0e-8) : tol(toll)
	public Dbrent(Doub toll)
	{
		tol = toll;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public final <T> Doub minimize(RefObject<T> funcd)
	{
		final Int ITMAX = 100;
		final Doub ZEPS = numeric_limits<Doub>.epsilon()*1.0e-3;
		Bool ok1 = new Bool();
		Bool ok2 = new Bool();
		Doub a = new Doub();
		Doub b = new Doub();
		Doub d = 0.0;
		Doub d1 = new Doub();
		Doub d2 = new Doub();
		Doub du = new Doub();
		Doub dv = new Doub();
		Doub dw = new Doub();
		Doub dx = new Doub();
		Doub e = 0.0;
		Doub fu = new Doub();
		Doub fv = new Doub();
		Doub fw = new Doub();
		Doub fx = new Doub();
		Doub olde = new Doub();
		Doub tol1 = new Doub();
		Doub tol2 = new Doub();
		Doub u = new Doub();
		Doub u1 = new Doub();
		Doub u2 = new Doub();
		Doub v = new Doub();
		Doub w = new Doub();
		Doub x = new Doub();
		Doub xm = new Doub();

		a = (ax < cx != null ? ax : cx);
		b = (ax > cx != null ? ax : cx);
		x = w = v = bx;
		fw = fv = fx = funcd.argvalue(x);
		dw = dv = dx = funcd.argvalue.df(x);
		for (Int iter = 0;iter<ITMAX;iter++)
		{
			xm = 0.5*(a+b);
			tol1 = tol *Math.abs(x)+ZEPS;
			tol2 = 2.0 *tol1;
			if (Math.abs(x-xm) <= (tol2-0.5*(b-a)))
			{
				fmin = fx;
				return xmin = x;
			}
			if (Math.abs(e) > tol1)
			{
				d1 = 2.0*(b-a);
				d2 = d1;
				if (dw != dx)
					d1 = (w-x)*dx/(dx-dw);
				if (dv != dx)
					d2 = (v-x)*dx/(dx-dv);
				u1 = x+d1;
				u2 = x+d2;
				ok1 = (a-u1)*(u1-b) > 0.0 && dx *d1 <= 0.0;
				ok2 = (a-u2)*(u2-b) > 0.0 && dx *d2 <= 0.0;
				olde = e;
				e = d;
				if (ok1 != null || ok2 != null)
				{
					if (ok1 != null && ok2 != null)
						d = (Math.abs(d1) < Math.abs(d2) ? d1 : d2);
					else if (ok1 != null)
						d = d1;
					else
						d = d2;
					if (Math.abs(d) <= Math.abs(0.5 *olde))
					{
						u = x+d;
						if (u-a < tol2 || b-u < tol2)
							d = SIGN(tol1,xm-x);
					}
					else
					{
						d = 0.5*(e = (dx >= 0.0 ? a-x : b-x));
					}
				}
				else
				{
					d = 0.5*(e = (dx >= 0.0 ? a-x : b-x));
				}
			}
			else
			{
				d = 0.5*(e = (dx >= 0.0 ? a-x : b-x));
			}
			if (Math.abs(d) >= tol1)
			{
				u = x+d;
				fu = funcd.argvalue(u);
			}
			else
			{
				u = x+SIGN(tol1,d);
				fu = funcd.argvalue(u);
				if (fu > fx)
				{
					fmin = fx;
					return xmin = x;
				}
			}
			du = funcd.argvalue.df(u);
			if (fu <= fx)
			{
				if (u >= x)
					a = x;
					else
						b = x;
				mov3(v,fv,dv,w,fw,dw);
				mov3(w,fw,dw,x,fx,dx);
				mov3(x,fx,dx,u,fu,du);
			}
			else
			{
				if (u < x)
					a = u;
					else
						b = u;
				if (fu <= fw || w == x)
				{
					mov3(v,fv,dv,w,fw,dw);
					mov3(w,fw,dw,u,fu,du);
				}
				else if (fu < fv || v == x || v == w)
				{
					mov3(v,fv,dv,u,fu,du);
				}
			}
		}
		throw("Too many iterations in routine dbrent");
	}
}