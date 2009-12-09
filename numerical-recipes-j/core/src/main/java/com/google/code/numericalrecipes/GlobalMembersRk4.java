package com.google.code.numericalrecipes;
public class GlobalMembersRk4
{
	public static void rk4(RefObject<VecDoub_I> y, RefObject<VecDoub_I> dydx, Doub x, Doub h, RefObject<VecDoub_O> yout, void derivs(Doub, VecDoub_I, VecDoub_O &))
	{
		Int n = y.argvalue.size();
		VecDoub dym = new VecDoub(n);
		VecDoub dyt = new VecDoub(n);
		VecDoub yt = new VecDoub(n);
		Doub hh = h *0.5;
		Doub h6 = h/6.0;
		Doub xh = x+hh;
		for (Int i = 0;i<n;i++)
			yt[i]=y.argvalue[i]+hh *dydx.argvalue[i];
		derivs(xh,yt,dyt);
		for (Int i = 0;i<n;i++)
			yt[i]=y.argvalue[i]+hh *dyt[i];
		derivs(xh,yt,dym);
		for (Int i = 0;i<n;i++)
		{
			yt[i]=y.argvalue[i]+h *dym[i];
			dym[i] += dyt[i];
		}
		derivs(x+h,yt,dyt);
		for (Int i = 0;i<n;i++)
			yout.argvalue[i]=y.argvalue[i]+h6*(dydx.argvalue[i]+dyt[i]+2.0 *dym[i]);
	}
}