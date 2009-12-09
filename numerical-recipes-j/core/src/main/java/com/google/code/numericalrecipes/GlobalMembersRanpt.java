package com.google.code.numericalrecipes;
public class GlobalMembersRanpt
{
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Ran ran = new Ran(RANSEED);
	public static void ranpt(RefObject<VecDoub_O> pt, RefObject<VecDoub_I> regn)
	{
		final int RANSEED = 5331;
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Ran ran(RANSEED);
		Int j = new Int();
		Int n = pt.argvalue.size();
		for (j = 0;j<n;j++)
			pt.argvalue[j]=regn.argvalue[j]+(regn.argvalue[n+j]-regn.argvalue[j])*ran.doub();
	}
}