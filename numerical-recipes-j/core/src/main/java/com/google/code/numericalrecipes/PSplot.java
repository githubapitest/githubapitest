package com.google.code.numericalrecipes;
public class PSplot implements PSpage
{

	public Doub pll = new Doub();
	public Doub qll = new Doub();
	public Doub pur = new Doub();
	public Doub qur = new Doub();
	public Doub xll = new Doub();
	public Doub yll = new Doub();
	public Doub xur = new Doub();
	public Doub yur = new Doub();
	public VecDoub xbox = new VecDoub();
	public VecDoub ybox = new VecDoub();
	public Doub majticsz = new Doub();
	public Doub minticsz = new Doub();

	public PSplot(RefObject<PSpage> page, Doub ppll, Doub ppur, Doub qqll, Doub qqur)
	{
		RefObject<FILE> tempRefObject = new RefObject<FILE>(page.argvalue.PLT);
		RefObject<String> tempRefObject2 = new RefObject<String>(page.argvalue.file);
		super(tempRefObject, tempRefObject2);
		page.argvalue.argvalue.PLT = tempRefObject.argvalue;
		page.argvalue.argvalue.file = tempRefObject2.argvalue;
		pll = ppll;
		qll = qqll;
		pur = ppur;
		qur = qqur;
		xll = ppll;
		yll = qqll;
		xur = ppur;
		yur = qqur;
		xbox = 4;
		ybox = 4;
		majticsz = 8.;
		minticsz = 4.;
			fontname = page.argvalue.fontname;
			fontsize = page.argvalue.fontsize;
			setlimits(xll, xur, yll, yur);
		}

	public final Doub p(Doub x)
	{
		return pll + (pur-pll)*(x-xll)/(xur-xll);
	}
	public final Doub q(Doub y)
	{
		return qll + (qur-qll)*(y-yll)/(yur-yll);
	}

	public final void setlimits(Doub xxll, Doub xxur, Doub yyll, Doub yyur)
	{
		xbox[0] = xbox[3] = xll = xxll;
		ybox[0] = ybox[1] = yll = yyll;
		xbox[1] = xbox[2] = xur = xxur;
		ybox[2] = ybox[3] = yur = yyur;
	}

	public final void lineseg(Doub xs, Doub ys, Doub xf, Doub yf)
	{
		super.lineseg(p(xs), q(ys), p(xf), q(yf));
	}

