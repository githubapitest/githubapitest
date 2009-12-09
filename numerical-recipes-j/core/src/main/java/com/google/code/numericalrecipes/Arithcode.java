package com.google.code.numericalrecipes;
public class Arithcode
{
	public Int nch = new Int();
	public Int nrad = new Int();
	public Int ncum = new Int();
	public Uint jdif = new Uint();
	public Uint nc = new Uint();
	public Uint minint = new Uint();
	public VecUint ilob = new VecUint();
	public VecUint iupb = new VecUint();
	public VecInt ncumfq = new VecInt();
	public static final Int NWK = 20;

	public Arithcode(RefObject<VecInt_I> nfreq, Int nnch, Int nnrad)
	{
		nch = nnch;
		nrad = nnrad;
		ilob = NWK;
		iupb = NWK;
		ncumfq = nch+2;
		Int j = new Int();
		if (nrad > 256)
			throw("output radix must be <= 256 in Arithcode");
		minint = numeric_limits<Uint>.max()/nrad;
		ncumfq[0]=0;
		for (j = 1;j<=nch;j++)
			ncumfq[j]=ncumfq[j-1]+MAX(nfreq.argvalue[j-1],1);
		ncum = ncumfq[nch+1]=ncumfq[nch]+1;
	}

	public final void messageinit()
	{
		Int j = new Int();
		jdif = nrad-1;
		for (j = NWK-1;j>=0;j--)
		{
			iupb[j]=nrad-1;
			ilob[j]=0;
			nc = j;
			if (jdif > minint)
				return;
			jdif = (jdif+1)*nrad-1;
		}
		throw("NWK too small in arcode.");
	}

	public final void codeone(Int ich, RefObject<String> code, RefObject<Int> lcd)
	{
		if (ich > nch)
			throw("bad ich in Arithcode");
		advance(ich, code.argvalue, lcd, 1);
	}

	public final Int decodeone(byte[] code, RefObject<Int> lcd)
	{
		Int ich = new Int();
		Uint j = new Uint();
		Uint ihi = new Uint();
		Uint ja = new Uint();
		Uint m = new Uint();
		ja = (Uchar) code[lcd.argvalue]-ilob[nc];
		for (j = nc+1;j<NWK;j++)
		{
			ja *= nrad;
			ja += Uchar(code[lcd.argvalue+j-nc])-ilob[j];
		}
		ihi = nch+1;
		ich = 0;
		while (ihi-ich > 1)
		{
			m = (ich+ihi)>>1;
			if (ja >= multdiv(jdif, ncumfq[m], ncum))
				ich = m;
			else
				ihi = m;
		}
		if (ich != nch)
			advance(ich, code, lcd, -1);
		return ich;
	}

	public final void advance(Int ich, byte[] code, RefObject<Int> lcd, Int isign)
	{
		Uint j = new Uint();
		Uint k = new Uint();
		Uint jh = new Uint();
		Uint jl = new Uint();
		jh = multdiv(jdif, ncumfq[ich+1], ncum);
		jl = multdiv(jdif, ncumfq[ich], ncum);
		jdif = jh-jl;
		RefObject<VecUint_I> tempRefObject = new RefObject<VecUint_I>(ilob);
		RefObject<VecUint_O> tempRefObject2 = new RefObject<VecUint_O>(iupb);
		arrsum(tempRefObject, tempRefObject2, jh, NWK, nrad, nc);
		ilob = tempRefObject.argvalue;
		iupb = tempRefObject2.argvalue;
		RefObject<VecUint_I> tempRefObject3 = new RefObject<VecUint_I>(ilob);
		RefObject<VecUint_O> tempRefObject4 = new RefObject<VecUint_O>(ilob);
		arrsum(tempRefObject3, tempRefObject4, jl, NWK, nrad, nc);
		ilob = tempRefObject3.argvalue;
		ilob = tempRefObject4.argvalue;
		for (j = nc;j<NWK;j++)
		{
			if (ich != nch && iupb[j] != ilob[j])
				break;
			if (isign > 0)
				code[lcd.argvalue] = ilob[j];
			lcd.argvalue++;
		}
		if (j+1 > NWK)
			return;
		nc = j;
		for(j = 0;jdif<minint;j++)
			jdif *= nrad;
		if (j > nc)
			throw("NWK too small in arcode.");
		if (j != 0)
		{
			for (k = nc;k<NWK;k++)
			{
				iupb[k-j]=iupb[k];
				ilob[k-j]=ilob[k];
			}
		}
		nc -= j;
		for (k = NWK-j;k<NWK;k++)
			iupb[k]=ilob[k]=0;
		return;
	}

	public final Uint multdiv(Uint j, Uint k, Uint m)
	{
		return Uint((Ullong(j)*Ullong(k)/Ullong(m)));
	}

	public final void arrsum(RefObject<VecUint_I> iin, RefObject<VecUint_O> iout, Uint ja, Int nwk, Uint nrad, Uint nc)
	{
		Uint karry = 0;
		Uint j = new Uint();
		Uint jtmp = new Uint();
		for (j = nwk-1;j>nc;j--)
		{
			jtmp = ja;
			ja /= nrad;
			iout.argvalue[j]=iin.argvalue[j]+(jtmp-ja *nrad)+karry;
			if (iout.argvalue[j] >= nrad)
			{
				iout.argvalue[j] -= nrad;
				karry = 1;
			}
			else
				karry = 0;
		}
		iout.argvalue[nc]=iin.argvalue[nc]+ja+karry;
	}
}