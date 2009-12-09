package com.google.code.numericalrecipes;
public class Anneal
{
	public Ranq1 myran = new Ranq1();
	public Anneal()
	{
		myran = 1234;
	}
	public final void order(RefObject<VecDoub_I> x, RefObject<VecDoub_I> y, RefObject<VecInt_IO> iorder)
	{
		final Doub TFACTR = 0.9;
		Bool ans = new Bool();
		Int i = new Int();
		Int i1 = new Int();
		Int i2 = new Int();
		Int nn = new Int();
		VecInt n = new VecInt(6);
		Doub de = new Doub();
		Doub path = 0.0;
		Doub t = 0.5;
		Int ncity = x.argvalue.size();
		Int nover = 100 *ncity;
		Int nlimit = 10 *ncity;
		for (i = 0;i<ncity-1;i++)
		{
			i1 = iorder.argvalue[i];
			i2 = iorder.argvalue[i+1];
			path += alen(x.argvalue[i1], x.argvalue[i2], y.argvalue[i1], y.argvalue[i2]);
		}
		i1 = iorder.argvalue[ncity-1];
		i2 = iorder.argvalue[0];
		path += alen(x.argvalue[i1], x.argvalue[i2], y.argvalue[i1], y.argvalue[i2]);
		for (Int j = 0;j<100;j++)
		{
			Int nsucc = 0;
			for (Int k = 0;k<nover;k++)
			{
				do
				{
					n[0]=(Int)(ncity *myran.doub());
					n[1]=(Int)((ncity-1)*myran.doub());
					if (n[1] >= n[0])
						++n[1];
					nn = (n[0]-n[1]+ncity-1) % ncity;
				} while (nn<2);
				if (myran.doub() < 0.5)
				{
					n[2]=n[1]+(Int)(Math.abs(nn-1)*myran.doub())+1;
					n[2] %= ncity;
					RefObject<VecInt_IO> tempRefObject = new RefObject<VecInt_IO>(n);
					de = trncst(x, y, iorder, tempRefObject);
					n = tempRefObject.argvalue;
					ans = metrop(de, t);
					if (ans != null)
					{
						++nsucc;
						path += de;
						RefObject<VecInt_I> tempRefObject2 = new RefObject<VecInt_I>(n);
						trnspt(iorder, tempRefObject2);
						n = tempRefObject2.argvalue;
					}
				}
				else
				{
					RefObject<VecInt_IO> tempRefObject3 = new RefObject<VecInt_IO>(n);
					de = revcst(x, y, iorder, tempRefObject3);
					n = tempRefObject3.argvalue;
					ans = metrop(de, t);
					if (ans != null)
					{
						++nsucc;
						path += de;
						RefObject<VecInt_I> tempRefObject4 = new RefObject<VecInt_I>(n);
						reverse(iorder, tempRefObject4);
						n = tempRefObject4.argvalue;
					}
				}
				if (nsucc >= nlimit)
					break;
			}
			System.out.printf("%.6f", "\n");
			System.out.printf("%.6f", "T = ");
			System.out.printf("%12.6f", t);
			System.out.printf("%12.6f", "	 Path Length = ");
			System.out.printf("%12.6f", path);
			System.out.printf("%12.6f", "\n");
			System.out.printf("%12.6f", "Successful Moves: ");
			System.out.printf("%12.6f", nsucc);
			System.out.printf("%12.6f", "\n");
			t *= TFACTR;
			if (nsucc == 0)
				return;
		}
	}

