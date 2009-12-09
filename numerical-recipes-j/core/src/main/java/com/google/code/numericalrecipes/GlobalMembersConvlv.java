package com.google.code.numericalrecipes;
public class GlobalMembersConvlv
{
	public static void convlv(RefObject<VecDoub_I> data, RefObject<VecDoub_I> respns, Int isign, RefObject<VecDoub_O> ans)
	{
		Int i = new Int();
		Int no2 = new Int();
		Int n = data.argvalue.size();
		Int m = respns.argvalue.size();
		Doub mag2 = new Doub();
		Doub tmp = new Doub();
		VecDoub temp = new VecDoub(n);
		temp[0]=respns.argvalue[0];
		for (i = 1;i<(m+1)/2;i++)
		{
			temp[i]=respns.argvalue[i];
			temp[n-i]=respns.argvalue[m-i];
		}
		for (i = (m+1)/2;i<n-(m-1)/2;i++)
			temp[i]=0.0;
		for (i = 0;i<n;i++)
			ans.argvalue[i]=data.argvalue[i];
		realft(ans.argvalue,1);
		realft(temp,1);
		no2 = n>>1;
		if (isign == 1)
		{
			for (i = 2;i<n;i+=2)
			{
				tmp = ans.argvalue[i];
				ans.argvalue[i]=(ans.argvalue[i]*temp[i]-ans.argvalue[i+1]*temp[i+1])/no2;
				ans.argvalue[i+1]=(ans.argvalue[i+1]*temp[i]+tmp *temp[i+1])/no2;
			}
			ans.argvalue[0]=ans.argvalue[0]*temp[0]/no2;
			ans.argvalue[1]=ans.argvalue[1]*temp[1]/no2;
		}
		else if (isign == -1)
		{
			for (i = 2;i<n;i+=2)
			{
				if ((mag2 = SQR(temp[i])+SQR(temp[i+1])) == 0.0)
					throw("Deconvolving at response zero in convlv");
				tmp = ans.argvalue[i];
				ans.argvalue[i]=(ans.argvalue[i]*temp[i]+ans.argvalue[i+1]*temp[i+1])/mag2/no2;
				ans.argvalue[i+1]=(ans.argvalue[i+1]*temp[i]-tmp *temp[i+1])/mag2/no2;
			}
			if (temp[0] == 0.0 || temp[1] == 0.0)
				throw("Deconvolving at response zero in convlv");
			ans.argvalue[0]=ans.argvalue[0]/temp[0]/no2;
			ans.argvalue[1]=ans.argvalue[1]/temp[1]/no2;
		}
		else
			throw("No meaning for isign in convlv");
		realft(ans.argvalue,-1);
	}
}