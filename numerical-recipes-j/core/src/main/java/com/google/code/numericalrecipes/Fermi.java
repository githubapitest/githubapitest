package com.google.code.numericalrecipes;
public class Fermi
{
	public Doub kk = new Doub();
	public Doub etaa = new Doub();
	public Doub thetaa = new Doub();
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(Doub t)
	{
		Doub x = new Doub();
		x = Math.exp(t-Math.exp(-t));
		return x*(1.0+Math.exp(-t))*Math.pow(x,kk)*Math.sqrt(1.0+thetaa *0.5 *x)/ (Math.exp(x-etaa)+1.0);
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(Doub x, Doub del)
	{
		if (x < 1.0)
			return Math.pow(del,kk)*Math.sqrt(1.0+thetaa *0.5 *x)/(Math.exp(x-etaa)+1.0);
		else
			return Math.pow(x,kk)*Math.sqrt(1.0+thetaa *0.5 *x)/(Math.exp(x-etaa)+1.0);
	}
	public final Doub val(Doub k, Doub eta, Doub theta)
	{
		final Doub EPS = 3.0e-9;
		final Int NMAX = 11;
		Doub a = new Doub();
		Doub aa = new Doub();
		Doub b = new Doub();
		Doub bb = new Doub();
		Doub hmax = new Doub();
		Doub olds = new Doub();
		Doub sum = new Doub();
		kk = k;
		etaa = eta;
		thetaa = theta;
		if (eta <= 15.0)
		{
			a = -4.5;
			b = 5.0;
			Trapzd<Fermi> s = new Trapzd<Fermi>(this,a,b);
			for (Int i = 1;i<=NMAX;i++)
			{
				sum = s.next();
				if (i > 3)
					if (Math.abs(sum-olds) <= EPS *Math.abs(olds))
						return sum;
				olds = sum;
			}
		}
		else
		{
			a = 0.0;
			b = eta;
			aa = eta;
			bb = eta+60.0;
			hmax = 4.3;
			DErule<Fermi> s = new DErule<Fermi>(this,a,b,hmax);
			DErule<Fermi> ss = new DErule<Fermi>(this,aa,bb,hmax);
			for (Int i = 1;i<=NMAX;i++)
			{
				sum = s.next()+ss.next();
				if (i > 3)
					if (Math.abs(sum-olds) <= EPS *Math.abs(olds))
						return sum;
				olds = sum;
			}
		}
		throw("no convergence in fermi");
		return 0.0;
	}
}