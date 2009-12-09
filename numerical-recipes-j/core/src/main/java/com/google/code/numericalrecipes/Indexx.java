package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
//C++ TO JAVA CONVERTER TODO TASK: Only the namespaces at the beginning of the file can be converted to the Java 'package' for this file:
//ORIGINAL LINE: namespace hpsort_util
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
public class Indexx
{
	public Int n = new Int();
	public VecInt indx = new VecInt();

//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
	public Indexx(NRvector<T> arr)
	{
		index(arr[0], arr.size());
	}
	public Indexx()
	{
	}

//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
	public final <T> void sort(RefObject<NRvector<T>> brr)
	{
		if (brr.argvalue.size() != n)
			throw("bad size in Index sort");
		NRvector<T> tmp = new NRvector<T>(brr.argvalue);
		for (Int j = 0;j<n;j++)
			brr.argvalue[j] = tmp[indx[j]];
	}

//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: inline const T & el(NRvector<T> &brr, Int j) const
	public final <T> T  el(RefObject<NRvector<T>> brr, Int j)
	{
		return brr.argvalue[indx[j]];
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
	public final <T> T  el(RefObject<NRvector<T>> brr, Int j)
	{
		return brr.argvalue[indx[j]];
	}

//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
	public final <T> void index(T[] arr, Int nn)
	{
		final Int M = 7;
		final Int NSTACK = 64;
		Int i = new Int();
		Int indxt = new Int();
		Int ir = new Int();
		Int j = new Int();
		Int k = new Int();
		Int jstack = -1;
		Int l = 0;
		T a = new T();
		VecInt istack = new VecInt(NSTACK);
		n = nn;
		indx.resize(n);
		ir = n-1;
		for (j = 0;j<n;j++)
			indx[j]=j;
		for (;;)
		{
			if (ir-l < M)
			{
				for (j = l+1;j<=ir;j++)
				{
					indxt = indx[j];
					a = arr[indxt];
					for (i = j-1;i>=l;i--)
					{
						if (arr[indx[i]] <= a)
							break;
						indx[i+1]=indx[i];
					}
					indx[i+1]=indxt;
				}
				if (jstack < 0)
					break;
				ir = istack[jstack--];
				l = istack[jstack--];
			}
			else
			{
				k = (l+ir) >> 1;
				SWAP(indx[k],indx[l+1]);
				if (arr[indx[l]] > arr[indx[ir]])
				{
					SWAP(indx[l],indx[ir]);
				}
				if (arr[indx[l+1]] > arr[indx[ir]])
				{
					SWAP(indx[l+1],indx[ir]);
				}
				if (arr[indx[l]] > arr[indx[l+1]])
				{
					SWAP(indx[l],indx[l+1]);
				}
				i = l+1;
				j = ir;
				indxt = indx[l+1];
				a = arr[indxt];
				for (;;)
				{
					do
						i++;
						while (arr[indx[i]] < a);
					do
						j--;
						while (arr[indx[j]] > a);
					if (j < i)
						break;
					SWAP(indx[i],indx[j]);
				}
				indx[l+1]=indx[j];
				indx[j]=indxt;
				jstack += 2;
				if (jstack >= NSTACK)
					throw("NSTACK too small in index.");
				if (ir-i+1 >= j-l)
				{
					istack[jstack]=ir;
					istack[jstack-1]=i;
					ir = j-1;
				}
				else
				{
					istack[jstack]=j-1;
					istack[jstack-1]=l;
					l = i;
				}
			}
		}
	}

	public final void rank(RefObject<VecInt_O> irank)
	{
		irank.argvalue.resize(n);
		for (Int j = 0;j<n;j++)
			irank.argvalue[indx[j]] = j;
	}

}