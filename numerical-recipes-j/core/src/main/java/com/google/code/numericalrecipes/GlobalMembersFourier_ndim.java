package com.google.code.numericalrecipes;
public class GlobalMembersFourier_ndim
{
	public static void fourn(Doub[] data, RefObject<VecInt_I> nn, Int isign)
	{
		Int idim = new Int();
		Int i1 = new Int();
		Int i2 = new Int();
		Int i3 = new Int();
		Int i2rev = new Int();
		Int i3rev = new Int();
		Int ip1 = new Int();
		Int ip2 = new Int();
		Int ip3 = new Int();
		Int ifp1 = new Int();
		Int ifp2 = new Int();
		Int ibit = new Int();
		Int k1 = new Int();
		Int k2 = new Int();
		Int n = new Int();
		Int nprev = new Int();
		Int nrem = new Int();
		Int ntot = 1;
		Int ndim = nn.argvalue.size();
		Doub tempi = new Doub();
		Doub tempr = new Doub();
		Doub theta = new Doub();
		Doub wi = new Doub();
		Doub wpi = new Doub();
		Doub wpr = new Doub();
		Doub wr = new Doub();
		Doub wtemp = new Doub();
		for (idim = 0;idim<ndim;idim++)
			ntot *= nn.argvalue[idim];
		if (ntot<2 || ntot&(ntot-1))
			throw("must have powers of 2 in fourn");
		nprev = 1;
		for (idim = ndim-1;idim>=0;idim--)
		{
			n = nn.argvalue[idim];
			nrem = ntot/(n *nprev);
			ip1 = nprev << 1;
			ip2 = ip1 *n;
			ip3 = ip2 *nrem;
			i2rev = 0;
			for (i2 = 0;i2<ip2;i2+=ip1)
			{
				if (i2 < i2rev)
				{
					for (i1 = i2;i1<i2+ip1-1;i1+=2)
					{
						for (i3 = i1;i3<ip3;i3+=ip2)
						{
							i3rev = i2rev+i3-i2;
							SWAP(data[i3],data[i3rev]);
							SWAP(data[i3+1],data[i3rev+1]);
						}
					}
				}
				ibit = ip2 >> 1;
				while (ibit >= ip1 && i2rev+1 > ibit)
				{
					i2rev -= ibit;
					ibit >>= 1;
				}
				i2rev += ibit;
			}
			ifp1 = ip1;
			while (ifp1 < ip2)
			{
				ifp2 = ifp1 << 1;
				theta = isign *6.28318530717959/(ifp2/ip1);
				wtemp = Math.sin(0.5 *theta);
				wpr = -2.0 *wtemp *wtemp;
				wpi = Math.sin(theta);
				wr = 1.0;
				wi = 0.0;
				for (i3 = 0;i3<ifp1;i3+=ip1)
				{
					for (i1 = i3;i1<i3+ip1-1;i1+=2)
					{
						for (i2 = i1;i2<ip3;i2+=ifp2)
						{
							k1 = i2;
							k2 = k1+ifp1;
							tempr = wr *data[k2]-wi *data[k2+1];
							tempi = wr *data[k2+1]+wi *data[k2];
							data[k2]=data[k1]-tempr;
							data[k2+1]=data[k1+1]-tempi;
							data[k1] += tempr;
							data[k1+1] += tempi;
						}
					}
					wr = (wtemp = wr)*wpr-wi *wpi+wr;
					wi = wi *wpr+wtemp *wpi+wi;
				}
				ifp1 = ifp2;
			}
			nprev *= n;
		}
	}

