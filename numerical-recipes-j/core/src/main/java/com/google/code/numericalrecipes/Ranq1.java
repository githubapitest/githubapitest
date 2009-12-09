package com.google.code.numericalrecipes;
public class Ranq1
{
	public Ullong v = new Ullong();
	public Ranq1(Ullong j)
	{
		v = 4101842887655102017L;
		v ^= j;
		v = int64();
	}
	public final Ullong int64()
	{
		v ^= v >> 21;
		v ^= v << 35;
		v ^= v >> 4;
		return v * 2685821657736338717L;
	}
	public final Doub doub()
	{
		return 5.42101086242752217E-20 * int64();
	}
	public final Uint int32()
	{
		return (Uint)int64();
	}
}