	public final Doub revcst(RefObject<VecDoub_I> x, RefObject<VecDoub_I> y, RefObject<VecInt_I> iorder, RefObject<VecInt_IO> n)
	{
		VecDoub xx = new VecDoub(4);
		VecDoub yy = new VecDoub(4);
		Int ncity = x.argvalue.size();
		n.argvalue[2]=(n.argvalue[0]+ncity-1) % ncity;
		n.argvalue[3]=(n.argvalue[1]+1) % ncity;
		for (Int j = 0;j<4;j++)
		{
			Int ii = iorder.argvalue[n.argvalue[j]];
			xx[j]=x.argvalue[ii];
			yy[j]=y.argvalue[ii];
		}
		Doub de = -alen(xx[0], xx[2], yy[0], yy[2]);
		de -= alen(xx[1], xx[3], yy[1], yy[3]);
		de += alen(xx[0], xx[3], yy[0], yy[3]);
		de += alen(xx[1], xx[2], yy[1], yy[2]);
		return de;
	}

	public final void reverse(RefObject<VecInt_IO> iorder, RefObject<VecInt_I> n)
	{
		Int ncity = iorder.argvalue.size();
		Int nn = (1+((n.argvalue[1]-n.argvalue[0]+ncity) % ncity))/2;
		for (Int j = 0;j<nn;j++)
		{
			Int k = (n.argvalue[0]+j) % ncity;
			Int l = (n.argvalue[1]-j+ncity) % ncity;
			Int itmp = iorder.argvalue[k];
			iorder.argvalue[k]=iorder.argvalue[l];
			iorder.argvalue[l]=itmp;
		}
	}

	public final Doub trncst(RefObject<VecDoub_I> x, RefObject<VecDoub_I> y, RefObject<VecInt_I> iorder, RefObject<VecInt_IO> n)
	{
		VecDoub xx = new VecDoub(6);
		VecDoub yy = new VecDoub(6);
		Int ncity = x.argvalue.size();
		n.argvalue[3]=(n.argvalue[2]+1) % ncity;
		n.argvalue[4]=(n.argvalue[0]+ncity-1) % ncity;
		n.argvalue[5]=(n.argvalue[1]+1) % ncity;
		for (Int j = 0;j<6;j++)
		{
			Int ii = iorder.argvalue[n.argvalue[j]];
			xx[j]=x.argvalue[ii];
			yy[j]=y.argvalue[ii];
		}
		Doub de = -alen(xx[1], xx[5], yy[1], yy[5]);
		de -= alen(xx[0], xx[4], yy[0], yy[4]);
		de -= alen(xx[2], xx[3], yy[2], yy[3]);
		de += alen(xx[0], xx[2], yy[0], yy[2]);
		de += alen(xx[1], xx[3], yy[1], yy[3]);
		de += alen(xx[4], xx[5], yy[4], yy[5]);
		return de;
	}

	public final void trnspt(RefObject<VecInt_IO> iorder, RefObject<VecInt_I> n)
	{
		Int ncity = iorder.argvalue.size();
		VecInt jorder = new VecInt(ncity);
		Int m1 = (n.argvalue[1]-n.argvalue[0]+ncity) % ncity;
		Int m2 = (n.argvalue[4]-n.argvalue[3]+ncity) % ncity;
		Int m3 = (n.argvalue[2]-n.argvalue[5]+ncity) % ncity;
		Int nn = 0;
		for (Int j = 0;j<=m1;j++)
		{
			Int jj = (j+n.argvalue[0]) % ncity;
			jorder[nn++]=iorder.argvalue[jj];
		}
		for (Int j = 0;j<=m2;j++)
		{
			Int jj = (j+n.argvalue[3]) % ncity;
			jorder[nn++]=iorder.argvalue[jj];
		}
		for (Int j = 0;j<=m3;j++)
		{
			Int jj = (j+n.argvalue[5]) % ncity;
			jorder[nn++]=iorder.argvalue[jj];
		}
		for (Int j = 0;j<ncity;j++)
			iorder.argvalue[j]=jorder[j];
	}

	public final Bool metrop(Doub de, Doub t)
	{
		return de < 0.0 || myran.doub() < Math.exp(-de/t);
	}

	public final Doub alen(Doub a, Doub b, Doub c, Doub d)
	{
		return Math.sqrt((b-a)*(b-a)+(d-c)*(d-c));
	}
}