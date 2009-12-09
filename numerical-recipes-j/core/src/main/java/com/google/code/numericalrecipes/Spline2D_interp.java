package com.google.code.numericalrecipes;
public class Spline2D_interp
{
	public Int m = new Int();
	public Int n = new Int();
	public final MatDoub y;
	public final VecDoub x1;
	public VecDoub yv = new VecDoub();
	public NRvector<Spline_interp> srp = new NRvector<Spline_interp>();

	public Spline2D_interp(RefObject<VecDoub_I> x1v, RefObject<VecDoub_I> x2v, RefObject<MatDoub_I> ym)
	{
		m = x1v.argvalue.size();
		n = x2v.argvalue.size();
		y = ym.argvalue;
		yv = m;
		x1 = x1v.argvalue;
		srp = m;
		for (Int i = 0;i<m;i++)
			srp[i] = new Spline_interp(x2v.argvalue, y[i][0]);
	}

	public void Dispose()
	{
		for (Int i = 0;i<m;i++)
			srp[i] = null;
	}

	public final Doub interp(Doub x1p, Doub x2p)
	{
		for (Int i = 0;i<m;i++)
			yv[i] = srp[i].interp(x2p);
		Spline_interp scol = new Spline_interp(x1,yv);
		return scol.interp(x1p);
	}
}