package com.google.code.numericalrecipes;
public class HMM
{
	public MatDoub a = new MatDoub();
	public MatDoub b = new MatDoub();
	public VecInt obs = new VecInt();
	public Int fbdone = new Int();
	public Int mstat = new Int();
	public Int nobs = new Int();
	public Int ksym = new Int();
	public Int lrnrm = new Int();
	public MatDoub alpha = new MatDoub();
	public MatDoub beta = new MatDoub();
	public MatDoub pstate = new MatDoub();
	public VecInt arnrm = new VecInt();
	public VecInt brnrm = new VecInt();
	public Doub BIG = new Doub();
	public Doub BIGI = new Doub();
	public Doub lhood = new Doub();
	public HMM(RefObject<MatDoub_I> aa, RefObject<MatDoub_I> bb, RefObject<VecInt_I> obss)
	{
		a = aa.argvalue;
		b = bb.argvalue;
		obs = obss.argvalue;
		fbdone = 0;
		mstat = a.nrows();
		nobs = obs.size();
		ksym = b.ncols();
		alpha = new MatDoub(nobs,mstat);
		beta = new MatDoub(nobs,mstat);
		pstate = new MatDoub(nobs,mstat);
		arnrm = nobs;
		brnrm = nobs;
		BIG = 1.e20;
		BIGI = 1./BIG;
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Doub sum = new Doub();
		if (a.ncols() != mstat)
			throw("transition matrix not square");
		if (b.nrows() != mstat)
			throw("symbol prob matrix wrong size");
		for (i = 0; i<nobs; i++)
		{
			if (obs[i] < 0 || obs[i] >= ksym)
				throw("bad data in obs");
		}
		for (i = 0; i<mstat; i++)
		{
			sum = 0.;
			for (j = 0; j<mstat; j++)
				sum += a[i][j];
			if (Math.abs(sum - 1.) > 0.01)
				throw("transition matrix not normalized");
			for (j = 0; j<mstat; j++)
				a[i][j] /= sum;
		}
		for (i = 0; i<mstat; i++)
		{
			sum = 0.;
			for (k = 0; k<ksym; k++)
				sum += b[i][k];
			if (Math.abs(sum - 1.) > 0.01)
				throw("symbol prob matrix not normalized");
			for (k = 0; k<ksym; k++)
				b[i][k] /= sum;
		}
	}
	public final void forwardbackward()
	{
		Int i = new Int();
		Int j = new Int();
		Int t = new Int();
		Doub sum = new Doub();
		Doub asum = new Doub();
		Doub bsum = new Doub();
		for (i = 0; i<mstat; i++)
			alpha[0][i] = b[i][obs[0]];
		arnrm[0] = 0;
		for (t = 1; t<nobs; t++)
		{
			asum = 0;
			for (j = 0; j<mstat; j++)
			{
				sum = 0.;
				for (i = 0; i<mstat; i++)
					sum += alpha[t-1][i]*a[i][j]*b[j][obs[t]];
				alpha[t][j] = sum;
				asum += sum;
			}
			arnrm[t] = arnrm[t-1];
			if (asum < BIGI)
			{
				++arnrm[t];
				for (j = 0; j<mstat; j++)
					alpha[t][j] *= BIG;
			}
		}
		for (i = 0; i<mstat; i++)
			beta[nobs-1][i] = 1.;
		brnrm[nobs-1] = 0;
		for (t = nobs-2; t>=0; t--)
		{
			bsum = 0.;
			for (i = 0; i<mstat; i++)
			{
				sum = 0.;
				for (j = 0; j<mstat; j++)
					sum += a[i][j]*b[j][obs[t+1]]*beta[t+1][j];
				beta[t][i] = sum;
				bsum += sum;
			}
			brnrm[t] = brnrm[t+1];
			if (bsum < BIGI)
			{
				++brnrm[t];
				for (j = 0; j<mstat; j++)
					beta[t][j] *= BIG;
			}
		}
		lhood = 0.;
		for (i = 0; i<mstat; i++)
			lhood += alpha[0][i]*beta[0][i];
		lrnrm = arnrm[0] + brnrm[0];
		while (lhood < BIGI)
		{
			lhood *= BIG;
			lrnrm++;
		}
		for (t = 0; t<nobs; t++)
		{
			sum = 0.;
			for (i = 0; i<mstat; i++)
				sum += (pstate[t][i] = alpha[t][i]*beta[t][i]);
			// sum = lhood*pow(BIGI, lrnrm - arnrm[t] - brnrm[t]);
			for (i = 0; i<mstat; i++)
				pstate[t][i] /= sum;
		}
		fbdone = 1;
	}
	public final void baumwelch()
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int t = new Int();
		Doub num = new Doub();
		Doub denom = new Doub();
		Doub term = new Doub();
		MatDoub bnew = new MatDoub(mstat,ksym);
		Doub[] powtab = new Doub[10];
		for (i = 0; i<10; i++)
			powtab[i] = Math.pow(BIGI,i-6);
		if (fbdone != 1)
			throw("must do forwardbackward first");
		for (i = 0; i<mstat; i++)
		{
			denom = 0.;
			for (k = 0; k<ksym; k++)
				bnew[i][k] = 0.;
			for (t = 0; t<nobs-1; t++)
			{
				term = (alpha[t][i]*beta[t][i]/lhood) * powtab[arnrm[t] + brnrm[t] - lrnrm + 6];
				denom += term;
				bnew[i][obs[t]] += term;
			}
			for (j = 0; j<mstat; j++)
			{
				num = 0.;
				for (t = 0; t<nobs-1; t++)
				{
					num += alpha[t][i]*b[j][obs[t+1]]*beta[t+1][j] * powtab[arnrm[t] + brnrm[t+1] - lrnrm + 6]/lhood;
				}
				a[i][j] *= (num/denom);
			}
			for (k = 0; k<ksym; k++)
				bnew[i][k] /= denom;
		}
		b = bnew;
		fbdone = 0;
	}
	public final Doub loglikelihood()
	{
		return Math.log(lhood)+lrnrm *Math.log(BIGI);
	}
}