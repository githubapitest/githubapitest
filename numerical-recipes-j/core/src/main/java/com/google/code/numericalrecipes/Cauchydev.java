package com.google.code.numericalrecipes;
public class Cauchydev implements Ran
{
	public Doub mu = new Doub();
	public Doub sig = new Doub();
	public Cauchydev(Doub mmu, Doub ssig, Ullong i)
	{
		super(i);
		mu = mmu;
		sig = ssig;
	}
	public final Doub dev()
	{
		Doub v1 = new Doub();
		Doub v2 = new Doub();
		do
		{
			v1 = 2.0 *doub()-1.0;
			v2 = doub();
		} while (SQR(v1)+SQR(v2) >= 1.|| v2 == 0.);
		return mu + sig *v1/v2;
	}
}