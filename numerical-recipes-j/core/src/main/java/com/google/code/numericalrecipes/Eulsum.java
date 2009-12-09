package com.google.code.numericalrecipes;
public class Eulsum
{
	public VecDoub wksp = new VecDoub();
	public Int n = new Int();
	public Int ncv = new Int();
	public Bool cnvgd = new Bool();
	public Doub sum = new Doub();
	public Doub eps = new Doub();
	public Doub lastval = new Doub();
	public Doub lasteps = new Doub();

	public Eulsum(Int nmax, Doub epss)
	{
		wksp = nmax;
		n = 0;
		ncv = 0;
		cnvgd = 0;
		sum = 0.;
		eps = epss;
		lastval = 0.;
	}

	public final Doub next(Doub term)
	{
		Int j = new Int();
		Doub tmp = new Doub();
		Doub dum = new Doub();
		if (n+1 > wksp.size())
			throw("wksp too small in eulsum");
		if (n == 0)
		{
			sum = 0.5*(wksp[n++]=term);
		}
		else
		{
			tmp = wksp[0];
			wksp[0]=term;
			for (j = 1;j<n;j++)
			{
				dum = wksp[j];
				wksp[j]=0.5*(wksp[j-1]+tmp);
				tmp = dum;
			}
			wksp[n]=0.5*(wksp[n-1]+tmp);
			if (Math.abs(wksp[n]) <= Math.abs(wksp[n-1]))
				sum += (0.5 *wksp[n++]);
			else
				sum += wksp[n];
		}
		lasteps = Math.abs(sum-lastval);
		if (lasteps <= eps)
			ncv++;
		if (ncv >= 2)
			cnvgd = 1;
		return (lastval = sum);
	}
}