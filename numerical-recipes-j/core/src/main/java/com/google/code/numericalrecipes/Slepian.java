package com.google.code.numericalrecipes;
public class Slepian implements Spectreg
{
	public Int jres = new Int();
	public Int kt = new Int();
	public MatDoub dpss = new MatDoub();
	public Doub p = new Doub();
	public Doub pp = new Doub();
	public Doub d = new Doub();
	public Doub dd = new Doub();
	public Slepian(Int em, Int jjres, Int kkt)
	{
		super(em);
		jres = jjres;
		kt = kkt;
		dpss = new MatDoub(kkt,2 *em);
		if (jres < 1 || kt >= 2 *jres)
			throw("kt too big or jres too small");
		filltable();
	}
	public final void filltable()
	{
		final Doub EPS = 1.e-10;
		final Doub PI = 4.*Math.atan(1.);
		Doub xx = new Doub();
		Doub xnew = new Doub();
		Doub xold = new Doub();
		Doub sw = new Doub();
		Doub ppp = new Doub();
		Doub ddd = new Doub();
		Doub sum = new Doub();
		Doub bet = new Doub();
		Doub ssub = new Doub();
		Doub ssup = new Doub();
		Doub u;
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int nl = new Int();
		VecDoub dg = new VecDoub(m2);
		VecDoub dgg = new VecDoub(m2);
		VecDoub gam = new VecDoub(m2);
		VecDoub sup = new VecDoub(m2-1);
		VecDoub sub = new VecDoub(m2-1);
		sw = 2.*SQR(Math.sin(jres *PI/m2));
		dg[0] = 0.25*(2 *m2+sw *SQR(m2-1.)-1.);
		for (i = 1;i<m2;i++)
		{
			dg[i] = 0.25*(sw *SQR(m2-1.-2 *i)+(2*(m2-i)-1.)*(2 *i+1.));
			sub[i-1] = sup[i-1] = -i*(Doub)(m2-i)/2.;
		}
		xx = -0.10859 - 0.068762/jres + 1.5692 *jres;
		xold = xx + 0.47276 + 0.20273/jres - 3.1387 *jres;
		for (k = 0; k<kt; k++)
		{
			u = dpss[k][0];
			for (i = 0;i<20;i++)
			{
				pp = 1.;
				p = dg[0] - xx;
				dd = 0.;
				d = -1.;
				for (j = 1; j<m2; j++)
				{
					ppp = pp;
					pp = p;
					ddd = dd;
					dd = d;
					p = pp*(dg[j]-xx) - ppp *SQR(sup[j-1]);
					d = -pp + dd*(dg[j]-xx) - ddd *SQR(sup[j-1]);
					if (Math.abs(p)>1.e30)
						renorm(-100);
					else if (Math.abs(p)<1.e-30)
						renorm(100);
				}
				xnew = xx - p/d;
				if (Math.abs(xx-xnew) < EPS *Math.abs(xnew))
					break;
				xx = xnew;
			}
			xx = xnew - (xold - xnew);
			xold = xnew;
			for (i = 0;i<m2;i++)
				dgg[i] = dg[i] - xnew;
			nl = m2/3;
			dgg[nl] = 1.;
			ssup = sup[nl];
			ssub = sub[nl-1];
			u[0] = sup[nl] = sub[nl-1] = 0.;
			bet = dgg[0];
			for (i = 1; i<m2; i++)
			{
				gam[i] = sup[i-1]/bet;
				bet = dgg[i] - sub[i-1]*gam[i];
				u[i] = ((i == nl != null? 1.: 0.) - sub[i-1]*u[i-1])/bet;
			}
			for (i = m2-2; i>=0; i--)
				u[i] -= gam[i+1]*u[i+1];
			sup[nl] = ssup;
			sub[nl-1] = ssub;
			sum = 0.;
			for (i = 0; i<m2; i++)
				sum += SQR(u[i]);
			sum = (u[3] > 0.)? Math.sqrt(sum) : -Math.sqrt(sum);
			for (i = 0; i<m2; i++)
				u[i] /= sum;
		}
	}
	public final void renorm(Int n)
	{
		p = ldexp(p,n);
		pp = ldexp(pp,n);
		d = ldexp(d,n);
		dd = ldexp(dd,n);
	}
	public static class Slepwindow
	{
		public Int k = new Int();
		public MatDoub dps;
		public Slepwindow(Int kkt, RefObject<MatDoub> dpss)
		{
			k = kkt;
			dps = dpss.argvalue;
		}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
		Doub operator ()(Int j, Int n)
		{
			return dps[k][j];
		}
	}

	public final void adddataseg(RefObject<VecDoub_I> data)
	{
		Int k = new Int();
		if (data.argvalue.size() != m2)
			throw("wrong size data segment");
		for (k = 0;k<kt;k++)
		{
			Slepwindow window = new Slepwindow(k,dpss);
			super.adddataseg(data.argvalue,window);
		}
	}
}