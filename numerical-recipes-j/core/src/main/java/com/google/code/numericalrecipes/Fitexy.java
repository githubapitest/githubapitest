package com.google.code.numericalrecipes;
public class Fitexy
{
	public Doub a = new Doub();
	public Doub b = new Doub();
	public Doub siga = new Doub();
	public Doub sigb = new Doub();
	public Doub chi2 = new Doub();
	public Doub q = new Doub();
	public Int ndat = new Int();
	public VecDoub xx = new VecDoub();
	public VecDoub yy = new VecDoub();
	public VecDoub sx = new VecDoub();
	public VecDoub sy = new VecDoub();
	public VecDoub ww = new VecDoub();
	public Doub aa = new Doub();
	public Doub offs = new Doub();

	public Fitexy(RefObject<VecDoub_I> x, RefObject<VecDoub_I> y, RefObject<VecDoub_I> sigx, RefObject<VecDoub_I> sigy)
	{
		ndat = x.argvalue.size();
		xx = ndat;
		yy = ndat;
		sx = ndat;
		sy = ndat;
		ww = ndat;
		final Doub POTN = 1.571000;
		final Doub BIG = 1.0e30;
		final Doub ACC = 1.0e-6;
		final Doub PI = 3.141592653589793238;
		Gamma gam = new Gamma();
		Brent brent = new Brent(ACC);
		Chixy chixy = new Chixy(xx,yy,sx,sy,ww,aa,offs);
		Int j = new Int();
		Doub amx = new Doub();
		Doub amn = new Doub();
		Doub varx = new Doub();
		Doub vary = new Doub();
		Doub[] ang = new Doub[7];
		Doub[] ch = new Doub[7];
		Doub scale = new Doub();
		Doub bmn = new Doub();
		Doub bmx = new Doub();
		Doub d1 = new Doub();
		Doub d2 = new Doub();
		Doub r2 = new Doub();
		Doub dum1 = new Doub();
		avevar(x.argvalue,dum1,varx);
		avevar(y.argvalue,dum1,vary);
		scale = Math.sqrt(varx/vary);
		for (j = 0;j<ndat;j++)
		{
			xx[j]=x.argvalue[j];
			yy[j]=y.argvalue[j]*scale;
			sx[j]=sigx.argvalue[j];
			sy[j]=sigy.argvalue[j]*scale;
			ww[j]=Math.sqrt(SQR(sx[j])+SQR(sy[j]));
		}
		Fitab fit = new Fitab(xx,yy,ww);
		b = fit.b;
		offs = ang[0] =0.0;
		ang[1] =Math.atan(b);
		ang[3] =0.0;
		ang[4] =ang[1];
		ang[5] =POTN;
		for (j = 3;j<6;j++)
			ch[j] =chixy(ang[j]);
		brent.bracket(ang[0],ang[1],chixy);
		ang[0] = brent.ax;
		ang[1] = brent.bx;
		ang[2] = brent.cx;
		ch[0] = brent.fa;
		ch[1] = brent.fb;
		ch[2] = brent.fc;
		b = brent.minimize(chixy);
		chi2 = chixy(b);
		a = aa;
		q = gam.gammq(0.5*(ndat-2),chi2 *0.5);
		r2 = 0.0;
		for (j = 0;j<ndat;j++)
			r2 += ww[j];
		r2 = 1.0/r2;
		bmx = bmn = BIG;
		offs = chi2+1.0;
		for (j = 0;j<6;j++)
		{
			if (ch[j] > offs)
			{
				d1 = Math.abs(ang[j]-b);
				while (d1 >= PI)
					d1 -= PI;
				d2 = PI-d1;
				if (ang[j] < b)
					SWAP(d1,d2);
				if (d1 < bmx)
					bmx = d1;
				if (d2 < bmn)
					bmn = d2;
			}
		}
		if (bmx < BIG)
		{
			bmx = zbrent(chixy,b,b+bmx,ACC)-b;
			amx = aa-a;
			bmn = zbrent(chixy,b,b-bmn,ACC)-b;
			amn = aa-a;
			sigb = Math.sqrt(0.5*(bmx *bmx+bmn *bmn))/(scale *SQR(Math.cos(b)));
			siga = Math.sqrt(0.5*(amx *amx+amn *amn)+r2)/scale;
		}
		else
			sigb = siga = BIG;
		a /= scale;
		b = Math.tan(b)/scale;
	}

	public static class Chixy
	{
		public VecDoub xx;
		public VecDoub yy;
		public VecDoub sx;
		public VecDoub sy;
		public VecDoub ww;
		public Doub aa;
		public Doub offs;

		public Chixy(RefObject<VecDoub> xxx, RefObject<VecDoub> yyy, RefObject<VecDoub> ssx, RefObject<VecDoub> ssy, RefObject<VecDoub> www, RefObject<Doub> aaa, RefObject<Doub> ooffs)
		{
			xx = xxx.argvalue;
			yy = yyy.argvalue;
			sx = ssx.argvalue;
			sy = ssy.argvalue;
			ww = www.argvalue;
			aa = aaa.argvalue;
			offs = ooffs.argvalue;
		}

//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
		Doub operator ()(Doub bang)
		{
			final Doub BIG = 1.0e30;
			Int j = new Int();
			Int nn = xx.size();
			Doub ans = new Doub();
			Doub avex = 0.0;
			Doub avey = 0.0;
			Doub sumw = 0.0;
			Doub b = new Doub();
			b = Math.tan(bang);
			for (j = 0;j<nn;j++)
			{
				ww[j] = SQR(b *sx[j])+SQR(sy[j]);
				sumw += (ww[j]=(ww[j] < 1.0/BIG != null ? BIG : 1.0/ww[j]));
				avex += ww[j]*xx[j];
				avey += ww[j]*yy[j];
			}
			avex /= sumw;
			avey /= sumw;
			aa = avey-b *avex;
			for (ans = -offs,j = 0;j<nn;j++)
				ans += ww[j]*SQR(yy[j]-aa-b *xx[j]);
			return ans;
		}
	}

}