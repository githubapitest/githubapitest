package com.google.code.numericalrecipes;
public class Daub4 implements Wavelet
{
	public final void filt(RefObject<VecDoub_IO> a, Int n, Int isign)
	{
		final Doub C0 = 0.4829629131445341;
		final Doub C1 = 0.8365163037378077;
		final Doub C2 = 0.2241438680420134;
		final Doub C3 = -0.1294095225512603;
		Int nh = new Int();
		Int i = new Int();
		Int j = new Int();
		if (n < 4)
			return;
		VecDoub wksp = new VecDoub(n);
		nh = n >> 1;
		if (isign >= 0)
		{
			for (i = 0,j = 0;j<n-3;j+=2,i++)
			{
				wksp[i] = C0 *a.argvalue[j]+C1 *a.argvalue[j+1]+C2 *a.argvalue[j+2]+C3 *a.argvalue[j+3];
				wksp[i+nh] = C3 *a.argvalue[j]-C2 *a.argvalue[j+1]+C1 *a.argvalue[j+2]-C0 *a.argvalue[j+3];
			}
			wksp[i] = C0 *a.argvalue[n-2]+C1 *a.argvalue[n-1]+C2 *a.argvalue[0]+C3 *a.argvalue[1];
			wksp[i+nh] = C3 *a.argvalue[n-2]-C2 *a.argvalue[n-1]+C1 *a.argvalue[0]-C0 *a.argvalue[1];
		}
		else
		{
			wksp[0] = C2 *a.argvalue[nh-1]+C1 *a.argvalue[n-1]+C0 *a.argvalue[0]+C3 *a.argvalue[nh];
			wksp[1] = C3 *a.argvalue[nh-1]-C0 *a.argvalue[n-1]+C1 *a.argvalue[0]-C2 *a.argvalue[nh];
			for (i = 0,j = 2;i<nh-1;i++)
			{
				wksp[j++] = C2 *a.argvalue[i]+C1 *a.argvalue[i+nh]+C0 *a.argvalue[i+1]+C3 *a.argvalue[i+nh+1];
				wksp[j++] = C3 *a.argvalue[i]-C0 *a.argvalue[i+nh]+C1 *a.argvalue[i+1]-C2 *a.argvalue[i+nh+1];
			}
		}
		for (i = 0;i<n;i++)
			a.argvalue[i]=wksp[i];
	}
}