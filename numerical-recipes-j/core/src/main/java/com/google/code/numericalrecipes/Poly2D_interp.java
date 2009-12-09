package com.google.code.numericalrecipes;
public class Poly2D_interp
{
	public Int m = new Int();
	public Int n = new Int();
	public Int mm = new Int();
	public Int nn = new Int();
	public final MatDoub y;
	public VecDoub yv = new VecDoub();
	public Poly_interp x1terp = new Poly_interp();
	public Poly_interp x2terp = new Poly_interp();

	public Poly2D_interp(RefObject<VecDoub_I> x1v, RefObject<VecDoub_I> x2v, RefObject<MatDoub_I> ym, Int mp, Int np)
	{
		m = x1v.argvalue.size();
		n = x2v.argvalue.size();
		mm = mp;
		nn = np;
		y = ym.argvalue;
		yv = m;
		x1terp = new Linear_interp(x1v.argvalue,yv,mm);
		x2terp = new Linear_interp(x2v.argvalue,x2v.argvalue,nn);
	}

	public final Doub interp(Doub x1p, Doub x2p)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		i = x1terp.cor ? x1terp.hunt(x1p) : x1terp.locate(x1p);
		j = x2terp.cor ? x2terp.hunt(x2p) : x2terp.locate(x2p);
		for (k = i;k<i+mm;k++)
		{
			x2terp.yy = y[k][0];
			yv[k] = x2terp.rawinterp(j,x2p);
		}
		return x1terp.rawinterp(i,x1p);
	}
}