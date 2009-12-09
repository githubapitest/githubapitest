package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
public class StepperBS<D> implements StepperBase
{
	public static final Int KMAXX = 8;
	public static final Int IMAXX = KMAXX+1;
	public Int k_targ = new Int();
	public VecInt nseq = new VecInt();
	public VecInt cost = new VecInt();
	public MatDoub table = new MatDoub();
	public VecDoub dydxnew = new VecDoub();
	public Int mu = new Int();
	public MatDoub coeff = new MatDoub();
	public VecDoub errfac = new VecDoub();
	public MatDoub ysave = new MatDoub();
	public MatDoub fsave = new MatDoub();
	public VecInt ipoint = new VecInt();
	public VecDoub dens = new VecDoub();
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	StepperBS(RefObject<VecDoub_IO> yy, RefObject<VecDoub_IO> dydxx, RefObject<Doub> xx, Doub atol, Doub rtol, boolean dens);
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
	private boolean first_step = true;
	boolean last_step = false;
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
	private boolean forward;
	boolean reject = false;
	boolean prev_reject = false;
	public final void step(Doub htry, RefObject<D> derivs)
	{
		final Doub STEPFAC1 = 0.65;
		final Doub STEPFAC2 = 0.94;
		final Doub STEPFAC3 = 0.02;
		final Doub STEPFAC4 = 4.0;
		final Doub KFAC1 = 0.8;
		final Doub KFAC2 = 0.9;
//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
//		static boolean first_step=true,last_step=false;
//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
//		static boolean forward,reject=false,prev_reject=false;
		Int i = new Int();
		Int k = new Int();
		Doub fac = new Doub();
		Doub h = new Doub();
		Doub hnew = new Doub();
		Doub hopt_int = new Doub();
		Doub err = new Doub();
		boolean firstk;
		VecDoub hopt = new VecDoub(IMAXX);
		VecDoub work = new VecDoub(IMAXX);
		VecDoub ysav = new VecDoub(n);
		VecDoub yseq = new VecDoub(n);
		VecDoub ymid = new VecDoub(n);
		VecDoub scale = new VecDoub(n);
		work[0]=0;
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
		}
		reject = false;
		firstk = true;
		hnew = Math.abs(h);
//C++ TO JAVA CONVERTER TODO TASK: There are no gotos or labels in Java:
		interp_error:
		while (firstk || reject)
		{
			h = forward ? hnew : -hnew;
			firstk = false;
			reject = false;
			if (Math.abs(h) <= Math.abs(x)*EPS)
				throw("step size underflow in StepperBS");
			Int ipt = -1;
			for (k = 0; k<=k_targ+1;k++)
			{
				RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(ysav);
				RefObject<VecDoub_O> tempRefObject2 = new RefObject<VecDoub_O>(yseq);
				RefObject<Int> tempRefObject3 = new RefObject<Int>(ipt);
				dy(tempRefObject, h, k, tempRefObject2, tempRefObject3, derivs);
				ysav = tempRefObject.argvalue;
				yseq = tempRefObject2.argvalue;
				ipt = tempRefObject3.argvalue;
				if (k == 0)
					 y=yseq;
				else
					for (i = 0;i<n;i++)
						table[k-1][i]=yseq[i];
				if (k != 0)
				{
					RefObject<MatDoub_IO> tempRefObject4 = new RefObject<MatDoub_IO>(table);
					RefObject<VecDoub_IO> tempRefObject5 = new RefObject<VecDoub_IO>(y);
					polyextr(k, tempRefObject4, tempRefObject5);
					table = tempRefObject4.argvalue;
					y = tempRefObject5.argvalue;
					err = 0.0;
					for (i = 0;i<n;i++)
					{
						scale[i]=atol+rtol *MAX(Math.abs(ysav[i]),Math.abs(y[i]));
						err+=SQR((y[i]-table[0][i])/scale[i]);
					}
					err = Math.sqrt(err/n);
					Doub expo = 1.0/(2 *k+1);
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
						else if (err>SQR(nseq[k_targ]*nseq[k_targ+1]/(nseq[0]*nseq[0])))
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
						else if (err>SQR(nseq[k+1]/nseq[0]))
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
				prev_reject = true;
		}
		derivs.argvalue(x+h,y,dydxnew);
		if (dense)
		{
			RefObject<VecDoub_I> tempRefObject6 = new RefObject<VecDoub_I>(dydxnew);
			RefObject<VecDoub_I> tempRefObject7 = new RefObject<VecDoub_I>(ysav);
			RefObject<VecDoub_I> tempRefObject8 = new RefObject<VecDoub_I>(scale);
			RefObject<Doub> tempRefObject9 = new RefObject<Doub>(err);
			prepare_dense(h, tempRefObject6, tempRefObject7, tempRefObject8, k, tempRefObject9);
			dydxnew = tempRefObject6.argvalue;
			ysav = tempRefObject7.argvalue;
			scale = tempRefObject8.argvalue;
			err = tempRefObject9.argvalue;
			hopt_int = h/MAX(Math.pow(err,1.0/(2 *k+3)),0.01);
			if (err > 10.0)
			{
				hnew = Math.abs(hopt_int);
				reject = true;
				prev_reject = true;
//C++ TO JAVA CONVERTER TODO TASK: There are no gotos or labels in Java:
				goto interp_error;
			}
		}
		dydx=dydxnew;
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
		if (dense)
			hnew = MIN(hnew,Math.abs(hopt_int));
		if (forward)
			hnext=hnew;
		else
			hnext=-hnew;
	}
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	virtual void dy(RefObject<VecDoub_I> y, Doub htot, Int k, RefObject<VecDoub_O> yend, RefObject<Int> ipt, RefObject<D> derivs);
	public final void polyextr(Int k, RefObject<MatDoub_IO> table, RefObject<VecDoub_IO> last)
	{
		Int l = last.argvalue.size();
		for (Int j = k-1; j>0; j--)
			for (Int i = 0; i<l; i++)
				table.argvalue[j-1][i]=table.argvalue[j][i]+coeff[k][j]*(table.argvalue[j][i]-table.argvalue[j-1][i]);
		for (Int i = 0; i<l; i++)
			last.argvalue[i]=table.argvalue[0][i]+coeff[k][0]*(table.argvalue[0][i]-last.argvalue[i]);
	}
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	virtual void prepare_dense(Doub h, RefObject<VecDoub_I> dydxnew, RefObject<VecDoub_I> ysav, RefObject<VecDoub_I> scale, Int k, RefObject<Doub> error);
	public Doub dense_out(Int i, Doub x, Doub h)
	{
		Doub theta = (x-xold)/h;
		Doub theta1 = 1.0-theta;
		Doub yinterp = dens[i]+theta*(dens[n+i]+theta1*(dens[2 *n+i]*theta +dens[3 *n+i]*theta1));
		if (mu<0)
			return yinterp;
		Doub theta05 = theta-0.5;
		Doub t4 = SQR(theta *theta1);
		Doub c = dens[n*(mu+4)+i];
		for (Int j = mu;j>0; j--)
			c = dens[n*(j+3)+i]+c *theta05/j;
		yinterp += t4 *c;
		return yinterp;
	}
	public void dense_interp(Int n, RefObject<VecDoub_IO> y, Int imit)
	{
		Doub y0 = new Doub();
		Doub y1 = new Doub();
		Doub yp0 = new Doub();
		Doub yp1 = new Doub();
		Doub ydiff = new Doub();
		Doub aspl = new Doub();
		Doub bspl = new Doub();
		Doub ph0 = new Doub();
		Doub ph1 = new Doub();
		Doub ph2 = new Doub();
		Doub ph3 = new Doub();
		Doub fac1 = new Doub();
		Doub fac2 = new Doub();
		VecDoub a = new VecDoub(31);
		for (Int i = 0; i<n; i++)
		{
			y0 = y.argvalue[i];
			y1 = y.argvalue[2 *n+i];
			yp0 = y.argvalue[n+i];
			yp1 = y.argvalue[3 *n+i];
			ydiff = y1-y0;
			aspl = -yp1+ydiff;
			bspl = yp0-ydiff;
			y.argvalue[n+i]=ydiff;
			y.argvalue[2 *n+i]=aspl;
			y.argvalue[3 *n+i]=bspl;
			if (imit < 0)
				continue;
			ph0 = (y0+y1)*0.5+0.125*(aspl+bspl);
			ph1 = ydiff+(aspl-bspl)*0.25;
			ph2 = -(yp0-yp1);
			ph3 = 6.0*(bspl-aspl);
			if (imit >= 1)
			{
				a[1]=16.0*(y.argvalue[5 *n+i]-ph1);
				if (imit >= 3)
				{
					a[3]=16.0*(y.argvalue[7 *n+i]-ph3+3 *a[1]);
					for (Int im = 5; im <=imit; im+=2)
					{
						fac1 = im*(im-1)/2.0;
						fac2 = fac1*(im-2)*(im-3)*2.0;
						a[im]=16.0*(y.argvalue[(im+4)*n+i]+fac1 *a[im-2]-fac2 *a[im-4]);
					}
				}
			}
			a[0]=(y.argvalue[4 *n+i]-ph0)*16.0;
			if (imit >= 2)
			{
				a[2]=(y.argvalue[n *6+i]-ph2+a[0])*16.0;
				for (Int im = 4; im <=imit; im+=2)
				{
					fac1 = im*(im-1)/2.0;
					fac2 = im*(im-1)*(im-2)*(im-3);
					a[im]=(y.argvalue[n*(im+4)+i]+a[im-2]*fac1-a[im-4]*fac2)*16.0;
				}
			}
			for (Int im = 0; im<=imit; im++)
				y.argvalue[n*(im+4)+i]=a[im];
		}
	}
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
