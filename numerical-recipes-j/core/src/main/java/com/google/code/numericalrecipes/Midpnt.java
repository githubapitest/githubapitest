package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
public class Midpnt<T> implements Quadrature
{
	public Doub a = new Doub();
	public Doub b = new Doub();
	public Doub s = new Doub();
	public T funk;
	public Midpnt(RefObject<T> funcc, Doub aa, Doub bb)
	{
		funk = funcc.argvalue;
		a = aa;
		b = bb;
		n=0;
	}
	public final Doub next()
	{
		Int it = new Int();
		Int j = new Int();
		Doub x = new Doub();
		Doub tnm = new Doub();
		Doub sum = new Doub();
		Doub del = new Doub();
		Doub ddel = new Doub();
		n++;
		if (n == 1)
		{
			return (s = (b-a)*func(0.5*(a+b)));
		}
		else
		{
			for(it = 1,j = 1;j<n-1;j++)
				it *= 3;
			tnm = it;
			del = (b-a)/(3.0 *tnm);
			ddel = del+del;
			x = a+0.5 *del;
			sum = 0.0;
			for (j = 0;j<it;j++)
			{
				sum += func(x);
				x += ddel;
				sum += func(x);
				x += del;
			}
			s = (s+(b-a)*sum/tnm)/3.0;
			return s;
		}
	}
	public Doub func(Doub x)
	{
		return funk(x);
	}
}