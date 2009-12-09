package com.google.code.numericalrecipes;
public class GlobalMembersDawson
{
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private VecDoub c = new VecDoub(NMAX);
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Bool init = true;
	public static Doub dawson(Doub x)
	{
		final Int NMAX = 6;
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static VecDoub c(NMAX);
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Bool init = true;
		final Doub H = 0.4;
		final Doub A1 = 2.0/3.0;
		final Doub A2 = 0.4;
		final Doub A3 = 2.0/7.0;
		Int i = new Int();
		Int n0 = new Int();
		Doub d1 = new Doub();
		Doub d2 = new Doub();
		Doub e1 = new Doub();
		Doub e2 = new Doub();
		Doub sum = new Doub();
		Doub x2 = new Doub();
		Doub xp = new Doub();
		Doub xx = new Doub();
		Doub ans = new Doub();
		if (init != null)
		{
			init = false;
			for (i = 0;i<NMAX;i++)
				c[i]=Math.exp(-SQR((2.0 *i+1.0)*H));
		}
		if (Math.abs(x) < 0.2)
		{
			x2 = x *x;
			ans = x*(1.0-A1 *x2*(1.0-A2 *x2*(1.0-A3 *x2)));
		}
		else
		{
			xx = Math.abs(x);
			n0 = 2 *(Int)(0.5 *xx/H+0.5);
			xp = xx-n0 *H;
			e1 = Math.exp(2.0 *xp *H);
			e2 = e1 *e1;
			d1 = n0+1;
			d2 = d1-2.0;
			sum = 0.0;
			for (i = 0;i<NMAX;i++,d1+=2.0,d2-=2.0,e1*=e2)
				sum += c[i]*(e1/d1+1.0/(d2 *e1));
			ans = 0.5641895835 *SIGN(Math.exp(-xp *xp),x)*sum;
		}
		return ans;
	}
}