package com.google.code.numericalrecipes;
public class GlobalMembersGauss_wgts
{
	public static void gauleg(Doub x1, Doub x2, RefObject<VecDoub_O> x, RefObject<VecDoub_O> w)
	{
		final Doub EPS = 1.0e-14;
		Doub z1 = new Doub();
		Doub z = new Doub();
		Doub xm = new Doub();
		Doub xl = new Doub();
		Doub pp = new Doub();
		Doub p3 = new Doub();
		Doub p2 = new Doub();
		Doub p1 = new Doub();
		Int n = x.argvalue.size();
		Int m = (n+1)/2;
		xm = 0.5*(x2+x1);
		xl = 0.5*(x2-x1);
		for (Int i = 0;i<m;i++)
		{
			z = Math.cos(3.141592654*(i+0.75)/(n+0.5));
			do
			{
				p1 = 1.0;
				p2 = 0.0;
				for (Int j = 0;j<n;j++)
				{
					p3 = p2;
					p2 = p1;
					p1 = ((2.0 *j+1.0)*z *p2-j *p3)/(j+1);
				}
				pp = n*(z *p1-p2)/(z *z-1.0);
				z1 = z;
				z = z1-p1/pp;
			} while (Math.abs(z-z1) > EPS);
			x.argvalue[i]=xm-xl *z;
			x.argvalue[n-1-i]=xm+xl *z;
			w.argvalue[i]=2.0 *xl/((1.0-z *z)*pp *pp);
			w.argvalue[n-1-i]=w.argvalue[i];
		}
	}
	public static void gaulag(RefObject<VecDoub_O> x, RefObject<VecDoub_O> w, Doub alf)
	{
		final Int MAXIT = 10;
		final Doub EPS = 1.0e-14;
		Int i = new Int();
		Int its = new Int();
		Int j = new Int();
		Doub ai = new Doub();
		Doub p1 = new Doub();
		Doub p2 = new Doub();
		Doub p3 = new Doub();
		Doub pp = new Doub();
		Doub z = new Doub();
		Doub z1 = new Doub();
		Int n = x.argvalue.size();
		for (i = 0;i<n;i++)
		{
			if (i == 0)
			{
				z = (1.0+alf)*(3.0+0.92 *alf)/(1.0+2.4 *n+1.8 *alf);
			}
			else if (i == 1)
			{
				z += (15.0+6.25 *alf)/(1.0+0.9 *alf+2.5 *n);
			}
			else
			{
				ai = i-1;
				z += ((1.0+2.55 *ai)/(1.9 *ai)+1.26 *ai *alf/ (1.0+3.5 *ai))*(z-x.argvalue[i-2])/(1.0+0.3 *alf);
			}
			for (its = 0;its<MAXIT;its++)
			{
				p1 = 1.0;
				p2 = 0.0;
				for (j = 0;j<n;j++)
				{
					p3 = p2;
					p2 = p1;
					p1 = ((2 *j+1+alf-z)*p2-(j+alf)*p3)/(j+1);
				}
				pp = (n *p1-(n+alf)*p2)/z;
				z1 = z;
				z = z1-p1/pp;
				if (Math.abs(z-z1) <= EPS)
					break;
			}
			if (its >= MAXIT)
				throw("too many iterations in gaulag");
			x.argvalue[i]=z;
			w.argvalue[i] = -Math.exp(gammln(alf+n)-gammln(Doub(n)))/(pp *n *p2);
		}
	}
	public static void gauher(RefObject<VecDoub_O> x, RefObject<VecDoub_O> w)
	{
		final Doub EPS = 1.0e-14;
		final Doub PIM4 = 0.7511255444649425;
		final Int MAXIT = 10;
		Int i = new Int();
		Int its = new Int();
		Int j = new Int();
		Int m = new Int();
		Doub p1 = new Doub();
		Doub p2 = new Doub();
		Doub p3 = new Doub();
		Doub pp = new Doub();
		Doub z = new Doub();
		Doub z1 = new Doub();
		Int n = x.argvalue.size();
		m = (n+1)/2;
		for (i = 0;i<m;i++)
		{
			if (i == 0)
			{
				z = Math.sqrt(Doub(2 *n+1))-1.85575 *Math.pow(Doub(2 *n+1),-0.16667);
			}
			else if (i == 1)
			{
				z -= 1.14 *Math.pow(Doub(n),0.426)/z;
			}
			else if (i == 2)
			{
				z = 1.86 *z-0.86 *x.argvalue[0];
			}
			else if (i == 3)
			{
				z = 1.91 *z-0.91 *x.argvalue[1];
			}
			else
			{
				z = 2.0 *z-x.argvalue[i-2];
			}
			for (its = 0;its<MAXIT;its++)
			{
				p1 = PIM4;
				p2 = 0.0;
				for (j = 0;j<n;j++)
				{
					p3 = p2;
					p2 = p1;
					p1 = z *Math.sqrt(2.0/(j+1))*p2-Math.sqrt(Doub(j)/(j+1))*p3;
				}
				pp = Math.sqrt(Doub(2 *n))*p2;
				z1 = z;
				z = z1-p1/pp;
				if (Math.abs(z-z1) <= EPS)
					break;
			}
			if (its >= MAXIT)
				throw("too many iterations in gauher");
			x.argvalue[i]=z;
			x.argvalue[n-1-i] = -z;
			w.argvalue[i]=2.0/(pp *pp);
			w.argvalue[n-1-i]=w.argvalue[i];
		}
	}
	public static void gaujac(RefObject<VecDoub_O> x, RefObject<VecDoub_O> w, Doub alf, Doub bet)
	{
		final Int MAXIT = 10;
		final Doub EPS = 1.0e-14;
		Int i = new Int();
		Int its = new Int();
		Int j = new Int();
		Doub alfbet = new Doub();
		Doub an = new Doub();
		Doub bn = new Doub();
		Doub r1 = new Doub();
		Doub r2 = new Doub();
		Doub r3 = new Doub();
		Doub a = new Doub();
		Doub b = new Doub();
		Doub c = new Doub();
		Doub p1 = new Doub();
		Doub p2 = new Doub();
		Doub p3 = new Doub();
		Doub pp = new Doub();
		Doub temp = new Doub();
		Doub z = new Doub();
		Doub z1 = new Doub();
		Int n = x.argvalue.size();
		for (i = 0;i<n;i++)
		{
			if (i == 0)
			{
				an = alf/n;
				bn = bet/n;
				r1 = (1.0+alf)*(2.78/(4.0+n *n)+0.768 *an/n);
				r2 = 1.0+1.48 *an+0.96 *bn+0.452 *an *an+0.83 *an *bn;
				z = 1.0-r1/r2;
			}
			else if (i == 1)
			{
				r1 = (4.1+alf)/((1.0+alf)*(1.0+0.156 *alf));
				r2 = 1.0+0.06*(n-8.0)*(1.0+0.12 *alf)/n;
				r3 = 1.0+0.012 *bet*(1.0+0.25 *Math.abs(alf))/n;
				z -= (1.0-z)*r1 *r2 *r3;
			}
			else if (i == 2)
			{
				r1 = (1.67+0.28 *alf)/(1.0+0.37 *alf);
				r2 = 1.0+0.22*(n-8.0)/n;
				r3 = 1.0+8.0 *bet/((6.28+bet)*n *n);
				z -= (x.argvalue[0]-z)*r1 *r2 *r3;
			}
			else if (i == n-2)
			{
				r1 = (1.0+0.235 *bet)/(0.766+0.119 *bet);
				r2 = 1.0/(1.0+0.639*(n-4.0)/(1.0+0.71*(n-4.0)));
				r3 = 1.0/(1.0+20.0 *alf/((7.5+alf)*n *n));
				z += (z-x.argvalue[n-4])*r1 *r2 *r3;
			}
			else if (i == n-1)
			{
				r1 = (1.0+0.37 *bet)/(1.67+0.28 *bet);
				r2 = 1.0/(1.0+0.22*(n-8.0)/n);
				r3 = 1.0/(1.0+8.0 *alf/((6.28+alf)*n *n));
				z += (z-x.argvalue[n-3])*r1 *r2 *r3;
			}
			else
			{
				z = 3.0 *x.argvalue[i-1]-3.0 *x.argvalue[i-2]+x.argvalue[i-3];
			}
			alfbet = alf+bet;
			for (its = 1;its<=MAXIT;its++)
			{
				temp = 2.0+alfbet;
				p1 = (alf-bet+temp *z)/2.0;
				p2 = 1.0;
				for (j = 2;j<=n;j++)
				{
					p3 = p2;
					p2 = p1;
					temp = 2 *j+alfbet;
					a = 2 *j*(j+alfbet)*(temp-2.0);
					b = (temp-1.0)*(alf *alf-bet *bet+temp*(temp-2.0)*z);
					c = 2.0*(j-1+alf)*(j-1+bet)*temp;
					p1 = (b *p2-c *p3)/a;
				}
				pp = (n*(alf-bet-temp *z)*p1+2.0*(n+alf)*(n+bet)*p2)/(temp*(1.0-z *z));
				z1 = z;
				z = z1-p1/pp;
				if (Math.abs(z-z1) <= EPS)
					break;
			}
			if (its > MAXIT)
				throw("too many iterations in gaujac");
			x.argvalue[i]=z;
			w.argvalue[i]=Math.exp(gammln(alf+n)+gammln(bet+n)-gammln(n+1.0)- gammln(n+alfbet+1.0))*temp *Math.pow(2.0,alfbet)/(pp *p2);
		}
	}
}