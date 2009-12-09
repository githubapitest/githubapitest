package com.google.code.numericalrecipes;
public class GlobalMembersGamma
{
	public static Doub gammln(Doub xx)
	{
		Int j = new Int();
		Doub x = new Doub();
		Doub tmp = new Doub();
		Doub y = new Doub();
		Doub ser = new Doub();
		Doub[] cof = {57.1562356658629235,-59.5979603554754912, 14.1360979747417471,-0.491913816097620199,.339946499848118887e-4, .465236289270485756e-4,-.983744753048795646e-4,.158088703224912494e-3, -.210264441724104883e-3,.217439618115212643e-3,-.164318106536763890e-3, .844182239838527433e-4,-.261908384015814087e-4,.368991826595316234e-5};
		if (xx <= 0)
			throw("bad arg in gammln");
		y = x = xx;
		tmp = x+5.24218750000000000;
		tmp = (x+0.5)*Math.log(tmp)-tmp;
		ser = 0.999999999999997092;
		for (j = 0;j<14;j++)
			ser += cof[j]/++y;
		return tmp+Math.log(2.5066282746310005 *ser/x);
	}
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private VecDoub a = new VecDoub(171);
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Bool init = true;
	public static Doub factrl(Int n)
	{
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static VecDoub a(171);
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Bool init=true;
		if (init != null)
		{
			init = false;
			a[0] = 1.;
			for (Int i = 1;i<171;i++)
				a[i] = i *a[i-1];
		}
		if (n < 0 || n > 170)
			throw("factrl out of range");
		return a[n];
	}
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private VecDoub a = new VecDoub(NTOP);
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Bool init = true;
	public static Doub factln(Int n)
	{
		final Int NTOP = 2000;
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static VecDoub a(NTOP);
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Bool init=true;
		if (init != null)
		{
			init = false;
			for (Int i = 0;i<NTOP;i++)
				a[i] = gammln(i+1.);
		}
		if (n < 0)
			throw("negative arg in factln");
		if (n < NTOP)
			return a[n];
		return gammln(n+1.);
	}
	public static Doub bico(Int n, Int k)
	{
		if (n<0 || k<0 || k>n)
			throw("bad args in bico");
		if (n<171)
			return Math.floor(0.5+factrl(n)/(factrl(k)*factrl(n-k)));
		return Math.floor(0.5+Math.exp(factln(n)-factln(k)-factln(n-k)));
	}
	public static Doub beta(Doub z, Doub w)
	{
		return Math.exp(gammln(z)+gammln(w)-gammln(z+w));
	}
}