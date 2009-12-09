package com.google.code.numericalrecipes;
public class RBF_thinplate implements RBF_fn
{
	public Doub r0 = new Doub();
	public RBF_thinplate()
	{
		this(1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: RBF_thinplate(Doub scale=1.) : r0(scale)
	public RBF_thinplate(Doub scale)
	{
		r0 = scale;
	}
	public final Doub rbf(Doub r)
	{
		return r <= 0.? 0.: SQR(r)*Math.log(r/r0);
	}
}