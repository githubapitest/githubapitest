package com.google.code.numericalrecipes;
public class Gaumixmod implements preGaumixmod
{
	public Int nn = new Int();
	public Int kk = new Int();
	public Int mm = new Int();
	public MatDoub data = new MatDoub();
	public MatDoub means = new MatDoub();
	public MatDoub resp = new MatDoub();
	public VecDoub frac = new VecDoub();
	public VecDoub lndets = new VecDoub();
	public java.util.ArrayList<Mat_mm> sig = new java.util.ArrayList<Mat_mm>();
	public Doub loglike = new Doub();
	public Gaumixmod(RefObject<MatDoub> ddata, RefObject<MatDoub> mmeans)
	{
		super(ddata.argvalue.ncols());
		nn = ddata.argvalue.nrows();
		kk = mmeans.argvalue.nrows();
		mm = mmstat;
		data = ddata.argvalue;
		means = mmeans.argvalue;
		resp = new MatDoub(nn,kk);
		frac = kk;
		lndets = kk;
		sig = kk;
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		for (k = 0;k<kk;k++)
		{
			frac[k] = 1./kk;
			for (i = 0;i<mm;i++)
			{
				for (j = 0;j<mm;j++)
					sig.get(k)[i][j] = 0.;
				sig.get(k)[i][i] = 1.0e-10;
			}
		}
		estep();
		mstep();
	}
	public final Doub estep()
	{
		Int k = new Int();
		Int m = new Int();
		Int n = new Int();
		Doub tmp = new Doub();
		Doub sum = new Doub();
		Doub max = new Doub();
		Doub oldloglike = new Doub();
		VecDoub u = new VecDoub(mm);
		VecDoub v = new VecDoub(mm);
		oldloglike = loglike;
		for (k = 0;k<kk;k++)
		{
			Cholesky[] choltmp = new Cholesky[k](sig);
			lndets[k] = choltmp.logdet();
			for (n = 0;n<nn;n++)
			{
				for (m = 0;m<mm;m++)
					u[m] = data[n][m]-means[k][m];
				choltmp.elsolve(u,v);
				for (sum = 0.,m = 0; m<mm; m++)
					sum += SQR(v[m]);
				resp[n][k] = -0.5*(sum + lndets[k]) + Math.log(frac[k]);
			}
		}
		loglike = 0;
		for (n = 0;n<nn;n++)
		{
			max = -99.9e99;
			for (k = 0;k<kk;k++)
				if (resp[n][k] > max)
					max = resp[n][k];
			for (sum = 0.,k = 0; k<kk; k++)
				sum += Math.exp(resp[n][k]-max);
			tmp = max + Math.log(sum);
			for (k = 0;k<kk;k++)
				resp[n][k] = Math.exp(resp[n][k] - tmp);
			loglike +=tmp;
		}
		return loglike - oldloglike;
	}
	public final void mstep()
	{
		Int j = new Int();
		Int n = new Int();
		Int k = new Int();
		Int m = new Int();
		Doub wgt = new Doub();
		Doub sum = new Doub();
		for (k = 0;k<kk;k++)
		{
			wgt = 0.;
			for (n = 0;n<nn;n++)
				wgt += resp[n][k];
			frac[k] = wgt/nn;
			for (m = 0;m<mm;m++)
			{
				for (sum = 0.,n = 0; n<nn; n++)
					sum += resp[n][k]*data[n][m];
				means[k][m] = sum/wgt;
				for (j = 0;j<mm;j++)
				{
					for (sum = 0.,n = 0; n<nn; n++)
					{
						sum += resp[n][k]* (data[n][m]-means[k][m])*(data[n][j]-means[k][j]);
					}
					sig.get(k)[m][j] = sum/wgt;
				}
			}
		}
	}
}