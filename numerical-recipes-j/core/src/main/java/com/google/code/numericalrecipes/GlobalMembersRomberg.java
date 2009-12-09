package com.google.code.numericalrecipes;
public class GlobalMembersRomberg
{
public static <T> Doub qromb(RefObject<T> func, Doub a, Doub b)
{
	return qromb(func, a, b, 1.0e-10);
}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Doub qromb(T &func, Doub a, Doub b, const Doub eps=1.0e-10)
	public static <T> Doub qromb(RefObject<T> func, Doub a, Doub b, Doub eps)
	{
		final Int JMAX = 20;
		final Int JMAXP = JMAX+1;
		final Int K = 5;
		VecDoub s = new VecDoub(JMAX);
		VecDoub h = new VecDoub(JMAXP);
		Poly_interp polint = new Poly_interp(h,s,K);
		h[0]=1.0;
		Trapzd<T> t = new Trapzd<T>(func.argvalue,a,b);
		for (Int j = 1;j<=JMAX;j++)
		{
			s[j-1]=t.next();
			if (j >= K)
			{
				Doub ss = polint.rawinterp(j-K,0.0);
				if (Math.abs(polint.dy) <= eps *Math.abs(ss))
					return ss;
			}
			h[j]=0.25 *h[j-1];
		}
		throw("Too many steps in routine qromb");
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
public static <T> Doub qromo(RefObject<Midpnt<T>> q)
{
	return qromo(q, 3.0e-9);
}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Doub qromo(Midpnt<T> &q, const Doub eps=3.0e-9)
	public static <T> Doub qromo(RefObject<Midpnt<T>> q, Doub eps)
	{
		final Int JMAX = 14;
		final Int JMAXP = JMAX+1;
		final Int K = 5;
		VecDoub h = new VecDoub(JMAXP);
		VecDoub s = new VecDoub(JMAX);
		Poly_interp polint = new Poly_interp(h,s,K);
		h[0]=1.0;
		for (Int j = 1;j<=JMAX;j++)
		{
			s[j-1]=q.argvalue.next();
			if (j >= K)
			{
				Doub ss = polint.rawinterp(j-K,0.0);
				if (Math.abs(polint.dy) <= eps *Math.abs(ss))
					return ss;
			}
			h[j]=h[j-1]/9.0;
		}
		throw("Too many steps in routine qromo");
	}
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
