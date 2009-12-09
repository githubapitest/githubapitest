package com.google.code.numericalrecipes;
public class GlobalMembersSpectrum
{
	public static Doub square(Int j,Int n)
	{
		return 1.;
	}

	public static Doub bartlett(Int j,Int n)
	{
		return 1.-Math.abs(2.*j/(n-1.)-1.);
	}

	public static Doub welch(Int j,Int n)
	{
		return 1.-SQR(2.*j/(n-1.)-1.);
	}
}