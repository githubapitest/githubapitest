package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
public class Trapzd<T> implements Quadrature
{
	public Doub a = new Doub();
	public Doub b = new Doub();
	public Doub s = new Doub();
	public T func;
	public Trapzd()
	{
	}
	public Trapzd(RefObject<T> funcc, Doub aa, Doub bb)
	{
		func = funcc.argvalue;
		a = aa;
		b = bb;
		n=0;
	}
	public final Doub next()
	{
		Doub x = new Doub();
		Doub tnm = new Doub();
		Doub sum = new Doub();
		Doub del = new Doub();
		Int it = new Int();
		Int j = new Int();
		n++;
		if (n == 1)
		{
			return (s = 0.5*(b-a)*(func(a)+func(b)));
		}
		else
		{
			for (it = 1,j = 1;j<n-1;j++)
				it <<= 1;
			tnm = it;
			del = (b-a)/tnm;
			x = a+0.5 *del;
			for (sum = 0.0,j = 0;j<it;j++,x+=del)
				sum += func(x);
			s = 0.5*(s+(b-a)*sum/tnm);
			return s;
		}
	}
}