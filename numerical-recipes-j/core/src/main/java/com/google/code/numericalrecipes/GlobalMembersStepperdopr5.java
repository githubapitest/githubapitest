package com.google.code.numericalrecipes;
public class GlobalMembersStepperdopr5
{
	public static StepperDopr5<D>.StepperDopr5(RefObject<VecDoub_IO> yy, RefObject<VecDoub_IO> dydxx, RefObject<Doub> xx, Doub atoll, Doub rtoll, boolean dens)
	{
		StepperBase = new <type missing>(yy.argvalue,dydxx.argvalue,xx.argvalue,atoll,rtoll,dens);
		k2 = n;
		k3 = n;
		k4 = n;
		k5 = n;
		k6 = n;
		rcont1 = n;
		rcont2 = n;
		rcont3 = n;
		rcont4 = n;
		rcont5 = n;
		dydxnew = n;
		EPS=numeric_limits<Doub>.epsilon();
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Doub StepperDopr5<D>::dense_out(const Int i,const Doub x,const Doub h)
	public <D> Doub StepperDopr5<D>.dense_out(Int i, Doub x, Doub h)
	{
		Doub s = (x-xold)/h;
		Doub s1 = 1.0-s;
		return rcont1[i]+s*(rcont2[i]+s1*(rcont3[i]+s*(rcont4[i]+s1 *rcont5[i])));
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
}