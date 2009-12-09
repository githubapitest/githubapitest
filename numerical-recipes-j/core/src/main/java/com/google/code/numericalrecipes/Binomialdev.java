package com.google.code.numericalrecipes;
public class Binomialdev implements Ran
{
	public Doub pp = new Doub();
	public Doub p = new Doub();
	public Doub pb = new Doub();
	public Doub expnp = new Doub();
	public Doub np = new Doub();
	public Doub glnp = new Doub();
	public Doub plog = new Doub();
	public Doub pclog = new Doub();
	public Doub sq = new Doub();
	public Int n = new Int();
	public Int swch = new Int();
	public Ullong uz = new Ullong();
	public Ullong uo = new Ullong();
	public Ullong unfin = new Ullong();
	public Ullong diff = new Ullong();
	public Ullong rltp = new Ullong();
	public Int[] pbits = new Int[5];
	public Doub[] cdf = new Doub[64];
	public Doub[] logfact = new Doub[1024];
	public Binomialdev(Int nn, Doub ppp, Ullong i)
	{
		super(i);
		pp = ppp;
		n = nn;
		Int j = new Int();
		pb = p = (pp <= 0.5 ? pp : 1.0-pp);
		if (n <= 64)
		{
			uz = 0;
			uo = 0xffffffffffffffffLL;
			rltp = 0;
			for (j = 0;j<5;j++)
				pbits[j] = 1 & ((Int)(pb *= 2.));
			pb -= Math.floor(pb);
			swch = 0;
		}
		else if (n *p < 30.)
		{
			cdf[0] = Math.exp(n *Math.log(1-p));
			for (j = 1;j<64;j++)
				cdf[j] = cdf[j-1] + Math.exp(gammln(n+1.) -gammln(j+1.)-gammln(n-j+1.)+j *Math.log(p)+(n-j)*Math.log(1.-p));
			swch = 1;
		}
		else
		{
			np = n *p;
			glnp = gammln(n+1.);
			plog = Math.log(p);
			pclog = Math.log(1.-p);
			sq = Math.sqrt(np*(1.-p));
			if (n < 1024)
				for (j = 0;j<=n;j++)
					logfact[j] = gammln(j+1.);
			swch = 2;
		}
	}
	public final Int dev()
	{
		Int j = new Int();
		Int k = new Int();
		Int kl = new Int();
		Int km = new Int();
		Doub y = new Doub();
		Doub u = new Doub();
		Doub v = new Doub();
		Doub u2 = new Doub();
		Doub v2 = new Doub();
		Doub b = new Doub();
		if (swch == 0)
		{
			unfin = uo;
			for (j = 0;j<5;j++)
			{
				diff = unfin & (int64()^(pbits[j]? uo : uz));
				if (pbits[j])
					rltp |= diff;
				else
					rltp = rltp & ~diff;
				unfin = unfin & ~diff;
			}
			k = 0;
			for (j = 0;j<n;j++)
			{
				if (unfin & 1 != 0)
				{
					if (doub() < pb)
						++k;
				}
				else
				{
					if (rltp & 1 != 0)
						++k;
				}
				unfin >>= 1;
				rltp >>= 1;
			}
		}
		else if (swch == 1)
		{
			y = doub();
			kl = -1;
			k = 64;
			while (k-kl>1)
			{
				km = (kl+k)/2;
				if (y < cdf[km])
					k = km;
				else
					kl = km;
			}
		}
		else
		{
			for (;;)
			{
				u = 0.645 *doub();
				v = -0.63 + 1.25 *doub();
				v2 = SQR(v);
				if (v >= 0.)
				{
					if (v2 > 6.5 *u*(0.645-u)*(u+0.2))
						continue;
				}
				else
				{
					if (v2 > 8.4 *u*(0.645-u)*(u+0.1))
						continue;
				}
				k = (Int)(Math.floor(sq*(v/u)+np+0.5));
				if (k < 0)
					continue;
				u2 = SQR(u);
				if (v >= 0.)
				{
					if (v2 < 12.25 *u2*(0.615-u)*(0.92-u))
						break;
				}
				else
				{
					if (v2 < 7.84 *u2*(0.615-u)*(1.2-u))
						break;
				}
				b = sq *Math.exp(glnp+k *plog+(n-k)*pclog - (n < 1024 ? logfact[k]+logfact[n-k] : gammln(k+1.)+gammln(n-k+1.)));
				if (u2 < b)
					break;
			}
		}
		if (p != pp)
			k = n - k;
		return k;
	}
}