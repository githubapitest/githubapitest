package com.google.code.numericalrecipes;
public class Cauchydist
{
	public Doub mu = new Doub();
	public Doub sig = new Doub();
	public Cauchydist(Doub mmu)
	{
		this(mmu, 1.);
	}
	public Cauchydist()
	{
		this(0., 1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Cauchydist(Doub mmu = 0., Doub ssig = 1.) : mu(mmu), sig(ssig)
	public Cauchydist(Doub mmu, Doub ssig)
	{
		mu = mmu;
		sig = ssig;
		if (sig <= 0.)
			throw("bad sig in Cauchydist");
	}
	public final Doub p(Doub x)
	{
		return 0.318309886183790671/(sig*(1.+SQR((x-mu)/sig)));
	}
	public final Doub cdf(Doub x)
	{
		return 0.5+0.318309886183790671 *Math.atan2(x-mu,sig);
	}
	public final Doub invcdf(Doub p)
	{
		if (p <= 0.|| p >= 1.)
			throw("bad p in Cauchydist");
		return mu + sig *Math.tan(3.14159265358979324*(p-0.5));
	}
}