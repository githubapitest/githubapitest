package com.google.code.numericalrecipes;
public abstract class Base_interp
{
	public Int n = new Int();
	public Int mm = new Int();
	public Int jsav = new Int();
	public Int cor = new Int();
	public Int dj = new Int();
	public final Doub xx;
	public final Doub yy;
	public Base_interp(RefObject<VecDoub_I> x, Doub y, Int m)
	{
		n = x.argvalue.size();
		mm = m;
		jsav = 0;
		cor = 0;
		xx = x.argvalue[0];
		yy = y;
		dj = MIN(1,(int)Math.pow((Doub)n,0.25));
	}

	public final Doub interp(Doub x)
	{
		Int jlo = cor != null ? hunt(x) : locate(x);
		return rawinterp(jlo, x);
	}

	public final Int locate(Doub x)
	{
		Int ju = new Int();
		Int jm = new Int();
		Int jl = new Int();
		if (n < 2 || mm < 2 || mm > n)
			throw("locate size error");
		Bool ascnd = (xx[n-1] >= xx[0]);
		jl = 0;
		ju = n-1;
		while (ju-jl > 1)
		{
			jm = (ju+jl) >> 1;
			if (x >= xx[jm] == ascnd)
				jl = jm;
			else
				ju = jm;
		}
		cor = Math.abs(jl-jsav) > dj != null ? 0 : 1;
		jsav = jl;
		return MAX(0,MIN(n-mm,jl-((mm-2)>>1)));
	}
	public final Int hunt(Doub x)
	{
		Int jl = jsav;
		Int jm = new Int();
		Int ju = new Int();
		Int inc = 1;
		if (n < 2 || mm < 2 || mm > n)
			throw("hunt size error");
		Bool ascnd = (xx[n-1] >= xx[0]);
		if (jl < 0 || jl > n-1)
		{
			jl = 0;
			ju = n-1;
		}
		else
		{
			if (x >= xx[jl] == ascnd)
			{
				for (;;)
				{
					ju = jl + inc;
					if (ju >= n-1)
					{
						ju = n-1;
						break;
					}
					else if (x < xx[ju] == ascnd)
						break;
					else
					{
						jl = ju;
						inc += inc;
					}
				}
			}
			else
			{
				ju = jl;
				for (;;)
				{
					jl = jl - inc;
					if (jl <= 0)
					{
						jl = 0;
						break;
					}
					else if (x >= xx[jl] == ascnd)
						break;
					else
					{
						ju = jl;
						inc += inc;
					}
				}
			}
		}
		while (ju-jl > 1)
		{
			jm = (ju+jl) >> 1;
			if (x >= xx[jm] == ascnd)
				jl = jm;
			else
				ju = jm;
		}
		cor = Math.abs(jl-jsav) > dj != null ? 0 : 1;
		jsav = jl;
		return MAX(0,MIN(n-mm,jl-((mm-2)>>1)));
	}

	public abstract Doub rawinterp(Int jlo, Doub x);

}