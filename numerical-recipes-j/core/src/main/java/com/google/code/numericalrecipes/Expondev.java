package com.google.code.numericalrecipes;
public class Expondev implements Ran
{
	public Doub beta = new Doub();
	public Expondev(Doub bbeta, Ullong i)
	{
		super(i);
		beta = bbeta;
	}
	public final Doub dev()
	{
		Doub u = new Doub();
		do
			u = doub();
			while (u == 0.);
		return -Math.log(u)/beta;
	}
}