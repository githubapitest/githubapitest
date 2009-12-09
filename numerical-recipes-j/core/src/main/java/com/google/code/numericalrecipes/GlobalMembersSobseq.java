package com.google.code.numericalrecipes;
public class GlobalMembersSobseq
{
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Int[] mdeg = {1,2,3,3,4,4};
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Uint in = new Uint();
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private VecUint ix = new VecUint(MAXDIM);
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private NRvector<Uint> iu = new NRvector<Uint>(MAXBIT);
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Uint[] ip = {0,1,1,2,1,4};
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Uint[] iv = {1,1,1,1,1,1,3,1,3,3,1,1,5,7,7,3,3,5,15,11,5,15,13,9};
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Doub fac = new Doub();
	public static void sobseq(Int n, RefObject<VecDoub_O> x)
	{
		final Int MAXBIT = 30;
		final Int MAXDIM = 6;
		Int j = new Int();
		Int k = new Int();
		Int l = new Int();
		Uint i = new Uint();
		Uint im = new Uint();
		Uint ipp = new Uint();
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Int mdeg[MAXDIM]={1,2,3,3,4,4};
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Uint in;
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static VecUint ix(MAXDIM);
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static NRvector<Uint*> iu(MAXBIT);
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Uint ip[MAXDIM]={0,1,1,2,1,4};
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Uint iv[MAXDIM *MAXBIT]= {1,1,1,1,1,1,3,1,3,3,1,1,5,7,7,3,3,5,15,11,5,15,13,9};
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Doub fac;

		if (n < 0)
		{
			for (k = 0;k<MAXDIM;k++)
				ix[k]=0;
			in = 0;
			if (iv[0] != 1)
				return;
			fac = 1.0/(1 << MAXBIT);
			for (j = 0,k = 0;j<MAXBIT;j++,k+=MAXDIM)
				iu[j] = iv[k];
			for (k = 0;k<MAXDIM;k++)
			{
				for (j = 0;j<mdeg[k];j++)
					iu[j][k] <<= (MAXBIT-1-j);
				for (j = mdeg[k];j<MAXBIT;j++)
				{
					ipp = ip[k];
					i = iu[j-mdeg[k]][k];
					i ^= (i >> mdeg[k]);
					for (l = mdeg[k]-1;l>=1;l--)
					{
						if (ipp & 1 != 0)
							i ^= iu[j-l][k];
						ipp >>= 1;
					}
					iu[j][k]=i;
				}
			}
		}
		else
		{
			im = in++;
			for (j = 0;j<MAXBIT;j++)
			{
				if (!(im & 1))
					break;
				im >>= 1;
			}
			if (j >= MAXBIT)
				throw("MAXBIT too small in sobseq");
			im = j *MAXDIM;
			for (k = 0;k<MIN(n,MAXDIM);k++)
			{
				ix[k] ^= iv[im+k];
				x.argvalue[k]=ix[k]*fac;
			}
		}
	}
}