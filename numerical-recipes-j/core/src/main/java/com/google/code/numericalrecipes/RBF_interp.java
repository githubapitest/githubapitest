package com.google.code.numericalrecipes;
public class RBF_interp
{
	public Int dim = new Int();
	public Int n = new Int();
	public final MatDoub pts;
	public final VecDoub vals;
	public VecDoub w = new VecDoub();
	public RBF_fn fn;
	public Bool norm = new Bool();

	public RBF_interp(RefObject<MatDoub_I> ptss, RefObject<VecDoub_I> valss, RefObject<RBF_fn> func)
	{
		this(ptss, valss, func, false);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: RBF_interp(MatDoub_I &ptss, VecDoub_I &valss, RBF_fn &func, Bool nrbf=false) : dim(ptss.ncols()), n(ptss.nrows()), pts(ptss), vals(valss), w(n), fn(func), norm(nrbf)
	public RBF_interp(RefObject<MatDoub_I> ptss, RefObject<VecDoub_I> valss, RefObject<RBF_fn> func, Bool nrbf)
	{
		dim = ptss.argvalue.ncols();
		n = ptss.argvalue.nrows();
		pts = ptss.argvalue;
		vals = valss.argvalue;
		w = n;
		fn = new RBF_fn(func.argvalue);
		norm = nrbf;
		Int i = new Int();
		Int j = new Int();
		Doub sum = new Doub();
		MatDoub rbf = new MatDoub(n,n);
		VecDoub rhs = new VecDoub(n);
		for (i = 0;i<n;i++)
		{
			sum = 0.;
			for (j = 0;j<n;j++)
			{
				sum += (rbf[i][j] = fn.rbf(rad(pts[i][0], pts[j][0])));
			}
			if (norm != null)
				rhs[i] = sum *vals[i];
			else
				rhs[i] = vals[i];
		}
		LUdcmp lu = new LUdcmp(rbf);
		lu.solve(rhs,w);
	}

	public final Doub interp(RefObject<VecDoub_I> pt)
	{
		Doub fval = new Doub();
		Doub sum = 0.;
		Doub sumw = 0.;
		if (pt.argvalue.size() != dim)
			throw("RBF_interp bad pt size");
		for (Int i = 0;i<n;i++)
		{
			fval = fn.rbf(rad(pt.argvalue[0], pts[i][0]));
			sumw += w[i]*fval;
			sum += fval;
		}
		return norm != null ? sumw/sum : sumw;
	}

	public final Doub rad(Doub[] p1, Doub[] p2)
	{
		Doub sum = 0.;
		for (Int i = 0;i<dim;i++)
			sum += SQR(p1[i]-p2[i]);
		return Math.sqrt(sum);
	}
}