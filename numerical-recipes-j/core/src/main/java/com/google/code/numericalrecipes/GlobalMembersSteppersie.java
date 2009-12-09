package com.google.code.numericalrecipes;
public class GlobalMembersSteppersie
{
	public static StepperSie<D>.StepperSie(RefObject<VecDoub_IO> yy, RefObject<VecDoub_IO> dydxx, RefObject<Doub> xx, Doub atoll, Doub rtoll, boolean dens)
	{
		StepperBase = new <type missing>(yy.argvalue,dydxx.argvalue,xx.argvalue,atoll,rtoll,dens);
		nseq = IMAXX;
		cost = IMAXX;
		table = new MatDoub(KMAXX,n);
		dfdy = new MatDoub(n,n);
		dfdx = n;
		calcjac = false;
		a = new MatDoub(n,n);
		coeff = new MatDoub(IMAXX,IMAXX);
		fsave = new MatDoub((IMAXX-1)*(IMAXX+1)/2+2,n);
		dens = (IMAXX+2)*n;
		factrl = IMAXX;
		final Doub costfunc = 1.0;
		final Doub costjac = 5.0;
		final Doub costlu = 1.0;
		final Doub costsolve = 1.0;
		EPS=numeric_limits<Doub>.epsilon();
		jac_redo=MIN(1.0e-4,rtol);
		theta=2.0 *jac_redo;
		nseq[0]=2;
		nseq[1]=3;
		for (Int i = 2;i<IMAXX;i++)
			nseq[i]=2 *nseq[i-2];
		cost[0]=costjac+costlu+nseq[0]*(costfunc+costsolve);
		for (Int k = 0;k<KMAXX;k++)
			cost[k+1]=cost[k]+(nseq[k+1]-1)*(costfunc+costsolve)+costlu;
		hnext=-1.0e99;
		Doub logfact = -Math.log10(rtol+atol)*0.6+0.5;
		k_targ=MAX(1,MIN(KMAXX-1,(Int)logfact));
		for (Int k = 0; k<IMAXX; k++)
		{
			for (Int l = 0; l<k; l++)
			{
				Doub ratio = Doub(nseq[k])/nseq[l];
				coeff[k][l]=1.0/(ratio-1.0);
			}
		}
		factrl[0]=1.0;
		for (Int k = 0; k<IMAXX-1; k++)
			factrl[k+1]=(k+1)*factrl[k];
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: boolean StepperSie<D>::dy(VecDoub_I &y,const Doub htot,const Int k,VecDoub_O &yend, Int &ipt,VecDoub_I &scale,D &derivs)
	public <D> boolean StepperSie<D>.dy(RefObject<VecDoub_I> y, Doub htot, Int k, RefObject<VecDoub_O> yend, RefObject<Int> ipt, RefObject<VecDoub_I> scale, RefObject<D> derivs)
	{
		VecDoub del = new VecDoub(n);
		VecDoub ytemp = new VecDoub(n);
		VecDoub dytemp = new VecDoub(n);
		Int nstep = nseq[k];
		Doub h = htot/nstep;
		for (Int i = 0;i<n;i++)
		{
			for (Int j = 0;j<n;j++)
				a[i][j] = -dfdy[i][j];
			a[i][i] += 1.0/h;
		}
		LUdcmp alu = new LUdcmp(a);
		Doub xnew = x+h;
		derivs.argvalue(xnew,y.argvalue,del);
		for (Int i = 0;i<n;i++)
			ytemp[i]=y.argvalue[i];
		alu.solve(del,del);
		if (dense && nstep == k+1)
		{
			ipt.argvalue++;
			for (Int i = 0;i<n;i++)
				fsave[ipt.argvalue][i]=del[i];
		}
		for (Int nn = 1;nn<nstep;nn++)
		{
			for (Int i = 0;i<n;i++)
				ytemp[i] += del[i];
			xnew += h;
			derivs.argvalue(xnew,ytemp,yend.argvalue);
			if (nn == 1 && k<=1)
			{
				Doub del1 = 0.0;
				for (Int i = 0;i<n;i++)
					del1 += SQR(del[i]/scale.argvalue[i]);
				del1 = Math.sqrt(del1);
				derivs.argvalue(x+h,ytemp,dytemp);
				for (Int i = 0;i<n;i++)
					del[i]=dytemp[i]-del[i]/h;
				alu.solve(del,del);
				Doub del2 = 0.0;
				for (Int i = 0;i<n;i++)
					del2 += SQR(del[i]/scale.argvalue[i]);
				del2 = Math.sqrt(del2);
				theta=del2/MAX(1.0,del1);
				if (theta > 1.0)
					return false;
			}
			alu.solve(yend.argvalue,del);
			if (dense && nn >= nstep-k-1)
			{
				ipt.argvalue++;
				for (Int i = 0;i<n;i++)
					fsave[ipt.argvalue][i]=del[i];
			}
		}
		for (Int i = 0;i<n;i++)
			yend.argvalue[i]=ytemp[i]+del[i];
		return true;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
}