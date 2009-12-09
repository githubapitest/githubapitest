package com.google.code.numericalrecipes;
public class Spectreg
{
	public Int m = new Int();
	public Int m2 = new Int();
	public Int nsum = new Int();
	public VecDoub specsum = new VecDoub();
	public VecDoub wksp = new VecDoub();

	public Spectreg(Int em)
	{
		m = em;
		m2 = 2 *m;
		nsum = 0;
		specsum = new VecDoub(m+1,0.);
		wksp = m2;
		if (m & (m-1) != 0)
			throw("m must be power of 2");
	}

//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class D>
	public final <D> void adddataseg(RefObject<VecDoub_I> data, RefObject<D> window)
	{
		Int i = new Int();
		Doub w = new Doub();
		Doub fac = new Doub();
		Doub sumw = 0.;
		if (data.argvalue.size() != m2)
			throw("wrong size data segment");
		for (i = 0;i<m2;i++)
		{
			w = window.argvalue(i,m2);
			wksp[i] = w *data.argvalue[i];
			sumw += SQR(w);
		}
		fac = 2./(sumw *m2);
		realft(wksp,1);
		specsum[0] += 0.5 *fac *SQR(wksp[0]);
		for (i = 1;i<m;i++)
			specsum[i] += fac*(SQR(wksp[2 *i])+SQR(wksp[2 *i+1]));
		specsum[m] += 0.5 *fac *SQR(wksp[1]);
		nsum++;
	}

	public final VecDoub spectrum()
	{
		VecDoub spec = new VecDoub(m+1);
		if (nsum == 0)
			throw("no data yet");
		for (Int i = 0;i<=m;i++)
			spec[i] = specsum[i]/nsum;
		return spec;
	}

	public final VecDoub frequencies()
	{
		VecDoub freq = new VecDoub(m+1);
		for (Int i = 0;i<=m;i++)
			freq[i] = i *0.5/m;
		return freq;
	}
}