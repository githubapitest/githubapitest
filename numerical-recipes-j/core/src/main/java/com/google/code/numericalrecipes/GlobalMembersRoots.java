package com.google.code.numericalrecipes;
public class GlobalMembersRoots
{
	public static <T> Bool zbrac(RefObject<T> func, RefObject<Doub> x1, RefObject<Doub> x2)
	{
		final Int NTRY = 50;
		final Doub FACTOR = 1.6;
		if (x1.argvalue == x2.argvalue)
			throw("Bad initial range in zbrac");
		Doub f1 = func.argvalue(x1.argvalue);
		Doub f2 = func.argvalue(x2.argvalue);
		for (Int j = 0;j<NTRY;j++)
		{
			if (f1 *f2 < 0.0)
				return true;
			if (Math.abs(f1) < Math.abs(f2))
				f1 = func.argvalue(x1.argvalue += FACTOR*(x1.argvalue-x2.argvalue));
			else
				f2 = func.argvalue(x2.argvalue += FACTOR*(x2.argvalue-x1.argvalue));
		}
		return false;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public static <T> void zbrak(RefObject<T> fx, Doub x1, Doub x2, Int n, RefObject<VecDoub_O> xb1, RefObject<VecDoub_O> xb2, RefObject<Int> nroot)
	{
		Int nb = 20;
		xb1.argvalue.resize(nb);
		xb2.argvalue.resize(nb);
		nroot.argvalue = 0;
		Doub dx = (x2-x1)/n;
		Doub x = x1;
		Doub fp = fx.argvalue(x1);
		for (Int i = 0;i<n;i++)
		{
			Doub fc = fx.argvalue(x += dx);
			if (fc *fp <= 0.0)
			{
				xb1.argvalue[nroot.argvalue]=x-dx;
				xb2.argvalue[nroot.argvalue++]=x;
				if(nroot.argvalue == nb)
				{
					VecDoub tempvec1 = new VecDoub(xb1.argvalue);
					VecDoub tempvec2 = new VecDoub(xb2.argvalue);
					xb1.argvalue.resize(2 *nb);
					xb2.argvalue.resize(2 *nb);
					for (Int j = 0; j<nb; j++)
					{
						xb1.argvalue[j]=tempvec1[j];
						xb2.argvalue[j]=tempvec2[j];
					}
					nb *= 2;
				}
			}
			fp = fc;
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public static <T> Doub rtbis(RefObject<T> func, Doub x1, Doub x2, Doub xacc)
	{
		final Int JMAX = 50;
		Doub dx = new Doub();
		Doub xmid = new Doub();
		Doub rtb = new Doub();
		Doub f = func.argvalue(x1);
		Doub fmid = func.argvalue(x2);
		if (f *fmid >= 0.0)
			throw("Root must be bracketed for bisection in rtbis");
		rtb = f < 0.0 ? (dx = x2-x1,x1) : (dx = x1-x2,x2);
		for (Int j = 0;j<JMAX;j++)
		{
			fmid = func.argvalue(xmid = rtb+(dx *= 0.5));
			if (fmid <= 0.0)
				rtb = xmid;
			if (Math.abs(dx) < xacc || fmid == 0.0)
				return rtb;
		}
		throw("Too many bisections in rtbis");
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public static <T> Doub rtflsp(RefObject<T> func, Doub x1, Doub x2, Doub xacc)
	{
		final Int MAXIT = 30;
		Doub xl = new Doub();
		Doub xh = new Doub();
		Doub del = new Doub();
		Doub fl = func.argvalue(x1);
		Doub fh = func.argvalue(x2);
		if (fl *fh > 0.0)
			throw("Root must be bracketed in rtflsp");
		if (fl < 0.0)
		{
			xl = x1;
			xh = x2;
		}
		else
		{
			xl = x2;
			xh = x1;
			SWAP(fl,fh);
		}
		Doub dx = xh-xl;
		for (Int j = 0;j<MAXIT;j++)
		{
			Doub rtf = xl+dx *fl/(fl-fh);
			Doub f = func.argvalue(rtf);
			if (f < 0.0)
			{
				del = xl-rtf;
				xl = rtf;
				fl = f;
			}
			else
			{
				del = xh-rtf;
				xh = rtf;
				fh = f;
			}
			dx = xh-xl;
			if (Math.abs(del) < xacc || f == 0.0)
				return rtf;
		}
		throw("Maximum number of iterations exceeded in rtflsp");
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public static <T> Doub rtsec(RefObject<T> func, Doub x1, Doub x2, Doub xacc)
	{
		final Int MAXIT = 30;
		Doub xl = new Doub();
		Doub rts = new Doub();
		Doub fl = func.argvalue(x1);
		Doub f = func.argvalue(x2);
		if (Math.abs(fl) < Math.abs(f))
		{
			rts = x1;
			xl = x2;
			SWAP(fl,f);
		}
		else
		{
			xl = x1;
			rts = x2;
		}
		for (Int j = 0;j<MAXIT;j++)
		{
			Doub dx = (xl-rts)*f/(f-fl);
			xl = rts;
			fl = f;
			rts += dx;
			f = func.argvalue(rts);
			if (Math.abs(dx) < xacc || f == 0.0)
				return rts;
		}
		throw("Maximum number of iterations exceeded in rtsec");
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public static <T> Doub zriddr(RefObject<T> func, Doub x1, Doub x2, Doub xacc)
	{
		final Int MAXIT = 60;
		Doub fl = func.argvalue(x1);
		Doub fh = func.argvalue(x2);
		if ((fl > 0.0 && fh < 0.0) || (fl < 0.0 && fh > 0.0))
		{
			Doub xl = x1;
			Doub xh = x2;
			Doub ans = -9.99e99;
			for (Int j = 0;j<MAXIT;j++)
			{
				Doub xm = 0.5*(xl+xh);
				Doub fm = func.argvalue(xm);
				Doub s = Math.sqrt(fm *fm-fl *fh);
				if (s == 0.0)
					return ans;
				Doub xnew = xm+(xm-xl)*((fl >= fh != null ? 1.0 : -1.0)*fm/s);
				if (Math.abs(xnew-ans) <= xacc)
					return ans;
				ans = xnew;
				Doub fnew = func.argvalue(ans);
				if (fnew == 0.0)
					return ans;
				if (SIGN(fm,fnew) != fm)
				{
					xl = xm;
					fl = fm;
					xh = ans;
					fh = fnew;
				}
				else if (SIGN(fl,fnew) != fl)
				{
					xh = ans;
					fh = fnew;
				}
				else if (SIGN(fh,fnew) != fh)
				{
					xl = ans;
					fl = fnew;
				}
				else
					throw("never get here.");
				if (Math.abs(xh-xl) <= xacc)
					return ans;
			}
			throw("zriddr exceed maximum iterations");
		}
		else
		{
			if (fl == 0.0)
				return x1;
			if (fh == 0.0)
				return x2;
			throw("root must be bracketed in zriddr.");
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public static <T> Doub zbrent(RefObject<T> func, Doub x1, Doub x2, Doub tol)
	{
		final Int ITMAX = 100;
		final Doub EPS = numeric_limits<Doub>.epsilon();
		Doub a = x1;
		Doub b = x2;
		Doub c = x2;
		Doub d = new Doub();
		Doub e = new Doub();
		Doub fa = func.argvalue(a);
		Doub fb = func.argvalue(b);
		Doub fc = new Doub();
		Doub p = new Doub();
		Doub q = new Doub();
		Doub r = new Doub();
		Doub s = new Doub();
		Doub tol1 = new Doub();
		Doub xm = new Doub();
		if ((fa > 0.0 && fb > 0.0) || (fa < 0.0 && fb < 0.0))
			throw("Root must be bracketed in zbrent");
		fc = fb;
		for (Int iter = 0;iter<ITMAX;iter++)
		{
			if ((fb > 0.0 && fc > 0.0) || (fb < 0.0 && fc < 0.0))
			{
				c = a;
				fc = fa;
				e = d = b-a;
			}
			if (Math.abs(fc) < Math.abs(fb))
			{
				a = b;
				b = c;
				c = a;
				fa = fb;
				fb = fc;
				fc = fa;
			}
			tol1 = 2.0 *EPS *Math.abs(b)+0.5 *tol;
			xm = 0.5*(c-b);
			if (Math.abs(xm) <= tol1 || fb == 0.0)
				return b;
			if (Math.abs(e) >= tol1 && Math.abs(fa) > Math.abs(fb))
			{
				s = fb/fa;
				if (a == c)
				{
					p = 2.0 *xm *s;
					q = 1.0-s;
				}
				else
				{
					q = fa/fc;
					r = fb/fc;
					p = s*(2.0 *xm *q*(q-r)-(b-a)*(r-1.0));
					q = (q-1.0)*(r-1.0)*(s-1.0);
				}
				if (p > 0.0)
					q = -q;
				p = Math.abs(p);
				Doub min1 = 3.0 *xm *q-Math.abs(tol1 *q);
				Doub min2 = Math.abs(e *q);
				if (2.0 *p < (min1 < min2 != null ? min1 : min2))
				{
					e = d;
					d = p/q;
				}
				else
				{
					d = xm;
					e = d;
				}
			}
			else
			{
				d = xm;
				e = d;
			}
			a = b;
			fa = fb;
			if (Math.abs(d) > tol1)
				b += d;
			else
				b += SIGN(tol1,xm);
				fb = func.argvalue(b);
		}
		throw("Maximum number of iterations exceeded in zbrent");
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public static <T> Doub rtnewt(RefObject<T> funcd, Doub x1, Doub x2, Doub xacc)
	{
		final Int JMAX = 20;
		Doub rtn = 0.5*(x1+x2);
		for (Int j = 0;j<JMAX;j++)
		{
			Doub f = funcd.argvalue(rtn);
			Doub df = funcd.argvalue.df(rtn);
			Doub dx = f/df;
			rtn -= dx;
			if ((x1-rtn)*(rtn-x2) < 0.0)
				throw("Jumped out of brackets in rtnewt");
			if (Math.abs(dx) < xacc)
				return rtn;
		}
		throw("Maximum number of iterations exceeded in rtnewt");
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public static <T> Doub rtsafe(RefObject<T> funcd, Doub x1, Doub x2, Doub xacc)
	{
		final Int MAXIT = 100;
		Doub xh = new Doub();
		Doub xl = new Doub();
		Doub fl = funcd.argvalue(x1);
		Doub fh = funcd.argvalue(x2);
		if ((fl > 0.0 && fh > 0.0) || (fl < 0.0 && fh < 0.0))
			throw("Root must be bracketed in rtsafe");
		if (fl == 0.0)
			return x1;
		if (fh == 0.0)
			return x2;
		if (fl < 0.0)
		{
			xl = x1;
			xh = x2;
		}
		else
		{
			xh = x1;
			xl = x2;
		}
		Doub rts = 0.5*(x1+x2);
		Doub dxold = Math.abs(x2-x1);
		Doub dx = dxold;
		Doub f = funcd.argvalue(rts);
		Doub df = funcd.argvalue.df(rts);
		for (Int j = 0;j<MAXIT;j++)
		{
			if ((((rts-xh)*df-f)*((rts-xl)*df-f) > 0.0) || (Math.abs(2.0 *f) > Math.abs(dxold *df)))
			{
				dxold = dx;
				dx = 0.5*(xh-xl);
				rts = xl+dx;
				if (xl == rts)
					return rts;
			}
			else
			{
				dxold = dx;
				dx = f/df;
				Doub temp = rts;
				rts -= dx;
				if (temp == rts)
					return rts;
			}
			if (Math.abs(dx) < xacc)
				return rts;
			Doub f = funcd.argvalue(rts);
			Doub df = funcd.argvalue.df(rts);
			if (f < 0.0)
				xl = rts;
			else
				xh = rts;
		}
		throw("Maximum number of iterations exceeded in rtsafe");
	}
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
