package com.google.code.numericalrecipes;
public class Huffcode
{
	public Int nch = new Int();
	public Int nodemax = new Int();
	public Int mq = new Int();
	public Int ilong = new Int();
	public Int nlong = new Int();
	public VecInt ncod = new VecInt();
	public VecInt left = new VecInt();
	public VecInt right = new VecInt();
	public VecUint icod = new VecUint();
	public Uint[] setbit = new Uint[32];

	public Huffcode(Int nnch, RefObject<VecInt_I> nfreq)
	{
		nch = nnch;
		mq = 2 *nch-1;
		icod = mq;
		ncod = mq;
		left = mq;
		right = mq;
		Int ibit = new Int();
		Int j = new Int();
		Int node = new Int();
		Int k = new Int();
		Int n = new Int();
		Int nused = new Int();
		VecInt index = new VecInt(mq);
		VecInt nprob = new VecInt(mq);
		VecInt up = new VecInt(mq);
		for (j = 0;j<32;j++)
			setbit[j] = 1 << j;
		for (nused = 0,j = 0;j<nch;j++)
		{
			nprob[j]=nfreq.argvalue[j];
			icod[j]=ncod[j]=0;
			if (nfreq.argvalue[j] != 0)
				index[nused++]=j;
		}
		for (j = nused-1;j>=0;j--)
		{
			RefObject<VecInt_IO> tempRefObject = new RefObject<VecInt_IO>(index);
			RefObject<VecInt_IO> tempRefObject2 = new RefObject<VecInt_IO>(nprob);
			heep(tempRefObject, tempRefObject2, nused, j);
			index = tempRefObject.argvalue;
			nprob = tempRefObject2.argvalue;
		}
		k = nch;
		while (nused > 1)
		{
			node = index[0];
			index[0]=index[(nused--)-1];
			RefObject<VecInt_IO> tempRefObject3 = new RefObject<VecInt_IO>(index);
			RefObject<VecInt_IO> tempRefObject4 = new RefObject<VecInt_IO>(nprob);
			heep(tempRefObject3, tempRefObject4, nused, 0);
			index = tempRefObject3.argvalue;
			nprob = tempRefObject4.argvalue;
			nprob[k]=nprob[index[0]]+nprob[node];
			left[k]=node;
			right[k++]=index[0];
			up[index[0]] = -(Int)k;
			index[0]=k-1;
			up[node]=k;
			RefObject<VecInt_IO> tempRefObject5 = new RefObject<VecInt_IO>(index);
			RefObject<VecInt_IO> tempRefObject6 = new RefObject<VecInt_IO>(nprob);
			heep(tempRefObject5, tempRefObject6, nused, 0);
			index = tempRefObject5.argvalue;
			nprob = tempRefObject6.argvalue;
		}
		up[(nodemax = k)-1]=0;
		for (j = 0;j<nch;j++)
		{
			if (nprob[j] != 0)
			{
				for (n = 0,ibit = 0,node = up[j];node;node = up[node-1],ibit++)
				{
					if (node < 0)
					{
						n |= setbit[ibit];
						node = -node;
					}
				}
				icod[j]=n;
				ncod[j]=ibit;
			}
		}
		nlong = 0;
		for (j = 0;j<nch;j++)
		{
			if (ncod[j] > nlong)
			{
				nlong = ncod[j];
				ilong = j;
			}
		}
		if (nlong > numeric_limits<Uint>.digits)
			throw("Code too long in Huffcode.  See text.");
	}

	public final void codeone(Int ich, byte[] code, RefObject<Int> nb)
	{
		Int m = new Int();
		Int n = new Int();
		Int nc = new Int();
		if (ich >= nch)
			throw("bad ich (out of range) in Huffcode");
		if (ncod[ich]==0)
			throw("bad ich (zero prob) in Huffcode");
		for (n = ncod[ich]-1;n >= 0;n--,++nb.argvalue)
		{
			nc = nb.argvalue >> 3;
			m = nb.argvalue & 7;
			if (m == 0)
				code[nc]=0;
			if ((icod[ich] & setbit[n]) != 0)
				code[nc] |= setbit[m];
		}
	}

	public final Int decodeone(byte[] code, RefObject<Int> nb)
	{
		Int nc = new Int();
		Int node = nodemax-1;
		for (;;)
		{
			nc = nb.argvalue >> 3;
			node = ((code[nc] & setbit[7 & nb.argvalue++]) != 0 ? right[node] : left[node]);
			if (node < nch)
				return node;
		}
	}

	public final void heep(RefObject<VecInt_IO> index, RefObject<VecInt_IO> nprob, Int n, Int m)
	{
		Int i = m;
		Int j = new Int();
		Int k = new Int();
		k = index.argvalue[i];
		while (i < (n >> 1))
		{
			if ((j = 2 *i+1) < n-1 && nprob.argvalue[index.argvalue[j]] > nprob.argvalue[index.argvalue[j+1]])
				j++;
			if (nprob.argvalue[k] <= nprob.argvalue[index.argvalue[j]])
				break;
			index.argvalue[i]=index.argvalue[j];
			i = j;
		}
		index.argvalue[i]=k;
	}
}