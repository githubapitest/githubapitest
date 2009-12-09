package com.google.code.numericalrecipes;
public class Ratfn
{
	public VecDoub cofs = new VecDoub();
	public Int nn = new Int();
	public Int dd = new Int();

	public Ratfn(RefObject<VecDoub_I> num, RefObject<VecDoub_I> den)
	{
		cofs = num.argvalue.size()+den.argvalue.size()-1;
		nn = num.argvalue.size();
		dd = den.argvalue.size();
		Int j = new Int();
		for (j = 0;j<nn;j++)
			cofs[j] = num.argvalue[j]/den.argvalue[0];
		for (j = 1;j<dd;j++)
			cofs[j+nn-1] = den.argvalue[j]/den.argvalue[0];
	}

	public Ratfn(RefObject<VecDoub_I> coffs, Int n, Int d)
	{
		cofs = coffs.argvalue;
		nn = n;
		dd = d;
	}

//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: Doub operator ()(Doub x) const
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(Doub x)
	{
		Int j = new Int();
		Doub sumn = 0.;
		Doub sumd = 0.;
		for (j = nn-1;j>=0;j--)
			sumn = sumn *x + cofs[j];
		for (j = nn+dd-2;j>=nn;j--)
			sumd = sumd *x + cofs[j];
		return sumn/(1.0+x *sumd);
	}

}