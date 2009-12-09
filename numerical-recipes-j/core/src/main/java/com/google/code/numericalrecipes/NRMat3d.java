package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>














public class NRMat3d<T>
{
	private GlobalMembersNr3.int nn;
	private GlobalMembersNr3.int mm;
	private GlobalMembersNr3.int kk;
	private T[][] v;
	public NRMat3d()
	{
		nn = 0;
		mm = 0;
		kk = 0;
		v = null;
	}
	public NRMat3d(int n, int m, int k)
	{
		nn = n;
		mm = m;
		kk = k;
		v = new T*[n];
		GlobalMembersNr3.int i;
		GlobalMembersNr3.int j;
		v[0] = new T[n *m];
		v[0][0] = new T[n *m *k];
		for(j = 1; j<m; j++)
			v[0][j] = v[0][j-1] + k;
		for(i = 1; i<n; i++)
		{
			v[i] = v[i-1] + m;
			v[i][0] = v[i-1][0] + m *k;
			for(j = 1; j<m; j++)
				v[i][j] = v[i][j-1] + k;
		}
	}
	public final T** getDefault(int i)
	{
		return v[i];
	}
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: inline const T* const * operator [](const int i) const
	public final T* * getDefault(int i)
	{
		return v[i];
	}
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: inline int dim1() const
	public final int dim1()
	{
		return nn;
	}
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: inline int dim2() const
	public final int dim2()
	{
		return mm;
	}
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: inline int dim3() const
	public final int dim3()
	{
		return kk;
	}
	public void Dispose()
	{
		if (v != null)
		{
			(v[0][0]) = null;
			(v[0]) = null;
			(v) = null;
		}
	}
}