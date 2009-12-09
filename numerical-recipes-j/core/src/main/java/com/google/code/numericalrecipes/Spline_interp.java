package com.google.code.numericalrecipes;
public class Spline_interp implements Base_interp
{
	public VecDoub y2 = new VecDoub();

	public Spline_interp(RefObject<VecDoub_I> xv, RefObject<VecDoub_I> yv, Doub yp1)
	{
		this(xv, yv, yp1, 1.e99);
	}
	public Spline_interp(RefObject<VecDoub_I> xv, RefObject<VecDoub_I> yv)
	{
		this(xv, yv, 1.e99, 1.e99);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Spline_interp(VecDoub_I &xv, VecDoub_I &yv, Doub yp1=1.e99, Doub ypn=1.e99) : Base_interp(xv,&yv[0],2), y2(xv.size())
	public Spline_interp(RefObject<VecDoub_I> xv, RefObject<VecDoub_I> yv, Doub yp1, Doub ypn)
	{
		super(xv, yv.argvalue[0], 2);
		y2 = xv.argvalue.size();
		sety2(xv.argvalue[0], yv.argvalue[0], yp1, ypn);
	}

	public Spline_interp(RefObject<VecDoub_I> xv, Doub yv, Doub yp1)
	{
		this(xv, yv, yp1, 1.e99);
	}
	public Spline_interp(RefObject<VecDoub_I> xv, Doub yv)
	{
		this(xv, yv, 1.e99, 1.e99);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Spline_interp(VecDoub_I &xv, const Doub *yv, Doub yp1=1.e99, Doub ypn=1.e99) : Base_interp(xv,yv,2), y2(xv.size())
	public Spline_interp(RefObject<VecDoub_I> xv, Doub yv, Doub yp1, Doub ypn)
	{
		super(xv, yv, 2);
		y2 = xv.argvalue.size();
		sety2(xv.argvalue[0], yv, yp1, ypn);
	}

	public final void sety2(Doub[] xv, Doub[] yv, Doub yp1, Doub ypn)
	{
		Int i = new Int();
		Int k = new Int();
		Doub p = new Doub();
		Doub qn = new Doub();
		Doub sig = new Doub();
		Doub un = new Doub();
		Int n = y2.size();
		VecDoub u = new VecDoub(n-1);
		if (yp1 > 0.99e99)
			y2[0]=u[0]=0.0;
		else
		{
			y2[0] = -0.5;
			u[0]=(3.0/(xv[1]-xv[0]))*((yv[1]-yv[0])/(xv[1]-xv[0])-yp1);
		}
		for (i = 1;i<n-1;i++)
		{
			sig = (xv[i]-xv[i-1])/(xv[i+1]-xv[i-1]);
			p = sig *y2[i-1]+2.0;
			y2[i]=(sig-1.0)/p;
			u[i]=(yv[i+1]-yv[i])/(xv[i+1]-xv[i]) - (yv[i]-yv[i-1])/(xv[i]-xv[i-1]);
			u[i]=(6.0 *u[i]/(xv[i+1]-xv[i-1])-sig *u[i-1])/p;
		}
		if (ypn > 0.99e99)
			qn = un = 0.0;
		else
		{
			qn = 0.5;
			un = (3.0/(xv[n-1]-xv[n-2]))*(ypn-(yv[n-1]-yv[n-2])/(xv[n-1]-xv[n-2]));
		}
		y2[n-1]=(un-qn *u[n-2])/(qn *y2[n-2]+1.0);
		for (k = n-2;k>=0;k--)
			y2[k]=y2[k]*y2[k+1]+u[k];
	}
	public final Doub rawinterp(Int jl, Doub x)
	{
		Int klo = jl;
		Int khi = jl+1;
		Doub y = new Doub();
		Doub h = new Doub();
		Doub b = new Doub();
		Doub a = new Doub();
		h = xx[khi]-xx[klo];
		if (h == 0.0)
			throw("Bad input to routine splint");
		a = (xx[khi]-x)/h;
		b = (x-xx[klo])/h;
		y = a *yy[klo]+b *yy[khi]+((a *a *a-a)*y2[klo] +(b *b *b-b)*y2[khi])*(h *h)/6.0;
		return y;
	}
}