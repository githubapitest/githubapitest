package com.google.code.numericalrecipes;
public class Studenttdist implements Beta
{
	public Doub nu = new Doub();
	public Doub mu = new Doub();
	public Doub sig = new Doub();
	public Doub np = new Doub();
	public Doub fac = new Doub();
	public Studenttdist(Doub nnu, Doub mmu)
	{
		this(nnu, mmu, 1.);
	}
	public Studenttdist(Doub nnu)
	{
		this(nnu, 0., 1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Studenttdist(Doub nnu, Doub mmu = 0., Doub ssig = 1.) : nu(nnu), mu(mmu), sig(ssig)
	public Studenttdist(Doub nnu, Doub mmu, Doub ssig)
	{
		nu = nnu;
		mu = mmu;
		sig = ssig;
		if (sig <= 0.|| nu <= 0.)
			throw("bad sig,nu in Studentdist");
		np = 0.5*(nu + 1.);
		fac = gammln(np)-gammln(0.5 *nu);
	}
	public final Doub p(Doub t)
	{
		return Math.exp(-np *Math.log(1.+SQR((t-mu)/sig)/nu)+fac) /(Math.sqrt(3.14159265358979324 *nu)*sig);
	}
	public final Doub cdf(Doub t)
	{
		Doub p = 0.5 *betai(0.5 *nu, 0.5, nu/(nu+SQR((t-mu)/sig)));
		if (t >= mu)
			return 1.- p;
		else
			return p;
	}
	public final Doub invcdf(Doub p)
	{
		if (p <= 0.|| p >= 1.)
			throw("bad p in Studentdist");
		Doub x = invbetai(2.*MIN(p,1.-p), 0.5 *nu, 0.5);
		x = sig *Math.sqrt(nu*(1.-x)/x);
		return (p >= 0.5? mu+x : mu-x);
	}
	public final Doub aa(Doub t)
	{
		if (t < 0.)
			throw("bad t in Studentdist");
		return 1.-betai(0.5 *nu, 0.5, nu/(nu+SQR(t)));
	}
	public final Doub invaa(Doub p)
	{
		if (p < 0.|| p >= 1.)
			throw("bad p in Studentdist");
		Doub x = invbetai(1.-p, 0.5 *nu, 0.5);
		return Math.sqrt(nu*(1.-x)/x);
	}
}