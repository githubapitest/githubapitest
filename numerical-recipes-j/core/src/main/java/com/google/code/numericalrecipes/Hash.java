package com.google.code.numericalrecipes;
import java.util.Hashtable<keyT,hfnT>.iget;
import java.util.Hashtable<keyT,hfnT>.iset;
import java.util.Hashtable<keyT,hfnT>.ierase;

//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class keyT, class hfnT>
public class Hash<keyT, elT, hfnT> implements java.util.Hashtable<keyT, hfnT>
{
	public java.util.ArrayList<elT> els = new java.util.ArrayList<elT>();

	public Hash(Int nh, Int nm)
	{
		hfnT> = new <type missing>(nh, nm);
		els = nm;
	}

	public final void set(keyT key, elT el)
		{
			els.set(iset(key), el);
		}

	public final Int get(keyT key, RefObject<elT> el)
	{
		Int ll = iget(key);
		if (ll < 0)
			return 0;
		el.argvalue = els.get(ll);
		return 1;
	}

//C++ TO JAVA CONVERTER TODO TASK: C++ to Java Converter cannot determine the 'set' logic for this indexer:
	public final elT getDefault (keyT key)
	{
		Int ll = iget(key);
		if (ll < 0)
		{
			ll = iset(key);
			els.set(ll, elT());
		}
		return els.get(ll);
	}

	public final Int count(keyT key)
	{
		Int ll = iget(key);
		return (ll < 0 ? 0 : 1);
	}

	public final Int erase(keyT key)
	{
		return (ierase(key) < 0 ? 0 : 1);
	}
}