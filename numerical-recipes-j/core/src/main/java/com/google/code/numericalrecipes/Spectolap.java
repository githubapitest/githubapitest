package com.google.code.numericalrecipes;
public class Spectolap implements Spectreg
{
	public Int first = new Int();
	public VecDoub fullseg = new VecDoub();

	public Spectolap(Int em)
	{
		super(em);
		first = 1;
		fullseg = 2 *em;
	}

//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class D>
	public final <D> void adddataseg(RefObject<VecDoub_I> data, RefObject<D> window)
	{
		Int i = new Int();
		if (data.argvalue.size() != m)
			throw("wrong size data segment");
		if (first != null)
		{
			for (i = 0;i<m;i++)
				fullseg[i+m] = data.argvalue [i];
			first = 0;
		}
		else
		{
			for (i = 0;i<m;i++)
			{
				fullseg[i] = fullseg[i+m];
				fullseg[i+m] = data.argvalue [i];
			}
			RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(fullseg);
			super.adddataseg(tempRefObject, window);
			fullseg = tempRefObject.argvalue;
		}
	}

//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class D>
	public final <D> void addlongdata(RefObject<VecDoub_I> data, RefObject<D> window)
	{
		Int i = new Int();
		Int k = new Int();
		Int noff = new Int();
		Int nt = data.argvalue.size();
		Int nk = (nt-1)/m;
		Doub del = nk > 1 ? (nt-m2)/(nk-1.) : 0.;
		if (nt < m2)
			throw("data length too short");
		for (k = 0;k<nk;k++)
		{
			noff = (Int)(k *del+0.5);
			for (i = 0;i<m2;i++)
				fullseg[i] = data.argvalue[noff+i];
			RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(fullseg);
			super.adddataseg(tempRefObject, window);
			fullseg = tempRefObject.argvalue;
		}
	}
}