package com.google.code.numericalrecipes;
public class Shep_interp
{
	public Int dim = new Int();
	public Int n = new Int();
	public final MatDoub pts;
	public final VecDoub vals;
	public Doub pneg = new Doub();

	public Shep_interp(RefObject<MatDoub_I> ptss, RefObject<VecDoub_I> valss)
	{
		this(ptss, valss, 2.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Shep_interp(MatDoub_I &ptss, VecDoub_I &valss, Doub p=2.) : dim(ptss.ncols()), n(ptss.nrows()), pts(ptss), vals(valss), pneg(-p)
	public Shep_interp(RefObject<MatDoub_I> ptss, RefObject<VecDoub_I> valss, Doub p)
	{
		dim = ptss.argvalue.ncols();
		n = ptss.argvalue.nrows();
		pts = ptss.argvalue;
		vals = valss.argvalue;
		pneg = -p;
	}

	public final Doub interp(RefObject<VecDoub_I> pt)
	{
		Doub r = new Doub();
		Doub w = new Doub();
		Doub sum = 0.;
		Doub sumw = 0.;
		if (pt.argvalue.size() != dim)
			throw("RBF_interp bad pt size");
		for (Int i = 0;i<n;i++)
		{
			if ((r = rad(pt.argvalue[0], pts[i][0])) == 0.)
				return vals[i];
			sum += (w = Math.pow(r,pneg));
			sumw += w *vals[i];
		}
		return sumw/sum;
	}

	public final Doub rad(Doub[] p1, Doub[] p2)
	{
		Doub sum = 0.;
		for (Int i = 0;i<dim;i++)
			sum += SQR(p1[i]-p2[i]);
		return Math.sqrt(sum);
	}
}