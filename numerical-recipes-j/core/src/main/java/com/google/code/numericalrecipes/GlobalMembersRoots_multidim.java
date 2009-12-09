package com.google.code.numericalrecipes;
public class GlobalMembersRoots_multidim
{
	public static <T> void lnsrch(RefObject<VecDoub_I> xold, Doub fold, RefObject<VecDoub_I> g, RefObject<VecDoub_IO> p, RefObject<VecDoub_O> x, RefObject<Doub> f, Doub stpmax, RefObject<Bool> check, RefObject<T> func)
	{
		final Doub ALF = 1.0e-4;
		final Doub TOLX = numeric_limits<Doub>.epsilon();
		Doub a = new Doub();
		Doub alam = new Doub();
		Doub alam2 = 0.0;
		Doub alamin = new Doub();
		Doub b = new Doub();
		Doub disc = new Doub();
		Doub f2 = 0.0;
		Doub rhs1 = new Doub();
		Doub rhs2 = new Doub();
		Doub slope = 0.0;
		Doub sum = 0.0;
		Doub temp = new Doub();
		Doub test = new Doub();
		Doub tmplam = new Doub();
		Int i = new Int();
		Int n = xold.argvalue.size();
		check.argvalue = false;
		for (i = 0;i<n;i++)
			sum += p.argvalue[i]*p.argvalue[i];
		sum = Math.sqrt(sum);
		if (sum > stpmax)
			for (i = 0;i<n;i++)
				p.argvalue[i] *= stpmax/sum;
		for (i = 0;i<n;i++)
			slope += g.argvalue[i]*p.argvalue[i];
		if (slope >= 0.0)
			throw("Roundoff problem in lnsrch.");
		test = 0.0;
		for (i = 0;i<n;i++)
		{
			temp = Math.abs(p.argvalue[i])/MAX(Math.abs(xold.argvalue[i]),1.0);
			if (temp > test)
				test = temp;
		}
		alamin = TOLX/test;
		alam = 1.0;
		for (;;)
		{
			for (i = 0;i<n;i++)
				x.argvalue[i]=xold.argvalue[i]+alam *p.argvalue[i];
			f.argvalue = func.argvalue(x.argvalue);
			if (alam < alamin)
			{
				for (i = 0;i<n;i++)
					x.argvalue[i]=xold.argvalue[i];
				check.argvalue = true;
				return;
			}
			else if (f.argvalue <= fold+ALF *alam *slope)
				return;
			else
			{
				if (alam == 1.0)
					tmplam = -slope/(2.0*(f.argvalue-fold-slope));
				else
				{
					rhs1 = f.argvalue-fold-alam *slope;
					rhs2 = f2-fold-alam2 *slope;
					a = (rhs1/(alam *alam)-rhs2/(alam2 *alam2))/(alam-alam2);
					b = (-alam2 *rhs1/(alam *alam)+alam *rhs2/(alam2 *alam2))/(alam-alam2);
					if (a == 0.0)
						tmplam = -slope/(2.0 *b);
					else
					{
						disc = b *b-3.0 *a *slope;
						if (disc < 0.0)
							tmplam = 0.5 *alam;
						else if (b <= 0.0)
							tmplam = (-b+Math.sqrt(disc))/(3.0 *a);
						else
							tmplam = -slope/(b+Math.sqrt(disc));
					}
					if (tmplam>0.5 *alam)
						tmplam = 0.5 *alam;
				}
			}
			alam2 = alam;
			f2 = f.argvalue;
			alam = MAX(tmplam,0.1 *alam);
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public static <T> void newt(RefObject<VecDoub_IO> x, RefObject<Bool> check, RefObject<T> vecfunc)
	{
		final Int MAXITS = 200;
		final Doub TOLF = 1.0e-8;
		final Doub TOLMIN = 1.0e-12;
		final Doub STPMX = 100.0;
		final Doub TOLX = numeric_limits<Doub>.epsilon();
		Int i = new Int();
		Int j = new Int();
		Int its = new Int();
		Int n = x.argvalue.size();
		Doub den = new Doub();
		Doub f = new Doub();
		Doub fold = new Doub();
		Doub stpmax = new Doub();
		Doub sum = new Doub();
		Doub temp = new Doub();
		Doub test = new Doub();
		VecDoub g = new VecDoub(n);
		VecDoub p = new VecDoub(n);
		VecDoub xold = new VecDoub(n);
		MatDoub fjac = new MatDoub(n,n);
		NRfmin<T> fmin = new NRfmin<T>(vecfunc.argvalue);
		NRfdjac<T> fdjac = new NRfdjac<T>(vecfunc.argvalue);
		VecDoub fvec = fmin.fvec;
		f = fmin(x.argvalue);
		test = 0.0;
		for (i = 0;i<n;i++)
			if (Math.abs(fvec[i]) > test)
				test = Math.abs(fvec[i]);
		if (test < 0.01 *TOLF)
		{
			check.argvalue = false;
			return;
		}
		sum = 0.0;
		for (i = 0;i<n;i++)
			sum += SQR(x.argvalue[i]);
		stpmax = STPMX *MAX(Math.sqrt(sum),Doub(n));
		for (its = 0;its<MAXITS;its++)
		{
			fjac = fdjac(x.argvalue,fvec);
			for (i = 0;i<n;i++)
			{
				sum = 0.0;
				for (j = 0;j<n;j++)
					sum += fjac[j][i]*fvec[j];
				g[i]=sum;
			}
			for (i = 0;i<n;i++)
				xold[i]=x.argvalue[i];
			fold = f;
			for (i = 0;i<n;i++)
				p[i] = -fvec[i];
			LUdcmp alu = new LUdcmp(fjac);
			alu.solve(p,p);
		RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(xold);
		RefObject<VecDoub_I> tempRefObject2 = new RefObject<VecDoub_I>(g);
		RefObject<VecDoub_IO> tempRefObject3 = new RefObject<VecDoub_IO>(p);
		RefObject<Doub> tempRefObject4 = new RefObject<Doub>(f);
		RefObject<T> tempRefObject5 = new RefObject<T>(fmin);
			lnsrch(tempRefObject, fold, tempRefObject2, tempRefObject3, x, tempRefObject4, stpmax, check, tempRefObject5);
			xold = tempRefObject.argvalue;
			g = tempRefObject2.argvalue;
			p = tempRefObject3.argvalue;
			f = tempRefObject4.argvalue;
			fmin = tempRefObject5.argvalue;
			test = 0.0;
			for (i = 0;i<n;i++)
				if (Math.abs(fvec[i]) > test)
					test = Math.abs(fvec[i]);
			if (test < TOLF)
			{
				check.argvalue = false;
				return;
			}
			if (check.argvalue != null)
			{
				test = 0.0;
				den = MAX(f,0.5 *n);
				for (i = 0;i<n;i++)
				{
					temp = Math.abs(g[i])*MAX(Math.abs(x.argvalue[i]),1.0)/den;
					if (temp > test)
						test = temp;
				}
				check.argvalue = (test < TOLMIN);
				return;
			}
			test = 0.0;
			for (i = 0;i<n;i++)
			{
				temp = (Math.abs(x.argvalue[i]-xold[i]))/MAX(Math.abs(x.argvalue[i]),1.0);
				if (temp > test)
					test = temp;
			}
			if (test < TOLX)
				return;
		}
		throw("MAXITS exceeded in newt");
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public static <T> void broydn(RefObject<VecDoub_IO> x, RefObject<Bool> check, RefObject<T> vecfunc)
	{
		final Int MAXITS = 200;
		final Doub EPS = numeric_limits<Doub>.epsilon();
		final Doub TOLF = 1.0e-8;
		final Doub TOLX = EPS;
		final Doub STPMX = 100.0;
		final Doub TOLMIN = 1.0e-12;
		Bool restrt = new Bool();
		Bool skip = new Bool();
		Int i = new Int();
		Int its = new Int();
		Int j = new Int();
		Int n = x.argvalue.size();
		Doub den = new Doub();
		Doub f = new Doub();
		Doub fold = new Doub();
		Doub stpmax = new Doub();
		Doub sum = new Doub();
		Doub temp = new Doub();
		Doub test = new Doub();
		VecDoub fvcold = new VecDoub(n);
		VecDoub g = new VecDoub(n);
		VecDoub p = new VecDoub(n);
		VecDoub s = new VecDoub(n);
		VecDoub t = new VecDoub(n);
		VecDoub w = new VecDoub(n);
		VecDoub xold = new VecDoub(n);
		QRdcmp qr;
		NRfmin<T> fmin = new NRfmin<T>(vecfunc.argvalue);
		NRfdjac<T> fdjac = new NRfdjac<T>(vecfunc.argvalue);
		VecDoub fvec = fmin.fvec;
		f = fmin(x.argvalue);
		test = 0.0;
		for (i = 0;i<n;i++)
			if (Math.abs(fvec[i]) > test)
				test = Math.abs(fvec[i]);
		if (test < 0.01 *TOLF)
		{
			check.argvalue = false;
			return;
		}
		for (sum = 0.0,i = 0;i<n;i++)
			sum += SQR(x.argvalue[i]);
		stpmax = STPMX *MAX(Math.sqrt(sum),Doub(n));
		restrt = true;
		for (its = 1;its<=MAXITS;its++)
		{
			if (restrt != null)
			{
				qr = new QRdcmp(fdjac(x.argvalue,fvec));
				if (qr.sing)
					throw("singular Jacobian in broydn");
			}
			else
			{
				for (i = 0;i<n;i++)
					s[i]=x.argvalue[i]-xold[i];
				for (i = 0;i<n;i++)
				{
					for (sum = 0.0,j = i;j<n;j++)
						sum += qr.r[i][j]*s[j];
					t[i]=sum;
				}
				skip = true;
				for (i = 0;i<n;i++)
				{
					for (sum = 0.0,j = 0;j<n;j++)
						sum += qr.qt[j][i]*t[j];
					w[i]=fvec[i]-fvcold[i]-sum;
					if (Math.abs(w[i]) >= EPS*(Math.abs(fvec[i])+Math.abs(fvcold[i])))
						skip = false;
					else
						w[i]=0.0;
				}
				if (skip == null)
				{
					qr.qtmult(w,t);
					for (den = 0.0,i = 0;i<n;i++)
						den += SQR(s[i]);
					for (i = 0;i<n;i++)
						s[i] /= den;
					qr.update(t,s);
					if (qr.sing)
						throw("singular update in broydn");
				}
			}
			qr.qtmult(fvec,p);
			for (i = 0;i<n;i++)
				p[i] = -p[i];
			for (i = n-1;i>=0;i--)
			{
				for (sum = 0.0,j = 0;j<=i;j++)
					sum -= qr.r[j][i]*p[j];
				g[i]=sum;
			}
			for (i = 0;i<n;i++)
			{
				xold[i]=x.argvalue[i];
				fvcold[i]=fvec[i];
			}
			fold = f;
			qr.rsolve(p,p);
		RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(xold);
		RefObject<VecDoub_I> tempRefObject2 = new RefObject<VecDoub_I>(g);
		RefObject<VecDoub_IO> tempRefObject3 = new RefObject<VecDoub_IO>(p);
		RefObject<Doub> tempRefObject4 = new RefObject<Doub>(f);
		RefObject<T> tempRefObject5 = new RefObject<T>(fmin);
			lnsrch(tempRefObject, fold, tempRefObject2, tempRefObject3, x, tempRefObject4, stpmax, check, tempRefObject5);
			xold = tempRefObject.argvalue;
			g = tempRefObject2.argvalue;
			p = tempRefObject3.argvalue;
			f = tempRefObject4.argvalue;
			fmin = tempRefObject5.argvalue;
			test = 0.0;
			for (i = 0;i<n;i++)
				if (Math.abs(fvec[i]) > test)
					test = Math.abs(fvec[i]);
			if (test < TOLF)
			{
				check.argvalue = false;
				qr = null;
				return;
			}
			if (check.argvalue != null)
			{
				if (restrt != null)
				{
					qr = null;
					return;
				}
				else
				{
					test = 0.0;
					den = MAX(f,0.5 *n);
					for (i = 0;i<n;i++)
					{
						temp = Math.abs(g[i])*MAX(Math.abs(x.argvalue[i]),1.0)/den;
						if (temp > test)
							test = temp;
					}
					if (test < TOLMIN)
					{
						qr = null;
						return;
					}
					else
						restrt = true;
				}
			}
			else
			{
				restrt = false;
				test = 0.0;
				for (i = 0;i<n;i++)
				{
					temp = (Math.abs(x.argvalue[i]-xold[i]))/MAX(Math.abs(x.argvalue[i]),1.0);
					if (temp > test)
						test = temp;
				}
				if (test < TOLX)
				{
					qr = null;
					return;
				}
			}
		}
		throw("MAXITS exceeded in broydn");
	}
}