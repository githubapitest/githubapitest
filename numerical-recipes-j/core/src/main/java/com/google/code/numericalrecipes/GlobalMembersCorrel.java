package com.google.code.numericalrecipes;
public class GlobalMembersCorrel
{
	public static void correl(RefObject<VecDoub_I> data1, RefObject<VecDoub_I> data2, RefObject<VecDoub_O> ans)
	{
		Int no2 = new Int();
		Int i = new Int();
		Int n = data1.argvalue.size();
		Doub tmp = new Doub();
		VecDoub temp = new VecDoub(n);
		for (i = 0;i<n;i++)
		{
			ans.argvalue[i]=data1.argvalue[i];
			temp[i]=data2.argvalue[i];
		}
		realft(ans.argvalue,1);
		realft(temp,1);
		no2 = n>>1;
		for (i = 2;i<n;i+=2)
		{
			tmp = ans.argvalue[i];
			ans.argvalue[i]=(ans.argvalue[i]*temp[i]+ans.argvalue[i+1]*temp[i+1])/no2;
			ans.argvalue[i+1]=(ans.argvalue[i+1]*temp[i]-tmp *temp[i+1])/no2;
		}
		ans.argvalue[0]=ans.argvalue[0]*temp[0]/no2;
		ans.argvalue[1]=ans.argvalue[1]*temp[1]/no2;
		realft(ans.argvalue,-1);
	}
}