package com.google.code.numericalrecipes;
public class Svm
{
	public Svmgenkernel gker;
	public Int m = new Int();
	public Int fnz = new Int();
	public Int fub = new Int();
	public Int niter = new Int();
	public VecDoub alph = new VecDoub();
	public VecDoub alphold = new VecDoub();
	public Ran ran = new Ran();
	public Bool alphinit = new Bool();
	public Doub dalph = new Doub();
	public Svm(RefObject<Svmgenkernel> inker)
	{
		gker = new Svmgenkernel(inker.argvalue);
		m = gker.y.size();
		alph = m;
		alphold = m;
		ran = 21;
		alphinit = false;
	}
	public final Doub relax(Doub lambda, Doub om)
	{
		Int iter = new Int();
		Int j = new Int();
		Int jj = new Int();
		Int k = new Int();
		Int kk = new Int();
		Doub sum = new Doub();
		VecDoub pinsum = new VecDoub(m);
		if (alphinit == false)
		{
			for (j = 0; j<m; j++)
				alph[j] = 0.;
			alphinit = true;
		}
		alphold = alph;
		Indexx x = new Indexx(alph);
		for (fnz = 0; fnz<m; fnz++)
			if (alph[x.indx[fnz]] != 0.)
				break;
		for (j = fnz; j<m-2; j++)
		{
			k = j + (ran.int32() % (m-j));
			SWAP(x.indx[j],x.indx[k]);
		}
		for (jj = 0; jj<m; jj++)
		{
			j = x.indx[jj];
			sum = 0.;
			for (kk = fnz; kk<m; kk++)
			{
				k = x.indx[kk];
				sum += (gker.ker[j][k] + 1.)*gker.y[k]*alph[k];
			}
			alph[j] = alph[j] - (om/(gker.ker[j][j]+1.))*(gker.y[j]*sum-1.);
			alph[j] = MAX(0.,MIN(lambda,alph[j]));
			if (jj < fnz && alph[j])
				SWAP(x.indx[--fnz],x.indx[jj]);
		}
		Indexx y = new Indexx(alph);
		for (fnz = 0; fnz<m; fnz++)
			if (alph[y.indx[fnz]] != 0.)
				break;
		for (fub = fnz; fub<m; fub++)
			if (alph[y.indx[fub]] == lambda)
				break;
		for (j = fnz; j<fub-2; j++)
		{
			k = j + (ran.int32() % (fub-j));
			SWAP(y.indx[j],y.indx[k]);
		}
		for (jj = fnz; jj<fub; jj++)
		{
			j = y.indx[jj];
			sum = 0.;
			for (kk = fub; kk<m; kk++)
			{
				k = y.indx[kk];
				sum += (gker.ker[j][k] + 1.)*gker.y[k]*alph[k];
			}
			pinsum[jj] = sum;
		}
		niter = MAX((Int)(0.5*(m+1.0)*(m-fnz+1.0)/(SQR(fub-fnz+1.0))),1);
		for (iter = 0; iter<niter; iter++)
		{
			for (jj = fnz; jj<fub; jj++)
			{
				j = y.indx[jj];
				sum = pinsum[jj];
				for (kk = fnz; kk<fub; kk++)
				{
					k = y.indx[kk];
					sum += (gker.ker[j][k] + 1.)*gker.y[k]*alph[k];
				}
				alph[j] = alph[j] - (om/(gker.ker[j][j]+1.))*(gker.y[j]*sum-1.);
				alph[j] = MAX(0.,MIN(lambda,alph[j]));
			}
		}
		dalph = 0.;
		for (j = 0;j<m;j++)
			dalph += SQR(alph[j]-alphold[j]);
		return Math.sqrt(dalph);
	}
	public final Doub predict(Int k)
	{
		Doub sum = 0.;
		for (Int j = 0; j<m; j++)
			sum += alph[j]*gker.y[j]*(gker.ker[j][k]+1.0);
		return sum;
	}
	public final Doub predict(RefObject<Doub> x)
	{
		Doub sum = 0.;
		for (Int j = 0; j<m; j++)
			sum += alph[j]*gker.y[j]*(gker.kernel(j, x)+1.0);
		return sum;
	}
}