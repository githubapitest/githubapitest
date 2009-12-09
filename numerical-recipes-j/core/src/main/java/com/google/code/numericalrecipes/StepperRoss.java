package com.google.code.numericalrecipes;
public class StepperRoss<D> implements StepperBase, Ross_constants
{
	public MatDoub dfdy = new MatDoub();
	public VecDoub dfdx = new VecDoub();
	public VecDoub k1 = new VecDoub();
	public VecDoub k2 = new VecDoub();
	public VecDoub k3 = new VecDoub();
	public VecDoub k4 = new VecDoub();
	public VecDoub k5 = new VecDoub();
	public VecDoub k6 = new VecDoub();
	public VecDoub cont1 = new VecDoub();
	public VecDoub cont2 = new VecDoub();
	public VecDoub cont3 = new VecDoub();
	public VecDoub cont4 = new VecDoub();
	public MatDoub a = new MatDoub();
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	StepperRoss(RefObject<VecDoub_IO> yy, RefObject<VecDoub_IO> dydxx, RefObject<Doub> xx, Doub atoll, Doub rtoll, boolean dens);
	public final void step(Doub htry, RefObject<D> derivs)
	{
		VecDoub dydxnew = new VecDoub(n);
		Doub h = htry;
		derivs.argvalue.jacobian(x,y,dfdx,dfdy);
		for (;;)
		{
			dy(h, derivs);
			Doub err = error();
			if (con.success(err, h))
				break;
			if (Math.abs(h) <= Math.abs(x)*EPS)
				throw("stepsize underflow in StepperRoss");
		}
		derivs.argvalue(x+h,yout,dydxnew);
		if (dense)
		{
			RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(dydxnew);
			prepare_dense(h, tempRefObject);
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
		VecDoub dydxnew = new VecDoub(n);
		Int i = new Int();
		for (i = 0;i<n;i++)
		{
			for (Int j = 0;j<n;j++)
				a[i][j] = -dfdy[i][j];
			a[i][i] += 1.0/(gam *h);
		}
		LUdcmp alu = new LUdcmp(a);
		for (i = 0;i<n;i++)
			ytemp[i]=dydx[i]+h *d1 *dfdx[i];
		 alu.solve(ytemp,k1);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+a21 *k1[i];
		derivs.argvalue(x+c2 *h,ytemp,dydxnew);
		for (i = 0;i<n;i++)
			ytemp[i]=dydxnew[i]+h *d2 *dfdx[i]+c21 *k1[i]/h;
		alu.solve(ytemp,k2);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+a31 *k1[i]+a32 *k2[i];
		derivs.argvalue(x+c3 *h,ytemp,dydxnew);
		for (i = 0;i<n;i++)
			ytemp[i]=dydxnew[i]+h *d3 *dfdx[i]+(c31 *k1[i]+c32 *k2[i])/h;
		alu.solve(ytemp,k3);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+a41 *k1[i]+a42 *k2[i]+a43 *k3[i];
		derivs.argvalue(x+c4 *h,ytemp,dydxnew);
		for (i = 0;i<n;i++)
			ytemp[i]=dydxnew[i]+h *d4 *dfdx[i]+(c41 *k1[i]+c42 *k2[i]+c43 *k3[i])/h;
		alu.solve(ytemp,k4);
		for (i = 0;i<n;i++)
			ytemp[i]=y[i]+a51 *k1[i]+a52 *k2[i]+a53 *k3[i]+a54 *k4[i];
		Doub xph = x+h;
		derivs.argvalue(xph,ytemp,dydxnew);
		for (i = 0;i<n;i++)
			k6[i]=dydxnew[i]+(c51 *k1[i]+c52 *k2[i]+c53 *k3[i]+c54 *k4[i])/h;
		alu.solve(k6,k5);
		for (i = 0;i<n;i++)
			ytemp[i] += k5[i];
		derivs.argvalue(xph,ytemp,dydxnew);
		for (i = 0;i<n;i++)
			k6[i]=dydxnew[i]+(c61 *k1[i]+c62 *k2[i]+c63 *k3[i]+c64 *k4[i]+c65 *k5[i])/h;
		alu.solve(k6,yerr);
		for (i = 0;i<n;i++)
			yout[i]=ytemp[i]+yerr[i];
	}
	public final void prepare_dense(Doub h, RefObject<VecDoub_I> dydxnew)
	{
		for (Int i = 0;i<n;i++)
		{
			cont1[i]=y[i];
			cont2[i]=yout[i];
			cont3[i]=d21 *k1[i]+d22 *k2[i]+d23 *k3[i]+d24 *k4[i]+d25 *k5[i];
			cont4[i]=d31 *k1[i]+d32 *k2[i]+d33 *k3[i]+d34 *k4[i]+d35 *k5[i];
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
		public boolean reject;
		public boolean first_step;
		public Doub errold = new Doub();
		public Doub hold = new Doub();
		public Controller()
		{
			reject = false;
			first_step = true;
		}
		public final boolean success(Doub err, RefObject<Doub> h)
		{
			final Doub safe = 0.9;
			final Doub fac1 = 5.0;
			final Doub fac2 = 1.0/6.0;
			Doub fac = MAX(fac2,MIN(fac1,Math.pow(err,0.25)/safe));
			Doub hnew = h.argvalue/fac;
			if (err <= 1.0)
			{
				if (!first_step)
				{
					Doub facpred = (hold/h.argvalue)*Math.pow(err *err/errold,0.25)/safe;
					facpred = MAX(fac2,MIN(fac1,facpred));
					fac = MAX(fac,facpred);
					hnew = h.argvalue/fac;
				}
				first_step = false;
				hold = h.argvalue;
				errold = MAX(0.01,err);
				if (reject)
					hnew = (h.argvalue >= 0.0 ? MIN(hnew,h.argvalue) : MAX(hnew,h.argvalue));
				hnext = hnew;
				reject = false;
				return true;
			}
			else
			{
				h.argvalue = hnew;
				reject = true;
				return false;
			}
		}
	}
	public Controller con = new Controller();
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
