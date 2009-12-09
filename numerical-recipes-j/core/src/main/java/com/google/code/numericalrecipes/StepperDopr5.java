package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
public class StepperDopr5<D> implements StepperBase
{
	public VecDoub k2 = new VecDoub();
	public VecDoub k3 = new VecDoub();
	public VecDoub k4 = new VecDoub();
	public VecDoub k5 = new VecDoub();
	public VecDoub k6 = new VecDoub();
	public VecDoub rcont1 = new VecDoub();
	public VecDoub rcont2 = new VecDoub();
	public VecDoub rcont3 = new VecDoub();
	public VecDoub rcont4 = new VecDoub();
	public VecDoub rcont5 = new VecDoub();
	public VecDoub dydxnew = new VecDoub();
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	StepperDopr5(RefObject<VecDoub_IO> yy, RefObject<VecDoub_IO> dydxx, RefObject<Doub> xx, Doub atoll, Doub rtoll, boolean dens);
	public final void step(Doub htry, RefObject<D> derivs)
	{
		Doub h = htry;
		for (;;)
		{
			dy(h, derivs);
			Doub err = error();
			if (con.success(err, h))
				break;
			if (Math.abs(h) <= Math.abs(x)*EPS)
				throw("stepsize underflow in StepperDopr5");
		}
		if (dense)
			prepare_dense(h, derivs);
		dydx=dydxnew;
		y=yout;
		xold=x;
		x += (hdid=h);
		hnext = con.hnext;
	}
	public final void dy(Doub h, RefObject<D> derivs)
	{
		final Doub c2 = 0.2;
		final Doub c3 = 0.3;
		final Doub c4 = 0.8;
		final Doub c5 = 8.0/9.0;
		final Doub a21 = 0.2;
		final Doub a31 = 3.0/40.0;
		final Doub a32 = 9.0/40.0;
		final Doub a41 = 44.0/45.0;
		final Doub a42 = -56.0/15.0;
		final Doub a43 = 32.0/9.0;
		final Doub a51 = 19372.0/6561.0;
		final Doub a52 = -25360.0/2187.0;
		final Doub a53 = 64448.0/6561.0;
		final Doub a54 = -212.0/729.0;
		final Doub a61 = 9017.0/3168.0;
		final Doub a62 = -355.0/33.0;
		final Doub a63 = 46732.0/5247.0;
		final Doub a64 = 49.0/176.0;
		final Doub a65 = -5103.0/18656.0;
		final Doub a71 = 35.0/384.0;
		final Doub a73 = 500.0/1113.0;
		final Doub a74 = 125.0/192.0;
		final Doub a75 = -2187.0/6784.0;
		final Doub a76 = 11.0/84.0;
		final Doub e1 = 71.0/57600.0;
		final Doub e3 = -71.0/16695.0;
		final Doub e4 = 71.0/1920.0;
		final Doub e5 = -17253.0/339200.0;
		final Doub e6 = 22.0/525.0;
		final Doub e7 = -1.0/40.0;
		VecDoub ytemp = new VecDoub(n);
		Int i = new Int();
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h *a21 *dydx[i];
		derivs.argvalue(x+c2 *h,ytemp,k2);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a31 *dydx[i]+a32 *k2[i]);
		derivs.argvalue(x+c3 *h,ytemp,k3);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a41 *dydx[i]+a42 *k2[i]+a43 *k3[i]);
		derivs.argvalue(x+c4 *h,ytemp,k4);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a51 *dydx[i]+a52 *k2[i]+a53 *k3[i]+a54 *k4[i]);
		derivs.argvalue(x+c5 *h,ytemp,k5);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+h*(a61 *dydx[i]+a62 *k2[i]+a63 *k3[i]+a64 *k4[i]+a65 *k5[i]);
		Doub xph = x+h;
		derivs.argvalue(xph,ytemp,k6);
		for (i = 0;i<n;i++)
			yout[i]=y[i]+h*(a71 *dydx[i]+a73 *k3[i]+a74 *k4[i]+a75 *k5[i]+a76 *k6[i]);
		derivs.argvalue(xph,yout,dydxnew);
		for (i = 0;i<n;i++)
		{
			yerr[i]=h*(e1 *dydx[i]+e3 *k3[i]+e4 *k4[i]+e5 *k5[i]+e6 *k6[i]+e7 *dydxnew[i]);
		}
	}
	public final void prepare_dense(Doub h, RefObject<D> derivs)
	{
		VecDoub ytemp = new VecDoub(n);
		final Doub d1 = -12715105075.0/11282082432.0;
		final Doub d3 = 87487479700.0/32700410799.0;
		final Doub d4 = -10690763975.0/1880347072.0;
		final Doub d5 = 701980252875.0/199316789632.0;
		final Doub d6 = -1453857185.0/822651844.0;
		final Doub d7 = 69997945.0/29380423.0;
		for (Int i = 0;i<n;i++)
		{
			rcont1[i]=y[i];
			Doub ydiff = yout[i]-y[i];
			rcont2[i]=ydiff;
			Doub bspl = h *dydx[i]-ydiff;
			rcont3[i]=bspl;
			rcont4[i]=ydiff-h *dydxnew[i]-bspl;
			rcont5[i]=h*(d1 *dydx[i]+d3 *k3[i]+d4 *k4[i]+d5 *k5[i]+d6 *k6[i]+ d7 *dydxnew[i]);
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Doub dense_out(Int i, Doub x, Doub h);
	public final Doub error()
	{
		Doub err = 0.0;
		Doub sk = new Doub();
		for (Int i = 0;i<n;i++)
		{
			sk = atol+rtol *MAX(Math.abs(y[i]),Math.abs(yout[i]));
			err += SQR(yerr[i]/sk);
		}
		return Math.sqrt(err/n);
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
			final Doub alpha = 0.2-beta *0.75;
			final Doub safe = 0.9;
			final Doub minscale = 0.2;
			final Doub maxscale = 10.0;
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
