package com.google.code.numericalrecipes;
public class Linear_interp implements Base_interp
{
	public Linear_interp(RefObject<VecDoub_I> xv, RefObject<VecDoub_I> yv)
	{
		super(xv.argvalue, yv.argvalue[0], 2);
	}
	public final Doub rawinterp(Int j, Doub x)
	{
		if (xx[j]==xx[j+1])
			return yy[j];
		else
			return yy[j] + ((x-xx[j])/(xx[j+1]-xx[j]))*(yy[j+1]-yy[j]);
	}
}