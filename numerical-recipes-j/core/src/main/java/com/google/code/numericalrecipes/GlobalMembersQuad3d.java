package com.google.code.numericalrecipes;
public class GlobalMembersQuad3d
{

	public static <T> Doub quad3d(RefObject<T> func, Doub x1, Doub x2, Doub y1(Doub), Doub y2(Doub), Doub z1(Doub, Doub), Doub z2(Doub, Doub))
	{
		NRf1 f1 = new NRf1(y1,y2,z1,z2);
		f1.f2.f3.func3d=func.argvalue;
		return qgaus(f1,x1,x2);
	}
}