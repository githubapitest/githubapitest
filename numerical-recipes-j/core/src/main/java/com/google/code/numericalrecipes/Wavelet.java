package com.google.code.numericalrecipes;
public abstract class Wavelet
{
	public abstract void filt(RefObject<VecDoub_IO> a, Int n, Int isign);
	public void condition(RefObject<VecDoub_IO> a, Int n, Int isign)
	{
	}
}