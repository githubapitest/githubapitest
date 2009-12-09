package com.google.code.numericalrecipes;
public class Daub4i implements Wavelet
{
	public final void filt(RefObject<VecDoub_IO> a, Int n, Int isign)
	{
		final Doub C0 = 0.4829629131445341;
		final Doub C1 = 0.8365163037378077;
		final Doub C2 = 0.2241438680420134;
		final Doub C3 = -0.1294095225512603;
		final Doub R00 = 0.603332511928053;
		final Doub R01 = 0.690895531839104;
		final Doub R02 = -0.398312997698228;
		final Doub R10 = -0.796543516912183;
		final Doub R11 = 0.546392713959015;
		final Doub R12 = -0.258792248333818;
		final Doub R20 = 0.0375174604524466;
		final Doub R21 = 0.457327659851769;
		final Doub R22 = 0.850088102549165;
		final Doub R23 = 0.223820356983114;
		final Doub R24 = -0.129222743354319;
		final Doub R30 = 0.0100372245644139;
		final Doub R31 = 0.122351043116799;
		final Doub R32 = 0.227428111655837;
		final Doub R33 = -0.836602921223654;
		final Doub R34 = 0.483012921773304;
		final Doub R43 = 0.443149049637559;
		final Doub R44 = 0.767556669298114;
		final Doub R45 = 0.374955331645687;
		final Doub R46 = 0.190151418429955;
		final Doub R47 = -0.194233407427412;
		final Doub R53 = 0.231557595006790;
		final Doub R54 = 0.401069519430217;
		final Doub R55 = -0.717579999353722;
		final Doub R56 = -0.363906959570891;
		final Doub R57 = 0.371718966535296;
		final Doub R65 = 0.230389043796969;
		final Doub R66 = 0.434896997965703;
		final Doub R67 = 0.870508753349866;
		final Doub R75 = -0.539822500731772;
		final Doub R76 = 0.801422961990337;
		final Doub R77 = -0.257512919478482;
		Int nh = new Int();
		Int i = new Int();
		Int j = new Int();
		if (n < 8)
			return;
		VecDoub wksp = new VecDoub(n);
		nh = n >> 1;
		if (isign >= 0)
		{
			wksp[0] = R00 *a.argvalue[0]+R01 *a.argvalue[1]+R02 *a.argvalue[2];
			wksp[nh] = R10 *a.argvalue[0]+R11 *a.argvalue[1]+R12 *a.argvalue[2];
			wksp[1] = R20 *a.argvalue[0]+R21 *a.argvalue[1]+R22 *a.argvalue[2]+R23 *a.argvalue[3]+R24 *a.argvalue[4];
			wksp[nh+1] = R30 *a.argvalue[0]+R31 *a.argvalue[1]+R32 *a.argvalue[2]+R33 *a.argvalue[3]+R34 *a.argvalue[4];
			for (i = 2,j = 3;j<n-4;j+=2,i++)
			{
				wksp[i] = C0 *a.argvalue[j]+C1 *a.argvalue[j+1]+C2 *a.argvalue[j+2]+C3 *a.argvalue[j+3];
				wksp[i+nh] = C3 *a.argvalue[j]-C2 *a.argvalue[j+1]+C1 *a.argvalue[j+2]-C0 *a.argvalue[j+3];
			}
			wksp[nh-2] = R43 *a.argvalue[n-5]+R44 *a.argvalue[n-4]+R45 *a.argvalue[n-3]+R46 *a.argvalue[n-2]+R47 *a.argvalue[n-1];
			wksp[n-2] = R53 *a.argvalue[n-5]+R54 *a.argvalue[n-4]+R55 *a.argvalue[n-3]+R56 *a.argvalue[n-2]+R57 *a.argvalue[n-1];
			wksp[nh-1] = R65 *a.argvalue[n-3]+R66 *a.argvalue[n-2]+R67 *a.argvalue[n-1];
			wksp[n-1] = R75 *a.argvalue[n-3]+R76 *a.argvalue[n-2]+R77 *a.argvalue[n-1];
		}
		else
		{
			wksp[0] = R00 *a.argvalue[0]+R10 *a.argvalue[nh]+R20 *a.argvalue[1]+R30 *a.argvalue[nh+1];
			wksp[1] = R01 *a.argvalue[0]+R11 *a.argvalue[nh]+R21 *a.argvalue[1]+R31 *a.argvalue[nh+1];
			wksp[2] = R02 *a.argvalue[0]+R12 *a.argvalue[nh]+R22 *a.argvalue[1]+R32 *a.argvalue[nh+1];
			if (n == 8)
			{
				wksp[3] = R23 *a.argvalue[1]+R33 *a.argvalue[5]+R43 *a.argvalue[2]+R53 *a.argvalue[6];
				wksp[4] = R24 *a.argvalue[1]+R34 *a.argvalue[5]+R44 *a.argvalue[2]+R54 *a.argvalue[6];
			}
			else
			{
				wksp[3] = R23 *a.argvalue[1]+R33 *a.argvalue[nh+1]+C0 *a.argvalue[2]+C3 *a.argvalue[nh+2];
				wksp[4] = R24 *a.argvalue[1]+R34 *a.argvalue[nh+1]+C1 *a.argvalue[2]-C2 *a.argvalue[nh+2];
				wksp[n-5] = C2 *a.argvalue[nh-3]+C1 *a.argvalue[n-3]+R43 *a.argvalue[nh-2]+R53 *a.argvalue[n-2];
				wksp[n-4] = C3 *a.argvalue[nh-3]-C0 *a.argvalue[n-3]+R44 *a.argvalue[nh-2]+R54 *a.argvalue[n-2];
			}
			for (i = 2,j = 5;i<nh-3;i++)
			{
				wksp[j++] = C2 *a.argvalue[i]+C1 *a.argvalue[i+nh]+C0 *a.argvalue[i+1]+C3 *a.argvalue[i+nh+1];
				wksp[j++] = C3 *a.argvalue[i]-C0 *a.argvalue[i+nh]+C1 *a.argvalue[i+1]-C2 *a.argvalue[i+nh+1];
			}
			wksp[n-3] = R45 *a.argvalue[nh-2]+R55 *a.argvalue[n-2]+R65 *a.argvalue[nh-1]+R75 *a.argvalue[n-1];
			wksp[n-2] = R46 *a.argvalue[nh-2]+R56 *a.argvalue[n-2]+R66 *a.argvalue[nh-1]+R76 *a.argvalue[n-1];
			wksp[n-1] = R47 *a.argvalue[nh-2]+R57 *a.argvalue[n-2]+R67 *a.argvalue[nh-1]+R77 *a.argvalue[n-1];
		}
		for (i = 0;i<n;i++)
			a.argvalue[i]=wksp[i];
	}
	public final void condition(RefObject<VecDoub_IO> a, Int n, Int isign)
	{
		Doub t0 = new Doub();
		Doub t1 = new Doub();
		Doub t2 = new Doub();
		Doub t3 = new Doub();
		if (n < 4)
			return;
		if (isign >= 0)
		{
			t0 = 0.324894048898962 *a.argvalue[0]+0.0371580151158803 *a.argvalue[1];
			t1 = 1.00144540498130 *a.argvalue[1];
			t2 = 1.08984305289504 *a.argvalue[n-2];
			t3 = -0.800813234246437 *a.argvalue[n-2]+2.09629288435324 *a.argvalue[n-1];
			a.argvalue[0]=t0;
			a.argvalue[1]=t1;
			a.argvalue[n-2]=t2;
			a.argvalue[n-1]=t3;
		}
		else
		{
			t0 = 3.07792649138669 *a.argvalue[0]-0.114204567242137 *a.argvalue[1];
			t1 = 0.998556681198888 *a.argvalue[1];
			t2 = 0.917563310922261 *a.argvalue[n-2];
			t3 = 0.350522032550918 *a.argvalue[n-2]+0.477032578540915 *a.argvalue[n-1];
			a.argvalue[0]=t0;
			a.argvalue[1]=t1;
			a.argvalue[n-2]=t2;
			a.argvalue[n-1]=t3;
		}
	}
}