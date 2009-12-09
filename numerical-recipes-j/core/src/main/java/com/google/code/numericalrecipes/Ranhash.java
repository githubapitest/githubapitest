package com.google.code.numericalrecipes;
public class Ranhash
{
	public final Ullong int64(Ullong u)
	{
		Ullong v = u * 3935559000370003845L + 2691343689449507681L;
		v ^= v >> 21;
		v ^= v << 37;
		v ^= v >> 4;
		v *= 4768777513237032717L;
		v ^= v << 20;
		v ^= v >> 41;
		v ^= v << 5;
		return v;
	}
	public final Uint int32(Ullong u)
		{
			return (Uint)(int64(u) & 0xffffffff);
		}
	public final Doub doub(Ullong u)
		{
			return 5.42101086242752217E-20 * int64(u);
		}
}