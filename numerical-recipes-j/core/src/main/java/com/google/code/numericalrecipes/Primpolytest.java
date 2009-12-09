package com.google.code.numericalrecipes;
public class Primpolytest
{
	public Int N = new Int();
	public Int nfactors = new Int();
	public VecUllong factors = new VecUllong();
	public VecInt t = new VecInt();
	public VecInt a = new VecInt();
	public VecInt p = new VecInt();

	public Primpolytest()
	{
		N = 32;
		nfactors = 5;
		factors = nfactors;
		t = N *N;
		a = N *N;
		p = N *N;
		Ullong[] factordata = {3,5,17,257,65537};
		for (Int i = 0;i<nfactors;i++)
			factors[i] = factordata[i];
	}

	public final Int ispident()
	{
		Int i = new Int();
		Int j = new Int();
		for (i = 0; i<N; i++)
			for (j = 0; j<N; j++)
			{
			if (i == j)
			{
				if (p[i *N+j] != 1)
					return 0;
			}
			else
			{
				if (p[i *N+j] != 0)
					return 0;
			}
		}
		return 1;
	}

	public final void mattimeseq(RefObject<VecInt> a, RefObject<VecInt> b)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int sum = new Int();
		VecInt tmp = new VecInt(N *N);
		for (i = 0; i<N; i++)
			for (j = 0; j<N; j++)
			{
			sum = 0;
			for (k = 0; k<N; k++)
				sum += a.argvalue[i *N+k] * b.argvalue[k *N+j];
			tmp[i *N+j] = sum & 1;
		}
		for (k = 0; k<N *N; k++)
			a.argvalue[k] = tmp[k];
	}

	public final void matpow(Ullong n)
	{
		Int k = new Int();
		for (k = 0; k<N *N; k++)
			p[k] = 0;
		for (k = 0; k<N; k++)
			p[k *N+k] = 1;
		while (1)
		{
			if (n & 1 != 0)
			{
				RefObject<VecInt> tempRefObject = new RefObject<VecInt>(p);
				RefObject<VecInt> tempRefObject2 = new RefObject<VecInt>(a);
				mattimeseq(tempRefObject, tempRefObject2);
				p = tempRefObject.argvalue;
				a = tempRefObject2.argvalue;
			}
			n >>= 1;
			if (n == 0)
				break;
			RefObject<VecInt> tempRefObject3 = new RefObject<VecInt>(a);
			RefObject<VecInt> tempRefObject4 = new RefObject<VecInt>(a);
			mattimeseq(tempRefObject3, tempRefObject4);
			a = tempRefObject3.argvalue;
			a = tempRefObject4.argvalue;
		}
	}

	public final Int test(Ullong n)
	{
		Int i = new Int();
		Int k = new Int();
		Int j = new Int();
		Ullong pow = new Ullong();
		Ullong tnm1 = new Ullong();
		Ullong nn = n;
		tnm1 = ((Ullong)1 << N) - 1;
		if (n > (tnm1 >> 1))
			throw("not a polynomial of degree N");
		for (k = 0; k<N *N; k++)
			t[k] = 0;
		for (i = 1; i<N; i++)
			t[i *N+(i-1)] = 1;
		j = 0;
		while (nn != null)
		{
			if (nn & 1 != 0)
				t[j] = 1;
			nn >>= 1;
			j++;
		}
		t[N-1] = 1;
		for (k = 0; k<N *N; k++)
			a[k] = t[k];
		matpow(tnm1);
		if (ispident() != 1)
			return 0;
		for (i = 0; i<nfactors; i++)
		{
			pow = tnm1/factors[i];
			for (k = 0; k<N *N; k++)
				a[k] = t[k];
			matpow(pow);
			if (ispident() == 1)
				return 0;
		}
		return 1;
	}
}