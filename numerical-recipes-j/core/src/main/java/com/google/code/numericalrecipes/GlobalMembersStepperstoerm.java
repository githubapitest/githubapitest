package com.google.code.numericalrecipes;
public class GlobalMembersStepperstoerm
{
	public static StepperStoerm<D>.StepperStoerm(RefObject<VecDoub_IO> yy, RefObject<VecDoub_IO> dydxx, RefObject<Doub> xx, Doub atoll, Doub rtoll, boolean dens)
	{
		StepperBS<D> = new <type missing>(yy.argvalue,dydxx.argvalue,xx.argvalue,atoll,rtoll,dens);
		ysavep = new MatDoub(IMAXX,n/2);
		neqn=n/2;
		cost[0]=nseq[0]/2+1;
		for (Int k = 0;k<KMAXX;k++)
			cost[k+1]=cost[k]+nseq[k+1]/2;
		for (Int i = 0; i<2 *IMAXX+1; i++)
		{
			Int ip7 = i+7;
			Doub fac = 1.5/ip7;
			errfac[i]=fac *fac *fac;
			Doub e = 0.5 *Math.sqrt(Doub(i+1)/ip7);
			for (Int j = 0; j<=i; j++)
			{
				errfac[i] *= e/(j+1);
			}
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: void StepperStoerm<D>::prepare_dense(const Doub h,VecDoub_I &dydxnew, VecDoub_I &ysav,VecDoub_I &scale,const Int k, Doub &error)
	public <D> void StepperStoerm<D>.prepare_dense(Doub h, RefObject<VecDoub_I> dydxnew, RefObject<VecDoub_I> ysav, RefObject<VecDoub_I> scale, Int k, RefObject<Doub> error)
	{
		Doub h2 = h *h;
		mu=MAX(1,2 *k-3);
		for (Int i = 0; i<neqn; i++)
		{
			dens[i]=ysav.argvalue[i];
			dens[neqn+i]=h *ysav.argvalue[neqn+i];
			dens[2 *neqn+i]=h2 *dydx[i];
			dens[3 *neqn+i]=y[i];
			dens[4 *neqn+i]=h *y[neqn+i];
			dens[5 *neqn+i]=h2 *dydxnew.argvalue[i];
		}
		for (Int j = 1; j<=k; j++)
		{
			Doub dblenj = nseq[j];
			for (Int l = j; l>=1; l--)
			{
				Doub factor = SQR(dblenj/nseq[l-1])-1.0;
				for (Int i = 0; i<neqn; i++)
				{
					ysave[l-1][i]=ysave[l][i]+(ysave[l][i]-ysave[l-1][i])/factor;
					ysavep[l-1][i]=ysavep[l][i]+(ysavep[l][i]-ysavep[l-1][i])/factor;
				}
			}
		}
		for (Int i = 0; i<neqn; i++)
		{
			dens[6 *neqn+i]=ysave[0][i];
			dens[7 *neqn+i]=h *ysavep[0][i];
		}
		for (Int kmi = 2; kmi<=mu; kmi++)
		{
			Int kbeg = (kmi-2)/2;
			if (kmi == 2 *kbeg+2)
			{
				if (kmi == 2)
				{
					for (Int i = 0; i<neqn; i++)
						ysave[0][i]=0.5*(dydxnew.argvalue[i]+fsave[0][i]);
					kbeg = 1;
				}
				for (Int kk = kbeg; kk<=k; kk++)
				{
					Doub facnj = 0.5 *Math.pow(nseq[kk]/2.0,kmi-2);
					Int ipt = kk *kk+kk+kmi/2-2;
					for (Int i = 0; i<neqn; i++)
						ysave[kk][i]=(fsave[ipt][i]+fsave[ipt+1][i])*facnj;
				}
			}
			else
			{
				for (Int kk = kbeg; kk<=k; kk++)
				{
					Doub facnj = Math.pow(nseq[kk]/2.0,kmi-2);
					Int ipt = kk *kk+kk+kbeg;
					for (Int i = 0; i<neqn; i++)
						ysave[kk][i]=fsave[ipt][i]*facnj;
				}
			}
			for (Int j = kbeg+1; j<=k; j++)
			{
				Doub dblenj = nseq[j];
				for (Int l = j; l>=kbeg+1; l--)
				{
					Doub factor = SQR(dblenj/nseq[l-1])-1.0;
					for (Int i = 0; i<neqn; i++)
						ysave[l-1][i]=ysave[l][i]+ (ysave[l][i]-ysave[l-1][i])/factor;
				}
			}
			for (Int i = 0; i<neqn; i++)
				dens[(kmi+6)*neqn+i]=ysave[kbeg][i]*h2;
			if (kmi == mu)
				continue;
			for (Int kk = (kmi-1)/2; kk<=k; kk++)
			{
				Int lbeg = kk *kk+kmi-2;
				Int lend = SQR(kk+1)-1;
				if (kmi == 2)
					lbeg++;
				for (Int l = lend; l>=lbeg; l--)
					for (Int i = 0; i<neqn; i++)
						fsave[l][i]=fsave[l][i]-fsave[l-1][i];
				if (kmi == 2)
				{
					Int l = lbeg-1;
					for (Int i = 0; i<neqn; i++)
						fsave[l][i]=fsave[l][i]-dydx[i];
				}
			}
		}
		dense_interp(neqn,dens,mu);
		error.argvalue = 0.0;
		if (mu >= 1)
		{
			for (Int i = 0; i<neqn; i++)
				error.argvalue += SQR(dens[(mu+6)*neqn+i]/scale.argvalue[i]);
			error.argvalue = Math.sqrt(error.argvalue/neqn)*errfac[mu-1];
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
}