package com.google.code.numericalrecipes;
public class Poly_interp implements Base_interp
{
	public Doub dy = new Doub();
	public Poly_interp(RefObject<VecDoub_I> xv, RefObject<VecDoub_I> yv, Int m)
	{
		super(xv, yv.argvalue[0], m);
		dy = 0.;
	}
	public final Doub rawinterp(Int jl, Doub x)
	{
		Int i = new Int();
		Int m = new Int();
		Int ns = 0;
		Doub y = new Doub();
		Doub den = new Doub();
		Doub dif = new Doub();
		Doub dift = new Doub();
		Doub ho = new Doub();
		Doub hp = new Doub();
		Doub w = new Doub();
		Doub[] xa = &xx[jl];
		Doub[] ya = &yy[jl];
		VecDoub c = new VecDoub(mm);
		VecDoub d = new VecDoub(mm);
		dif = Math.abs(x-xa[0]);
		for (i = 0;i<mm;i++)
		{
			if ((dift = Math.abs(x-xa[i])) < dif)
			{
				ns = i;
				dif = dift;
			}
			c[i] =ya[i];
			d[i] =ya[i];
		}
		y = ya[ns--];
		for (m = 1;m<mm;m++)
		{
			for (i = 0;i<mm-m;i++)
			{
				ho = xa[i]-x;
				hp = xa[i+m]-x;
				w = c[i+1]-d[i];
				if ((den = ho-hp) == 0.0)
					throw("Poly_interp error");
				den = w/den;
				d[i]=hp *den;
				c[i]=ho *den;
			}
			y += (dy = (2*(ns+1) < (mm-m) ? c[ns+1] : d[ns--]));
		}
		return y;
	}
}