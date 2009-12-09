package com.google.code.numericalrecipes;
public abstract class Linbcg
{
	public abstract void asolve(RefObject<VecDoub_I> b, RefObject<VecDoub_O> x, Int itrnsp);
	public abstract void atimes(RefObject<VecDoub_I> x, RefObject<VecDoub_O> r, Int itrnsp);
	public final void solve(RefObject<VecDoub_I> b, RefObject<VecDoub_IO> x, Int itol, Doub tol, Int itmax, RefObject<Int> iter, RefObject<Doub> err)
	{
		Doub ak = new Doub();
		Doub akden = new Doub();
		Doub bk = new Doub();
		Doub bkden = 1.0;
		Doub bknum = new Doub();
		Doub bnrm = new Doub();
		Doub dxnrm = new Doub();
		Doub xnrm = new Doub();
		Doub zm1nrm = new Doub();
		Doub znrm = new Doub();
		final Doub EPS = 1.0e-14;
		Int j = new Int();
		Int n = b.argvalue.size();
		VecDoub p = new VecDoub(n);
		VecDoub pp = new VecDoub(n);
		VecDoub r = new VecDoub(n);
		VecDoub rr = new VecDoub(n);
		VecDoub z = new VecDoub(n);
		VecDoub zz = new VecDoub(n);
		iter.argvalue = 0;
		RefObject<VecDoub_O> tempRefObject = new RefObject<VecDoub_O>(r);
		atimes(x, tempRefObject, 0);
		r = tempRefObject.argvalue;
		for (j = 0;j<n;j++)
		{
			r[j]=b.argvalue[j]-r[j];
			rr[j]=r[j];
		}
		//atimes(r,rr,0);
		if (itol == 1)
		{
			bnrm = snrm(b, itol);
			RefObject<VecDoub_I> tempRefObject2 = new RefObject<VecDoub_I>(r);
			RefObject<VecDoub_O> tempRefObject3 = new RefObject<VecDoub_O>(z);
			asolve(tempRefObject2, tempRefObject3, 0);
			r = tempRefObject2.argvalue;
			z = tempRefObject3.argvalue;
		}
		else if (itol == 2)
		{
			RefObject<VecDoub_O> tempRefObject4 = new RefObject<VecDoub_O>(z);
			asolve(b, tempRefObject4, 0);
			z = tempRefObject4.argvalue;
			RefObject<VecDoub_I> tempRefObject5 = new RefObject<VecDoub_I>(z);
			bnrm = snrm(tempRefObject5, itol);
			z = tempRefObject5.argvalue;
			RefObject<VecDoub_I> tempRefObject6 = new RefObject<VecDoub_I>(r);
			RefObject<VecDoub_O> tempRefObject7 = new RefObject<VecDoub_O>(z);
			asolve(tempRefObject6, tempRefObject7, 0);
			r = tempRefObject6.argvalue;
			z = tempRefObject7.argvalue;
		}
		else if (itol == 3 || itol == 4)
		{
			RefObject<VecDoub_O> tempRefObject8 = new RefObject<VecDoub_O>(z);
			asolve(b, tempRefObject8, 0);
			z = tempRefObject8.argvalue;
			RefObject<VecDoub_I> tempRefObject9 = new RefObject<VecDoub_I>(z);
			bnrm = snrm(tempRefObject9, itol);
			z = tempRefObject9.argvalue;
			RefObject<VecDoub_I> tempRefObject10 = new RefObject<VecDoub_I>(r);
			RefObject<VecDoub_O> tempRefObject11 = new RefObject<VecDoub_O>(z);
			asolve(tempRefObject10, tempRefObject11, 0);
			r = tempRefObject10.argvalue;
			z = tempRefObject11.argvalue;
			RefObject<VecDoub_I> tempRefObject12 = new RefObject<VecDoub_I>(z);
			znrm = snrm(tempRefObject12, itol);
			z = tempRefObject12.argvalue;
		}
		else
			throw("illegal itol in linbcg");
		while (iter.argvalue < itmax)
		{
			++iter.argvalue;
			RefObject<VecDoub_I> tempRefObject13 = new RefObject<VecDoub_I>(rr);
			RefObject<VecDoub_O> tempRefObject14 = new RefObject<VecDoub_O>(zz);
			asolve(tempRefObject13, tempRefObject14, 1);
			rr = tempRefObject13.argvalue;
			zz = tempRefObject14.argvalue;
			for (bknum = 0.0,j = 0;j<n;j++)
				bknum += z[j]*rr[j];
			if (iter.argvalue == 1)
			{
				for (j = 0;j<n;j++)
				{
					p[j]=z[j];
					pp[j]=zz[j];
				}
			}
			else
			{
				bk = bknum/bkden;
				for (j = 0;j<n;j++)
				{
					p[j]=bk *p[j]+z[j];
					pp[j]=bk *pp[j]+zz[j];
				}
			}
			bkden = bknum;
			RefObject<VecDoub_I> tempRefObject15 = new RefObject<VecDoub_I>(p);
			RefObject<VecDoub_O> tempRefObject16 = new RefObject<VecDoub_O>(z);
			atimes(tempRefObject15, tempRefObject16, 0);
			p = tempRefObject15.argvalue;
			z = tempRefObject16.argvalue;
			for (akden = 0.0,j = 0;j<n;j++)
				akden += z[j]*pp[j];
			ak = bknum/akden;
			RefObject<VecDoub_I> tempRefObject17 = new RefObject<VecDoub_I>(pp);
			RefObject<VecDoub_O> tempRefObject18 = new RefObject<VecDoub_O>(zz);
			atimes(tempRefObject17, tempRefObject18, 1);
			pp = tempRefObject17.argvalue;
			zz = tempRefObject18.argvalue;
			for (j = 0;j<n;j++)
			{
				x.argvalue[j] += ak *p[j];
				r[j] -= ak *z[j];
				rr[j] -= ak *zz[j];
			}
			RefObject<VecDoub_I> tempRefObject19 = new RefObject<VecDoub_I>(r);
			RefObject<VecDoub_O> tempRefObject20 = new RefObject<VecDoub_O>(z);
			asolve(tempRefObject19, tempRefObject20, 0);
			r = tempRefObject19.argvalue;
			z = tempRefObject20.argvalue;
			if (itol == 1)
			{
				RefObject<VecDoub_I> tempRefObject21 = new RefObject<VecDoub_I>(r);
				err.argvalue = snrm(tempRefObject21, itol)/bnrm;
				r = tempRefObject21.argvalue;
			}
			else if (itol == 2)
			{
				RefObject<VecDoub_I> tempRefObject22 = new RefObject<VecDoub_I>(z);
				err.argvalue = snrm(tempRefObject22, itol)/bnrm;
				z = tempRefObject22.argvalue;
			}
			else if (itol == 3 || itol == 4)
			{
				zm1nrm = znrm;
				RefObject<VecDoub_I> tempRefObject23 = new RefObject<VecDoub_I>(z);
				znrm = snrm(tempRefObject23, itol);
				z = tempRefObject23.argvalue;
				if (Math.abs(zm1nrm-znrm) > EPS *znrm)
				{
					RefObject<VecDoub_I> tempRefObject24 = new RefObject<VecDoub_I>(p);
					dxnrm = Math.abs(ak)*snrm(tempRefObject24, itol);
					p = tempRefObject24.argvalue;
					err.argvalue = znrm/Math.abs(zm1nrm-znrm)*dxnrm;
				}
				else
				{
					err.argvalue = znrm/bnrm;
					continue;
				}
				xnrm = snrm(x, itol);
				if (err.argvalue <= 0.5 *xnrm)
					err.argvalue /= xnrm;
				else
				{
					err.argvalue = znrm/bnrm;
					continue;
				}
			}
			if (err.argvalue <= tol)
				break;
		}
	}
	public final Doub snrm(RefObject<VecDoub_I> sx, Int itol)
	{
		Int i = new Int();
		Int isamax = new Int();
		Int n = sx.argvalue.size();
		Doub ans = new Doub();
		if (itol <= 3)
		{
			ans = 0.0;
			for (i = 0;i<n;i++)
				ans += SQR(sx.argvalue[i]);
			return Math.sqrt(ans);
		}
		else
		{
			isamax = 0;
			for (i = 0;i<n;i++)
			{
				if (Math.abs(sx.argvalue[i]) > Math.abs(sx.argvalue[isamax]))
					isamax = i;
			}
			return Math.abs(sx.argvalue[isamax]);
		}
	}
}