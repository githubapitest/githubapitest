package com.google.code.numericalrecipes;
public class Bilin_interp
{
	public Int m = new Int();
	public Int n = new Int();
	public final MatDoub y;
	public Linear_interp x1terp = new Linear_interp();
	public Linear_interp x2terp = new Linear_interp();

	public Bilin_interp(RefObject<VecDoub_I> x1v, RefObject<VecDoub_I> x2v, RefObject<MatDoub_I> ym)
	{
		m = x1v.argvalue.size();
		n = x2v.argvalue.size();
		y = ym.argvalue;
		x1terp = new Linear_interp(x1v.argvalue,x1v.argvalue);
		x2terp = new Linear_interp(x2v.argvalue,x2v.argvalue);
	}

	public final Doub interp(Doub x1p, Doub x2p)
	{
		Int i = new Int();
		Int j = new Int();
		Doub yy = new Doub();
		Doub t = new Doub();
		Doub u = new Doub();
		i = x1terp.cor ? x1terp.hunt(x1p) : x1terp.locate(x1p);
		j = x2terp.cor ? x2terp.hunt(x2p) : x2terp.locate(x2p);
		t = (x1p-x1terp.xx[i])/(x1terp.xx[i+1]-x1terp.xx[i]);
		u = (x2p-x2terp.xx[j])/(x2terp.xx[j+1]-x2terp.xx[j]);
		yy = (1.-t)*(1.-u)*y[i][j] + t*(1.-u)*y[i+1][j] + (1.-t)*u *y[i][j+1] + t *u *y[i+1][j+1];
		return yy;
	}
}