package com.google.code.numericalrecipes;
public class Icrc
{

	public Uint jcrc = new Uint();
	public Uint jfill = new Uint();
	public Uint poly = new Uint();
	public static Uint[] icrctb = new Uint[256];

	public Icrc(Int jpoly)
	{
		this(jpoly, true);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Icrc(const Int jpoly, const Bool fill=true) : jfill(fill ? 255 : 0)
	public Icrc(Int jpoly, Bool fill)
	{
		jfill = fill != null ? 255 : 0;
		Int j = new Int();
		Uint[] okpolys = {0x755B,0xA7D3,0x8005,0x1021,0x5935,0x90D9,0x5B93,0x2D17};
		poly = okpolys[jpoly & 7];
		for (j = 0;j<256;j++)
		{
			icrctb[j] =icrc1(j << 8, 0);
		}
		jcrc = (jfill | (jfill << 8));
	}

	public final Uint crc(String bufptr)
	{
		jcrc = (jfill | (jfill << 8));
		return concat(bufptr);
	}

	public final Uint concat(String bufptr)
	{
		Uint j = new Uint();
		Uint cword = jcrc;
		Uint len = bufptr.length();
		for (j = 0;j<len;j++)
		{
			cword = icrctb[Uchar(bufptr.charAt(j)) ^ hibyte(cword)] ^ (lobyte(cword) << 8);
		}
		return jcrc = cword;
	}

	public final Uint icrc1(Uint jcrc, Uchar onech)
	{
		Int i = new Int();
		Uint ans = (jcrc ^ onech << 8);
		for (i = 0;i<8;i++)
		{
			if (ans & 0x8000 != 0)
				ans = (ans <<= 1) ^ poly;
			else
				ans <<= 1;
			ans &= 0xffff;
		}
		return ans;
	}

	public final Uchar lobyte(short x)
	{
		return (Uchar)(x & 0xff);
	}
	public final Uchar hibyte(short x)
	{
		return (Uchar)((x >> 8) & 0xff);
	}
}