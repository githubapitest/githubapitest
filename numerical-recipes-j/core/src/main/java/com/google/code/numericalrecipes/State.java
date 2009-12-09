package com.google.code.numericalrecipes;
public class State
{
	public Doub lam1 = new Doub();
	public Doub lam2 = new Doub();
	public Doub tc = new Doub();
	public Int k1 = new Int();
	public Int k2 = new Int();
	public Doub plog = new Doub();

	public State(Doub la1, Doub la2, Doub t, Int kk1, Int kk2)
	{
		lam1 = la1;
		lam2 = la2;
		tc = t;
		k1 = kk1;
		k2 = kk2;
	}
	public State()
	{
	}
}