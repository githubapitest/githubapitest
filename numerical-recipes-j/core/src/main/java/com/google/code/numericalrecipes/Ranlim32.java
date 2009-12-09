package com.google.code.numericalrecipes;
public class Ranlim32
{
	public Uint u = new Uint();
	public Uint v = new Uint();
	public Uint w1 = new Uint();
	public Uint w2 = new Uint();
	public Ranlim32(Uint j)
	{
		v = 2244614371;
		w1 = 521288629;
		w2 = 362436069;
		u = j ^ v;
		int32();
		v = u;
		int32();
	}
	public final Uint int32()
	{
		u = u * 2891336453 + 1640531513;
		v ^= v >> 13;
		v ^= v << 17;
		v ^= v >> 5;
		w1 = 33378 * (w1 & 0xffff) + (w1 >> 16);
		w2 = 57225 * (w2 & 0xffff) + (w2 >> 16);
		Uint x = u ^ (u << 9);
		x ^= x >> 17;
		x ^= x << 6;
		Uint y = w1 ^ (w1 << 17);
		y ^= y >> 15;
		y ^= y << 5;
		return (x + v) ^ (y + w2);
	}
	public final Doub doub()
	{
		return 2.32830643653869629E-10 * int32();
	}
	public final Doub truedoub()
	{
		return 2.32830643653869629E-10 * (int32() + 2.32830643653869629E-10 * int32());
	}
}