	public final void polyline(RefObject<VecDoub> x, RefObject<VecDoub> y, Int close, Int fill)
	{
		polyline(x, y, close, fill, 0);
	}
	public final void polyline(RefObject<VecDoub> x, RefObject<VecDoub> y, Int close)
	{
		polyline(x, y, close, 0, 0);
	}
	public final void polyline(RefObject<VecDoub> x, RefObject<VecDoub> y)
	{
		polyline(x, y, 0, 0, 0);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void polyline(VecDoub &x, VecDoub &y, Int close=0, Int fill=0, Int clip=0)
	public final void polyline(RefObject<VecDoub> x, RefObject<VecDoub> y, Int close, Int fill, Int clip)
	{
		Int i = new Int();
		VecDoub xx = new VecDoub(x.argvalue);
		VecDoub yy = new VecDoub(y.argvalue);
		for (i = 0;i<x.argvalue.size();i++)
			xx[i] = p(x.argvalue[i]);
		for (i = 0;i<y.argvalue.size();i++)
			yy[i] = q(y.argvalue[i]);
		RefObject<VecDoub> tempRefObject = new RefObject<VecDoub>(xx);
		RefObject<VecDoub> tempRefObject2 = new RefObject<VecDoub>(yy);
		super.polyline(tempRefObject, tempRefObject2, close, fill, clip);
		xx = tempRefObject.argvalue;
		yy = tempRefObject2.argvalue;
	}

	public final void dot(Doub x, Doub y)
	{
		dot(x, y, 2.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void dot(Doub x, Doub y, Doub size=2.)
	public final void dot(Doub x, Doub y, Doub size)
	{
		super.pointsymbol(p(x), q(y), 108, size);
	}

	public final void pointsymbol(Doub x, Doub y, Int num, double size)
	{
		super.pointsymbol(p(x), q(y), num, size);
	}

	public final void lineplot(RefObject<VecDoub> x, RefObject<VecDoub> y)
	{
		polyline(x.argvalue,y.argvalue);
	}

	public final void frame()
	{
		polyline(xbox,ybox,1,0);
	}

	public final void clear()
	{
		gsave();
		setgray(1.);
		polyline(xbox,ybox,1,1);
		grestore();
	}

	public final void clip()
	{
		gsave();
		RefObject<VecDoub> tempRefObject = new RefObject<VecDoub>(xbox);
		RefObject<VecDoub> tempRefObject2 = new RefObject<VecDoub>(ybox);
		polyline(tempRefObject, tempRefObject2, 1, 0, 1);
		xbox = tempRefObject.argvalue;
		ybox = tempRefObject2.argvalue;
	}

	public final void clip(RefObject<VecDoub> x, RefObject<VecDoub> y)
	{
		gsave();
		polyline(x, y, 1, 0, 1);
	}

	public final void unclip()
	{
		grestore();
	}

	public final void xlabel(RefObject<String> text)
	{
		putctext(text.argvalue,0.5*(pll+pur),qll-2.*fontsize-8.);
	}

	public final void ylabel(RefObject<String> text)
	{
		putctext(text.argvalue,pll-3.*fontsize-8.,0.5*(qll+qur),90.);
	}

	public final void label(RefObject<String> text, double x, double y)
	{
		label(text, x, y, 0.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void label(byte *text, double x, double y, double rot=0.)
	public final void label(RefObject<String> text, double x, double y, double rot)
	{
		puttext(text.argvalue,p(x),q(y),rot);
	}

	public final void scalestr(RefObject<String> str, double x)
	{
		if (Math.abs(x) < 1.e-15)
			x = 0.;
		String.format(str.argvalue,"%g",x);
	}

	public final void scales(Doub xmajd, Doub xmind, Doub ymajd, Doub ymind, Int dox, Int doy, Int doxx)
	{
		scales(xmajd, xmind, ymajd, ymind, dox, doy, doxx, 1);
	}
	public final void scales(Doub xmajd, Doub xmind, Doub ymajd, Doub ymind, Int dox, Int doy)
	{
		scales(xmajd, xmind, ymajd, ymind, dox, doy, 1, 1);
	}
	public final void scales(Doub xmajd, Doub xmind, Doub ymajd, Doub ymind, Int dox)
	{
		scales(xmajd, xmind, ymajd, ymind, dox, 2, 1, 1);
	}
	public final void scales(Doub xmajd, Doub xmind, Doub ymajd, Doub ymind)
	{
		scales(xmajd, xmind, ymajd, ymind, 2, 2, 1, 1);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void scales(Doub xmajd, Doub xmind, Doub ymajd, Doub ymind, Int dox=2, Int doy=2, Int doxx=1, Int doyy=1)
	public final void scales(Doub xmajd, Doub xmind, Doub ymajd, Doub ymind, Int dox, Int doy, Int doxx, Int doyy)
	{
		String str = new String(new char[128]);
		Doub x = new Doub();
		Doub y = new Doub();
		Doub xlo = new Doub();
		Doub ylo = new Doub();
		if (dox != null || doxx != null)
		{
			xlo = Math.ceil(MIN(xll,xur)/xmajd)*xmajd;
			for (x = xlo;x<=MAX(xll,xur);x+=xmajd)
			{
				RefObject<String> tempRefObject = new RefObject<String>(str);
				scalestr(tempRefObject, x);
				str = tempRefObject.argvalue;
				if (dox>1)
					putctext(str,p(x),qll-fontsize-2.);
				if (dox != null)
					super.lineseg(p(x), qll, p(x), qll+majticsz);
				if (doxx != null)
					super.lineseg(p(x), qur, p(x), qur-majticsz);
			}
			xlo = Math.ceil(MIN(xll,xur)/xmind)*xmind;
			for (x = xlo;x<=MAX(xll,xur);x+=xmind)
			{
				if (dox != null)
					super.lineseg(p(x), qll, p(x), qll+minticsz);
				if (doxx != null)
					super.lineseg(p(x), qur, p(x), qur-minticsz);
			}
		}
		if (doy != null || doyy != null)
		{
			ylo = Math.ceil(MIN(yll,yur)/ymajd)*ymajd;
			for (y = ylo;y<=MAX(yll,yur);y+=ymajd)
			{
				RefObject<String> tempRefObject2 = new RefObject<String>(str);
				scalestr(tempRefObject2, y);
				str = tempRefObject2.argvalue;
				if (doy>1)
					putrtext(str,pll-4.,q(y)-0.3 *fontsize);
				if (doy != null)
					super.lineseg(pll, q(y), pll+majticsz, q(y));
				if (doyy != null)
					super.lineseg(pur, q(y), pur-majticsz, q(y));
			}
			ylo = Math.ceil(MIN(yll,yur)/ymind)*ymind;
			for (y = ylo;y<=MAX(yll,yur);y+=ymind)
			{
				if (doy != null)
					super.lineseg(pll, q(y), pll+minticsz, q(y));
				if (doyy != null)
					super.lineseg(pur, q(y), pur-minticsz, q(y));
			}
		}
	}

	public final void autoscales()
	{
		double xmajd;
		double xmind;
		double ymajd;
		double ymind;
		xmajd = Math.pow(10.,((Int)(Math.log10(Math.abs(xur-xll))-1.1)));
		xmind = xmajd/5.;
		ymajd = Math.pow(10.,((Int)(Math.log10(Math.abs(yur-yll))-1.1)));
		ymind = ymajd/5.;
		scales(xmajd,xmind,ymajd,ymind);
	}
}