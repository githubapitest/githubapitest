package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
public class DErule<T> implements Quadrature
{
	public Doub a = new Doub();
	public Doub b = new Doub();
	public Doub hmax = new Doub();
	public Doub s = new Doub();
	public T func;

	public DErule(RefObject<T> funcc, Doub aa, Doub bb)
	{
		this(funcc, aa, bb, 3.7);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: DErule(T &funcc, const Doub aa, const Doub bb, const Doub hmaxx=3.7) : func(funcc), a(aa), b(bb), hmax(hmaxx)
	public DErule(RefObject<T> funcc, Doub aa, Doub bb, Doub hmaxx)
	{
		func = funcc.argvalue;
		a = aa;
		b = bb;
		hmax = hmaxx;
		n=0;
	}

	public final Doub next()
	{
		Doub del = new Doub();
		Doub fact = new Doub();
		Doub q = new Doub();
		Doub sum = new Doub();
		Doub t = new Doub();
		Doub twoh = new Doub();
		Int it = new Int();
		Int j = new Int();
		n++;
		if (n == 1)
		{
			fact = 0.25;
			return s = hmax *2.0*(b-a)*fact *func(0.5*(b+a),0.5*(b-a));
		}
		else
		{
			for (it = 1,j = 1;j<n-1;j++)
				it <<= 1;
			twoh = hmax/it;
			t = 0.5 *twoh;
			for (sum = 0.0,j = 0;j<it;j++)
			{
				q = Math.exp(-2.0 *Math.sinh(t));
				del = (b-a)*q/(1.0+q);
				fact = q/SQR(1.0+q)*Math.cosh(t);
				sum += fact*(func(a+del,del)+func(b-del,del));
				t += twoh;
			}
			return s = 0.5 *s+(b-a)*twoh *sum;
		}
	}
}