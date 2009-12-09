package com.google.code.numericalrecipes;
public class GlobalMembersRlft3_sharpen
{
		public static Int i = new Int();
		public static Int j = new Int();
		public static Int nx = 256;
		public static Int ny = 256;
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
	//	MatDoub data(nx NamelessParameter1, ny NamelessParameter2);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
	//	VecDoub speq(2 *nx);
		public static Doub fac = new Doub();
//C++ TO JAVA CONVERTER TODO TASK: The following statement was not recognized, possibly due to an unrecognized macro:
		... rlft3(data,speq,1);
//C++ TO JAVA CONVERTER TODO TASK: The following statement was not recognized, possibly due to an unrecognized macro:
		for (i=0;i<nx/2;i++)
//C++ TO JAVA CONVERTER TODO TASK: The following method format was not recognized, possibly due to an unrecognized macro:
			for (j=0;j<ny/2;j++)
			{
				fac = 1.+3.*Math.sqrt(SQR(i *2./nx)+SQR(j *2./ny));
				Cmplx(data[i])[j] *= fac;
				if (i>0)
					Cmplx(data[nx-i])[j] *= fac;
		}
//C++ TO JAVA CONVERTER TODO TASK: The following method format was not recognized, possibly due to an unrecognized macro:
		for (j=0;j<ny/2;j++)
		{
			fac = 1.+3.*Math.sqrt(1.+SQR(j *2./ny));
			Cmplx(data[nx/2])[j] *= fac;
		}
//C++ TO JAVA CONVERTER TODO TASK: The following method format was not recognized, possibly due to an unrecognized macro:
		for (i=0;i<nx/2;i++)
		{
			fac = 1.+3.*Math.sqrt(SQR(i *2./nx)+1.);
			Cmplx(speq)[i] *= fac;
			if (i>0)
				Cmplx(speq)[nx-i] *= fac;
		}
//C++ TO JAVA CONVERTER TODO TASK: The following statement was not recognized, possibly due to an unrecognized macro:
		Cmplx(speq)[nx/2] *= (1.+3.*sqrt(2.));
//C++ TO JAVA CONVERTER TODO TASK: The following statement was not recognized, possibly due to an unrecognized macro:
		rlft3(data,speq,-1);
}