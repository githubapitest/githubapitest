package com.google.code.numericalrecipes;
public class Fitmrq
{
	public static final Int NDONE = 4;
	public static final Int ITMAX = 1000;
	public Int ndat = new Int();
	public Int ma = new Int();
	public Int mfit = new Int();
	public VecDoub_I x;
	public VecDoub_I y;
	public VecDoub_I sig;
	public Doub tol = new Doub();
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	void (*funcs)(const Doub, VecDoub_I &, Doub &, VecDoub_O &);
	public VecBool ia = new VecBool();
	public VecDoub a = new VecDoub();
	public MatDoub covar = new MatDoub();
	public MatDoub alpha = new MatDoub();
	public Doub chisq = new Doub();

	public Fitmrq(RefObject<VecDoub_I> xx, RefObject<VecDoub_I> yy, RefObject<VecDoub_I> ssig, RefObject<VecDoub_I> aa, void funks(Doub, VecDoub_I , Doub &, VecDoub_O &))
	{
		this(xx, yy, ssig, aa, , 1.e-3);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Fitmrq(VecDoub_I &xx, VecDoub_I &yy, VecDoub_I &ssig, VecDoub_I &aa, void funks(const Doub, VecDoub_I &, Doub &, VecDoub_O &), const Doub TOL=1.e-3) : ndat(xx.size()), ma(aa.size()), x(xx), y(yy), sig(ssig), tol(TOL), funcs(funks), ia(ma), alpha(ma,ma), a(aa), covar(ma,ma)
	public Fitmrq(RefObject<VecDoub_I> xx, RefObject<VecDoub_I> yy, RefObject<VecDoub_I> ssig, RefObject<VecDoub_I> aa, void funks(Doub, VecDoub_I, Doub &, VecDoub_O &), Doub TOL)
	{
		ndat = xx.argvalue.size();
		ma = aa.argvalue.size();
		x = xx.argvalue;
		y = yy.argvalue;
		sig = ssig.argvalue;
		tol = TOL;
		funcs = funks;
		ia = ma;
		alpha = new MatDoub(ma,ma);
		a = aa.argvalue;
		covar = new MatDoub(ma,ma);
		for (Int i = 0;i<ma;i++)
			ia[i] = true;
	}

	public final void hold(Int i, Doub val)
	{
		ia[i]=false;
		a[i]=val;
	}
	public final void free(Int i)
	{
		ia[i]=true;
	}

	public final void fit()
	{
		Int j = new Int();
		Int k = new Int();
		Int l = new Int();
		Int iter = new Int();
		Int done = 0;
		Doub alamda = .001;
		Doub ochisq = new Doub();
		VecDoub atry = new VecDoub(ma);
		VecDoub beta = new VecDoub(ma);
		VecDoub da = new VecDoub(ma);
		mfit = 0;
		for (j = 0;j<ma;j++)
			if (ia[j])
				mfit++;
		MatDoub oneda = new MatDoub(mfit,1);
		MatDoub temp = new MatDoub(mfit,mfit);
		RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(a);
		RefObject<MatDoub_O> tempRefObject2 = new RefObject<MatDoub_O>(alpha);
		RefObject<VecDoub_O> tempRefObject3 = new RefObject<VecDoub_O>(beta);
		mrqcof(tempRefObject, tempRefObject2, tempRefObject3);
		a = tempRefObject.argvalue;
		alpha = tempRefObject2.argvalue;
		beta = tempRefObject3.argvalue;
		for (j = 0;j<ma;j++)
			atry[j]=a[j];
		ochisq = chisq;
		for (iter = 0;iter<ITMAX;iter++)
		{
			if (done == NDONE)
				alamda = 0.;
			for (j = 0;j<mfit;j++)
			{
				for (k = 0;k<mfit;k++)
					covar[j][k]=alpha[j][k];
				covar[j][j]=alpha[j][j]*(1.0+alamda);
				for (k = 0;k<mfit;k++)
					temp[j][k]=covar[j][k];
				oneda[j][0]=beta[j];
			}
			gaussj(temp,oneda);
			for (j = 0;j<mfit;j++)
			{
				for (k = 0;k<mfit;k++)
					covar[j][k]=temp[j][k];
				da[j]=oneda[j][0];
			}
			if (done == NDONE)
			{
				RefObject<MatDoub_IO> tempRefObject4 = new RefObject<MatDoub_IO>(covar);
				covsrt(tempRefObject4);
				covar = tempRefObject4.argvalue;
				RefObject<MatDoub_IO> tempRefObject5 = new RefObject<MatDoub_IO>(alpha);
				covsrt(tempRefObject5);
				alpha = tempRefObject5.argvalue;
				return;
			}
			for (j = 0,l = 0;l<ma;l++)
				if (ia[l])
					atry[l]=a[l]+da[j++];
			RefObject<VecDoub_I> tempRefObject6 = new RefObject<VecDoub_I>(atry);
			RefObject<MatDoub_O> tempRefObject7 = new RefObject<MatDoub_O>(covar);
			RefObject<VecDoub_O> tempRefObject8 = new RefObject<VecDoub_O>(da);
			mrqcof(tempRefObject6, tempRefObject7, tempRefObject8);
			atry = tempRefObject6.argvalue;
			covar = tempRefObject7.argvalue;
			da = tempRefObject8.argvalue;
			if (Math.abs(chisq-ochisq) < MAX(tol,tol *chisq))
				done++;
			if (chisq < ochisq)
			{
				alamda *= 0.1;
				ochisq = chisq;
				for (j = 0;j<mfit;j++)
				{
					for (k = 0;k<mfit;k++)
						alpha[j][k]=covar[j][k];
						beta[j]=da[j];
				}
				for (l = 0;l<ma;l++)
					a[l]=atry[l];
			}
			else
			{
				alamda *= 10.0;
				chisq = ochisq;
			}
		}
		throw("Fitmrq too many iterations");
	}


	public final void mrqcof(RefObject<VecDoub_I> a, RefObject<MatDoub_O> alpha, RefObject<VecDoub_O> beta)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int l = new Int();
		Int m = new Int();
		Doub ymod = new Doub();
		Doub wt = new Doub();
		Doub sig2i = new Doub();
		Doub dy = new Doub();
		VecDoub dyda = new VecDoub(ma);
		for (j = 0;j<mfit;j++)
		{
			for (k = 0;k<=j;k++)
				alpha.argvalue[j][k]=0.0;
			beta.argvalue[j]=0.;
		}
		chisq = 0.;
		for (i = 0;i<ndat;i++)
		{
			funcs(x[i],a.argvalue,ymod,dyda);
			sig2i = 1.0/(sig[i]*sig[i]);
			dy = y[i]-ymod;
			for (j = 0,l = 0;l<ma;l++)
			{
				if (ia[l])
				{
					wt = dyda[l]*sig2i;
					for (k = 0,m = 0;m<l+1;m++)
						if (ia[m])
							alpha.argvalue[j][k++] += wt *dyda[m];
					beta.argvalue[j++] += dy *wt;
				}
			}
			chisq += dy *dy *sig2i;
		}
		for (j = 1;j<mfit;j++)
			for (k = 0;k<j;k++)
				alpha.argvalue[k][j]=alpha.argvalue[j][k];
	}

	public final void covsrt(RefObject<MatDoub_IO> covar)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		for (i = mfit;i<ma;i++)
			for (j = 0;j<i+1;j++)
				covar.argvalue[i][j]=covar.argvalue[j][i]=0.0;
		k = mfit-1;
		for (j = ma-1;j>=0;j--)
		{
			if (ia[j])
			{
				for (i = 0;i<ma;i++)
					SWAP(covar.argvalue[i][k],covar.argvalue[i][j]);
				for (i = 0;i<ma;i++)
					SWAP(covar.argvalue[k][i],covar.argvalue[j][i]);
				k--;
			}
		}
	}

}