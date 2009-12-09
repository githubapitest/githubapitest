package com.google.code.numericalrecipes;
public class Proposal
{
	public Normaldev gau = new Normaldev();
	public Doub logstep = new Doub();

	public Proposal(Int ranseed, Doub lstep)
	{
		gau = new Normaldev(0.,1.,ranseed);
		logstep = lstep;
	}

//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	void operator ()(State s1, RefObject<State> s2, RefObject<Doub> qratio)
	{
		Doub r = gau.doub();
		if (r < 0.9)
		{
			s2.argvalue.lam1 = s1.lam1 * Math.exp(logstep *gau.dev());
			s2.argvalue.lam2 = s1.lam2 * Math.exp(logstep *gau.dev());
			s2.argvalue.tc = s1.tc * Math.exp(logstep *gau.dev());
			s2.argvalue.k1 = s1.k1;
			s2.argvalue.k2 = s1.k2;
			qratio.argvalue = (s2.argvalue.lam1/s1.lam1)*(s2.argvalue.lam2/s1.lam2)*(s2.argvalue.tc/s1.tc);
		}
		else
		{
			r = gau.doub();
			if (s1.k1>1)
			{
				if (r<0.5)
					s2.argvalue.k1 = s1.k1;
				else if (r<0.75)
					s2.argvalue.k1 = s1.k1 + 1;
				else
					s2.argvalue.k1 = s1.k1 - 1;
			}
			else
			{
				if (r<0.75)
					s2.argvalue.k1 = s1.k1;
				else
					s2.argvalue.k1 = s1.k1 + 1;
			}
			s2.argvalue.lam1 = s2.argvalue.k1 *s1.lam1/s1.k1;
			r = gau.doub();
			if (s1.k2>1)
			{
				if (r<0.5)
					s2.argvalue.k2 = s1.k2;
				else if (r<0.75)
					s2.argvalue.k2 = s1.k2 + 1;
				else
					s2.argvalue.k2 = s1.k2 - 1;
			}
			else
			{
				if (r<0.75)
					s2.argvalue.k2 = s1.k2;
				else
					s2.argvalue.k2 = s1.k2 + 1;
			}
			s2.argvalue.lam2 = s2.argvalue.k2 *s1.lam2/s1.k2;
			s2.argvalue.tc = s1.tc;
			qratio.argvalue = 1.;
		}
	}
}