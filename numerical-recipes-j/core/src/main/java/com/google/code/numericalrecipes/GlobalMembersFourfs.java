package com.google.code.numericalrecipes;
public class GlobalMembersFourfs
{
	public static void fourew(RefObject<NRvector<fstream>> file, RefObject<Int> na, RefObject<Int> nb, RefObject<Int> nc, RefObject<Int> nd)
	{
		Int i = new Int();
		for (i = 0;i<4;i++)
			file.argvalue[i].seekp(0);
		for (i = 0;i<4;i++)
			file.argvalue[i].seekg(0);
		SWAP(file.argvalue[1],file.argvalue[3]);
		SWAP(file.argvalue[0],file.argvalue[2]);
		na.argvalue = 2;
		nb.argvalue = 3;
		nc.argvalue = 0;
		nd.argvalue = 1;
	}
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Int[] mate = {1,0,3,2};
	public static void fourfs(RefObject<NRvector<fstream>> file, RefObject<VecInt_I> nn, Int isign)
	{
		final Int KBF = 128;
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Int mate[4]={1,0,3,2};
		Int cc = new Int();
		Int cc0 = new Int();
		Int j = new Int();
		Int j12 = new Int();
		Int jk = new Int();
		Int k = new Int();
		Int kk = new Int();
		Int n = 1;
		Int mm = new Int();
		Int kc = 0;
		Int kd = new Int();
		Int ks = new Int();
		Int kr = new Int();
		Int na = new Int();
		Int nb = new Int();
		Int nc = new Int();
		Int nd = new Int();
		Int nr = new Int();
		Int ns = new Int();
		Int nv = new Int();
		Doub tempr = new Doub();
		Doub tempi = new Doub();
		Doub wr = new Doub();
		Doub wi = new Doub();
		Doub wpr = new Doub();
		Doub wpi = new Doub();
		Doub wtemp = new Doub();
		Doub theta = new Doub();
		VecDoub afa = new VecDoub(KBF);
		VecDoub afb = new VecDoub(KBF);
		VecDoub afc = new VecDoub(KBF);
		Int ndim = nn.argvalue.size();
		for (j = 0;j<ndim;j++)
		{
			n *= nn.argvalue[j];
			if (nn.argvalue[j] <= 1)
				throw("invalid Doub or wrong ndim in fourfs");
		}
		nv = 0;
		jk = nn.argvalue[nv];
		mm = n;
		ns = n/KBF;
		nr = ns >> 1;
		kd = KBF >> 1;
		ks = n;
	RefObject<Int> tempRefObject = new RefObject<Int>(na);
	RefObject<Int> tempRefObject2 = new RefObject<Int>(nb);
	RefObject<Int> tempRefObject3 = new RefObject<Int>(nc);
	RefObject<Int> tempRefObject4 = new RefObject<Int>(nd);
		fourew(file, tempRefObject, tempRefObject2, tempRefObject3, tempRefObject4);
		na = tempRefObject.argvalue;
		nb = tempRefObject2.argvalue;
		nc = tempRefObject3.argvalue;
		nd = tempRefObject4.argvalue;
		for (;;)
		{
			theta = isign *3.141592653589793/(n/mm);
			wtemp = Math.sin(0.5 *theta);
			wpr = -2.0 *wtemp *wtemp;
			wpi = Math.sin(theta);
			wr = 1.0;
			wi = 0.0;
			mm >>= 1;
			for (j12 = 0;j12<2;j12++)
			{
				kr = 0;
				do
				{
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					cc0 = file.argvalue[na].tellg()/sizeof(Doub);
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					file.argvalue[na].read((String) afa[0], KBF *sizeof(Doub));
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					cc = file.argvalue[na].tellg()/sizeof(Doub);
					if ((cc-cc0) != KBF)
						throw("read error 1 in fourfs");
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					cc0 = file.argvalue[nb].tellg()/sizeof(Doub);
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					file.argvalue[nb].read((String) afb[0], KBF *sizeof(Doub));
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					cc = file.argvalue[nb].tellg()/sizeof(Doub);
					if ((cc-cc0) != KBF)
						throw("read error 2 in fourfs");
					for (j = 0;j<KBF;j+=2)
					{
						tempr = wr *afb[j]-wi *afb[j+1];
						tempi = wi *afb[j]+wr *afb[j+1];
						afb[j]=afa[j]-tempr;
						afa[j] += tempr;
						afb[j+1]=afa[j+1]-tempi;
						afa[j+1] += tempi;
					}
					kc += kd;
					if (kc == mm)
					{
						kc = 0;
						wr = (wtemp = wr)*wpr-wi *wpi+wr;
						wi = wi *wpr+wtemp *wpi+wi;
					}
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					cc0 = file.argvalue[nc].tellp()/sizeof(Doub);
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					file.argvalue[nc].write((String) afa[0], KBF *sizeof(Doub));
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					cc = file.argvalue[nc].tellp()/sizeof(Doub);
					if ((cc-cc0) != KBF)
						throw("write error 1 in fourfs");
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					cc0 = file.argvalue[nd].tellp()/sizeof(Doub);
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					file.argvalue[nd].write((String) afb[0], KBF *sizeof(Doub));
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					cc = file.argvalue[nd].tellp()/sizeof(Doub);
					if ((cc-cc0) != KBF)
						throw("write error 2 in fourfs");
				} while (++kr < nr);
				if (j12 == 0 && ks != n && ks == KBF)
				{
					na = mate[na];
					nb = na;
				}
				if (nr == 0)
					break;
			}
		RefObject<Int> tempRefObject5 = new RefObject<Int>(na);
		RefObject<Int> tempRefObject6 = new RefObject<Int>(nb);
		RefObject<Int> tempRefObject7 = new RefObject<Int>(nc);
		RefObject<Int> tempRefObject8 = new RefObject<Int>(nd);
			fourew(file, tempRefObject5, tempRefObject6, tempRefObject7, tempRefObject8);
			na = tempRefObject5.argvalue;
			nb = tempRefObject6.argvalue;
			nc = tempRefObject7.argvalue;
			nd = tempRefObject8.argvalue;
			jk >>= 1;
			while (jk == 1)
			{
				mm = n;
				jk = nn.argvalue[++nv];
			}
			ks >>= 1;
			if (ks > KBF)
			{
				for (j12 = 0;j12<2;j12++)
				{
					for (kr = 0;kr<ns;kr+=ks/KBF)
					{
						for (k = 0;k<ks;k+=KBF)
						{
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
							cc0 = file.argvalue[na].tellg()/sizeof(Doub);
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
							file.argvalue[na].read((String) afa[0], KBF *sizeof(Doub));
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
							cc = file.argvalue[na].tellg()/sizeof(Doub);
							if ((cc-cc0) != KBF)
								throw("read error 3 in fourfs");
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
							cc0 = file.argvalue[nc].tellp()/sizeof(Doub);
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
							file.argvalue[nc].write((String) afa[0], KBF *sizeof(Doub));
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
							cc = file.argvalue[nc].tellp()/sizeof(Doub);
							if ((cc-cc0) != KBF)
								throw("write error 3 in fourfs");
						}
						nc = mate[nc];
					}
					na = mate[na];
				}
			RefObject<Int> tempRefObject9 = new RefObject<Int>(na);
			RefObject<Int> tempRefObject10 = new RefObject<Int>(nb);
			RefObject<Int> tempRefObject11 = new RefObject<Int>(nc);
			RefObject<Int> tempRefObject12 = new RefObject<Int>(nd);
				fourew(file, tempRefObject9, tempRefObject10, tempRefObject11, tempRefObject12);
				na = tempRefObject9.argvalue;
				nb = tempRefObject10.argvalue;
				nc = tempRefObject11.argvalue;
				nd = tempRefObject12.argvalue;
			}
			else if (ks == KBF)
				nb = na;
			else
				break;
		}
		j = 0;
		for (;;)
		{
			theta = isign *3.141592653589793/(n/mm);
			wtemp = Math.sin(0.5 *theta);
			wpr = -2.0 *wtemp *wtemp;
			wpi = Math.sin(theta);
			wr = 1.0;
			wi = 0.0;
			mm >>= 1;
			ks = kd;
			kd >>= 1;
			for (j12 = 0;j12<2;j12++)
			{
				for (kr = 0;kr<ns;kr++)
				{
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					cc0 = file.argvalue[na].tellg()/sizeof(Doub);
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					file.argvalue[na].read((String) afc[0], KBF *sizeof(Doub));
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
					cc = file.argvalue[na].tellg()/sizeof(Doub);
					if ((cc-cc0) != KBF)
						throw("read error 4 in fourfs");
					kk = 0;
					k = ks;
					for (;;)
					{
						tempr = wr *afc[kk+ks]-wi *afc[kk+ks+1];
						tempi = wi *afc[kk+ks]+wr *afc[kk+ks+1];
						afa[j]=afc[kk]+tempr;
						afb[j]=afc[kk]-tempr;
						afa[++j]=afc[++kk]+tempi;
						afb[j++]=afc[kk++]-tempi;
						if (kk < k)
							continue;
						kc += kd;
						if (kc == mm)
						{
							kc = 0;
							wr = (wtemp = wr)*wpr-wi *wpi+wr;
							wi = wi *wpr+wtemp *wpi+wi;
						}
						kk += ks;
						if (kk > KBF-1)
							break;
						else
							k = kk+ks;
					}
					if (j > KBF-1)
					{
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
						cc0 = file.argvalue[nc].tellp()/sizeof(Doub);
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
						file.argvalue[nc].write((String) afa[0], KBF *sizeof(Doub));
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
						cc = file.argvalue[nc].tellp()/sizeof(Doub);
						if ((cc-cc0) != KBF)
							throw("write error 4 in fourfs");
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
						cc0 = file.argvalue[nd].tellp()/sizeof(Doub);
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
						file.argvalue[nd].write((String) afb[0], KBF *sizeof(Doub));
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
						cc = file.argvalue[nd].tellp()/sizeof(Doub);
						if ((cc-cc0) != KBF)
							throw("write error 5 in fourfs");
						j = 0;
					}
				}
				na = mate[na];
			}
		RefObject<Int> tempRefObject13 = new RefObject<Int>(na);
		RefObject<Int> tempRefObject14 = new RefObject<Int>(nb);
		RefObject<Int> tempRefObject15 = new RefObject<Int>(nc);
		RefObject<Int> tempRefObject16 = new RefObject<Int>(nd);
			fourew(file, tempRefObject13, tempRefObject14, tempRefObject15, tempRefObject16);
			na = tempRefObject13.argvalue;
			nb = tempRefObject14.argvalue;
			nc = tempRefObject15.argvalue;
			nd = tempRefObject16.argvalue;
			jk >>= 1;
			if (jk > 1)
				continue;
			mm = n;
			do
			{
				if (nv < ndim-1)
					jk = nn.argvalue[++nv];
				else
					return;
			} while (jk == 1);
		}
	}
}