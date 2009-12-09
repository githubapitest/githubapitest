package com.google.code.numericalrecipes;
public class GlobalMembersStepperross
{
	public static final Doub Ross_constants.c2=0.386;
	public static final Doub Ross_constants.c3=0.21;
	public static final Doub Ross_constants.c4=0.63;
	public static final Doub Ross_constants.bet2p=0.0317;
	public static final Doub Ross_constants.bet3p=0.0635;
	public static final Doub Ross_constants.bet4p=0.3438;
	public static final Doub Ross_constants.d1= 0.2500000000000000e+00;
	public static final Doub Ross_constants.d2=-0.1043000000000000e+00;
	public static final Doub Ross_constants.d3= 0.1035000000000000e+00;
	public static final Doub Ross_constants.d4=-0.3620000000000023e-01;
	public static final Doub Ross_constants.a21= 0.1544000000000000e+01;
	public static final Doub Ross_constants.a31= 0.9466785280815826e+00;
	public static final Doub Ross_constants.a32= 0.2557011698983284e+00;
	public static final Doub Ross_constants.a41= 0.3314825187068521e+01;
	public static final Doub Ross_constants.a42= 0.2896124015972201e+01;
	public static final Doub Ross_constants.a43= 0.9986419139977817e+00;
	public static final Doub Ross_constants.a51= 0.1221224509226641e+01;
	public static final Doub Ross_constants.a52= 0.6019134481288629e+01;
	public static final Doub Ross_constants.a53= 0.1253708332932087e+02;
	public static final Doub Ross_constants.a54=-0.6878860361058950e+00;
	public static final Doub Ross_constants.c21=-0.5668800000000000e+01;
	public static final Doub Ross_constants.c31=-0.2430093356833875e+01;
	public static final Doub Ross_constants.c32=-0.2063599157091915e+00;
	public static final Doub Ross_constants.c41=-0.1073529058151375e+00;
	public static final Doub Ross_constants.c42=-0.9594562251023355e+01;
	public static final Doub Ross_constants.c43=-0.2047028614809616e+02;
	public static final Doub Ross_constants.c51= 0.7496443313967647e+01;
	public static final Doub Ross_constants.c52=-0.1024680431464352e+02;
	public static final Doub Ross_constants.c53=-0.3399990352819905e+02;
	public static final Doub Ross_constants.c54= 0.1170890893206160e+02;
	public static final Doub Ross_constants.c61= 0.8083246795921522e+01;
	public static final Doub Ross_constants.c62=-0.7981132988064893e+01;
	public static final Doub Ross_constants.c63=-0.3152159432874371e+02;
	public static final Doub Ross_constants.c64= 0.1631930543123136e+02;
	public static final Doub Ross_constants.c65=-0.6058818238834054e+01;
	public static final Doub Ross_constants.gam= 0.2500000000000000e+00;
	public static final Doub Ross_constants.d21= 0.1012623508344586e+02;
	public static final Doub Ross_constants.d22=-0.7487995877610167e+01;
	public static final Doub Ross_constants.d23=-0.3480091861555747e+02;
	public static final Doub Ross_constants.d24=-0.7992771707568823e+01;
	public static final Doub Ross_constants.d25= 0.1025137723295662e+01;
	public static final Doub Ross_constants.d31=-0.6762803392801253e+00;
	public static final Doub Ross_constants.d32= 0.6087714651680015e+01;
	public static final Doub Ross_constants.d33= 0.1643084320892478e+02;
	public static final Doub Ross_constants.d34= 0.2476722511418386e+02;
	public static final Doub Ross_constants.d35=-0.6594389125716872e+01;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
	public static StepperRoss<D>.StepperRoss(RefObject<VecDoub_IO> yy, RefObject<VecDoub_IO> dydxx, RefObject<Doub> xx, Doub atoll, Doub rtoll, boolean dens)
	{
		StepperBase = new <type missing>(yy.argvalue,dydxx.argvalue,xx.argvalue,atoll,rtoll,dens);
		dfdy = new MatDoub(n,n);
		dfdx = n;
		k1 = n;
		k2 = n;
		k3 = n;
		k4 = n;
		k5 = n;
		k6 = n;
		cont1 = n;
		cont2 = n;
		cont3 = n;
		cont4 = n;
		a = new MatDoub(n,n);
		EPS=numeric_limits<Doub>.epsilon();
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Doub StepperRoss<D>::dense_out(const Int i,const Doub x,const Doub h)
	public <D> Doub StepperRoss<D>.dense_out(Int i, Doub x, Doub h)
	{
		Doub s = (x-xold)/h;
		Doub s1 = 1.0-s;
		return cont1[i]*s1+s*(cont2[i]+s1*(cont3[i]+s *cont4[i]));
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
}