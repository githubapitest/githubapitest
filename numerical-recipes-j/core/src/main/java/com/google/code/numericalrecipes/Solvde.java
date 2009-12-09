package com.google.code.numericalrecipes;
public class Solvde
{
	public final Int itmax = new Int();
	public final Doub conv = new Doub();
	public final Doub slowc = new Doub();
	public final VecDoub scalv;
	public final VecInt indexv;
	public final Int nb = new Int();
	public MatDoub y;
	public Difeq difeq;
	public Int ne = new Int();
	public Int m = new Int();
	public VecInt kmax = new VecInt();
	public VecDoub ermax = new VecDoub();
	public Mat3DDoub c = new Mat3DDoub();
	public MatDoub s = new MatDoub();
	public Solvde(Int itmaxx, Doub convv, Doub slowcc, RefObject<VecDoub_I> scalvv, RefObject<VecInt_I> indexvv, Int nbb, RefObject<MatDoub_IO> yy, RefObject<Difeq> difeqq)
	{
		itmax = itmaxx;
		conv = convv;
		slowc = slowcc;
		scalv = scalvv.argvalue;
		indexv = indexvv.argvalue;
		nb = nbb;
		y = yy.argvalue;
		difeq = difeqq.argvalue;
		ne = y.nrows();
		m = y.ncols();
		kmax = ne;
		ermax = ne;
		c = new Mat3DDoub(ne,ne-nb+1,m+1);
		s = new MatDoub(ne,2 *ne+1);
		Int jv = new Int();
		Int k = new Int();
		Int nvars = ne *m;
		Int k1 = 0;
		Int k2 = m;
		Int j1 = 0;
		Int j2 = nb;
		Int j3 = nb;
		Int j4 = ne;
		Int j5 = j4+j1;
		Int j6 = j4+j2;
		Int j7 = j4+j3;
		Int j8 = j4+j4;
		Int j9 = j8+j1;
		Int ic1 = 0;
		Int ic2 = ne-nb;
		Int ic3 = ic2;
		Int ic4 = ne;
		Int jc1 = 0;
		Int jcf = ic3;
		for (Int it = 0;it<itmax;it++)
		{
			k = k1;
			difeq.smatrix(k,k1,k2,j9,ic3,ic4,indexv,s,y);
			pinvs(ic3, ic4, j5, j9, jc1, k1);
			for (k = k1+1;k<k2;k++)
			{
				Int kp = k;
				difeq.smatrix(k,k1,k2,j9,ic1,ic4,indexv,s,y);
				red(ic1, ic4, j1, j2, j3, j4, j9, ic3, jc1, jcf, kp);
				pinvs(ic1, ic4, j3, j9, jc1, k);
			}
			k = k2;
			difeq.smatrix(k,k1,k2,j9,ic1,ic2,indexv,s,y);
			red(ic1, ic2, j5, j6, j7, j8, j9, ic3, jc1, jcf, k2);
			pinvs(ic1, ic2, j7, j9, jcf, k2);
			bksub(ne, nb, jcf, k1, k2);
			Doub err = 0.0;
			for (Int j = 0;j<ne;j++)
			{
				jv = indexv[j];
				Doub errj = 0.0;
				Doub vmax = 0.0;
				Int km = 0;
				for (k = k1;k<k2;k++)
				{
					Doub vz = Math.abs(c[jv][0][k]);
					if (vz > vmax)
					{
						vmax = vz;
						km = k+1;
					}
					errj += vz;
				}
				err += errj/scalv[j];
				ermax[j]=c[jv][0][km-1]/scalv[j];
				kmax[j]=km;
			}
			err /= nvars;
			Doub fac = (err > slowc != null ? slowc/err : 1.0);
			for (Int j = 0;j<ne;j++)
			{
				jv = indexv[j];
				for (k = k1;k<k2;k++)
				y[j][k] -= fac *c[jv][0][k];
			}
			System.out.printf("%8d", "Iter.");
			System.out.printf("%10d", "Error");
			System.out.printf("%10d", "FAC");
			System.out.printf("%10d", "\n");
			System.out.printf("%6d", it);
			System.out.printf("%13.6f", err);
			System.out.printf("%12.6f", fac);
			System.out.printf("%12.6f", "\n");
			if (err < conv)
				return;
		}
		throw("Too many iterations in solvde");
	}
	public final void pinvs(Int ie1, Int ie2, Int je1, Int jsf, Int jc1, Int k)
	{
		Int jpiv = new Int();
		Int jp = new Int();
		Int je2 = new Int();
		Int jcoff = new Int();
		Int j = new Int();
		Int irow = new Int();
		Int ipiv = new Int();
		Int id = new Int();
		Int icoff = new Int();
		Int i = new Int();
		Doub pivinv = new Doub();
		Doub piv = new Doub();
		Doub big = new Doub();
		final Int iesize = ie2-ie1;
		VecInt indxr = new VecInt(iesize);
		VecDoub pscl = new VecDoub(iesize);
		je2 = je1+iesize;
		for (i = ie1;i<ie2;i++)
		{
			big = 0.0;
			for (j = je1;j<je2;j++)
				if (Math.abs(s[i][j]) > big)
					big = Math.abs(s[i][j]);
			if (big == 0.0)
				throw("Singular matrix - row all 0, in pinvs");
			pscl[i-ie1]=1.0/big;
			indxr[i-ie1]=0;
		}
		for (id = 0;id<iesize;id++)
		{
			piv = 0.0;
			for (i = ie1;i<ie2;i++)
			{
				if (indxr[i-ie1] == 0)
				{
					big = 0.0;
					for (j = je1;j<je2;j++)
					{
						if (Math.abs(s[i][j]) > big)
						{
							jp = j;
							big = Math.abs(s[i][j]);
						}
					}
					if (big *pscl[i-ie1] > piv)
					{
						ipiv = i;
						jpiv = jp;
						piv = big *pscl[i-ie1];
					}
				}
			}
			if (s[ipiv][jpiv] == 0.0)
				throw("Singular matrix in routine pinvs");
			indxr[ipiv-ie1]=jpiv+1;
			pivinv = 1.0/s[ipiv][jpiv];
			for (j = je1;j<=jsf;j++)
				s[ipiv][j] *= pivinv;
			s[ipiv][jpiv]=1.0;
			for (i = ie1;i<ie2;i++)
			{
				if (indxr[i-ie1] != jpiv+1)
				{
					if (s[i][jpiv] != 0.0)
					{
						Doub dum = s[i][jpiv];
						for (j = je1;j<=jsf;j++)
							s[i][j] -= dum *s[ipiv][j];
						s[i][jpiv]=0.0;
					}
				}
			}
		}
		jcoff = jc1-je2;
		icoff = ie1-je1;
		for (i = ie1;i<ie2;i++)
		{
			irow = indxr[i-ie1]+icoff;
			for (j = je2;j<=jsf;j++)
				c[irow-1][j+jcoff][k]=s[i][j];
		}
	}
	public final void bksub(Int ne, Int nb, Int jf, Int k1, Int k2)
	{
		Int nbf = ne-nb;
		Int im = 1;
		for (Int k = k2-1;k>=k1;k--)
		{
			if (k == k1)
				im = nbf+1;
			Int kp = k+1;
			for (Int j = 0;j<nbf;j++)
			{
				Doub xx = c[j][jf][kp];
				for (Int i = im-1;i<ne;i++)
					c[i][jf][k] -= c[i][j][k]*xx;
			}
		}
		for (Int k = k1;k<k2;k++)
		{
			Int kp = k+1;
			for (Int i = 0;i<nb;i++)
				c[i][0][k]=c[i+nbf][jf][k];
			for (Int i = 0;i<nbf;i++)
				c[i+nb][0][k]=c[i][jf][kp];
		}
	}
	public final void red(Int iz1, Int iz2, Int jz1, Int jz2, Int jm1, Int jm2, Int jmf, Int ic1, Int jc1, Int jcf, Int kc)
	{
		Int l = new Int();
		Int j = new Int();
		Int i = new Int();
		Doub vx = new Doub();
		Int loff = jc1-jm1;
		Int ic = ic1;
		for (j = jz1;j<jz2;j++)
		{
			for (l = jm1;l<jm2;l++)
			{
				vx = c[ic][l+loff][kc-1];
				for (i = iz1;i<iz2;i++)
					s[i][l] -= s[i][j]*vx;
			}
			vx = c[ic][jcf][kc-1];
			for (i = iz1;i<iz2;i++)
				s[i][jmf] -= s[i][j]*vx;
			ic += 1;
		}
	}
}