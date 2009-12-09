package com.google.code.numericalrecipes;
public class GlobalMembersWavelet
{
	public static void wt1(RefObject<VecDoub_IO> a, Int isign, RefObject<Wavelet> wlet)
	{
		Int nn = new Int();
		Int n = a.argvalue.size();
		if (n < 4)
			return;
		if (isign >= 0)
		{
			wlet.argvalue.condition(a, n, 1);
			for (nn = n;nn>=4;nn>>=1)
				wlet.argvalue.filt(a, nn, isign);
		}
		else
		{
			for (nn = 4;nn<=n;nn<<=1)
				wlet.argvalue.filt(a, nn, isign);
			wlet.argvalue.condition(a, n, -1);
		}
	}
	public static void wtn(RefObject<VecDoub_IO> a, RefObject<VecInt_I> nn, Int isign, RefObject<Wavelet> wlet)
	{
		Int idim = new Int();
		Int i1 = new Int();
		Int i2 = new Int();
		Int i3 = new Int();
		Int k = new Int();
		Int n = new Int();
		Int nnew = new Int();
		Int nprev = 1;
		Int nt = new Int();
		Int ntot = 1;
		Int ndim = nn.argvalue.size();
		for (idim = 0;idim<ndim;idim++)
			ntot *= nn.argvalue[idim];
		if (ntot&(ntot-1))
			throw("all lengths must be powers of 2 in wtn");
		for (idim = 0;idim<ndim;idim++)
		{
			n = nn.argvalue[idim];
			VecDoub wksp = new VecDoub(n);
			nnew = n *nprev;
			if (n > 4)
			{
				for (i2 = 0;i2<ntot;i2+=nnew)
				{
					for (i1 = 0;i1<nprev;i1++)
					{
						for (i3 = i1+i2,k = 0;k<n;k++,i3+=nprev)
							wksp[k]=a.argvalue[i3];
						if (isign >= 0)
						{
						RefObject<VecDoub_IO> tempRefObject = new RefObject<VecDoub_IO>(wksp);
							wlet.argvalue.condition(tempRefObject, n, 1);
							wksp = tempRefObject.argvalue;
							for(nt = n;nt>=4;nt >>= 1)
						{
							RefObject<VecDoub_IO> tempRefObject2 = new RefObject<VecDoub_IO>(wksp);
								wlet.argvalue.filt(tempRefObject2, nt, isign);
								wksp = tempRefObject2.argvalue;
							}
						}
						else
						{
							for(nt = 4;nt<=n;nt <<= 1)
						{
							RefObject<VecDoub_IO> tempRefObject3 = new RefObject<VecDoub_IO>(wksp);
								wlet.argvalue.filt(tempRefObject3, nt, isign);
								wksp = tempRefObject3.argvalue;
							}
						RefObject<VecDoub_IO> tempRefObject4 = new RefObject<VecDoub_IO>(wksp);
							wlet.argvalue.condition(tempRefObject4, n, -1);
							wksp = tempRefObject4.argvalue;
						}
						for (i3 = i1+i2,k = 0;k<n;k++,i3+=nprev)
							a.argvalue[i3]=wksp[k];
					}
				}
			}
			nprev = nnew;
		}
	}
}