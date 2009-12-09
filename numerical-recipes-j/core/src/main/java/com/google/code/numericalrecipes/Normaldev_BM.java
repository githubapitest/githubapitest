package com.google.code.numericalrecipes;
public class Normaldev_BM implements Ran
{
	public Doub mu = new Doub();
	public Doub sig = new Doub();
	public Doub storedval = new Doub();
	public Normaldev_BM(Doub mmu, Doub ssig, Ullong i)
	{
		super(i);
		mu = mmu;
		sig = ssig;
		storedval = 0.;
	}
	public final Doub dev()
	{
		Doub v1 = new Doub();
		Doub v2 = new Doub();
		Doub rsq = new Doub();
		Doub fac = new Doub();
		if (storedval == 0.)
		{
			do
			{
				v1 = 2.0 *doub()-1.0;
				v2 = 2.0 *doub()-1.0;
				rsq = v1 *v1+v2 *v2;
			} while (rsq >= 1.0 || rsq == 0.0);
			fac = Math.sqrt(-2.0 *Math.log(rsq)/rsq);
			storedval = v1 *fac;
			return mu + sig *v2 *fac;
		}
		else
		{
			fac = storedval;
			storedval = 0.;
			return mu + sig *fac;
		}
	}
}