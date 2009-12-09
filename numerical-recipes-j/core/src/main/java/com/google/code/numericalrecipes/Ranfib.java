package com.google.code.numericalrecipes;
public class Ranfib
{
	public Doub[] dtab = new Doub[55];
	public Doub dd = new Doub();
	public Int inext = new Int();
	public Int inextp = new Int();
	public Ranfib(Ullong j)
	{
		inext = 0;
		inextp = 31;
		Ranq1 init = new Ranq1(j);
		for (int k = 0; k<55; k++)
			dtab[k] = init.doub();
	}
	public final Doub doub()
	{
		if (++inext == 55)
			inext = 0;
		if (++inextp == 55)
			inextp = 0;
		dd = dtab[inext] - dtab[inextp];
		if (dd < 0)
			dd += 1.0;
		return (dtab[inext] = dd);
	}
	public final int int32()
		{
			return (int)(doub() * 4294967295.0);
		}
}