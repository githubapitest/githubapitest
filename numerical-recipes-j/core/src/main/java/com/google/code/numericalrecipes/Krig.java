package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
public class Krig<T>
{
	public final MatDoub x;
	public final T vgram;
	public Int ndim = new Int();
	public Int npt = new Int();
	public Doub lastval = new Doub();
	public Doub lasterr = new Doub();
	public VecDoub y = new VecDoub();
	public VecDoub dstar = new VecDoub();
	public VecDoub vstar = new VecDoub();
	public VecDoub yvi = new VecDoub();
	public MatDoub v = new MatDoub();
	public LUdcmp vi;

	public Krig(RefObject<MatDoub_I> xx, RefObject<VecDoub_I> yy, RefObject<T> vargram)
	{
		this(xx, yy, vargram, null);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Krig(MatDoub_I &xx, VecDoub_I &yy, T &vargram, const Doub *err=null) : x(xx),vgram(vargram),npt(xx.nrows()),ndim(xx.ncols()),dstar(npt+1), vstar(npt+1),v(npt+1,npt+1),y(npt+1),yvi(npt+1)
	public Krig(RefObject<MatDoub_I> xx, RefObject<VecDoub_I> yy, RefObject<T> vargram, Doub[] err)
	{
		x = xx.argvalue;
		vgram = vargram.argvalue;
		npt = xx.argvalue.nrows();
		ndim = xx.argvalue.ncols();
		dstar = npt+1;
		vstar = npt+1;
		v = new MatDoub(npt+1,npt+1);
		y = npt+1;
		yvi = npt+1;
		Int i = new Int();
		Int j = new Int();
		for (i = 0;i<npt;i++)
		{
			y[i] = yy.argvalue[i];
			for (j = i;j<npt;j++)
			{
				v[i][j] = v[j][i] = vgram(rdist(x[i][0], x[j][0]));
			}
			v[i][npt] = v[npt][i] = 1.;
		}
		v[npt][npt] = y[npt] = 0.;
		if (err != null)
			for (i = 0;i<npt;i++)
				v[i][i] -= SQR(err[i]);
		vi = new LUdcmp(v);
		vi.solve(y,yvi);
	}
	public void Dispose()
	{
		vi = null;
	}

	public final Doub interp(RefObject<VecDoub_I> xstar)
	{
		Int i = new Int();
		for (i = 0;i<npt;i++)
			vstar[i] = vgram(rdist(xstar.argvalue[0], x[i][0]));
		vstar[npt] = 1.;
		lastval = 0.;
		for (i = 0;i<=npt;i++)
			lastval += yvi[i]*vstar[i];
		return lastval;
	}

	public final Doub interp(RefObject<VecDoub_I> xstar, RefObject<Doub> esterr)
	{
		lastval = interp(xstar);
		vi.solve(vstar,dstar);
		lasterr = 0;
		for (Int i = 0;i<=npt;i++)
			lasterr += dstar[i]*vstar[i];
		esterr.argvalue = lasterr = Math.sqrt(MAX(0.,lasterr));
		return lastval;
	}

	public final Doub rdist(Doub[] x1, Doub[] x2)
	{
		Doub d = 0.;
		for (Int i = 0;i<ndim;i++)
			d += SQR(x1[i]-x2[i]);
		return Math.sqrt(d);
	}
}