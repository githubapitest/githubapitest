package com.google.code.numericalrecipes;
public class GlobalMembersMcintegrate
{
	public static VecDoub torusfuncs(VecDoub x)
	{
		Doub den = 1.;
		VecDoub f = new VecDoub(4);
		f[0] = den;
		for (Int i = 1;i<4;i++)
			f[i] = x[i-1]*den;
		return f;
	}

	public static Bool torusregion(VecDoub x)
	{
		return SQR(x[2])+SQR(Math.sqrt(SQR(x[0])+SQR(x[1]))-3.) <= 1.;
	}
	public static VecDoub torusmap(VecDoub s)
	{
		VecDoub xx = new VecDoub(s);
		xx[2] = 0.2 *Math.log(5.*s[2]);
		return xx;
	}
}