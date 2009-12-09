package com.google.code.numericalrecipes;
public class StepperDopr853<D> implements StepperBase, Dopr853_constants
{
	public VecDoub yerr2 = new VecDoub();
	public VecDoub k2 = new VecDoub();
	public VecDoub k3 = new VecDoub();
	public VecDoub k4 = new VecDoub();
	public VecDoub k5 = new VecDoub();
	public VecDoub k6 = new VecDoub();
	public VecDoub k7 = new VecDoub();
	public VecDoub k8 = new VecDoub();
	public VecDoub k9 = new VecDoub();
	public VecDoub k10 = new VecDoub();
	public VecDoub rcont1 = new VecDoub();
	public VecDoub rcont2 = new VecDoub();
	public VecDoub rcont3 = new VecDoub();
	public VecDoub rcont4 = new VecDoub();
	public VecDoub rcont5 = new VecDoub();
	public VecDoub rcont6 = new VecDoub();
	public VecDoub rcont7 = new VecDoub();
	public VecDoub rcont8 = new VecDoub();
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	StepperDopr853(RefObject<VecDoub_IO> yy, RefObject<VecDoub_IO> dydxx, RefObject<Doub> xx, Doub atoll, Doub rtoll, boolean dens);
	public final void step(Doub htry, RefObject<D> derivs)
	{
		VecDoub dydxnew = new VecDoub(n);
		Doub h = htry;
		for (;;)
		{
			dy(h, derivs);
			Doub err = error(h);
			if (con.success(err, h))
				break;
			if (Math.abs(h) <= Math.abs(x)*EPS)
				throw("stepsize underflow in StepperDopr853");
		}
		derivs.argvalue(x+h,yout,dydxnew);
		if (dense)
		{
			RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(dydxnew);
			prepare_dense(h, tempRefObject, derivs);
			dydxnew = tempRefObject.argvalue;
		}
		dydx=dydxnew;
		y=yout;
		xold=x;
		x += (hdid=h);
		hnext = con.hnext;
	}
	public final void dy(Doub h, RefObject<D> derivs)
	{
		VecDoub ytemp = new VecDoub(n);
		Int i = new Int();
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h *a21 *dydx[i];
		derivs.argvalue(x+c2 *h,ytemp,k2);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a31 *dydx[i]+a32 *k2[i]);
		derivs.argvalue(x+c3 *h,ytemp,k3);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a41 *dydx[i]+a43 *k3[i]);
		derivs.argvalue(x+c4 *h,ytemp,k4);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a51 *dydx[i]+a53 *k3[i]+a54 *k4[i]);
		derivs.argvalue(x+c5 *h,ytemp,k5);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a61 *dydx[i]+a64 *k4[i]+a65 *k5[i]);
		derivs.argvalue(x+c6 *h,ytemp,k6);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a71 *dydx[i]+a74 *k4[i]+a75 *k5[i]+a76 *k6[i]);
		derivs.argvalue(x+c7 *h,ytemp,k7);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a81 *dydx[i]+a84 *k4[i]+a85 *k5[i]+a86 *k6[i]+a87 *k7[i]);
		derivs.argvalue(x+c8 *h,ytemp,k8);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a91 *dydx[i]+a94 *k4[i]+a95 *k5[i]+a96 *k6[i]+a97 *k7[i]+ a98 *k8[i]);
		derivs.argvalue(x+c9 *h,ytemp,k9);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a101 *dydx[i]+a104 *k4[i]+a105 *k5[i]+a106 *k6[i]+ a107 *k7[i]+a108 *k8[i]+a109 *k9[i]);
		derivs.argvalue(x+c10 *h,ytemp,k10);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a111 *dydx[i]+a114 *k4[i]+a115 *k5[i]+a116 *k6[i]+ a117 *k7[i]+a118 *k8[i]+a119 *k9[i]+a1110 *k10[i]);
		derivs.argvalue(x+c11 *h,ytemp,k2);
		Doub xph = x+h;
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a121 *dydx[i]+a124 *k4[i]+a125 *k5[i]+a126 *k6[i]+ a127 *k7[i]+a128 *k8[i]+a129 *k9[i]+a1210 *k10[i]+a1211 *k2[i]);
		derivs.argvalue(xph,ytemp,k3);
		for (i = 0;i<n;i++)
		{
			k4[i]=b1 *dydx[i]+b6 *k6[i]+b7 *k7[i]+b8 *k8[i]+b9 *k9[i]+b10 *k10[i]+ b11 *k2[i]+b12 *k3[i];
			yout[i]=y[i]+h *k4[i];
		}
		for (i = 0;i<n;i++)
		{
			yerr[i]=k4[i]-bhh1 *dydx[i]-bhh2 *k9[i]-bhh3 *k3[i];
			yerr2[i]=er1 *dydx[i]+er6 *k6[i]+er7 *k7[i]+er8 *k8[i]+er9 *k9[i]+ er10 *k10[i]+er11 *k2[i]+er12 *k3[i];
		}
	}
	public final void prepare_dense(Doub h, RefObject<VecDoub_I> dydxnew, RefObject<D> derivs)
	{
		Int i = new Int();
		Doub ydiff = new Doub();
		Doub bspl = new Doub();
		VecDoub ytemp = new VecDoub(n);
		for (i = 0;i<n;i++)
		{
			rcont1[i]=y[i];
			ydiff = yout[i]-y[i];
			rcont2[i]=ydiff;
			bspl = h *dydx[i]-ydiff;
			rcont3[i]=bspl;
			rcont4[i]=ydiff-h *dydxnew.argvalue[i]-bspl;
			rcont5[i]=d41 *dydx[i]+d46 *k6[i]+d47 *k7[i]+d48 *k8[i]+ d49 *k9[i]+d410 *k10[i]+d411 *k2[i]+d412 *k3[i];
			rcont6[i]=d51 *dydx[i]+d56 *k6[i]+d57 *k7[i]+d58 *k8[i]+ d59 *k9[i]+d510 *k10[i]+d511 *k2[i]+d512 *k3[i];
			rcont7[i]=d61 *dydx[i]+d66 *k6[i]+d67 *k7[i]+d68 *k8[i]+ d69 *k9[i]+d610 *k10[i]+d611 *k2[i]+d612 *k3[i];
			rcont8[i]=d71 *dydx[i]+d76 *k6[i]+d77 *k7[i]+d78 *k8[i]+ d79 *k9[i]+d710 *k10[i]+d711 *k2[i]+d712 *k3[i];
		}
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a141 *dydx[i]+a147 *k7[i]+a148 *k8[i]+a149 *k9[i]+ a1410 *k10[i]+a1411 *k2[i]+a1412 *k3[i]+a1413 *dydxnew.argvalue[i]);
		derivs.argvalue(x+c14 *h,ytemp,k10);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a151 *dydx[i]+a156 *k6[i]+a157 *k7[i]+a158 *k8[i]+ a1511 *k2[i]+a1512 *k3[i]+a1513 *dydxnew.argvalue[i]+a1514 *k10[i]);
		derivs.argvalue(x+c15 *h,ytemp,k2);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a161 *dydx[i]+a166 *k6[i]+a167 *k7[i]+a168 *k8[i]+ a169 *k9[i]+a1613 *dydxnew.argvalue[i]+a1614 *k10[i]+a1615 *k2[i]);
		derivs.argvalue(x+c16 *h,ytemp,k3);
		for (i = 0;i<n;i++)
		{
			rcont5[i]=h*(rcont5[i]+d413 *dydxnew.argvalue[i]+d414 *k10[i]+d415 *k2[i]+d416 *k3[i]);
			rcont6[i]=h*(rcont6[i]+d513 *dydxnew.argvalue[i]+d514 *k10[i]+d515 *k2[i]+d516 *k3[i]);
			rcont7[i]=h*(rcont7[i]+d613 *dydxnew.argvalue[i]+d614 *k10[i]+d615 *k2[i]+d616 *k3[i]);
			rcont8[i]=h*(rcont8[i]+d713 *dydxnew.argvalue[i]+d714 *k10[i]+d715 *k2[i]+d716 *k3[i]);
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Doub dense_out(Int i, Doub x, Doub h);
	public final Doub error(Doub h)
	{
		Doub err = 0.0;
		Doub err2 = 0.0;
		Doub sk = new Doub();
		Doub deno = new Doub();
		for (Int i = 0;i<n;i++)
		{
			sk = atol+rtol *MAX(Math.abs(y[i]),Math.abs(yout[i]));
			err2 += SQR(yerr[i]/sk);
			err += SQR(yerr2[i]/sk);
		}
		deno = err+0.01 *err2;
		if (deno <= 0.0)
			deno = 1.0;
		return Math.abs(h)*err *Math.sqrt(1.0/(n *deno));
	}
	public static class Controller
	{
		public Doub hnext = new Doub();
		public Doub errold = new Doub();
		public boolean reject;
		public Controller()
		{
			reject = false;
			errold = 1.0e-4;
		}
		public final boolean success(Doub err, RefObject<Doub> h)
		{
			final Doub beta = 0.0;
			final Doub alpha = 1.0/8.0-beta *0.2;
			final Doub safe = 0.9;
			final Doub minscale = 0.333;
			final Doub maxscale = 6.0;
			Doub scale = new Doub();
			if (err <= 1.0)
			{
				if (err == 0.0)
					scale = maxscale;
				else
				{
					scale = safe *Math.pow(err,-alpha)*Math.pow(errold,beta);
					if (scale<minscale)
						scale = minscale;
					if (scale>maxscale)
						scale = maxscale;
				}
				if (reject)
					hnext = h.argvalue *MIN(scale,1.0);
				else
					hnext = h.argvalue *scale;
				errold = MAX(err,1.0e-4);
				reject = false;
				return true;
			}
			else
			{
				scale = MAX(safe *Math.pow(err,-alpha),minscale);
				h.argvalue *= scale;
				reject = true;
				return false;
			}
		}
	}
	public Controller con = new Controller();
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
