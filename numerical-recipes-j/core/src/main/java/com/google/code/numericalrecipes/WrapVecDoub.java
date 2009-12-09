package com.google.code.numericalrecipes;
public class WrapVecDoub
{
	public VecDoub vvec = new VecDoub();
	public VecDoub v;
	public Int n = new Int();
	public Int mask = new Int();

	public WrapVecDoub(Int nn)
	{
		vvec = nn;
		v = vvec;
		n = nn/2;
		mask = n-1;
		validate();
	}

	public WrapVecDoub(RefObject<VecDoub> vec)
	{
		v = vec.argvalue;
		n = vec.argvalue.size()/2;
		mask = n-1;
		validate();
	}

	public final void validate()
	{
		if (n&(n-1))
			throw("vec size must be power of 2");
	}

//C++ TO JAVA CONVERTER TODO TASK: C++ to Java Converter cannot determine the 'set' logic for this indexer:
	public final Complex getDefault (Int i)
	{
		return (Complex)v[(i mask) << 1];
	}

	public final Doub real(Int i)
	{
		return v[(i mask) << 1];
	}

	public final Doub imag(Int i)
	{
		return v[((i mask) << 1)+1];
	}

//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	operator VecDoub&()
	{
		return v;
	}

}