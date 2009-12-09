package com.google.code.numericalrecipes;
import java.util.Hashtable<keyT,hfnT>.iget;
import java.util.Hashtable<keyT,hfnT>.iset;
import java.util.Hashtable<keyT,hfnT>.ierase;

public class Hashfn2
{
	public static Ullong[] hashfn_tab = new Ullong[256];
	public Ullong h = new Ullong();
	public Int n = new Int();
	public Hashfn2(Int nn)
	{
		n = nn;
		if (n == 1)
			n = 0;
		h = 0x544B2FBACAAF1684LL;
		for (Int j = 0; j<256; j++)
		{
			for (Int i = 0; i<31; i++)
			{
				h = (h >> 7) ^ h;
				h = (h << 11) ^ h;
				h = (h >> 10) ^ h;
			}
			hashfn_tab[j] = h;
		}
	}
	public final Ullong fn(Object key)
	{
		Int j = new Int();
//C++ TO JAVA CONVERTER TODO TASK: Pointer arithmetic is detected on this variable, so pointers on this variable are left unchanged.
		byte *k = (String)key;
		h = 0xBB40E64DA205B064LL;
		j = 0;
		while (n != null ? j++ < n : *k)
		{
			h = (h * 7664345821815920749L) ^ hashfn_tab[(byte)(*k)];
			k++;
		}
		return h;
	}
}