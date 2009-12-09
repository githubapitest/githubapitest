package com.google.code.numericalrecipes;
public class Normaldev implements Ran
{
	public Doub mu = new Doub();
	public Doub sig = new Doub();
	public Normaldev(Doub mmu, Doub ssig, Ullong i)
	{
		super(i);
		mu = mmu;
		sig = ssig;
	}
	public final Doub dev()
	{
		Doub u = new Doub();
		Doub v = new Doub();
		Doub x = new Doub();
		Doub y = new Doub();
		Doub q = new Doub();
		do
		{
			u = doub();
			v = 1.7156*(doub()-0.5);
			x = u - 0.449871;
			y = Math.abs(v) + 0.386595;
			q = SQR(x) + y*(0.19600 *y-0.25472 *x);
		} while (q > 0.27597 && (q > 0.27846 || SQR(v) > -4.*Math.log(u)*SQR(u)));
		return mu + sig *v/u;
	}
}