package com.google.code.numericalrecipes;
public class Ran
{
	public Ullong u = new Ullong();
	public Ullong v = new Ullong();
	public Ullong w = new Ullong();
	public Ran(Ullong j)
	{
		v = 4101842887655102017L;
		w = 1;
		u = j ^ v;
		int64();
		v = u;
		int64();
		w = v;
		int64();
	}
	public final Ullong int64()
	{
		u = u * 2862933555777941757L + 7046029254386353087L;
		v ^= v >> 17;
		v ^= v << 31;
		v ^= v >> 8;
		w = 4294957665*(w & 0xffffffff) + (w >> 32);
		Ullong x = u ^ (u << 21);
		x ^= x >> 35;
		x ^= x << 4;
		return (x + v) ^ w;
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