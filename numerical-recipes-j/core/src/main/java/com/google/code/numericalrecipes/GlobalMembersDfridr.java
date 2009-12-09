package com.google.code.numericalrecipes;
public class GlobalMembersDfridr
{
	public static <T> Doub dfridr(RefObject<T> func, Doub x, Doub h, RefObject<Doub> err)
	{
		final Int ntab = 10;
		final Doub con = 1.4;
		final Doub con2 = (con *con);
		final Doub big = numeric_limits<Doub>.max();
		final Doub safe = 2.0;
		Int i = new Int();
		Int j = new Int();
		Doub errt = new Doub();
		Doub fac = new Doub();
		Doub hh = new Doub();
		Doub ans = new Doub();
		MatDoub a = new MatDoub(ntab,ntab);
		if (h == 0.0)
			throw("h must be nonzero in dfridr.");
		hh = h;
		a[0][0]=(func.argvalue(x+hh)-func.argvalue(x-hh))/(2.0 *hh);
		err.argvalue = big;
		for (i = 1;i<ntab;i++)
		{
			hh /= con;
			a[0][i]=(func.argvalue(x+hh)-func.argvalue(x-hh))/(2.0 *hh);
			fac = con2;
			for (j = 1;j<=i;j++)
			{
				a[j][i]=(a[j-1][i]*fac-a[j-1][i-1])/(fac-1.0);
				fac = con2 *fac;
				errt = MAX(Math.abs(a[j][i]-a[j-1][i]),Math.abs(a[j][i]-a[j-1][i-1]));
				if (errt <= err.argvalue)
				{
					err.argvalue = errt;
					ans = a[j][i];
				}
			}
			if (Math.abs(a[i][i]-a[i-1][i-1]) >= safe *err.argvalue)
				break;
		}
		return ans;
	}
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
