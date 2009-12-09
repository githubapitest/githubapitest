package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>

// exception handling

//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if ! _USENRERRORCLASS_
//C++ TO JAVA CONVERTER TODO TASK: Alternate #define macros with the same name cannot be converted to Java:
//#define throw(message) {printf("ERROR: %s\n     in file %s at line %d\n", message,__FILE__,__LINE__); throw(1);}
//#else
public class NRerror
{
	public String message;
	public String file;
	public GlobalMembersNr3.int line;
	public NRerror(RefObject<String> m, RefObject<String> f, int l)
	{
		message = m.argvalue;
		file = f.argvalue;
		line = l;
	}
}