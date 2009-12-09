package com.google.code.numericalrecipes;
public class GlobalMembersFourier
{
	public static void four1(Doub[] data, Int n, Int isign)
	{
		Int nn = new Int();
		Int mmax = new Int();
		Int m = new Int();
		Int j = new Int();
		Int istep = new Int();
		Int i = new Int();
		Doub wtemp = new Doub();
		Doub wr = new Doub();
		Doub wpr = new Doub();
		Doub wpi = new Doub();
		Doub wi = new Doub();
		Doub theta = new Doub();
		Doub tempr = new Doub();
		Doub tempi = new Doub();
		if (n<2 || n&(n-1))
			throw("n must be power of 2 in four1");
		nn = n << 1;
		j = 1;
		for (i = 1;i<nn;i+=2)
		{
			if (j > i)
			{
				SWAP(data[j-1],data[i-1]);
				SWAP(data[j],data[i]);
			}
			m = n;
			while (m >= 2 && j > m)
			{
				j -= m;
				m >>= 1;
			}
			j += m;
		}
		mmax = 2;
		while (nn > mmax)
		{
			istep = mmax << 1;
			theta = isign*(6.28318530717959/mmax);
			wtemp = Math.sin(0.5 *theta);
			wpr = -2.0 *wtemp *wtemp;
			wpi = Math.sin(theta);
			wr = 1.0;
			wi = 0.0;
			for (m = 1;m<mmax;m+=2)
			{
				for (i = m;i<=nn;i+=istep)
				{
					j = i+mmax;
					tempr = wr *data[j-1]-wi *data[j];
					tempi = wr *data[j]+wi *data[j-1];
					data[j-1]=data[i-1]-tempr;
					data[j]=data[i]-tempi;
					data[i-1] += tempr;
					data[i] += tempi;
				}
				wr = (wtemp = wr)*wpr-wi *wpi+wr;
				wi = wi *wpr+wtemp *wpi+wi;
			}
			mmax = istep;
		}
	}
	public static void four1(RefObject<VecDoub_IO> data, Int isign)
	{
		four1(data.argvalue[0], data.argvalue.size()/2, isign);
	}

