package com.google.code.numericalrecipes;
public class GlobalMembersSort
{
public static <T> void sort(RefObject<NRvector<T>> arr)
{
	sort(arr, -1);
}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void sort(NRvector<T> &arr, Int m=-1)
	public static <T> void sort(RefObject<NRvector<T>> arr, Int m)
	{
		final Int M = 7;
		final Int NSTACK = 64;
		Int i = new Int();
		Int ir = new Int();
		Int j = new Int();
		Int k = new Int();
		Int jstack = -1;
		Int l = 0;
		Int n = arr.argvalue.size();
		T a = new T();
		VecInt istack = new VecInt(NSTACK);
		if (m>0)
			n = MIN(m,n);
		ir = n-1;
		for (;;)
		{
			if (ir-l < M)
			{
				for (j = l+1;j<=ir;j++)
				{
					a = arr.argvalue[j];
					for (i = j-1;i>=l;i--)
					{
						if (arr.argvalue[i] <= a)
							break;
						arr.argvalue[i+1]=arr.argvalue[i];
					}
					arr.argvalue[i+1]=a;
				}
				if (jstack < 0)
					break;
				ir = istack[jstack--];
				l = istack[jstack--];
			}
			else
			{
				k = (l+ir) >> 1;
				SWAP(arr.argvalue[k],arr.argvalue[l+1]);
				if (arr.argvalue[l] > arr.argvalue[ir])
				{
					SWAP(arr.argvalue[l],arr.argvalue[ir]);
				}
				if (arr.argvalue[l+1] > arr.argvalue[ir])
				{
					SWAP(arr.argvalue[l+1],arr.argvalue[ir]);
				}
				if (arr.argvalue[l] > arr.argvalue[l+1])
				{
					SWAP(arr.argvalue[l],arr.argvalue[l+1]);
				}
				i = l+1;
				j = ir;
				a = arr.argvalue[l+1];
				for (;;)
				{
					do
						i++;
						while (arr.argvalue[i] < a);
					do
						j--;
						while (arr.argvalue[j] > a);
					if (j < i)
						break;
					SWAP(arr.argvalue[i],arr.argvalue[j]);
				}
				arr.argvalue[l+1]=arr.argvalue[j];
				arr.argvalue[j]=a;
				jstack += 2;
				if (jstack >= NSTACK)
					throw("NSTACK too small in sort.");
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
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T, class U>
	public static <T, U> void sort2(RefObject<NRvector<T>> arr, RefObject<NRvector<U>> brr)
	{
		final Int M = 7;
		final Int NSTACK = 64;
		Int i = new Int();
		Int ir = new Int();
		Int j = new Int();
		Int k = new Int();
		Int jstack = -1;
		Int l = 0;
		Int n = arr.argvalue.size();
		T a = new T();
		U b = new U();
		VecInt istack = new VecInt(NSTACK);
		ir = n-1;
		for (;;)
		{
			if (ir-l < M)
			{
				for (j = l+1;j<=ir;j++)
				{
					a = arr.argvalue[j];
					b = brr.argvalue[j];
					for (i = j-1;i>=l;i--)
					{
						if (arr.argvalue[i] <= a)
							break;
						arr.argvalue[i+1]=arr.argvalue[i];
						brr.argvalue[i+1]=brr.argvalue[i];
					}
					arr.argvalue[i+1]=a;
					brr.argvalue[i+1]=b;
				}
				if (jstack < 0)
					break;
				ir = istack[jstack--];
				l = istack[jstack--];
			}
			else
			{
				k = (l+ir) >> 1;
				SWAP(arr.argvalue[k],arr.argvalue[l+1]);
				SWAP(brr.argvalue[k],brr.argvalue[l+1]);
				if (arr.argvalue[l] > arr.argvalue[ir])
				{
					SWAP(arr.argvalue[l],arr.argvalue[ir]);
					SWAP(brr.argvalue[l],brr.argvalue[ir]);
				}
				if (arr.argvalue[l+1] > arr.argvalue[ir])
				{
					SWAP(arr.argvalue[l+1],arr.argvalue[ir]);
					SWAP(brr.argvalue[l+1],brr.argvalue[ir]);
				}
				if (arr.argvalue[l] > arr.argvalue[l+1])
				{
					SWAP(arr.argvalue[l],arr.argvalue[l+1]);
					SWAP(brr.argvalue[l],brr.argvalue[l+1]);
				}
				i = l+1;
				j = ir;
				a = arr.argvalue[l+1];
				b = brr.argvalue[l+1];
				for (;;)
				{
					do
						i++;
						while (arr.argvalue[i] < a);
					do
						j--;
						while (arr.argvalue[j] > a);
					if (j < i)
						break;
					SWAP(arr.argvalue[i],arr.argvalue[j]);
					SWAP(brr.argvalue[i],brr.argvalue[j]);
				}
				arr.argvalue[l+1]=arr.argvalue[j];
				arr.argvalue[j]=a;
				brr.argvalue[l+1]=brr.argvalue[j];
				brr.argvalue[j]=b;
				jstack += 2;
				if (jstack >= NSTACK)
					throw("NSTACK too small in sort2.");
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
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
public static <T> void shell(RefObject<NRvector<T>> a)
{
	shell(a, -1);
}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void shell(NRvector<T> &a, Int m=-1)
	public static <T> void shell(RefObject<NRvector<T>> a, Int m)
	{
		Int i = new Int();
		Int j = new Int();
		Int inc = new Int();
		Int n = a.argvalue.size();
		T v = new T();
		if (m>0)
			n = MIN(m,n);
		inc = 1;
		do
		{
			inc *= 3;
			inc++;
		} while (inc <= n);
		do
		{
			inc /= 3;
			for (i = inc;i<n;i++)
			{
				v = a.argvalue[i];
				j = i;
				while (a.argvalue[j-inc] > v)
				{
					a.argvalue[j]=a.argvalue[j-inc];
					j -= inc;
					if (j < inc)
						break;
				}
				a.argvalue[j]=v;
			}
		} while (inc > 1);
	}
		public static <T> void sift_down(RefObject<NRvector<T>> ra, Int l, Int r)
		{
			Int j = new Int();
			Int jold = new Int();
			T a = new T();
			a = ra.argvalue[l];
			jold = l;
			j = 2 *l+1;
			while (j <= r)
			{
				if (j < r && ra.argvalue[j] < ra.argvalue[j+1])
					j++;
				if (a >= ra.argvalue[j])
					break;
				ra.argvalue[jold]=ra.argvalue[j];
				jold = j;
				j = 2 *j+1;
			}
			ra.argvalue[jold]=a;
		}

	public static <T> void hpsort(RefObject<NRvector<T>> ra)
	{
		Int i = new Int();
		Int n = ra.argvalue.size();
		for (i = n/2-1; i>=0; i--)
	{
		RefObject<NRvector<T>> tempRefObject = new RefObject<NRvector<T>>(ra);
			hpsort_util.sift_down(tempRefObject, i, n-1);
			ra.argvalue = tempRefObject.argvalue;
		}
		for (i = n-1; i>0; i--)
		{
			SWAP(ra.argvalue[0],ra.argvalue[i]);
		RefObject<NRvector<T>> tempRefObject2 = new RefObject<NRvector<T>>(ra);
			hpsort_util.sift_down(tempRefObject2, 0, i-1);
			ra.argvalue = tempRefObject2.argvalue;
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
	public static <T> void piksrt(RefObject<NRvector<T>> arr)
	{
		Int i = new Int();
		Int j = new Int();
		Int n = arr.argvalue.size();
		T a = new T();
		for (j = 1;j<n;j++)
		{
			a = arr.argvalue[j];
			i = j;
			while (i > 0 && arr.argvalue[i-1] > a)
			{
				arr.argvalue[i]=arr.argvalue[i-1];
				i--;
			}
			arr.argvalue[i]=a;
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T, class U>
	public static <T, U> void piksr2(RefObject<NRvector<T>> arr, RefObject<NRvector<U>> brr)
	{
		Int i = new Int();
		Int j = new Int();
		Int n = arr.argvalue.size();
		T a = new T();
		U b = new U();
		for (j = 1;j<n;j++)
		{
			a = arr.argvalue[j];
			b = brr.argvalue[j];
			i = j;
			while (i > 0 && arr.argvalue[i-1] > a)
			{
				arr.argvalue[i]=arr.argvalue[i-1];
				brr.argvalue[i]=brr.argvalue[i-1];
				i--;
			}
			arr.argvalue[i]=a;
			brr.argvalue[i]=b;
		}
	}
	public static <T> T select(Int k, RefObject<NRvector<T>> arr)
	{
		Int i = new Int();
		Int ir = new Int();
		Int j = new Int();
		Int l = new Int();
		Int mid = new Int();
		Int n = arr.argvalue.size();
		T a = new T();
		l = 0;
		ir = n-1;
		for (;;)
		{
			if (ir <= l+1)
			{
				if (ir == l+1 && arr.argvalue[ir] < arr.argvalue[l])
					SWAP(arr.argvalue[l],arr.argvalue[ir]);
				return arr.argvalue[k];
			}
			else
			{
				mid = (l+ir) >> 1;
				SWAP(arr.argvalue[mid],arr.argvalue[l+1]);
				if (arr.argvalue[l] > arr.argvalue[ir])
					SWAP(arr.argvalue[l],arr.argvalue[ir]);
				if (arr.argvalue[l+1] > arr.argvalue[ir])
					SWAP(arr.argvalue[l+1],arr.argvalue[ir]);
				if (arr.argvalue[l] > arr.argvalue[l+1])
					SWAP(arr.argvalue[l],arr.argvalue[l+1]);
				i = l+1;
				j = ir;
				a = arr.argvalue[l+1];
				for (;;)
				{
					do
						i++;
						while (arr.argvalue[i] < a);
					do
						j--;
						while (arr.argvalue[j] > a);
					if (j < i)
						break;
					SWAP(arr.argvalue[i],arr.argvalue[j]);
				}
				arr.argvalue[l+1]=arr.argvalue[j];
				arr.argvalue[j]=a;
				if (j >= k)
					ir = j-1;
				if (j <= k)
					l = i;
			}
		}
	}
}