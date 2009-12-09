package com.google.code.numericalrecipes;
public class RBF_inversemultiquadric implements RBF_fn
{
	public Doub r02 = new Doub();
	public RBF_inversemultiquadric()
	{
		this(1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: RBF_inversemultiquadric(Doub scale=1.) : r02(SQR(scale))
	public RBF_inversemultiquadric(Doub scale)
	{
		r02 = SQR(scale);
	}
	public final Doub rbf(Doub r)
	{
		return 1./Math.sqrt(SQR(r)+r02);
	}
}