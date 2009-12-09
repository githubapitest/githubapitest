package com.google.code.numericalrecipes;
public class Logisticdev implements Ran
{
	public Doub mu = new Doub();
	public Doub sig = new Doub();
	public Logisticdev(Doub mmu, Doub ssig, Ullong i)
	{
		super(i);
		mu = mmu;
		sig = ssig;
	}
	public final Doub dev()
	{
		Doub u = new Doub();
		do
			u = doub();
			while (u*(1.-u) == 0.);
		return mu + 0.551328895421792050 *sig *Math.log(u/(1.-u));
	}
}