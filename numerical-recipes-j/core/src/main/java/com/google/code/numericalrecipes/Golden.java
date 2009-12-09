package com.google.code.numericalrecipes;
public class Golden implements Bracketmethod
{
	public Doub xmin = new Doub();
	public Doub fmin = new Doub();
	public final Doub tol = new Doub();
	public Golden()
	{
		this(3.0e-8);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Golden(const Doub toll=3.0e-8) : tol(toll)
	public Golden(Doub toll)
	{
		tol = toll;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public final <T> Doub minimize(RefObject<T> func)
	{
		final Doub R = 0.61803399;
		final Doub C = 1.0-R;
		Doub x1 = new Doub();
		Doub x2 = new Doub();
		Doub x0 = ax;
		Doub x3 = cx;
		if (Math.abs(cx-bx) > Math.abs(bx-ax))
		{
			x1 = bx;
			x2 = bx+C*(cx-bx);
		}
		else
		{
			x2 = bx;
			x1 = bx-C*(bx-ax);
		}
		Doub f1 = func.argvalue(x1);
		Doub f2 = func.argvalue(x2);
		while (Math.abs(x3-x0) > tol*(Math.abs(x1)+Math.abs(x2)))
		{
			if (f2 < f1)
			{
				shft3(x0,x1,x2,R *x2+C *x3);
				shft2(f1,f2,func.argvalue(x2));
			}
			else
			{
				shft3(x3,x2,x1,R *x1+C *x0);
				shft2(f2,f1,func.argvalue(x1));
			}
		}
		if (f1 < f2)
		{
			xmin = x1;
			fmin = f1;
		}
		else
		{
			xmin = x2;
			fmin = f2;
		}
		return xmin;
	}
}