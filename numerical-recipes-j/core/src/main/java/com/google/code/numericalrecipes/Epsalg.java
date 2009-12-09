package com.google.code.numericalrecipes;
public class Epsalg
{
	public VecDoub e = new VecDoub();
	public Int n = new Int();
	public Int ncv = new Int();
	public Bool cnvgd = new Bool();
	public Doub eps = new Doub();
	public Doub small = new Doub();
	public Doub big = new Doub();
	public Doub lastval = new Doub();
	public Doub lasteps = new Doub();

	public Epsalg(Int nmax, Doub epss)
	{
		e = nmax;
		n = 0;
		ncv = 0;
		cnvgd = 0;
		eps = epss;
		lastval = 0.;
		small = numeric_limits<Doub>.min()*10.0;
		big = numeric_limits<Doub>.max();
	}

	public final Doub next(Doub sum)
	{
		Doub diff = new Doub();
		Doub temp1 = new Doub();
		Doub temp2 = new Doub();
		Doub val = new Doub();
		e[n]=sum;
		temp2 = 0.0;
		for (Int j = n; j>0; j--)
		{
			temp1 = temp2;
			temp2 = e[j-1];
			diff = e[j]-temp2;
			if (Math.abs(diff) <= small)
				e[j-1]=big;
			else
				e[j-1]=temp1+1.0/diff;
		}
		n++;
		val = (n & 1) ? e[0] : e[1];
		if (Math.abs(val) > 0.01 *big)
			val = lastval;
		lasteps = Math.abs(val-lastval);
		if (lasteps > eps)
			ncv = 0;
		else
			ncv++;
		if (ncv >= 3)
			cnvgd = 1;
		return (lastval = val);
	}

}