	public static void four1(RefObject<VecComplex_IO> data, Int isign)
	{
		four1((Doub)(data.argvalue[0]), data.argvalue.size(), isign);
	}
	public static void realft(RefObject<VecDoub_IO> data, Int isign)
	{
		Int i = new Int();
		Int i1 = new Int();
		Int i2 = new Int();
		Int i3 = new Int();
		Int i4 = new Int();
		Int n = data.argvalue.size();
		Doub c1 = 0.5;
		Doub c2 = new Doub();
		Doub h1r = new Doub();
		Doub h1i = new Doub();
		Doub h2r = new Doub();
		Doub h2i = new Doub();
		Doub wr = new Doub();
		Doub wi = new Doub();
		Doub wpr = new Doub();
		Doub wpi = new Doub();
		Doub wtemp = new Doub();
		Doub theta = 3.141592653589793238/Doub(n>>1);
		if (isign == 1)
		{
			c2 = -0.5;
		RefObject<VecComplex_IO> tempRefObject = new RefObject<VecComplex_IO>(data);
			four1(tempRefObject, 1);
			data.argvalue = tempRefObject.argvalue;
		}
		else
		{
			c2 = 0.5;
			theta = -theta;
		}
		wtemp = Math.sin(0.5 *theta);
		wpr = -2.0 *wtemp *wtemp;
		wpi = Math.sin(theta);
		wr = 1.0+wpr;
		wi = wpi;
		for (i = 1;i<(n>>2);i++)
		{
			i2 = 1+(i1 = i+i);
			i4 = 1+(i3 = n-i1);
			h1r = c1*(data.argvalue[i1]+data.argvalue[i3]);
			h1i = c1*(data.argvalue[i2]-data.argvalue[i4]);
			h2r = -c2*(data.argvalue[i2]+data.argvalue[i4]);
			h2i = c2*(data.argvalue[i1]-data.argvalue[i3]);
			data.argvalue[i1]=h1r+wr *h2r-wi *h2i;
			data.argvalue[i2]=h1i+wr *h2i+wi *h2r;
			data.argvalue[i3]=h1r-wr *h2r+wi *h2i;
			data.argvalue[i4]= -h1i+wr *h2i+wi *h2r;
			wr = (wtemp = wr)*wpr-wi *wpi+wr;
			wi = wi *wpr+wtemp *wpi+wi;
		}
		if (isign == 1)
		{
			data.argvalue[0] = (h1r = data.argvalue[0])+data.argvalue[1];
			data.argvalue[1] = h1r-data.argvalue[1];
		}
		else
		{
			data.argvalue[0]=c1*((h1r = data.argvalue[0])+data.argvalue[1]);
			data.argvalue[1]=c1*(h1r-data.argvalue[1]);
		RefObject<VecComplex_IO> tempRefObject2 = new RefObject<VecComplex_IO>(data);
			four1(tempRefObject2, -1);
			data.argvalue = tempRefObject2.argvalue;
		}
	}
	public static void sinft(RefObject<VecDoub_IO> y)
	{
		Int j = new Int();
		Int n = y.argvalue.size();
		Doub sum = new Doub();
		Doub y1 = new Doub();
		Doub y2 = new Doub();
		Doub theta = new Doub();
		Doub wi = 0.0;
		Doub wr = 1.0;
		Doub wpi = new Doub();
		Doub wpr = new Doub();
		Doub wtemp = new Doub();
		theta = 3.141592653589793238/Doub(n);
		wtemp = Math.sin(0.5 *theta);
		wpr = -2.0 *wtemp *wtemp;
		wpi = Math.sin(theta);
		y.argvalue[0]=0.0;
		for (j = 1;j<(n>>1)+1;j++)
		{
			wr = (wtemp = wr)*wpr-wi *wpi+wr;
			wi = wi *wpr+wtemp *wpi+wi;
			y1 = wi*(y.argvalue[j]+y.argvalue[n-j]);
			y2 = 0.5*(y.argvalue[j]-y.argvalue[n-j]);
			y.argvalue[j]=y1+y2;
			y.argvalue[n-j]=y1-y2;
		}
		realft(y, 1);
		y.argvalue[0]*=0.5;
		sum = y.argvalue[1]=0.0;
		for (j = 0;j<n-1;j+=2)
		{
			sum += y.argvalue[j];
			y.argvalue[j]=y.argvalue[j+1];
			y.argvalue[j+1]=sum;
		}
	}
	public static void cosft1(RefObject<VecDoub_IO> y)
	{
		final Doub PI = 3.141592653589793238;
		Int j = new Int();
		Int n = y.argvalue.size()-1;
		Doub sum = new Doub();
		Doub y1 = new Doub();
		Doub y2 = new Doub();
		Doub theta = new Doub();
		Doub wi = 0.0;
		Doub wpi = new Doub();
		Doub wpr = new Doub();
		Doub wr = 1.0;
		Doub wtemp = new Doub();
		VecDoub yy = new VecDoub(n);
		theta = PI/n;
		wtemp = Math.sin(0.5 *theta);
		wpr = -2.0 *wtemp *wtemp;
		wpi = Math.sin(theta);
		sum = 0.5*(y.argvalue[0]-y.argvalue[n]);
		yy[0]=0.5*(y.argvalue[0]+y.argvalue[n]);
		for (j = 1;j<n/2;j++)
		{
			wr = (wtemp = wr)*wpr-wi *wpi+wr;
			wi = wi *wpr+wtemp *wpi+wi;
			y1 = 0.5*(y.argvalue[j]+y.argvalue[n-j]);
			y2 = (y.argvalue[j]-y.argvalue[n-j]);
			yy[j]=y1-wi *y2;
			yy[n-j]=y1+wi *y2;
			sum += wr *y2;
		}
		yy[n/2]=y.argvalue[n/2];
	RefObject<VecDoub_IO> tempRefObject = new RefObject<VecDoub_IO>(yy);
		realft(tempRefObject, 1);
		yy = tempRefObject.argvalue;
		for (j = 0;j<n;j++)
			y.argvalue[j]=yy[j];
		y.argvalue[n]=y.argvalue[1];
		y.argvalue[1]=sum;
		for (j = 3;j<n;j+=2)
		{
			sum += y.argvalue[j];
			y.argvalue[j]=sum;
		}
	}
	public static void cosft2(RefObject<VecDoub_IO> y, Int isign)
	{
		final Doub PI = 3.141592653589793238;
		Int i = new Int();
		Int n = y.argvalue.size();
		Doub sum = new Doub();
		Doub sum1 = new Doub();
		Doub y1 = new Doub();
		Doub y2 = new Doub();
		Doub ytemp = new Doub();
		Doub theta = new Doub();
		Doub wi = 0.0;
		Doub wi1 = new Doub();
		Doub wpi = new Doub();
		Doub wpr = new Doub();
		Doub wr = 1.0;
		Doub wr1 = new Doub();
		Doub wtemp = new Doub();
		theta = 0.5 *PI/n;
		wr1 = Math.cos(theta);
		wi1 = Math.sin(theta);
		wpr = -2.0 *wi1 *wi1;
		wpi = Math.sin(2.0 *theta);
		if (isign == 1)
		{
			for (i = 0;i<n/2;i++)
			{
				y1 = 0.5*(y.argvalue[i]+y.argvalue[n-1-i]);
				y2 = wi1*(y.argvalue[i]-y.argvalue[n-1-i]);
				y.argvalue[i]=y1+y2;
				y.argvalue[n-1-i]=y1-y2;
				wr1 = (wtemp = wr1)*wpr-wi1 *wpi+wr1;
				wi1 = wi1 *wpr+wtemp *wpi+wi1;
			}
			realft(y, 1);
			for (i = 2;i<n;i+=2)
			{
				wr = (wtemp = wr)*wpr-wi *wpi+wr;
				wi = wi *wpr+wtemp *wpi+wi;
				y1 = y.argvalue[i]*wr-y.argvalue[i+1]*wi;
				y2 = y.argvalue[i+1]*wr+y.argvalue[i]*wi;
				y.argvalue[i]=y1;
				y.argvalue[i+1]=y2;
			}
			sum = 0.5 *y.argvalue[1];
			for (i = n-1;i>0;i-=2)
			{
				sum1 = sum;
				sum += y.argvalue[i];
				y.argvalue[i]=sum1;
			}
		}
		else if (isign == -1)
		{
			ytemp = y.argvalue[n-1];
			for (i = n-1;i>2;i-=2)
				y.argvalue[i]=y.argvalue[i-2]-y.argvalue[i];
			y.argvalue[1]=2.0 *ytemp;
			for (i = 2;i<n;i+=2)
			{
				wr = (wtemp = wr)*wpr-wi *wpi+wr;
				wi = wi *wpr+wtemp *wpi+wi;
				y1 = y.argvalue[i]*wr+y.argvalue[i+1]*wi;
				y2 = y.argvalue[i+1]*wr-y.argvalue[i]*wi;
				y.argvalue[i]=y1;
				y.argvalue[i+1]=y2;
			}
			realft(y, -1);
			for (i = 0;i<n/2;i++)
			{
				y1 = y.argvalue[i]+y.argvalue[n-1-i];
				y2 = (0.5/wi1)*(y.argvalue[i]-y.argvalue[n-1-i]);
				y.argvalue[i]=0.5*(y1+y2);
				y.argvalue[n-1-i]=0.5*(y1-y2);
				wr1 = (wtemp = wr1)*wpr-wi1 *wpi+wr1;
				wi1 = wi1 *wpr+wtemp *wpi+wi1;
			}
		}
	}
}