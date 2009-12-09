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










// basic type names (redefine if your bit lengths don't match)


//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if _MSC_VER
//C++ TO JAVA CONVERTER TODO TASK: Alternate typedef's with the same name cannot be converted to Java:
typedef __int64 Llong;
//C++ TO JAVA CONVERTER TODO TASK: Alternate typedef's with the same name cannot be converted to Java:
typedef unsigned __int64 Ullong;
//#else
//C++ TO JAVA CONVERTER TODO TASK: Alternate typedef's with the same name cannot be converted to Java:
typedef long long int Llong;
//C++ TO JAVA CONVERTER TODO TASK: Alternate typedef's with the same name cannot be converted to Java:
typedef unsigned long long int Ullong;
//#endif

//Uint proto_nan[2]={0xffffffff, 0x7fffffff};
//double NaN = *( double* )proto_nan;

//Doub NaN = sqrt(-1.);

// vector types



//C++ TO JAVA CONVERTER TODO TASK: The typedef Llong was defined in alternate ways and cannot be replaced in-line:
//C++ TO JAVA CONVERTER TODO TASK: The typedef Llong was defined in alternate ways and cannot be replaced in-line:
//C++ TO JAVA CONVERTER TODO TASK: The typedef Llong was defined in alternate ways and cannot be replaced in-line:
//C++ TO JAVA CONVERTER TODO TASK: The typedef Llong was defined in alternate ways and cannot be replaced in-line:

//C++ TO JAVA CONVERTER TODO TASK: The typedef Ullong was defined in alternate ways and cannot be replaced in-line:
//C++ TO JAVA CONVERTER TODO TASK: The typedef Ullong was defined in alternate ways and cannot be replaced in-line:
//C++ TO JAVA CONVERTER TODO TASK: The typedef Ullong was defined in alternate ways and cannot be replaced in-line:
//C++ TO JAVA CONVERTER TODO TASK: The typedef Ullong was defined in alternate ways and cannot be replaced in-line:








// matrix types



//C++ TO JAVA CONVERTER TODO TASK: The typedef Llong was defined in alternate ways and cannot be replaced in-line:
//C++ TO JAVA CONVERTER TODO TASK: The typedef Llong was defined in alternate ways and cannot be replaced in-line:
//C++ TO JAVA CONVERTER TODO TASK: The typedef Llong was defined in alternate ways and cannot be replaced in-line:
//C++ TO JAVA CONVERTER TODO TASK: The typedef Llong was defined in alternate ways and cannot be replaced in-line:

//C++ TO JAVA CONVERTER TODO TASK: The typedef Ullong was defined in alternate ways and cannot be replaced in-line:
//C++ TO JAVA CONVERTER TODO TASK: The typedef Ullong was defined in alternate ways and cannot be replaced in-line:
//C++ TO JAVA CONVERTER TODO TASK: The typedef Ullong was defined in alternate ways and cannot be replaced in-line:
//C++ TO JAVA CONVERTER TODO TASK: The typedef Ullong was defined in alternate ways and cannot be replaced in-line:





// 3D matrix types


// Floating Point Exceptions for Microsoft compilers

//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if _TURNONFPES_
//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if _MSC_VER
public class turn_on_floating_exceptions<T>
{
	public turn_on_floating_exceptions()
	{
		GlobalMembersNr3.int cw = _controlfp(0, 0);
		cw &=~(EM_INVALID | EM_OVERFLOW | EM_ZERODIVIDE);
		_controlfp(cw, MCW_EM);
	}
}