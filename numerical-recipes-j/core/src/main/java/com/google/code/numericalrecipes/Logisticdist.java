package com.google.code.numericalrecipes;
public class Logisticdist
{
	public Doub mu = new Doub();
	public Doub sig = new Doub();
	public Logisticdist(Doub mmu)
	{
		this(mmu, 1.);
	}
	public Logisticdist()
	{
		this(0., 1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Logisticdist(Doub mmu = 0., Doub ssig = 1.) : mu(mmu), sig(ssig)
	public Logisticdist(Doub mmu, Doub ssig)
	{
		mu = mmu;
		sig = ssig;
		if (sig <= 0.)
			throw("bad sig in Logisticdist");
	}
	public final Doub p(Doub x)
	{
		Doub e = Math.exp(-Math.abs(1.81379936423421785*(x-mu)/sig));
		return 1.81379936423421785 *e/(sig *SQR(1.+e));
	}
	public final Doub cdf(Doub x)
	{
		Doub e = Math.exp(-Math.abs(1.81379936423421785*(x-mu)/sig));
		if (x >= mu)
			return 1./(1.+e);
		else
			return e/(1.+e);
	}
	public final Doub invcdf(Doub p)
	{
		if (p <= 0.|| p >= 1.)
			throw("bad p in Logisticdist");
		return mu + 0.551328895421792049 *sig *Math.log(p/(1.-p));
	}
}