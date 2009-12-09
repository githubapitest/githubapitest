package com.google.code.numericalrecipes;
public class GlobalMembersQuadrature
{
public static <T> Doub qtrap(RefObject<T> func, Doub a, Doub b)
{
	return qtrap(func, a, b, 1.0e-10);
}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Doub qtrap(T &func, const Doub a, const Doub b, const Doub eps=1.0e-10)
	public static <T> Doub qtrap(RefObject<T> func, Doub a, Doub b, Doub eps)
	{
		final Int JMAX = 20;
		Doub s = new Doub();
		Doub olds = 0.0;
		Trapzd<T> t = new Trapzd<T>(func.argvalue,a,b);
		for (Int j = 0;j<JMAX;j++)
		{
			s = t.next();
			if (j > 5)
				if (Math.abs(s-olds) < eps *Math.abs(olds) || (s == 0.0 && olds == 0.0))
					return s;
			olds = s;
		}
		throw("Too many steps in routine qtrap");
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
public static <T> Doub qsimp(RefObject<T> func, Doub a, Doub b)
{
	return qsimp(func, a, b, 1.0e-10);
}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Doub qsimp(T &func, const Doub a, const Doub b, const Doub eps=1.0e-10)
	public static <T> Doub qsimp(RefObject<T> func, Doub a, Doub b, Doub eps)
	{
		final Int JMAX = 20;
		Doub s = new Doub();
		Doub st = new Doub();
		Doub ost = 0.0;
		Doub os = 0.0;
		Trapzd<T> t = new Trapzd<T>(func.argvalue,a,b);
		for (Int j = 0;j<JMAX;j++)
		{
			st = t.next();
			s = (4.0 *st-ost)/3.0;
			if (j > 5)
				if (Math.abs(s-os) < eps *Math.abs(os) || (s == 0.0 && os == 0.0))
					return s;
			os = s;
			ost = st;
		}
		throw("Too many steps in routine qsimp");
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
}