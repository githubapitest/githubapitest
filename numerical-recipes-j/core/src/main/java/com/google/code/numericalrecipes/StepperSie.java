package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
public class StepperSie<D> implements StepperBase
{
	public static final Int KMAXX = 12;
	public static final Int IMAXX = KMAXX+1;
	public Int k_targ = new Int();
	public VecInt nseq = new VecInt();
	public VecDoub cost = new VecDoub();
	public MatDoub table = new MatDoub();
	public MatDoub dfdy = new MatDoub();
	public VecDoub dfdx = new VecDoub();
	public Doub jac_redo = new Doub();
	public boolean calcjac;
	public Doub theta = new Doub();
	public MatDoub a = new MatDoub();
	public Int kright = new Int();
	public MatDoub coeff = new MatDoub();
	public MatDoub fsave = new MatDoub();
	public VecDoub dens = new VecDoub();
	public VecDoub factrl = new VecDoub();
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	StepperSie(RefObject<VecDoub_IO> yy, RefObject<VecDoub_IO> dydxx, RefObject<Doub> xx, Doub atol, Doub rtol, boolean dens);
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
	private boolean first_step = true;
	boolean last_step = false;
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
	private boolean forward;
	boolean reject = false;
	boolean prev_reject = false;
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
	private Doub errold = new Doub();
	public final void step(Doub htry, RefObject<D> derivs)
	{
		final Doub STEPFAC1 = 0.6;
		final Doub STEPFAC2 = 0.93;
		final Doub STEPFAC3 = 0.1;
		final Doub STEPFAC4 = 4.0;
		final Doub STEPFAC5 = 0.5;
		final Doub KFAC1 = 0.7;
		final Doub KFAC2 = 0.9;
//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
//		static boolean first_step=true,last_step=false;
//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
//		static boolean forward,reject=false,prev_reject=false;
//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
//		static Doub errold;
		Int i = new Int();
		Int k = new Int();
		Doub fac = new Doub();
		Doub h = new Doub();
		Doub hnew = new Doub();
		Doub err = new Doub();
		boolean firstk;
		VecDoub hopt = new VecDoub(IMAXX);
		VecDoub work = new VecDoub(IMAXX);
		VecDoub ysav = new VecDoub(n);
		VecDoub yseq = new VecDoub(n);
		VecDoub ymid = new VecDoub(n);
		VecDoub scale = new VecDoub(n);
		work[0]=1.e30;
		h = htry;
		forward = h>0 ? true : false;
		for (i = 0;i<n;i++)
			ysav[i]=y[i];
		if (h != hnext && !first_step)
		{
			last_step = true;
		}
		if (reject)
		{
			prev_reject = true;
			last_step = false;
			theta = 2.0 *jac_redo;
		}
		for (i = 0;i<n;i++)
			scale[i]=atol+rtol *Math.abs(y[i]);
		reject = false;
		firstk = true;
		hnew = Math.abs(h);
//C++ TO JAVA CONVERTER TODO TASK: There are no gotos or labels in Java:
		compute_jac:
		if (theta > jac_redo && !calcjac)
		{
			derivs.argvalue.jacobian(x,y,dfdx,dfdy);
			calcjac = true;
		}
		while (firstk || reject)
		{
			h = forward ? hnew : -hnew;
			firstk = false;
			reject = false;
			if (Math.abs(h) <= Math.abs(x)*EPS)
				throw("step size underflow in StepperSie");
			Int ipt = -1;
			for (k = 0; k<=k_targ+1;k++)
			{
				RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(ysav);
				RefObject<VecDoub_O> tempRefObject2 = new RefObject<VecDoub_O>(yseq);
				RefObject<Int> tempRefObject3 = new RefObject<Int>(ipt);
				RefObject<VecDoub_I> tempRefObject4 = new RefObject<VecDoub_I>(scale);
				boolean success = dy(tempRefObject, h, k, tempRefObject2, tempRefObject3, tempRefObject4, derivs);
				ysav = tempRefObject.argvalue;
				yseq = tempRefObject2.argvalue;
				ipt = tempRefObject3.argvalue;
				scale = tempRefObject4.argvalue;
				if (!success)
				{
					reject = true;
					hnew = Math.abs(h)*STEPFAC5;
					break;
				}
				if (k == 0)
					 y=yseq;
				else
					for (i = 0;i<n;i++)
						table[k-1][i]=yseq[i];
				if (k != 0)
				{
					RefObject<MatDoub_IO> tempRefObject5 = new RefObject<MatDoub_IO>(table);
					RefObject<VecDoub_IO> tempRefObject6 = new RefObject<VecDoub_IO>(y);
					polyextr(k, tempRefObject5, tempRefObject6);
					table = tempRefObject5.argvalue;
					y = tempRefObject6.argvalue;
					err = 0.0;
					for (i = 0;i<n;i++)
					{
						scale[i]=atol+rtol *Math.abs(ysav[i]);
						err+=SQR((y[i]-table[0][i])/scale[i]);
					}
					err = Math.sqrt(err/n);
					if (err > 1.0/EPS || (k > 1 && err >= errold))
					{
						reject = true;
						hnew = Math.abs(h)*STEPFAC5;
						break;
					}
					errold = max(4.0 *err,1.0);
					Doub expo = 1.0/(k+1);
					Doub facmin = Math.pow(STEPFAC3,expo);
					if (err == 0.0)
						fac = 1.0/facmin;
					else
					{
						fac = STEPFAC2/Math.pow(err/STEPFAC1,expo);
						fac = MAX(facmin/STEPFAC4,MIN(1.0/facmin,fac));
					}
					hopt[k]=Math.abs(h *fac);
					work[k]=cost[k]/hopt[k];
					if ((first_step || last_step) && err <= 1.0)
						break;
					if (k == k_targ-1 && !prev_reject && !first_step && !last_step)
					{
						if (err <= 1.0)
							break;
						else if (err>nseq[k_targ]*nseq[k_targ+1]*4.0)
						{
							reject = true;
							k_targ = k;
							if (k_targ>1 && work[k-1]<KFAC1 *work[k])
								k_targ--;
							hnew = hopt[k_targ];
							break;
						}
					}
					if (k == k_targ)
					{
						if (err <= 1.0)
							break;
						else if (err>nseq[k+1]*2.0)
						{
							reject = true;
							if (k_targ>1 && work[k-1]<KFAC1 *work[k])
								k_targ--;
							hnew = hopt[k_targ];
							break;
						}
					}
					if (k == k_targ+1)
					{
						if (err > 1.0)
						{
							reject = true;
							if (k_targ>1 && work[k_targ-1]<KFAC1 *work[k_targ])
								k_targ--;
							hnew = hopt[k_targ];
						}
						break;
					}
				}
			}
			if (reject)
			{
				prev_reject = true;
				if (!calcjac)
				{
					theta = 2.0 *jac_redo;
//C++ TO JAVA CONVERTER TODO TASK: There are no gotos or labels in Java:
					goto compute_jac;
				}
			}
		}
		calcjac = false;
		if (dense)
		{
			RefObject<VecDoub_I> tempRefObject7 = new RefObject<VecDoub_I>(ysav);
			RefObject<VecDoub_I> tempRefObject8 = new RefObject<VecDoub_I>(scale);
			RefObject<Doub> tempRefObject9 = new RefObject<Doub>(err);
			prepare_dense(h, tempRefObject7, tempRefObject8, k, tempRefObject9);
			ysav = tempRefObject7.argvalue;
			scale = tempRefObject8.argvalue;
			err = tempRefObject9.argvalue;
		}
		xold=x;
		x+=h;
		hdid=h;
		first_step = false;
		Int kopt = new Int();
		if (k == 1)
			kopt = 2;
		else if (k <= k_targ)
		{
			kopt = k;
			if (work[k-1] < KFAC1 *work[k])
				kopt = k-1;
			else if (work[k] < KFAC2 *work[k-1])
				kopt = MIN(k+1,KMAXX-1);
		}
		else
		{
			kopt = k-1;
			if (k > 2 && work[k-2] < KFAC1 *work[k-1])
				kopt = k-2;
			if (work[k] < KFAC2 *work[kopt])
				kopt = MIN(k,KMAXX-1);
		}
		if (prev_reject)
		{
			k_targ = MIN(kopt,k);
			hnew = MIN(Math.abs(h),hopt[k_targ]);
			prev_reject = false;
		}
		else
		{
			if (kopt <= k)
				hnew = hopt[kopt];
			else
			{
				if (k<k_targ && work[k]<KFAC2 *work[k-1])
					hnew = hopt[k]*cost[kopt+1]/cost[k];
				else
					hnew = hopt[k]*cost[kopt]/cost[k];
			}
			k_targ = kopt;
		}
		if (forward)
			hnext=hnew;
		else
			hnext=-hnew;
	}
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	boolean dy(RefObject<VecDoub_I> y, Doub htot, Int k, RefObject<VecDoub_O> yend, RefObject<Int> ipt, RefObject<VecDoub_I> scale, RefObject<D> derivs);
	public final void polyextr(Int k, RefObject<MatDoub_IO> table, RefObject<VecDoub_IO> last)
	{
		Int l = last.argvalue.size();
		for (Int j = k-1; j>0; j--)
			for (Int i = 0; i<l; i++)
				table.argvalue[j-1][i]=table.argvalue[j][i]+coeff[k][j]*(table.argvalue[j][i]-table.argvalue[j-1][i]);
		for (Int i = 0; i<l; i++)
			last.argvalue[i]=table.argvalue[0][i]+coeff[k][0]*(table.argvalue[0][i]-last.argvalue[i]);
	}
	public final void prepare_dense(Doub h, RefObject<VecDoub_I> ysav, RefObject<VecDoub_I> scale, Int k, RefObject<Doub> error)
	{
		kright = k;
		for (Int i = 0; i<n; i++)
		{
			dens[i]=ysav.argvalue[i];
			dens[n+i]=y[i];
		}
		for (Int klr = 0; klr < kright; klr++)
		{
			if (klr >= 1)
			{
				for (Int kk = klr; kk<=k; kk++)
				{
					Int lbeg = ((kk+3)*kk)/2;
					Int lend = lbeg-kk+1;
					for (Int l = lbeg; l>=lend; l--)
						for (Int i = 0; i<n; i++)
							fsave[l][i]=fsave[l][i]-fsave[l-1][i];
				}
			}
			for (Int kk = klr; kk<=k; kk++)
			{
				Doub facnj = nseq[kk];
				facnj = Math.pow(facnj,klr+1)/factrl[klr+1];
				Int ipt = ((kk+3)*kk)/2;
					Int krn = (kk+2)*n;
				for (Int i = 0; i<n; i++)
				{
					dens[krn+i]=fsave[ipt][i]*facnj;
				}
			}
			for (Int j = klr+1; j<=k; j++)
			{
				Doub dblenj = nseq[j];
				for (Int l = j; l>=klr+1; l--)
				{
					Doub factor = dblenj/nseq[l-1]-1.0;
					for (Int i = 0; i<n; i++)
					{
						Int krn = (l+2)*n+i;
						dens[krn-n]=dens[krn]+(dens[krn]-dens[krn-n])/factor;
					}
				}
			}
		}
		for (Int in = 0; in<n; in++)
		{
			for (Int j = 1; j<=kright+1; j++)
			{
				Int ii = n *j+in;
				dens[ii]=dens[ii]-dens[ii-n];
			}
		}
	}
	public final Doub dense_out(Int i, Doub x, Doub h)
	{
		Doub theta = (x-xold)/h;
		Int k = kright;
		Doub yinterp = dens[(k+1)*n+i];
		for (Int j = 1; j<=k; j++)
			yinterp = dens[(k+1-j)*n+i]+yinterp*(theta-1.0);
		return dens[i]+yinterp *theta;
	}
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	void dense_interp(Int n, RefObject<VecDoub_IO> y, Int imit);
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
