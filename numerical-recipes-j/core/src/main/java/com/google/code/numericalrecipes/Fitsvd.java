package com.google.code.numericalrecipes;
public class Fitsvd
{
	public Int ndat = new Int();
	public Int ma = new Int();
	public Doub tol = new Doub();
	public VecDoub_I x;
	public VecDoub_I y;
	public VecDoub_I sig;
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	VecDoub (*funcs)(const Doub);
	public VecDoub a = new VecDoub();
	public MatDoub covar = new MatDoub();
	public Doub chisq = new Doub();

	public Fitsvd(RefObject<VecDoub_I> xx, RefObject<VecDoub_I> yy, RefObject<VecDoub_I> ssig, VecDoub funks(Doub))
	{
		this(xx, yy, ssig, , 1.e-12);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Fitsvd(VecDoub_I &xx, VecDoub_I &yy, VecDoub_I &ssig, VecDoub funks(const Doub), const Doub TOL=1.e-12) : ndat(yy.size()), x(&xx), xmd(null), y(yy), sig(ssig), funcs(funks), tol(TOL)
	public Fitsvd(RefObject<VecDoub_I> xx, RefObject<VecDoub_I> yy, RefObject<VecDoub_I> ssig, VecDoub funks(Doub), Doub TOL)
	{
		ndat = yy.argvalue.size();
		x = xx.argvalue;
		xmd = null;
		y = yy.argvalue;
		sig = ssig.argvalue;
		funcs = funks;
		tol = TOL;
	}

	public final void fit()
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Doub tmp = new Doub();
		Doub thresh = new Doub();
		Doub sum = new Doub();
		if (x != null)
			ma = funcs((x)[0]).size();
		else
		{
			RefObject<MatDoub_I> tempRefObject = new RefObject<MatDoub_I>(xmd);
			ma = funcsmd(row(tempRefObject, 0)).size();
			xmd = tempRefObject.argvalue;
		}
		a.resize(ma);
		covar.resize(ma,ma);
		MatDoub aa = new MatDoub(ndat,ma);
		VecDoub b = new VecDoub(ndat);
		VecDoub afunc = new VecDoub(ma);
		for (i = 0;i<ndat;i++)
		{
			if (x != null)
				afunc = funcs((x)[i]);
			else
			{
				RefObject<MatDoub_I> tempRefObject2 = new RefObject<MatDoub_I>(xmd);
				afunc = funcsmd(row(tempRefObject2, i));
				xmd = tempRefObject2.argvalue;
			}
			tmp = 1.0/sig[i];
			for (j = 0;j<ma;j++)
				aa[i][j]=afunc[j]*tmp;
			b[i]=y[i]*tmp;
		}
		SVD svd = new SVD(aa);
		thresh = (tol > 0.? tol *svd.w[0] : -1.);
		svd.solve(b,a,thresh);
		chisq = 0.0;
		for (i = 0;i<ndat;i++)
		{
			sum = 0.;
			for (j = 0;j<ma;j++)
				sum += aa[i][j]*a[j];
			chisq += SQR(sum-b[i]);
		}
		for (i = 0;i<ma;i++)
		{
			for (j = 0;j<i+1;j++)
			{
				sum = 0.0;
				for (k = 0;k<ma;k++)
					if (svd.w[k] > svd.tsh)
					sum += svd.v[i][k]*svd.v[j][k]/SQR(svd.w[k]);
				covar[j][i]=covar[i][j]=sum;
			}
		}

	}

	public MatDoub_I xmd;
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	VecDoub (*funcsmd)(VecDoub_I &);

	public Fitsvd(RefObject<MatDoub_I> xx, RefObject<VecDoub_I> yy, RefObject<VecDoub_I> ssig, VecDoub funks(VecDoub_I ))
	{
		this(xx, yy, ssig, , 1.e-12);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Fitsvd(MatDoub_I &xx, VecDoub_I &yy, VecDoub_I &ssig, VecDoub funks(VecDoub_I &), const Doub TOL=1.e-12) : ndat(yy.size()), x(null), xmd(&xx), y(yy), sig(ssig), funcsmd(funks), tol(TOL)
	public Fitsvd(RefObject<MatDoub_I> xx, RefObject<VecDoub_I> yy, RefObject<VecDoub_I> ssig, VecDoub funks(VecDoub_I ), Doub TOL)
	{
		ndat = yy.argvalue.size();
		x = null;
		xmd = xx.argvalue;
		y = yy.argvalue;
		sig = ssig.argvalue;
		funcsmd = funks;
		tol = TOL;
	}

	public final VecDoub row(RefObject<MatDoub_I> a, Int i)
	{
		Int j = new Int();
		Int n = a.argvalue.ncols();
		VecDoub ans = new VecDoub(n);
		for (j = 0;j<n;j++)
			ans[j] = a.argvalue[i][j];
		return ans;
	}
}