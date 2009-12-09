package com.google.code.numericalrecipes;
public class Ranq2
{
	public Ullong v = new Ullong();
	public Ullong w = new Ullong();
	public Ranq2(Ullong j)
	{
		v = 4101842887655102017L;
		w = 1;
		v ^= j;
		w = int64();
		v = int64();
	}
	public final Ullong int64()
	{
		v ^= v >> 17;
		v ^= v << 31;
		v ^= v >> 8;
		w = 4294957665*(w & 0xffffffff) + (w >> 32);
		return v ^ w;
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