package com.google.code.numericalrecipes;
public class GlobalMembersOdeint
{

	public static Odeint<Stepper>.Odeint(RefObject<VecDoub_IO> ystartt, Doub xx1, Doub xx2, Doub atol, Doub rtol, Doub h1, Doub hminn, RefObject<Output> outt, RefObject<typename Stepper.Dtype> derivss)
	{
		nvar = ystartt.argvalue.size();
		y = nvar;
		dydx = nvar;
		ystart = ystartt.argvalue;
		x = xx1;
		nok = 0;
		nbad = 0;
		x1 = xx1;
		x2 = xx2;
		hmin = hminn;
		dense = outt.argvalue.dense;
		out = new Output(outt.argvalue);
		derivs = derivss.argvalue;
		s = new Stepper(y,dydx,x,atol,rtol,dense);
		EPS=numeric_limits<Doub>.epsilon();
		h=SIGN(h1,x2-x1);
		for (Int i = 0;i<nvar;i++)
			y[i]=ystart[i];
		out.init(s.neqn, x1, x2);
	}
}