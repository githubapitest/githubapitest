package com.google.code.numericalrecipes;
public class Fitlin
{
	public Int ndat = new Int();
	public Int ma = new Int();
	public VecDoub_I x;
	public VecDoub_I y;
	public VecDoub_I sig;
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	VecDoub (*funcs)(const Doub);
	public VecBool ia = new VecBool();

	public VecDoub a = new VecDoub();
	public MatDoub covar = new MatDoub();
	public Doub chisq = new Doub();

	public Fitlin(RefObject<VecDoub_I> xx, RefObject<VecDoub_I> yy, RefObject<VecDoub_I> ssig, VecDoub funks(Doub))
	{
		ndat = xx.argvalue.size();
		x = xx.argvalue;
		y = yy.argvalue;
		sig = ssig.argvalue;
		funcs = funks;
		ma = funcs(x[0]).size();
		a.resize(ma);
		covar.resize(ma,ma);
		ia.resize(ma);
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
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int l = new Int();
		Int m = new Int();
		Int mfit = 0;
		Doub ym = new Doub();
		Doub wt = new Doub();
		Doub sum = new Doub();
		Doub sig2i = new Doub();
		VecDoub afunc = new VecDoub(ma);
		for (j = 0;j<ma;j++)
			if (ia[j])
				mfit++;
		if (mfit == 0)
			throw("lfit: no parameters to be fitted");
		MatDoub temp = new MatDoub(mfit,mfit,0.);
		MatDoub beta = new MatDoub(mfit,1,0.);
		for (i = 0;i<ndat;i++)
		{
			afunc = funcs(x[i]);
			ym = y[i];
			if (mfit < ma)
			{
				for (j = 0;j<ma;j++)
					if (!ia[j])
						ym -= a[j]*afunc[j];
			}
			sig2i = 1.0/SQR(sig[i]);
			for (j = 0,l = 0;l<ma;l++)
			{
				if (ia[l])
				{
					wt = afunc[l]*sig2i;
					for (k = 0,m = 0;m<=l;m++)
						if (ia[m])
							temp[j][k++] += wt *afunc[m];
					beta[j++][0] += ym *wt;
				}
			}
		}
		for (j = 1;j<mfit;j++)
			for (k = 0;k<j;k++)
				temp[k][j]=temp[j][k];
		gaussj(temp,beta);
		for (j = 0,l = 0;l<ma;l++)
			if (ia[l])
				a[l]=beta[j++][0];
		chisq = 0.0;
		for (i = 0;i<ndat;i++)
		{
			afunc = funcs(x[i]);
			sum = 0.0;
			for (j = 0;j<ma;j++)
				sum += a[j]*afunc[j];
			chisq += SQR((y[i]-sum)/sig[i]);
		}
		for (j = 0;j<mfit;j++)
			for (k = 0;k<mfit;k++)
				covar[j][k]=temp[j][k];
		for (i = mfit;i<ma;i++)
			for (j = 0;j<i+1;j++)
				covar[i][j]=covar[j][i]=0.0;
		k = mfit-1;
		for (j = ma-1;j>=0;j--)
		{
			if (ia[j])
			{
				for (i = 0;i<ma;i++)
					SWAP(covar[i][k],covar[i][j]);
				for (i = 0;i<ma;i++)
					SWAP(covar[k][i],covar[j][i]);
				k--;
			}
		}
	}
}