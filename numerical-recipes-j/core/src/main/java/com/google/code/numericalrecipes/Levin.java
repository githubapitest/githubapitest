package com.google.code.numericalrecipes;
public class Levin
{
	public VecDoub numer = new VecDoub();
	public VecDoub denom = new VecDoub();
	public Int n = new Int();
	public Int ncv = new Int();
	public Bool cnvgd = new Bool();
	public Doub small = new Doub();
	public Doub big = new Doub();
	public Doub eps = new Doub();
	public Doub lastval = new Doub();
	public Doub lasteps = new Doub();

	public Levin(Int nmax, Doub epss)
	{
		numer = nmax;
		denom = nmax;
		n = 0;
		ncv = 0;
		cnvgd = 0;
		eps = epss;
		lastval = 0.;
		small = numeric_limits<Doub>.min()*10.0;
		big = numeric_limits<Doub>.max();
	}

	public final Doub next(Doub sum, Doub omega)
	{
		return next(sum, omega, 1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Doub next(Doub sum, Doub omega, Doub beta=1.)
	public final Doub next(Doub sum, Doub omega, Doub beta)
	{
		Int j = new Int();
		Doub fact = new Doub();
		Doub ratio = new Doub();
		Doub term = new Doub();
		Doub val = new Doub();
		term = 1.0/(beta+n);
		denom[n]=term/omega;
		numer[n]=sum *denom[n];
		if (n > 0)
		{
			ratio = (beta+n-1)*term;
			for (j = 1;j<=n;j++)
			{
				fact = (n-j+beta)*term;
				numer[n-j]=numer[n-j+1]-fact *numer[n-j];
				denom[n-j]=denom[n-j+1]-fact *denom[n-j];
				term = term *ratio;
			}
		}
		n++;
		val = Math.abs(denom[0]) < small != null ? lastval : numer[0]/denom[0];
		lasteps = Math.abs(val-lastval);
		if (lasteps <= eps)
			ncv++;
		if (ncv >= 2)
			cnvgd = 1;
		return (lastval = val);
	}
}