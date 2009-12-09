package com.google.code.numericalrecipes;
public class GlobalMembersMoment
{
	public static void moment(RefObject<VecDoub_I> data, RefObject<Doub> ave, RefObject<Doub> adev, RefObject<Doub> sdev, RefObject<Doub> var, RefObject<Doub> skew, RefObject<Doub> curt)
	{
		Int j = new Int();
		Int n = data.argvalue.size();
		Doub ep = 0.0;
		Doub s = new Doub();
		Doub p = new Doub();
		if (n <= 1)
			throw("n must be at least 2 in moment");
		s = 0.0;
		for (j = 0;j<n;j++)
			s += data.argvalue[j];
		ave.argvalue = s/n;
		adev.argvalue = var.argvalue = skew.argvalue = curt.argvalue = 0.0;
		for (j = 0;j<n;j++)
		{
			adev.argvalue += Math.abs(s = data.argvalue[j]-ave.argvalue);
			ep += s;
			var.argvalue += (p = s *s);
			skew.argvalue += (p *= s);
			curt.argvalue += (p *= s);
		}
		adev.argvalue /= n;
		var.argvalue = (var.argvalue-ep *ep/n)/(n-1);
		sdev.argvalue = Math.sqrt(var.argvalue);
		if (var.argvalue != 0.0)
		{
			skew.argvalue /= (n *var.argvalue *sdev.argvalue);
			curt.argvalue = curt.argvalue/(n *var.argvalue *var.argvalue)-3.0;
		}
		else
			throw("No skew/kurtosis when variance = 0 (in moment)");
	}
	public static void avevar(RefObject<VecDoub_I> data, RefObject<Doub> ave, RefObject<Doub> var)
	{
		Doub s = new Doub();
		Doub ep = new Doub();
		Int j = new Int();
		Int n = data.argvalue.size();
		ave.argvalue = 0.0;
		for (j = 0;j<n;j++)
			ave.argvalue += data.argvalue[j];
		ave.argvalue /= n;
		var.argvalue = ep = 0.0;
		for (j = 0;j<n;j++)
		{
			s = data.argvalue[j]-ave.argvalue;
			ep += s;
			var.argvalue += s *s;
		}
		var.argvalue = (var.argvalue-ep *ep/n)/(n-1);
	}
}