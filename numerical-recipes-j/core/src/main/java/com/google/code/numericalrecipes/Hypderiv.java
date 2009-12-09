package com.google.code.numericalrecipes;
public class Hypderiv
{
	public Complex a = new Complex();
	public Complex b = new Complex();
	public Complex c = new Complex();
	public Complex z0 = new Complex();
	public Complex dz = new Complex();
	public Hypderiv(Complex aa, Complex bb, Complex cc, Complex z00, Complex dzz)
	{
		a = aa;
		b = bb;
		c = cc;
		z0 = z00;
		dz = dzz;
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	void operator ()(Doub s, RefObject<VecDoub_I> yy, RefObject<VecDoub_O> dyyds)
	{
		Complex z = new Complex();
		Complex[] y = new Complex[2];
		Complex[] dyds = new Complex[2];
		y[0] =Complex(yy.argvalue[0],yy.argvalue[1]);
		y[1] =Complex(yy.argvalue[2],yy.argvalue[3]);
		z = z0+s *dz;
		dyds[0] =y[1]*dz;
		dyds[1] =(a *b *y[0]-(c-(a+b+1.0)*z)*y[1])*dz/(z*(1.0-z));
		dyyds.argvalue[0] =real(dyds[0]);
		dyyds.argvalue[1] =imag(dyds[0]);
		dyyds.argvalue[2] =real(dyds[1]);
		dyyds.argvalue[3] =imag(dyds[1]);
	}
}