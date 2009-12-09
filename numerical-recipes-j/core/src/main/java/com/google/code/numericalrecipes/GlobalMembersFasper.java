package com.google.code.numericalrecipes;
public class GlobalMembersFasper
{
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Int[] nfac = {0,1,1,2,6,24,120,720,5040,40320,362880};
	public static void spread(Doub y, RefObject<VecDoub_IO> yy, Doub x, Int m)
	{
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Int nfac[11]={0,1,1,2,6,24,120,720,5040,40320,362880};
		Int ihi = new Int();
		Int ilo = new Int();
		Int ix = new Int();
		Int j = new Int();
		Int nden = new Int();
		Int n = yy.argvalue.size();
		Doub fac = new Doub();
		if (m > 10)
			throw("factorial table too small in spread");
		ix = (Int)x;
		if (x == Doub(ix))
			yy.argvalue[ix-1] += y;
		else
		{
			ilo = MIN(MAX((Int)(x-0.5 *m),0),(Int)(n-m));
			ihi = ilo+m;
			nden = nfac[m];
			fac = x-ilo-1;
			for (j = ilo+1;j<ihi;j++)
				fac *= (x-j-1);
			yy.argvalue[ihi-1] += y *fac/(nden*(x-ihi));
			for (j = ihi-1;j>ilo;j--)
			{
				nden = (nden/(j-ilo))*(j-ihi);
				yy.argvalue[j-1] += y *fac/(nden*(x-j));
			}
		}
	}
	public static void fasper(RefObject<VecDoub_I> x, RefObject<VecDoub_I> y, Doub ofac, Doub hifac, RefObject<VecDoub_O> px, RefObject<VecDoub_O> py, RefObject<Int> nout, RefObject<Int> jmax, RefObject<Doub> prob)
	{
		final Int MACC = 4;
		Int j = new Int();
		Int k = new Int();
		Int nwk = new Int();
		Int nfreq = new Int();
		Int nfreqt = new Int();
		Int n = x.argvalue.size();
		Int np = px.argvalue.size();
		Doub ave = new Doub();
		Doub ck = new Doub();
		Doub ckk = new Doub();
		Doub cterm = new Doub();
		Doub cwt = new Doub();
		Doub den = new Doub();
		Doub df = new Doub();
		Doub effm = new Doub();
		Doub expy = new Doub();
		Doub fac = new Doub();
		Doub fndim = new Doub();
		Doub hc2wt = new Doub();
		Doub hs2wt = new Doub();
		Doub hypo = new Doub();
		Doub pmax = new Doub();
		Doub sterm = new Doub();
		Doub swt = new Doub();
		Doub var = new Doub();
		Doub xdif = new Doub();
		Doub xmax = new Doub();
		Doub xmin = new Doub();
		nout.argvalue = (Int)(0.5 *ofac *hifac *n);
		nfreqt = (Int)(ofac *hifac *n *MACC);
		nfreq = 64;
		while (nfreq < nfreqt)
			nfreq <<= 1;
		nwk = nfreq << 1;
		if (np < nout.argvalue)
		{
			px.argvalue.resize(nout.argvalue);
			py.argvalue.resize(nout.argvalue);
		}
		avevar(y.argvalue,ave,var);
		if (var == 0.0)
			throw("zero variance in fasper");
		xmin = x.argvalue[0];
		xmax = xmin;
		for (j = 1;j<n;j++)
		{
			if (x.argvalue[j] < xmin)
				xmin = x.argvalue[j];
			if (x.argvalue[j] > xmax)
				xmax = x.argvalue[j];
		}
		xdif = xmax-xmin;
		VecDoub wk1 = new VecDoub(nwk,0.);
		VecDoub wk2 = new VecDoub(nwk,0.);
		fac = nwk/(xdif *ofac);
		fndim = nwk;
		for (j = 0;j<n;j++)
		{
			ck = Math.IEEEremainder((x.argvalue[j]-xmin)*fac,fndim);
			ckk = 2.0*(ck++);
			ckk = Math.IEEEremainder(ckk,fndim);
			++ckk;
		RefObject<VecDoub_IO> tempRefObject = new RefObject<VecDoub_IO>(wk1);
			spread(y.argvalue[j]-ave, tempRefObject, ck, MACC);
			wk1 = tempRefObject.argvalue;
		RefObject<VecDoub_IO> tempRefObject2 = new RefObject<VecDoub_IO>(wk2);
			spread(1.0, tempRefObject2, ckk, MACC);
			wk2 = tempRefObject2.argvalue;
		}
		realft(wk1,1);
		realft(wk2,1);
		df = 1.0/(xdif *ofac);
		pmax = -1.0;
		for (k = 2,j = 0;j<nout.argvalue;j++,k+=2)
		{
			hypo = Math.sqrt(wk2[k]*wk2[k]+wk2[k+1]*wk2[k+1]);
			hc2wt = 0.5 *wk2[k]/hypo;
			hs2wt = 0.5 *wk2[k+1]/hypo;
			cwt = Math.sqrt(0.5+hc2wt);
			swt = SIGN(Math.sqrt(0.5-hc2wt),hs2wt);
			den = 0.5 *n+hc2wt *wk2[k]+hs2wt *wk2[k+1];
			cterm = SQR(cwt *wk1[k]+swt *wk1[k+1])/den;
			sterm = SQR(cwt *wk1[k+1]-swt *wk1[k])/(n-den);
			px.argvalue[j]=(j+1)*df;
			py.argvalue[j]=(cterm+sterm)/(2.0 *var);
			if (py.argvalue[j] > pmax)
				pmax = py.argvalue[jmax.argvalue = j];
		}
		expy = Math.exp(-pmax);
		effm = 2.0 *nout.argvalue/ofac;
		prob.argvalue = effm *expy;
		if (prob.argvalue > 0.01)
			prob.argvalue = 1.0-Math.pow(1.0-expy,effm);
	}
}