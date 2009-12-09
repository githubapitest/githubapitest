package com.google.code.numericalrecipes;
public class Lognormaldist implements Erf
{
	public Doub mu = new Doub();
	public Doub sig = new Doub();
	public Lognormaldist(Doub mmu)
	{
		this(mmu, 1.);
	}
	public Lognormaldist()
	{
		this(0., 1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Lognormaldist(Doub mmu = 0., Doub ssig = 1.) : mu(mmu), sig(ssig)
	public Lognormaldist(Doub mmu, Doub ssig)
	{
		mu = mmu;
		sig = ssig;
		if (sig <= 0.)
			throw("bad sig in Lognormaldist");
	}
	public final Doub p(Doub x)
	{
		if (x < 0.)
			throw("bad x in Lognormaldist");
		if (x == 0.)
			return 0.;
		return (0.398942280401432678/(sig *x))*Math.exp(-0.5 *SQR((Math.log(x)-mu)/sig));
	}
	public final Doub cdf(Doub x)
	{
		if (x < 0.)
			throw("bad x in Lognormaldist");
		if (x == 0.)
			return 0.;
		return 0.5 *erfc(-0.707106781186547524*(Math.log(x)-mu)/sig);
	}
	public final Doub invcdf(Doub p)
	{
		if (p <= 0.|| p >= 1.)
			throw("bad p in Lognormaldist");
		return Math.exp(-1.41421356237309505 *sig *inverfc(2.*p)+mu);
	}
}