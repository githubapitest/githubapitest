package com.google.code.numericalrecipes;
public class GlobalMembersQgaus
{
	public static <T> Doub qgaus(RefObject<T> func, Doub a, Doub b)
	{
		Doub[] x = {0.1488743389816312,0.4333953941292472, 0.6794095682990244,0.8650633666889845,0.9739065285171717};
		Doub[] w = {0.2955242247147529,0.2692667193099963, 0.2190863625159821,0.1494513491505806,0.0666713443086881};
		Doub xm = 0.5*(b+a);
		Doub xr = 0.5*(b-a);
		Doub s = 0;
		for (Int j = 0;j<5;j++)
		{
			Doub dx = xr *x[j];
			s += w[j]*(func.argvalue(xm+dx)+func.argvalue(xm-dx));
		}
		return s *= xr;
	}
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
