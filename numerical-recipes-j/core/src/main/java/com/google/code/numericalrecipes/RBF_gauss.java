package com.google.code.numericalrecipes;
public class RBF_gauss implements RBF_fn
{
	public Doub r0 = new Doub();
	public RBF_gauss()
	{
		this(1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: RBF_gauss(Doub scale=1.) : r0(scale)
	public RBF_gauss(Doub scale)
	{
		r0 = scale;
	}
	public final Doub rbf(Doub r)
	{
		return Math.exp(-0.5 *SQR(r/r0));
	}
}