package com.google.code.numericalrecipes;
public class GlobalMembersKstests_2d
{
	public static void quadct(Doub x, Doub y, RefObject<VecDoub_I> xx, RefObject<VecDoub_I> yy, RefObject<Doub> fa, RefObject<Doub> fb, RefObject<Doub> fc, RefObject<Doub> fd)
	{
		Int k = new Int();
		Int na = new Int();
		Int nb = new Int();
		Int nc = new Int();
		Int nd = new Int();
		Int nn = xx.argvalue.size();
		Doub ff = new Doub();
		na = nb = nc = nd = 0;
		for (k = 0;k<nn;k++)
		{
			if (yy.argvalue[k] == y && xx.argvalue[k] == x)
				continue;
			if (yy.argvalue[k] > y)
				xx.argvalue[k] > x != null ? ++na : ++nb;
			else
				xx.argvalue[k] > x != null ? ++nd : ++nc;
		}
		ff = 1.0/nn;
		fa.argvalue = ff *na;
		fb.argvalue = ff *nb;
		fc.argvalue = ff *nc;
		fd.argvalue = ff *nd;
	}
	public static void ks2d1s(RefObject<VecDoub_I> x1, RefObject<VecDoub_I> y1, void quadvl(Doub, const Doub, Doub, Doub &, Doub &, Doub &), RefObject<Doub> d1, RefObject<Doub> prob)
	{
		Int j = new Int();
		Int n1 = x1.argvalue.size();
		Doub dum = new Doub();
		Doub dumm = new Doub();
		Doub fa = new Doub();
		Doub fb = new Doub();
		Doub fc = new Doub();
		Doub fd = new Doub();
		Doub ga = new Doub();
		Doub gb = new Doub();
		Doub gc = new Doub();
		Doub gd = new Doub();
		Doub r1 = new Doub();
		Doub rr = new Doub();
		Doub sqen = new Doub();
		KSdist ks = new KSdist();
		d1.argvalue = 0.0;
		for (j = 0;j<n1;j++)
		{
		RefObject<Doub> tempRefObject = new RefObject<Doub>(fa);
		RefObject<Doub> tempRefObject2 = new RefObject<Doub>(fb);
		RefObject<Doub> tempRefObject3 = new RefObject<Doub>(fc);
		RefObject<Doub> tempRefObject4 = new RefObject<Doub>(fd);
			quadct(x1.argvalue[j], y1.argvalue[j], x1, y1, tempRefObject, tempRefObject2, tempRefObject3, tempRefObject4);
			fa = tempRefObject.argvalue;
			fb = tempRefObject2.argvalue;
			fc = tempRefObject3.argvalue;
			fd = tempRefObject4.argvalue;
			quadvl(x1.argvalue[j],y1.argvalue[j],ga,gb,gc,gd);
			if (fa > ga)
				fa += 1.0/n1;
			if (fb > gb)
				fb += 1.0/n1;
			if (fc > gc)
				fc += 1.0/n1;
			if (fd > gd)
				fd += 1.0/n1;
			d1.argvalue = MAX(d1.argvalue,Math.abs(fa-ga));
			d1.argvalue = MAX(d1.argvalue,Math.abs(fb-gb));
			d1.argvalue = MAX(d1.argvalue,Math.abs(fc-gc));
			d1.argvalue = MAX(d1.argvalue,Math.abs(fd-gd));
		}
		pearsn(x1.argvalue,y1.argvalue,r1,dum,dumm);
		sqen = Math.sqrt(Doub(n1));
		rr = Math.sqrt(1.0-r1 *r1);
		prob.argvalue = ks.qks(d1.argvalue *sqen/(1.0+rr*(0.25-0.75/sqen)));
	}
	public static void ks2d2s(RefObject<VecDoub_I> x1, RefObject<VecDoub_I> y1, RefObject<VecDoub_I> x2, RefObject<VecDoub_I> y2, RefObject<Doub> d, RefObject<Doub> prob)
	{
		Int j = new Int();
		Int n1 = x1.argvalue.size();
		Int n2 = x2.argvalue.size();
		Doub d1 = new Doub();
		Doub d2 = new Doub();
		Doub dum = new Doub();
		Doub dumm = new Doub();
		Doub fa = new Doub();
		Doub fb = new Doub();
		Doub fc = new Doub();
		Doub fd = new Doub();
		Doub ga = new Doub();
		Doub gb = new Doub();
		Doub gc = new Doub();
		Doub gd = new Doub();
		Doub r1 = new Doub();
		Doub r2 = new Doub();
		Doub rr = new Doub();
		Doub sqen = new Doub();
		KSdist ks = new KSdist();
		d1 = 0.0;
		for (j = 0;j<n1;j++)
		{
		RefObject<Doub> tempRefObject = new RefObject<Doub>(fa);
		RefObject<Doub> tempRefObject2 = new RefObject<Doub>(fb);
		RefObject<Doub> tempRefObject3 = new RefObject<Doub>(fc);
		RefObject<Doub> tempRefObject4 = new RefObject<Doub>(fd);
			quadct(x1.argvalue[j], y1.argvalue[j], x1, y1, tempRefObject, tempRefObject2, tempRefObject3, tempRefObject4);
			fa = tempRefObject.argvalue;
			fb = tempRefObject2.argvalue;
			fc = tempRefObject3.argvalue;
			fd = tempRefObject4.argvalue;
		RefObject<Doub> tempRefObject5 = new RefObject<Doub>(ga);
		RefObject<Doub> tempRefObject6 = new RefObject<Doub>(gb);
		RefObject<Doub> tempRefObject7 = new RefObject<Doub>(gc);
		RefObject<Doub> tempRefObject8 = new RefObject<Doub>(gd);
			quadct(x1.argvalue[j], y1.argvalue[j], x2, y2, tempRefObject5, tempRefObject6, tempRefObject7, tempRefObject8);
			ga = tempRefObject5.argvalue;
			gb = tempRefObject6.argvalue;
			gc = tempRefObject7.argvalue;
			gd = tempRefObject8.argvalue;
			if (fa > ga)
				fa += 1.0/n1;
			if (fb > gb)
				fb += 1.0/n1;
			if (fc > gc)
				fc += 1.0/n1;
			if (fd > gd)
				fd += 1.0/n1;
			d1 = MAX(d1,Math.abs(fa-ga));
			d1 = MAX(d1,Math.abs(fb-gb));
			d1 = MAX(d1,Math.abs(fc-gc));
			d1 = MAX(d1,Math.abs(fd-gd));
		}
		d2 = 0.0;
		for (j = 0;j<n2;j++)
		{
		RefObject<Doub> tempRefObject9 = new RefObject<Doub>(fa);
		RefObject<Doub> tempRefObject10 = new RefObject<Doub>(fb);
		RefObject<Doub> tempRefObject11 = new RefObject<Doub>(fc);
		RefObject<Doub> tempRefObject12 = new RefObject<Doub>(fd);
			quadct(x2.argvalue[j], y2.argvalue[j], x1, y1, tempRefObject9, tempRefObject10, tempRefObject11, tempRefObject12);
			fa = tempRefObject9.argvalue;
			fb = tempRefObject10.argvalue;
			fc = tempRefObject11.argvalue;
			fd = tempRefObject12.argvalue;
		RefObject<Doub> tempRefObject13 = new RefObject<Doub>(ga);
		RefObject<Doub> tempRefObject14 = new RefObject<Doub>(gb);
		RefObject<Doub> tempRefObject15 = new RefObject<Doub>(gc);
		RefObject<Doub> tempRefObject16 = new RefObject<Doub>(gd);
			quadct(x2.argvalue[j], y2.argvalue[j], x2, y2, tempRefObject13, tempRefObject14, tempRefObject15, tempRefObject16);
			ga = tempRefObject13.argvalue;
			gb = tempRefObject14.argvalue;
			gc = tempRefObject15.argvalue;
			gd = tempRefObject16.argvalue;
			if (ga > fa)
				ga += 1.0/n1;
			if (gb > fb)
				gb += 1.0/n1;
			if (gc > fc)
				gc += 1.0/n1;
			if (gd > fd)
				gd += 1.0/n1;
			d2 = MAX(d2,Math.abs(fa-ga));
			d2 = MAX(d2,Math.abs(fb-gb));
			d2 = MAX(d2,Math.abs(fc-gc));
			d2 = MAX(d2,Math.abs(fd-gd));
		}
		d.argvalue = 0.5*(d1+d2);
		sqen = Math.sqrt(n1 *n2/Doub(n1+n2));
		pearsn(x1.argvalue,y1.argvalue,r1,dum,dumm);
		pearsn(x2.argvalue,y2.argvalue,r2,dum,dumm);
		rr = Math.sqrt(1.0-0.5*(r1 *r1+r2 *r2));
		prob.argvalue = ks.qks(d.argvalue *sqen/(1.0+rr*(0.25-0.75/sqen)));
	}
}