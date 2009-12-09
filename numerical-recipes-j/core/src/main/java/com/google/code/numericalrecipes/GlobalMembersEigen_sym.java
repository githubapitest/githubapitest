package com.google.code.numericalrecipes;
public class GlobalMembersEigen_sym
{
public static void eigsrt(RefObject<VecDoub_IO> d)
{
	eigsrt(d, null);
}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void eigsrt(VecDoub_IO &d, MatDoub_IO *v=null)
	public static void eigsrt(RefObject<VecDoub_IO> d, RefObject<MatDoub_IO> v)
	{
		Int k = new Int();
		Int n = d.argvalue.size();
		for (Int i = 0;i<n-1;i++)
		{
			Doub p = d.argvalue[k = i];
			for (Int j = i;j<n;j++)
				if (d.argvalue[j] >= p)
					p = d.argvalue[k = j];
			if (k != i)
			{
				d.argvalue[k]=d.argvalue[i];
				d.argvalue[i]=p;
				if (v.argvalue != null)
					for (Int j = 0;j<n;j++)
					{
						p = (v.argvalue)[j][i];
						v.argvalue[j][i]=(v.argvalue)[j][k];
						v.argvalue[j][k]=p;
					}
			}
		}
	}
}