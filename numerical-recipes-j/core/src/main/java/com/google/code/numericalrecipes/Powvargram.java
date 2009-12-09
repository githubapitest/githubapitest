package com.google.code.numericalrecipes;
public class Powvargram
{
	public Doub alph = new Doub();
	public Doub bet = new Doub();
	public Doub nugsq = new Doub();

	public Powvargram(RefObject<MatDoub_I> x, RefObject<VecDoub_I> y, Doub beta)
	{
		this(x, y, beta, 0.);
	}
	public Powvargram(RefObject<MatDoub_I> x, RefObject<VecDoub_I> y)
	{
		this(x, y, 1.5, 0.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Powvargram(MatDoub_I &x, VecDoub_I &y, const Doub beta=1.5, const Doub nug=0.) : bet(beta), nugsq(nug *nug)
	public Powvargram(RefObject<MatDoub_I> x, RefObject<VecDoub_I> y, Doub beta, Doub nug)
	{
		bet = beta;
		nugsq = nug *nug;
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int npt = x.argvalue.nrows();
		Int ndim = x.argvalue.ncols();
		Doub rb = new Doub();
		Doub num = 0.;
		Doub denom = 0.;
		for (i = 0;i<npt;i++)
			for (j = i+1;j<npt;j++)
			{
			rb = 0.;
			for (k = 0;k<ndim;k++)
				rb += SQR(x.argvalue[i][k]-x.argvalue[j][k]);
			rb = Math.pow(rb,0.5 *beta);
			num += rb*(0.5 *SQR(y.argvalue[i]-y.argvalue[j]) - nugsq);
			denom += SQR(rb);
		}
		alph = num/denom;
	}

//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: Doub operator ()(const Doub r) const
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(Doub r)
	{
		return nugsq+alph *Math.pow(r,bet);
	}
}