	public static void fourn(RefObject<VecDoub_IO> data, RefObject<VecInt_I> nn, Int isign)
	{
	RefObject<VecInt_I> tempRefObject = new RefObject<VecInt_I>(nn);
		fourn(data.argvalue[0], tempRefObject, isign);
		nn.argvalue = tempRefObject.argvalue;
	}
	public static void rlft3(Doub[] data, RefObject<Doub> speq, Int isign, Int nn1, Int nn2, Int nn3)
	{
		Int i1 = new Int();
		Int i2 = new Int();
		Int i3 = new Int();
		Int j1 = new Int();
		Int j2 = new Int();
		Int j3 = new Int();
		Int k1 = new Int();
		Int k2 = new Int();
		Int k3 = new Int();
		Int k4 = new Int();
		Doub theta = new Doub();
		Doub wi = new Doub();
		Doub wpi = new Doub();
		Doub wpr = new Doub();
		Doub wr = new Doub();
		Doub wtemp = new Doub();
		Doub c1 = new Doub();
		Doub c2 = new Doub();
		Doub h1r = new Doub();
		Doub h1i = new Doub();
		Doub h2r = new Doub();
		Doub h2i = new Doub();
		VecInt nn = new VecInt(3);
		VecDoubp spq = new VecDoubp(nn1);
		for (i1 = 0;i1<nn1;i1++)
			spq[i1] = speq.argvalue + 2 *nn2 *i1;
		c1 = 0.5;
		c2 = -0.5 *isign;
		theta = isign*(6.28318530717959/nn3);
		wtemp = Math.sin(0.5 *theta);
		wpr = -2.0 *wtemp *wtemp;
		wpi = Math.sin(theta);
		nn[0] = nn1;
		nn[1] = nn2;
		nn[2] = nn3 >> 1;
		if (isign == 1)
		{
		RefObject<VecInt_I> tempRefObject = new RefObject<VecInt_I>(nn);
		RefObject<VecDoub_IO> tempRefObject2 = new RefObject<VecDoub_IO>(data);
			fourn(tempRefObject2, tempRefObject, isign);
			nn = tempRefObject.argvalue;
			data = tempRefObject2.argvalue;
			k1 = 0;
			for (i1 = 0;i1<nn1;i1++)
				for (i2 = 0,j2 = 0;i2<nn2;i2++,k1+=nn3)
				{
					spq[i1][j2++]=data[k1];
					spq[i1][j2++]=data[k1+1];
				}
		}
		for (i1 = 0;i1<nn1;i1++)
		{
			j1 = (i1 != 0 ? nn1-i1 : 0);
			wr = 1.0;
			wi = 0.0;
			for (i3 = 0;i3<=(nn3>>1);i3+=2)
			{
				k1 = i1 *nn2 *nn3;
				k3 = j1 *nn2 *nn3;
				for (i2 = 0;i2<nn2;i2++,k1+=nn3)
				{
					if (i3 == 0)
					{
						j2 = (i2 != 0 ? ((nn2-i2)<<1) : 0);
						h1r = c1*(data[k1]+spq[j1][j2]);
						h1i = c1*(data[k1+1]-spq[j1][j2+1]);
						h2i = c2*(data[k1]-spq[j1][j2]);
						h2r = -c2*(data[k1+1]+spq[j1][j2+1]);
						data[k1]=h1r+h2r;
						data[k1+1]=h1i+h2i;
						spq[j1][j2]=h1r-h2r;
						spq[j1][j2+1]=h2i-h1i;
					}
					else
					{
						j2 = (i2 != 0 ? nn2-i2 : 0);
						j3 = nn3-i3;
						k2 = k1+i3;
						k4 = k3+j2 *nn3+j3;
						h1r = c1*(data[k2]+data[k4]);
						h1i = c1*(data[k2+1]-data[k4+1]);
						h2i = c2*(data[k2]-data[k4]);
						h2r = -c2*(data[k2+1]+data[k4+1]);
						data[k2]=h1r+wr *h2r-wi *h2i;
						data[k2+1]=h1i+wr *h2i+wi *h2r;
						data[k4]=h1r-wr *h2r+wi *h2i;
						data[k4+1]= -h1i+wr *h2i+wi *h2r;
					}
				}
				wr = (wtemp = wr)*wpr-wi *wpi+wr;
				wi = wi *wpr+wtemp *wpi+wi;
			}
		}
		if (isign == -1)
	{
		RefObject<VecInt_I> tempRefObject3 = new RefObject<VecInt_I>(nn);
		RefObject<VecDoub_IO> tempRefObject4 = new RefObject<VecDoub_IO>(data);
			fourn(tempRefObject4, tempRefObject3, isign);
			nn = tempRefObject3.argvalue;
			data = tempRefObject4.argvalue;
		}
	}

	public static void rlft3(RefObject<Mat3DDoub_IO> data, RefObject<MatDoub_IO> speq, Int isign)
	{
		if (speq.argvalue.nrows() != data.argvalue.dim1() || speq.argvalue.ncols() != 2 *data.argvalue.dim2())
			throw("bad dims in rlft3");
		rlft3(data.argvalue[0][0][0], speq.argvalue[0][0], isign, data.argvalue.dim1(), data.argvalue.dim2(), data.argvalue.dim3());
	}

	public static void rlft3(RefObject<MatDoub_IO> data, RefObject<VecDoub_IO> speq, Int isign)
	{
		if (speq.argvalue.size() != 2 *data.argvalue.nrows())
			throw("bad dims in rlft3");
		rlft3(data.argvalue[0][0], speq.argvalue[0], isign, 1, data.argvalue.nrows(), data.argvalue.ncols());
	}
}