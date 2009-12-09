package com.google.code.numericalrecipes;
public class Fitab
{
	public Int ndata = new Int();
	public Doub a = new Doub();
	public Doub b = new Doub();
	public Doub siga = new Doub();
	public Doub sigb = new Doub();
	public Doub chi2 = new Doub();
	public Doub q = new Doub();
	public Doub sigdat = new Doub();
	public VecDoub_I x;
	public VecDoub_I y;
	public VecDoub_I sig;

	public Fitab(RefObject<VecDoub_I> xx, RefObject<VecDoub_I> yy, RefObject<VecDoub_I> ssig)
	{
		ndata = xx.argvalue.size();
		x = xx.argvalue;
		y = yy.argvalue;
		sig = ssig.argvalue;
		chi2 = 0.;
		q = 1.;
		sigdat = 0.;
		Gamma gam = new Gamma();
		Int i = new Int();
		Doub ss = 0.;
		Doub sx = 0.;
		Doub sy = 0.;
		Doub st2 = 0.;
		Doub t = new Doub();
		Doub wt = new Doub();
		Doub sxoss = new Doub();
		b = 0.0;
		for (i = 0;i<ndata;i++)
		{
			wt = 1.0/SQR(sig[i]);
			ss += wt;
			sx += x[i]*wt;
			sy += y[i]*wt;
		}
		sxoss = sx/ss;
		for (i = 0;i<ndata;i++)
		{
			t = (x[i]-sxoss)/sig[i];
			st2 += t *t;
			b += t *y[i]/sig[i];
		}
		b /= st2;
		a = (sy-sx *b)/ss;
		siga = Math.sqrt((1.0+sx *sx/(ss *st2))/ss);
		sigb = Math.sqrt(1.0/st2);
		for (i = 0;i<ndata;i++)
			chi2 += SQR((y[i]-a-b *x[i])/sig[i]);
		if (ndata>2)
			q = gam.gammq(0.5*(ndata-2),0.5 *chi2);
	}

	public Fitab(RefObject<VecDoub_I> xx, RefObject<VecDoub_I> yy)
	{
		ndata = xx.argvalue.size();
		x = xx.argvalue;
		y = yy.argvalue;
		sig = xx.argvalue;
		chi2 = 0.;
		q = 1.;
		sigdat = 0.;
		Int i = new Int();
		Doub ss = new Doub();
		Doub sx = 0.;
		Doub sy = 0.;
		Doub st2 = 0.;
		Doub t = new Doub();
		Doub sxoss = new Doub();
		b = 0.0;
		for (i = 0;i<ndata;i++)
		{
			sx += x[i];
			sy += y[i];
		}
		ss = ndata;
		sxoss = sx/ss;
		for (i = 0;i<ndata;i++)
		{
			t = x[i]-sxoss;
			st2 += t *t;
			b += t *y[i];
		}
		b /= st2;
		a = (sy-sx *b)/ss;
		siga = Math.sqrt((1.0+sx *sx/(ss *st2))/ss);
		sigb = Math.sqrt(1.0/st2);
		for (i = 0;i<ndata;i++)
			chi2 += SQR(y[i]-a-b *x[i]);
		if (ndata > 2)
			sigdat = Math.sqrt(chi2/(ndata-2));
		siga *= sigdat;
		sigb *= sigdat;
	}
}