package com.google.code.numericalrecipes;
public class Rat_interp implements Base_interp
{
	public Doub dy = new Doub();
	public Rat_interp(RefObject<VecDoub_I> xv, RefObject<VecDoub_I> yv, Int m)
	{
		super(xv, yv.argvalue[0], m);
		dy = 0.;
	}
	public final Doub rawinterp(Int jl, Doub x)
	{
		final Doub TINY = 1.0e-99;
		Int m = new Int();
		Int i = new Int();
		Int ns = 0;
		Doub y = new Doub();
		Doub w = new Doub();
		Doub t = new Doub();
		Doub hh = new Doub();
		Doub h = new Doub();
		Doub dd = new Doub();
		Doub[] xa = &xx[jl];
		Doub[] ya = &yy[jl];
		VecDoub c = new VecDoub(mm);
		VecDoub d = new VecDoub(mm);
		hh = Math.abs(x-xa[0]);
		for (i = 0;i<mm;i++)
		{
			h = Math.abs(x-xa[i]);
			if (h == 0.0)
			{
				dy = 0.0;
				return ya[i];
			}
			else if (h < hh)
			{
				ns = i;
				hh = h;
			}
			c[i] =ya[i];
			d[i] =ya[i]+TINY;
		}
		y = ya[ns--];
		for (m = 1;m<mm;m++)
		{
			for (i = 0;i<mm-m;i++)
			{
				w = c[i+1]-d[i];
				h = xa[i+m]-x;
				t = (xa[i]-x)*d[i]/h;
				dd = t-c[i+1];
				if (dd == 0.0)
					throw("Error in routine ratint");
				dd = w/dd;
				d[i]=c[i+1]*dd;
				c[i]=t *dd;
			}
			y += (dy = (2*(ns+1) < (mm-m) ? c[ns+1] : d[ns--]));
		}
		return y;
	}
}