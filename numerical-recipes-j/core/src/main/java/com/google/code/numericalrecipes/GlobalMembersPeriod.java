package com.google.code.numericalrecipes;
public class GlobalMembersPeriod
{
	public static void period(RefObject<VecDoub_I> x, RefObject<VecDoub_I> y, Doub ofac, Doub hifac, RefObject<VecDoub_O> px, RefObject<VecDoub_O> py, RefObject<Int> nout, RefObject<Int> jmax, RefObject<Doub> prob)
	{
		final Doub TWOPI = 6.283185307179586476;
		Int i = new Int();
		Int j = new Int();
		Int n = x.argvalue.size();
		Int np = px.argvalue.size();
		Doub ave = new Doub();
		Doub c = new Doub();
		Doub cc = new Doub();
		Doub cwtau = new Doub();
		Doub effm = new Doub();
		Doub expy = new Doub();
		Doub pnow = new Doub();
		Doub pymax = new Doub();
		Doub s = new Doub();
		Doub ss = new Doub();
		Doub sumc = new Doub();
		Doub sumcy = new Doub();
		Doub sums = new Doub();
		Doub sumsh = new Doub();
		Doub sumsy = new Doub();
		Doub swtau = new Doub();
		Doub var = new Doub();
		Doub wtau = new Doub();
		Doub xave = new Doub();
		Doub xdif = new Doub();
		Doub xmax = new Doub();
		Doub xmin = new Doub();
		Doub yy = new Doub();
		Doub arg = new Doub();
		Doub wtemp = new Doub();
		VecDoub wi = new VecDoub(n);
		VecDoub wpi = new VecDoub(n);
		VecDoub wpr = new VecDoub(n);
		VecDoub wr = new VecDoub(n);
		nout.argvalue = (Int)(0.5 *ofac *hifac *n);
		if (np < nout.argvalue)
		{
			px.argvalue.resize(nout.argvalue);
			py.argvalue.resize(nout.argvalue);
		}
		avevar(y.argvalue,ave,var);
		if (var == 0.0)
			throw("zero variance in period");
		xmax = xmin = x.argvalue[0];
		for (j = 0;j<n;j++)
		{
			if (x.argvalue[j] > xmax)
				xmax = x.argvalue[j];
			if (x.argvalue[j] < xmin)
				xmin = x.argvalue[j];
		}
		xdif = xmax-xmin;
		xave = 0.5*(xmax+xmin);
		pymax = 0.0;
		pnow = 1.0/(xdif *ofac);
		for (j = 0;j<n;j++)
		{
			arg = TWOPI*((x.argvalue[j]-xave)*pnow);
			wpr[j]= -2.0 *SQR(Math.sin(0.5 *arg));
			wpi[j]=Math.sin(arg);
			wr[j]=Math.cos(arg);
			wi[j]=wpi[j];
		}
		for (i = 0;i<nout.argvalue;i++)
		{
			px.argvalue[i]=pnow;
			sumsh = sumc = 0.0;
			for (j = 0;j<n;j++)
			{
				c = wr[j];
				s = wi[j];
				sumsh += s *c;
				sumc += (c-s)*(c+s);
			}
			wtau = 0.5 *Math.atan2(2.0 *sumsh,sumc);
			swtau = Math.sin(wtau);
			cwtau = Math.cos(wtau);
			sums = sumc = sumsy = sumcy = 0.0;
			for (j = 0;j<n;j++)
			{
				s = wi[j];
				c = wr[j];
				ss = s *cwtau-c *swtau;
				cc = c *cwtau+s *swtau;
				sums += ss *ss;
				sumc += cc *cc;
				yy = y.argvalue[j]-ave;
				sumsy += yy *ss;
				sumcy += yy *cc;
				wr[j]=((wtemp = wr[j])*wpr[j]-wi[j]*wpi[j])+wr[j];
				wi[j]=(wi[j]*wpr[j]+wtemp *wpi[j])+wi[j];
			}
			py.argvalue[i]=0.5*(sumcy *sumcy/sumc+sumsy *sumsy/sums)/var;
			if (py.argvalue[i] >= pymax)
				pymax = py.argvalue[jmax.argvalue = i];
			pnow += 1.0/(ofac *xdif);
		}
		expy = Math.exp(-pymax);
		effm = 2.0 *nout.argvalue/ofac;
		prob.argvalue = effm *expy;
		if (prob.argvalue > 0.01)
			prob.argvalue = 1.0-Math.pow(1.0-expy,effm);
	}
}