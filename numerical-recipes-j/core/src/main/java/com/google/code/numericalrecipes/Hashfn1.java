package com.google.code.numericalrecipes;
import java.util.Hashtable<keyT,hfnT>.iget;
import java.util.Hashtable<keyT,hfnT>.iset;
import java.util.Hashtable<keyT,hfnT>.ierase;

public class Hashfn1
{
	public Ranhash hasher = new Ranhash();
	public Int n = new Int();
	public Hashfn1(Int nn)
	{
		n = nn;
	}
	public final Ullong fn(Object key)
	{
		Uint k;
		Ullong kk;
		switch (n)
		{
			case 4:
				k = (Uint)key;
				return hasher.int64(k);
			case 8:
				kk = (Ullong)key;
				 return hasher.int64(kk);
			default:
				throw("Hashfn1 is for 4 or 8 byte keys only.");
		}
	}
}