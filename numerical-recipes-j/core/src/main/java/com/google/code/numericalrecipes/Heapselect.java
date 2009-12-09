package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
public class Heapselect
{
	public Int m = new Int();
	public Int n = new Int();
	public Int srtd = new Int();
	public VecDoub heap = new VecDoub();

	public Heapselect(Int mm)
	{
		m = mm;
		n = 0;
		srtd = 0;
		heap = new VecDoub(mm,1.e99);
	}

	public final void add(Doub val)
	{
		Int j = new Int();
		Int k = new Int();
		if (n<m)
		{
			heap[n++] = val;
			if (n == m)
				GlobalMembersSort.sort(heap);
		}
		else
		{
			if (val > heap[0])
			{
				heap[0]=val;
				for (j = 0;;)
				{
					k = (j << 1) + 1;
					if (k > m-1)
						break;
					if (k != (m-1) && heap[k] > heap[k+1])
						k++;
					if (heap[j] <= heap[k])
						break;
					SWAP(heap[k],heap[j]);
					j = k;
				}
			}
			n++;
		}
		srtd = 0;
	}

	public final Doub report(Int k)
	{
		Int mm = MIN(n,m);
		if (k > mm-1)
			throw("Heapselect k too big");
		if (k == m-1)
			return heap[0];
		if (srtd == null)
		{
			GlobalMembersSort.sort(heap);
			srtd = 1;
		}
		return heap[mm-1-k];
	}
}