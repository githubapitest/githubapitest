package com.google.code.numericalrecipes;
public class GlobalMembersStepperbs
{
	public static StepperBS<D>.StepperBS(RefObject<VecDoub_IO> yy, RefObject<VecDoub_IO> dydxx, RefObject<Doub> xx, Doub atoll, Doub rtoll, boolean dens)
	{
		StepperBase = new <type missing>(yy.argvalue,dydxx.argvalue,xx.argvalue,atoll,rtoll,dens);
		nseq = IMAXX;
		cost = IMAXX;
		table = new MatDoub(KMAXX,n);
		dydxnew = n;
		coeff = new MatDoub(IMAXX,IMAXX);
		errfac = 2 *IMAXX+2;
		ysave = new MatDoub(IMAXX,n);
		fsave = new MatDoub(IMAXX*(2 *IMAXX+1),n);
		ipoint = IMAXX+1;
		dens = (2 *IMAXX+5)*n;
		EPS=numeric_limits<Doub>.epsilon();
		if (dense)
			for (Int i = 0;i<IMAXX;i++)
				nseq[i]=4 *i+2;
		else
			for (Int i = 0;i<IMAXX;i++)
				nseq[i]=2*(i+1);
		cost[0]=nseq[0]+1;
		for (Int k = 0;k<KMAXX;k++)
			cost[k+1]=cost[k]+nseq[k+1];
		hnext=-1.0e99;
		Doub logfact = -Math.log10(MAX(1.0e-12,rtol))*0.6+0.5;
		k_targ=MAX(1,MIN(KMAXX-1,(Int)logfact));
		for (Int k = 0; k<IMAXX; k++)
		{
			for (Int l = 0; l<k; l++)
			{
				Doub ratio = Doub(nseq[k])/nseq[l];
				coeff[k][l]=1.0/(ratio *ratio-1.0);
			}
		}
		for (Int i = 0; i<2 *IMAXX+1; i++)
		{
			Int ip5 = i+5;
			errfac[i]=1.0/(ip5 *ip5);
			Doub e = 0.5 *Math.sqrt(Doub(i+1)/ip5);
			for (Int j = 0; j<=i; j++)
			{
				errfac[i] *= e/(j+1);
			}
		}
		ipoint[0]=0;
		for (Int i = 1; i<=IMAXX; i++)
		{
			Int njadd = 4 *i-2;
			if (nseq[i-1] > njadd)
				njadd++;
			ipoint[i]=ipoint[i-1]+njadd;
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: void StepperBS<D>::dy(VecDoub_I &y,const Doub htot,const Int k,VecDoub_O &yend, Int &ipt,D &derivs)
	public <D> void StepperBS<D>.dy(RefObject<VecDoub_I> y, Doub htot, Int k, RefObject<VecDoub_O> yend, RefObject<Int> ipt, RefObject<D> derivs)
	{
		VecDoub ym = new VecDoub(n);
		VecDoub yn = new VecDoub(n);
		Int nstep = nseq[k];
		Doub h = htot/nstep;
		for (Int i = 0;i<n;i++)
		{
			ym[i]=y.argvalue[i];
			yn[i]=y.argvalue[i]+h *dydx[i];
		}
		Doub xnew = x+h;
		derivs.argvalue(xnew,yn,yend.argvalue);
		Doub h2 = 2.0 *h;
		for (Int nn = 1;nn<nstep;nn++)
		{
			if (dense && nn == nstep/2)
			{
					for (Int i = 0;i<n;i++)
						ysave[k][i]=yn[i];
			}
			if (dense && Math.abs(nn-nstep/2) <= 2 *k+1)
			{
				ipt.argvalue++;
				for (Int i = 0;i<n;i++)
					fsave[ipt.argvalue][i]=yend.argvalue[i];
			}
			for (Int i = 0;i<n;i++)
			{
				Doub swap = ym[i]+h2 *yend.argvalue[i];
				ym[i]=yn[i];
				yn[i]=swap;
			}
			xnew += h;
			derivs.argvalue(xnew,yn,yend.argvalue);
		}
		if (dense && nstep/2 <= 2 *k+1)
		{
			ipt.argvalue++;
			for (Int i = 0;i<n;i++)
				fsave[ipt.argvalue][i]=yend.argvalue[i];
		}
		for (Int i = 0;i<n;i++)
			yend.argvalue[i]=0.5*(ym[i]+yn[i]+h *yend.argvalue[i]);
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: void StepperBS<D>::prepare_dense(const Doub h,VecDoub_I &dydxnew, VecDoub_I &ysav,VecDoub_I &scale,const Int k,Doub &error)
	public <D> void StepperBS<D>.prepare_dense(Doub h, RefObject<VecDoub_I> dydxnew, RefObject<VecDoub_I> ysav, RefObject<VecDoub_I> scale, Int k, RefObject<Doub> error)
	{
		mu=2 *k-1;
		for (Int i = 0; i<n; i++)
		{
			dens[i]=ysav.argvalue[i];
			dens[n+i]=h *dydx[i];
			dens[2 *n+i]=y[i];
			dens[3 *n+i]=dydxnew.argvalue[i]*h;
		}
		for (Int j = 1; j<=k; j++)
		{
			Doub dblenj = nseq[j];
			for (Int l = j; l>=1; l--)
			{
				Doub factor = SQR(dblenj/nseq[l-1])-1.0;
				for (Int i = 0; i<n; i++)
					ysave[l-1][i]=ysave[l][i]+(ysave[l][i]-ysave[l-1][i])/factor;
			}
		}
		for (Int i = 0; i<n; i++)
			dens[4 *n+i]=ysave[0][i];
		for (Int kmi = 1; kmi<=mu; kmi++)
		{
			Int kbeg = (kmi-1)/2;
			for (Int kk = kbeg; kk<=k; kk++)
			{
				Doub facnj = Math.pow(nseq[kk]/2.0,kmi-1);
				Int ipt = ipoint[kk+1]-2 *kk+kmi-3;
				for (Int i = 0; i<n; i++)
					ysave[kk][i]=fsave[ipt][i]*facnj;
			}
			for (Int j = kbeg+1; j<=k; j++)
			{
				Doub dblenj = nseq[j];
				for (Int l = j; l>=kbeg+1; l--)
				{
					Doub factor = SQR(dblenj/nseq[l-1])-1.0;
					for (Int i = 0; i<n; i++)
						ysave[l-1][i]=ysave[l][i]+ (ysave[l][i]-ysave[l-1][i])/factor;
				}
			}
			for (Int i = 0; i<n; i++)
				dens[(kmi+4)*n+i]=ysave[kbeg][i]*h;
			if (kmi == mu)
				continue;
			for (Int kk = kmi/2; kk<=k; kk++)
			{
				Int lbeg = ipoint[kk+1]-1;
				Int lend = ipoint[kk]+kmi;
				if (kmi == 1)
					lend += 2;
				for (Int l = lbeg; l>=lend; l-=2)
					for (Int i = 0; i<n; i++)
						fsave[l][i]=fsave[l][i]-fsave[l-2][i];
				if (kmi == 1)
				{
					Int l = lend-2;
					for (Int i = 0; i<n; i++)
						fsave[l][i]=fsave[l][i]-dydx[i];
				}
			}
			for (Int kk = kmi/2; kk<=k; kk++)
			{
				Int lbeg = ipoint[kk+1]-2;
				Int lend = ipoint[kk]+kmi+1;
				for (Int l = lbeg; l>=lend; l-=2)
					for (Int i = 0; i<n; i++)
						fsave[l][i]=fsave[l][i]-fsave[l-2][i];
			}
		}
		dense_interp(n,dens,mu);
		error.argvalue = 0.0;
		if (mu >= 1)
		{
			for (Int i = 0; i<n; i++)
				error.argvalue += SQR(dens[(mu+4)*n+i]/scale.argvalue[i]);
			error.argvalue = Math.sqrt(error.argvalue/n)*errfac[mu-1];
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
}