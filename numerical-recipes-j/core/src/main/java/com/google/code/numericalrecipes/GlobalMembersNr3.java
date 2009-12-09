package com.google.code.numericalrecipes;
public class GlobalMembersNr3
{

	///#define _CHECKBOUNDS_ 1
	///#define _USESTDVECTOR_ 1
	///#define _USENRERRORCLASS_ 1
	///#define _TURNONFPES_ 1

	// all the system #include's we'll ever need
	//C++ TO JAVA CONVERTER WARNING: The following #include directive was ignored:
	//#include <fcntl.h>


	// macro-like inline functions

	public static <T> T SQR(T a)
	{
		return a *a;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>

	public static <T> T MAX(T a, T b)
			{
				return b > a != null ? (b) : (a);
			}

	public static float MAX(double a, float b)
			{
				return b > a != 0 ? (b) : (float)a;
			}

	public static float MAX(float a, double b)
			{
				return b > a ? (float)b : (a);
			}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>

	public static <T> T MIN(T a, T b)
			{
				return b < a != null ? (b) : (a);
			}

	public static float MIN(double a, float b)
			{
				return b < a != 0 ? (b) : (float)a;
			}

	public static float MIN(float a, double b)
			{
				return b < a ? (float)b : (a);
			}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>

	public static <T> T SIGN(T a, T b)
		{
			return b >= 0 ? (a >= 0 ? a : -a) : (a >= 0 ? -a : a);
		}

	public static float SIGN(float a, double b)
		{
			return b >= 0 ? (a >= 0 ? a : -a) : (a >= 0 ? -a : a);
		}

	public static float SIGN(double a, float b)
		{
			return (float)(b >= 0 ? (a >= 0 ? a : -a) : (a >= 0 ? -a : a));
		}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>

	public static <T> void SWAP(RefObject<T> a, RefObject<T> b)
		{
			T dum = a.argvalue;
			a.argvalue = b.argvalue;
			b.argvalue = dum;
		}
	//C++ TO JAVA CONVERTER TODO TASK: Alternate #define macros with the same name cannot be converted to Java:
	//#define throw(message) {printf("ERROR: %s\n     in file %s at line %d\n", NRerror(message,__FILE__,__LINE__),__FILE__,__LINE__); throwTangibleTempFlag_(1);};
	public static void NRcatch(NRerror err)
	{
		System.out.printf("ERROR: %s\n     in file %s at line %d\n", err.message, err.file, err.line);
		exit(1);
	}
	//#endif
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

	// NRvector definitions

	public static java.util.ArrayList<T>.vector()
	{
		nn = 0;
		v = null;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

	public static java.util.ArrayList<T>.vector(int n)
	{
		nn = n;
		v = n>0 ? new T[n] : null;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

	public static java.util.ArrayList<T>.vector(int n, T a)
	{
		nn = n;
		v = n>0 ? new T[n] : null;
		for(int i = 0; i<n; i++)
			v[i] = a;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

	public static java.util.ArrayList<T>.vector(int n, T a)
	{
		nn = n;
		v = n>0 ? new T[n] : null;
		for(int i = 0; i<n; i++)
			v[i] = a++;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

	public static java.util.ArrayList<T>.vector(java.util.ArrayList<T> rhs)
	{
		nn = rhs.nn;
		v = nn>0 ? new T[nn] : null;
		for(int i = 0; i<nn; i++)
			v[i] = rhs.get(i);
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

//C++ TO JAVA CONVERTER NOTE: This 'copyFrom' method was converted from the original C++ copy assignment operator:
//ORIGINAL LINE: java.util.ArrayList<T> & java.util.ArrayList<T>::operator =(const java.util.ArrayList<T> &rhs)
	public static <T> java.util.ArrayList<T>  java.util.ArrayList<T>.copyFrom(java.util.ArrayList<T> rhs)
	// postcondition: normal assignment via copying has been performed;
	//		if vector and rhs were different sizes, vector
	//		has been resized to match the size of rhs
	{
		if (this != rhs)
		{
			if (nn != rhs.nn)
			{
				if (v != null)
					(v) = null;
				nn=rhs.nn;
				v= nn>0 ? new T[nn] : null;
			}
			for (int i = 0; i<nn; i++)
				v[i]=rhs.get(i);
		}
		return this;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

	public static <T> T  java.util.ArrayList<T>.getDefault(int i) //subscripting
	{
	//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	//#if _CHECKBOUNDS_
	if (i<0 || i>=nn)
	{
	//C++ TO JAVA CONVERTER TODO TASK: The #define macro throw was defined in alternate ways and cannot be replaced in-line:
		throw("NRvector subscript out of bounds");
	}
	//#endif
		return v[i];
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: inline const T & java.util.ArrayList<T>::operator [](const int i) const
	public static <T> T  java.util.ArrayList<T>.getDefault(int i) //subscripting
	{
	//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	//#if _CHECKBOUNDS_
	if (i<0 || i>=nn)
	{
	//C++ TO JAVA CONVERTER TODO TASK: The #define macro throw was defined in alternate ways and cannot be replaced in-line:
		throw("NRvector subscript out of bounds");
	}
	//#endif
		return v[i];
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: inline int java.util.ArrayList<T>::size() const
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
	public <T> int java.util.ArrayList<T>.size()
	{
		return nn;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: void java.util.ArrayList<T>::resize(int newn)
	public <T> void java.util.ArrayList<T>.resize(int newn)
	{
		if (newn != nn)
		{
			if (v != null)
				(v) = null;
			nn = newn;
			v = nn > 0 ? new T[nn] : null;
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: void java.util.ArrayList<T>::assign(int newn, const T& a)
	public <T> void java.util.ArrayList<T>.assign(int newn, T a)
	{
		if (newn != nn)
		{
			if (v != null)
				(v) = null;
			nn = newn;
			v = nn > 0 ? new T[nn] : null;
		}
		for (int i = 0;i<nn;i++)
			v[i] = a;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: java.util.ArrayList<T>::~vector()
	public java.util.ArrayList<T>.~vector()
	{
		if (v != null)
			(v) = null;
	}

	// end of NRvector definitions

	//#endif //ifdef _USESTDVECTOR_
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>





	// NaN: uncomment one of the following 3 methods of defining a global NaN
	// you can test by verifying that (NaN != NaN) is true

	public static final double NaN = numeric_limits<Double>.quiet_NaN();
	public static turn_on_floating_exceptions yes_turn_on_floating_exceptions = new turn_on_floating_exceptions();
	//#endif // _MSC_VER 
	//#endif // _TURNONFPES 
}