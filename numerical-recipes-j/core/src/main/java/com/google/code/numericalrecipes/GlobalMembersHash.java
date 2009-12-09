package com.google.code.numericalrecipes;
import java.util.Hashtable<keyT,hfnT>.iget;
import java.util.Hashtable<keyT,hfnT>.iset;
import java.util.Hashtable<keyT,hfnT>.ierase;

public class GlobalMembersHash
{

	public static java.util.Hashtable<keyT,hfnT>.Hashtable(Int nh, Int nv)
	{
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
		hash = sizeof(keyT);
		nhash = nh;
		nmax = nv;
		nn = 0;
		ng = 0;
		htable = nh;
		next = nv;
		garbg = nv;
		thehash = nv;
		for (Int j = 0; j<nh; j++)
		{
			htable[j] = -1;
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class keyT, class hfnT>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int java.util.Hashtable<keyT,hfnT>::iget(const keyT &key)
	public <keyT, hfnT> Int java.util.Hashtable<keyT,hfnT>.iget(keyT key)
	{
		Int j = new Int();
		Int k = new Int();
		Ullong pp = hash.fn(key);
		j = (Int)(pp % nhash);
		for (k = htable[j]; k != -1; k = next[k])
		{
			if (thehash[k] == pp)
			{
				return k;
			}
		}
		return -1;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class keyT, class hfnT>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int java.util.Hashtable<keyT,hfnT>::iset(const keyT &key)
	public <keyT, hfnT> Int java.util.Hashtable<keyT,hfnT>.iset(keyT key)
	{
		Int j = new Int();
		Int k = new Int();
		Int kprev = new Int();
		Ullong pp = hash.fn(key);
		j = (Int)(pp % nhash);
		if (htable[j] == -1)
		{
			k = ng != null ? garbg[--ng] : nn++;
			htable[j] = k;
		}
		else
		{
			for (k = htable[j]; k != -1; k = next[k])
			{
				if (thehash[k] == pp)
				{
					return k;
				}
				kprev = k;
			}
			k = ng != null ? garbg[--ng] : nn++;
			next[kprev] = k;
		}
		if (k >= nmax)
			throw("storing too many values");
		thehash[k] = pp;
		next[k] = -1;
		return k;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class keyT, class hfnT>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int java.util.Hashtable<keyT,hfnT>::ierase(const keyT &key)
	public <keyT, hfnT> Int java.util.Hashtable<keyT,hfnT>.ierase(keyT key)
	{
		Int j = new Int();
		Int k = new Int();
		Int kprev = new Int();
		Ullong pp = hash.fn(key);
		j = (Int)(pp % nhash);
		if (htable[j] == -1)
			return -1;
		kprev = -1;
		for (k = htable[j]; k != -1; k = next[k])
		{
			if (thehash[k] == pp)
			{
				if (kprev == -1)
					htable[j] = next[k];
				else
					next[kprev] = next[k];
				garbg[ng++] = k;
				return k;
			}
			kprev = k;
		}
		return -1;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class keyT, class hfnT>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int java.util.Hashtable<keyT,hfnT>::ireserve()
	public <keyT, hfnT> Int java.util.Hashtable<keyT,hfnT>.ireserve()
	{
		Int k = ng != null ? garbg[--ng] : nn++;
		if (k >= nmax)
			throw("reserving too many values");
		next[k] = -2;
		return k;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class keyT, class hfnT>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int java.util.Hashtable<keyT,hfnT>::irelinquish(Int k)
	public <keyT, hfnT> Int java.util.Hashtable<keyT,hfnT>.irelinquish(Int k)
	{
		if (next[k] != -2)
		{
			return -1;
		}
		garbg[ng++] = k;
		return k;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class keyT, class elT, class hfnT>

	public static Mhash<keyT,elT,hfnT>.Mhash(Int nh, Int nm)
	{
		hfnT> = new <type missing>(nh, nm);
		nextget = -1;
		els = nm;
		nextsis = nm;
		for (Int j = 0; j<nm; j++)
		{
			nextsis[j] = -2;
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class keyT, class elT, class hfnT>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int Mhash<keyT,elT,hfnT>::store(const keyT &key, const elT &el)
	public <keyT, elT, hfnT> Int Mhash<keyT,elT,hfnT>.store(keyT key, elT el)
	{
		Int j = new Int();
		Int k = new Int();
		j = iset(key);
		if (nextsis[j] == -2)
		{
			els[j] = el;
			nextsis[j] = -1;
			return j;
		}
		else
		{
			while (nextsis[j] != -1)
			{
				j = nextsis[j];
			}
			k = ireserve();
			els[k] = el;
			nextsis[j] = k;
			nextsis[k] = -1;
			return k;
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class keyT, class elT, class hfnT>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int Mhash<keyT,elT,hfnT>::erase(const keyT &key, const elT &el)
	public <keyT, elT, hfnT> Int Mhash<keyT,elT,hfnT>.erase(keyT key, elT el)
	{
		Int j = -1;
		Int kp = -1;
		Int kpp = -1;
		Int k = iget(key);
		while (k >= 0)
		{
			if (j < 0 && el == els[k])
				j = k;
			kpp = kp;
			kp = k;
			k = nextsis[k];
		}
		if (j < 0)
			return 0;
		if (kpp < 0)
		{
			ierase(key);
			nextsis[j] = -2;
		}
		else
		{
			if (j != kp)
				els[j] = els[kp];
			nextsis[kpp] = -1;
			irelinquish(kp);
			nextsis[kp] = -2;
		}
		return 1;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class keyT, class elT, class hfnT>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int Mhash<keyT,elT,hfnT>::count(const keyT &key)
	public <keyT, elT, hfnT> Int Mhash<keyT,elT,hfnT>.count(keyT key)
	{
		Int next = new Int();
		Int n = 1;
		if ((next = iget(key)) < 0)
			return 0;
		while ((next = nextsis[next]) >= 0)
		{
			n++;
		}
		return n;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class keyT, class elT, class hfnT>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int Mhash<keyT,elT,hfnT>::getinit(const keyT &key)
	public <keyT, elT, hfnT> Int Mhash<keyT,elT,hfnT>.getinit(keyT key)
	{
		nextget = iget(key);
		return ((nextget < 0)? 0 : 1);
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class keyT, class elT, class hfnT>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int Mhash<keyT,elT,hfnT>::getnext(elT &el)
	public <keyT, elT, hfnT> Int Mhash<keyT,elT,hfnT>.getnext(RefObject<elT> el)
	{
		if (nextget < 0)
		{
			return 0;
		}
		el.argvalue = els[nextget];
		nextget = nextsis[nextget];
		return 1;
	}
}