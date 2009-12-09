package com.google.code.numericalrecipes;
public class GlobalMembersHypgeo
{
	public static void hypser(Complex a, Complex b, Complex c, Complex z, RefObject<Complex> series, RefObject<Complex> deriv)
	{
		deriv.argvalue = 0.0;
		Complex fac = 1.0;
		Complex temp = fac;
		Complex aa = a;
		Complex bb = b;
		Complex cc = c;
		for (Int n = 1;n<=1000;n++)
		{
			fac *= ((aa *bb)/cc);
			deriv.argvalue += fac;
			fac *= ((1.0/n)*z);
			series.argvalue = temp+fac;
			if (series.argvalue == temp)
				return;
			temp = series.argvalue;
			aa += 1.0;
			bb += 1.0;
			cc += 1.0;
		}
		throw("convergence failure in hypser");
	}
	public static Complex hypgeo(Complex a, Complex b, Complex c, Complex z)
	{
		final Doub atol = 1.0e-14;
		final Doub rtol = 1.0e-14;
		Complex ans = new Complex();
		Complex dz = new Complex();
		Complex z0 = new Complex();
		Complex[] y = new Complex[2];
		VecDoub yy = new VecDoub(4);
		if (norm(z) <= 0.25)
		{
		RefObject<Complex> tempRefObject = new RefObject<Complex>(ans);
			hypser(a, b, c, z, tempRefObject, y[1]);
			ans = tempRefObject.argvalue;
			return ans;
		}
		else if (real(z) < 0.0)
			z0 = Complex(-0.5,0.0);
		else if (real(z) <= 1.0)
			z0 = Complex(0.5,0.0);
		else
			z0 = Complex(0.0,imag(z) >= 0.0 ? 0.5 : -0.5);
		dz = z-z0;
		hypser(a, b, c, z0, y[0], y[1]);
		yy[0] =real(y[0]);
		yy[1] =imag(y[0]);
		yy[2] =real(y[1]);
		yy[3] =imag(y[1]);
		Hypderiv d = new Hypderiv(a,b,c,z0,dz);
		Output out = new Output();
		Odeint<StepperBS<Hypderiv> > ode = new Odeint<StepperBS<Hypderiv> >(yy,0.0,1.0,atol,rtol,0.1,0.0,out,d);
		ode.integrate();
		y[0] =Complex(yy[0],yy[1]);
		return y[0];
	}
}