package com.google.code.numericalrecipes;
public class Curve_interp
{
	public Int dim = new Int();
	public Int n = new Int();
	public Int in = new Int();
	public Bool cls = new Bool();
	public MatDoub pts = new MatDoub();
	public VecDoub s = new VecDoub();
	public VecDoub ans = new VecDoub();
	public NRvector<Spline_interp> srp = new NRvector<Spline_interp>();

	public Curve_interp(RefObject<MatDoub> ptsin)
	{
		this(ptsin, 0);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Curve_interp(MatDoub &ptsin, Bool close=0) : n(ptsin.nrows()), dim(ptsin.ncols()), in(close ? 2 *n : n), cls(close), pts(dim,in), s(in), ans(dim), srp(dim)
	public Curve_interp(RefObject<MatDoub> ptsin, Bool close)
	{
		n = ptsin.argvalue.nrows();
		dim = ptsin.argvalue.ncols();
		in = close != null ? 2 *n : n;
		cls = close;
		pts = new MatDoub(dim,in);
		s = in;
		ans = dim;
		srp = dim;
		Int i = new Int();
		Int ii = new Int();
		Int im = new Int();
		Int j = new Int();
		Int ofs = new Int();
		Doub ss = new Doub();
		Doub soff = new Doub();
		Doub db = new Doub();
		Doub de = new Doub();
		ofs = close != null ? n/2 : 0;
		s[0] = 0.;
		for (i = 0;i<in;i++)
		{
			ii = (i-ofs+n) % n;
			im = (ii-1+n) % n;
			for (j = 0;j<dim;j++)
				pts[j][i] = ptsin.argvalue[ii][j];
			if (i>0)
			{
				s[i] = s[i-1] + rad(ptsin.argvalue[ii][0], ptsin.argvalue[im][0]);
				if (s[i] == s[i-1])
					throw("error in Curve_interp");
			}
		}
		ss = close != null ? s[ofs+n]-s[ofs] : s[n-1]-s[0];
		soff = s[ofs];
		for (i = 0;i<in;i++)
			s[i] = (s[i]-soff)/ss;
		for (j = 0;j<dim;j++)
		{
			db = in < 4 ? 1.e99 : fprime(s[0], pts[j][0], 1);
			de = in < 4 ? 1.e99 : fprime(s[in-1], pts[j][in-1], -1);
			srp[j] = new Spline_interp(s, pts[j][0], db, de);
		}
	}
	public void Dispose()
	{
		for (Int j = 0;j<dim;j++)
			srp[j] = null;
	}

	public final VecDoub interp(Doub t)
	{
		if (cls != null)
			t = t - Math.floor(t);
		for (Int j = 0;j<dim;j++)
			ans[j] = srp[j].interp(t);
		return ans;
	}

	public final Doub fprime(Doub[] x, Doub[] y, Int pm)
	{
		Doub s1 = x[0]-x[pm *1];
		Doub s2 = x[0]-x[pm *2];
		Doub s3 = x[0]-x[pm *3];
		Doub s12 = s1-s2;
		Doub s13 = s1-s3;
		Doub s23 = s2-s3;
		return -(s1 *s2/(s13 *s23 *s3))*y[pm *3]+(s1 *s3/(s12 *s2 *s23))*y[pm *2] -(s2 *s3/(s1 *s12 *s13))*y[pm *1]+(1./s1+1./s2+1./s3)*y[0];
	}

	public final Doub rad(Doub[] p1, Doub[] p2)
	{
		Doub sum = 0.;
		for (Int i = 0;i<dim;i++)
			sum += SQR(p1[i]-p2[i]);
		return Math.sqrt(sum);
	}

}