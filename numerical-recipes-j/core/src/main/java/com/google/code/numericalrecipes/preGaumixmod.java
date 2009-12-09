package com.google.code.numericalrecipes;
public class preGaumixmod
{
	public static Int mmstat = -1;
	public static class Mat_mm implements MatDoub
	{
		public Mat_mm()
		{
			super(mmstat,mmstat);
		}
	}
	public preGaumixmod(Int mm)
	{
		mmstat = mm;
	}
}