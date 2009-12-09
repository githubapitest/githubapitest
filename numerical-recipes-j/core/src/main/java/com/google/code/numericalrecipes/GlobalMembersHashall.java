package com.google.code.numericalrecipes;
public class GlobalMembersHashall
{
	public static void psdes(RefObject<Uint> lword, RefObject<Uint> rword)
	{
		final int NITER = 2;
		Uint[] c1 = { 0xbaa96887L, 0x1e17d32cL, 0x03bcdc3cL, 0x0f33d1b2L};
		Uint[] c2 = { 0x4b0f3b58L, 0xe874f0c3L, 0x6955c5a6L, 0x55a7ca46L};
		Uint i = new Uint();
		Uint ia = new Uint();
		Uint ib = new Uint();
		Uint iswap = new Uint();
		Uint itmph = 0;
		Uint itmpl = 0;
		for (i = 0;i<NITER;i++)
		{
			ia = (iswap = rword.argvalue) ^ c1[i];
			itmpl = ia & 0xffff;
			itmph = ia >> 16;
			ib = itmpl *itmpl+ ~(itmph *itmph);
			rword.argvalue = lword.argvalue ^ (((ia = (ib >> 16) | ((ib & 0xffff) << 16)) ^ c2[i])+itmpl *itmph);
			lword.argvalue = iswap;
		}
	}
	public static void hashall(RefObject<VecUint> arr)
	{
		Int m = arr.argvalue.size();
		Int n = m-1;
		n|=n>>1;
		n|=n>>2;
		n|=n>>4;
		n|=n>>8;
		n|=n>>16;
		n++;
		Int nb = n;
		Int nb2 = n>>1;
		Int j = new Int();
		Int jb = new Int();
		if (n<2)
			throw("size must be > 1");
		while (nb > 1)
		{
			for (jb = 0;jb<n-nb+1;jb+=nb)
				for (j = 0;j<nb2;j++)
					if (jb+j+nb2 < m)
						psdes(arr.argvalue[jb+j], arr.argvalue[jb+j+nb2]);
			nb = nb2;
			nb2 >>= 1;
		}
		nb2 = n>>1;
		if (m != n)
			for (j = nb2;j<m;j++)
				psdes(arr.argvalue[j], arr.argvalue[j-nb2]);
	}
}