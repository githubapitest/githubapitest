package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

public class NRmatrix<T>
{
	private GlobalMembersNr3.int nn;
	private GlobalMembersNr3.int mm;
	private T[] v;
	public NRmatrix()
	{
		nn = 0;
		mm = 0;
		v = null;
	}
	public NRmatrix(int n, int m)
	{
		nn = n;
		mm = m;
		v = n>0 ? new T[n] : null;
		GlobalMembersNr3.int i;
		GlobalMembersNr3.int nel = m *n;
		if (v != null)
			v[0] = nel>0 ? new T[nel] : null;
		for (i = 1;i<n;i++)
			v[i] = v[i-1] + m;
	}
	public NRmatrix(int n, int m, T a)
	{
		nn = n;
		mm = m;
		v = n>0 ? new T[n] : null;
		GlobalMembersNr3.int i;
		GlobalMembersNr3.int j;
		GlobalMembersNr3.int nel = m *n;
		if (v != null)
			v[0] = nel>0 ? new T[nel] : null;
		for (i = 1; i< n; i++)
			v[i] = v[i-1] + m;
		for (i = 0; i< n; i++)
			for (j = 0; j<m; j++)
				v[i][j] = a;
	}
	public NRmatrix(int n, int m, T a)
	{
		nn = n;
		mm = m;
		v = n>0 ? new T[n] : null;
		GlobalMembersNr3.int i;
		GlobalMembersNr3.int j;
		GlobalMembersNr3.int nel = m *n;
		if (v != null)
			v[0] = nel>0 ? new T[nel] : null;
		for (i = 1; i< n; i++)
			v[i] = v[i-1] + m;
		for (i = 0; i< n; i++)
			for (j = 0; j<m; j++)
				v[i][j] = a++;
	}
	public NRmatrix(NRmatrix rhs)
	{
		nn = rhs.nn;
		mm = rhs.mm;
		v = nn>0 ? new T[nn] : null;
		GlobalMembersNr3.int i;
		GlobalMembersNr3.int j;
		GlobalMembersNr3.int nel = mm *nn;
		if (v != null)
			v[0] = nel>0 ? new T[nel] : null;
		for (i = 1; i< nn; i++)
			v[i] = v[i-1] + mm;
		for (i = 0; i< nn; i++)
			for (j = 0; j<mm; j++)
				v[i][j] = rhs[i][j];
	}
//C++ TO JAVA CONVERTER NOTE: This 'copyFrom' method was converted from the original C++ copy assignment operator:
//ORIGINAL LINE: NRmatrix<T> & operator =(const NRmatrix<T> &rhs)
	public final NRmatrix<T>  copyFrom(NRmatrix<T> rhs)
	// postcondition: normal assignment via copying has been performed;
	//		if matrix and rhs were different sizes, matrix
	//		has been resized to match the size of rhs
	{
		if (this != rhs)
		{
			GlobalMembersNr3.int i;
			GlobalMembersNr3.int j;
			GlobalMembersNr3.int nel;
			if (nn != rhs.nn || mm != rhs.mm)
			{
				if (v != null)
				{
					(v[0]) = null;
					(v) = null;
				}
				nn = rhs.nn;
				mm = rhs.mm;
				v = nn>0 ? new T[nn] : null;
				nel = mm *nn;
				if (v != null)
					v[0] = nel>0 ? new T[nel] : null;
				for (i = 1; i< nn; i++)
					v[i] = v[i-1] + mm;
			}
			for (i = 0; i< nn; i++)
				for (j = 0; j<mm; j++)
					v[i][j] = rhs[i][j];
		}
		return this;
	}
//C++ TO JAVA CONVERTER TODO TASK: Alternate typedef's with the same name cannot be converted to Java:
	typedef T value_type;
	public final T getDefault(int i)
	{
//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if _CHECKBOUNDS_
	if (i<0 || i>=nn)
	{
	//C++ TO JAVA CONVERTER TODO TASK: The #define macro throw was defined in alternate ways and cannot be replaced in-line:
		throw("NRmatrix subscript out of bounds");
	}
//#endif
		return v[i];
	}
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: inline const T* operator [](const int i) const
	public final T getDefault(int i)
	{
//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if _CHECKBOUNDS_
	if (i<0 || i>=nn)
	{
	//C++ TO JAVA CONVERTER TODO TASK: The #define macro throw was defined in alternate ways and cannot be replaced in-line:
		throw("NRmatrix subscript out of bounds");
	}
//#endif
		return v[i];
	}
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: inline int nrows() const
	public final int nrows()
	{
		return nn;
	}
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: inline int ncols() const
	public final int ncols()
	{
		return mm;
	}
	public final void resize(int newn, int newm)
	{
		GlobalMembersNr3.int i;
		GlobalMembersNr3.int nel;
		if (newn != nn || newm != mm)
		{
			if (v != null)
			{
				(v[0]) = null;
				(v) = null;
			}
			nn = newn;
			mm = newm;
			v = nn>0 ? new T[nn] : null;
			nel = mm *nn;
			if (v != null)
				v[0] = nel>0 ? new T[nel] : null;
			for (i = 1; i< nn; i++)
				v[i] = v[i-1] + mm;
		}
	}
	public final void assign(int newn, int newm, T a)
	{
		GlobalMembersNr3.int i;
		GlobalMembersNr3.int j;
		GlobalMembersNr3.int nel;
		if (newn != nn || newm != mm)
		{
			if (v != null)
			{
				(v[0]) = null;
				(v) = null;
			}
			nn = newn;
			mm = newm;
			v = nn>0 ? new T[nn] : null;
			nel = mm *nn;
			if (v != null)
				v[0] = nel>0 ? new T[nel] : null;
			for (i = 1; i< nn; i++)
				v[i] = v[i-1] + mm;
		}
		for (i = 0; i< nn; i++)
			for (j = 0; j<mm; j++)
				v[i][j] = a;
	}
	public void Dispose()
	{
		if (v != null)
		{
			(v[0]) = null;
			(v) = null;
		}
	}
}