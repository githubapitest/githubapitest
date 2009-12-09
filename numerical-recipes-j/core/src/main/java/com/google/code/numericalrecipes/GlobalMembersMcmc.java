package com.google.code.numericalrecipes;
public class GlobalMembersMcmc
{
	public static Doub mcmcstep(Int m, RefObject<State> s, RefObject<Plog> plog, RefObject<Proposal> propose)
	{
		State sprop = new State();
		Doub qratio = new Doub();
		Doub alph = new Doub();
		Doub ran = new Doub();
		Int accept = 0;
		plog.argvalue(s.argvalue);
		for (Int i = 0;i<m;i++)
		{
			propose.argvalue(s.argvalue,sprop,qratio);
			alph = min(1.,qratio *Math.exp(plog.argvalue(sprop)-s.argvalue.plog));
			ran = propose.argvalue.gau.doub();
			if (ran < alph)
			{
//C++ TO JAVA CONVERTER WARNING: The following line was determined to be a copy assignment (rather than a reference assignment) - this should be verified and a 'copyFrom' method should be created if it does not yet exist:
//ORIGINAL LINE: s = sprop;
				s.argvalue.copyFrom(sprop);
				plog.argvalue(s.argvalue);
				accept++;
			}
		}
		return accept/Doub(m);
	}
}