package com.google.code.numericalrecipes;
public class Ranbyte
{
	public Int[] s = new Int[256];
	public Int i = new Int();
	public Int j = new Int();
	public Int ss = new Int();
	public Uint v = new Uint();
	public Ranbyte(Int u)
	{
		v = 2244614371 ^ u;
		for (i = 0; i<256; i++)
		{
			s[i] = i;
		}
		for (j = 0, i = 0; i<256; i++)
		{
			ss = s[i];
			j = (j + ss + (v >> 24)) & 0xff;
			s[i] = s[j];
			s[j] = ss;
			v = (v << 24) | (v >> 8);
		}
		i = j = 0;
		for (Int k = 0; k<256; k++)
			int8();
	}
	public final byte int8()
	{
		i = (i+1) & 0xff;
		ss = s[i];
		j = (j+ss) & 0xff;
		s[i] = s[j];
		s[j] = ss;
		return (byte)(s[(s[i]+s[j]) & 0xff]);
	}
	public final Uint int32()
	{
		v = 0;
		for (int k = 0; k<4; k++)
		{
			i = (i+1) & 0xff;
			ss = s[i];
			j = (j+ss) & 0xff;
			s[i] = s[j];
			s[j] = ss;
			v = (v << 8) | s[(s[i]+s[j]) & 0xff];
		}
		return v;
	}
	public final Doub doub()
	{
		return 2.32830643653869629E-10 * (int32() + 2.32830643653869629E-10 * int32());
	}
}