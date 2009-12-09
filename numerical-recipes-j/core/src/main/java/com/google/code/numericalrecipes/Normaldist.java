package com.google.code.numericalrecipes;
public class Normaldist implements Erf
{
	public Doub mu = new Doub();
	public Doub sig = new Doub();
	public Normaldist(Doub mmu)
	{
		this(mmu, 1.);
	}
	public Normaldist()
	{
		this(0., 1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Normaldist(Doub mmu = 0., Doub ssig = 1.) : mu(mmu), sig(ssig)
	public Normaldist(Doub mmu, Doub ssig)
	{
		mu = mmu;
		sig = ssig;
		if (sig <= 0.)
			throw("bad sig in Normaldist");
	}
	public final Doub p(Doub x)
	{
		return (0.398942280401432678/sig)*Math.exp(-0.5 *SQR((x-mu)/sig));
	}
	public final Doub cdf(Doub x)
	{
		return 0.5 *erfc(-0.707106781186547524*(x-mu)/sig);
	}
	public final Doub invcdf(Doub p)
	{
		if (p <= 0.|| p >= 1.)
			throw("bad p in Normaldist");
		return -1.41421356237309505 *sig *inverfc(2.*p)+mu;
	}
}