package com.google.code.numericalrecipes;
public class GlobalMembersStattests
{
	public static void ttest(RefObject<VecDoub_I> data1, RefObject<VecDoub_I> data2, RefObject<Doub> t, RefObject<Doub> prob)
	{
		Beta beta = new Beta();
		Doub var1 = new Doub();
		Doub var2 = new Doub();
		Doub svar = new Doub();
		Doub df = new Doub();
		Doub ave1 = new Doub();
		Doub ave2 = new Doub();
		Int n1 = data1.argvalue.size();
		Int n2 = data2.argvalue.size();
		avevar(data1.argvalue,ave1,var1);
		avevar(data2.argvalue,ave2,var2);
		df = n1+n2-2;
		svar = ((n1-1)*var1+(n2-1)*var2)/df;
		t.argvalue = (ave1-ave2)/Math.sqrt(svar*(1.0/n1+1.0/n2));
		prob.argvalue = beta.betai(0.5 *df,0.5,df/(df+t.argvalue *t.argvalue));
	}
	public static void tutest(RefObject<VecDoub_I> data1, RefObject<VecDoub_I> data2, RefObject<Doub> t, RefObject<Doub> prob)
	{
		Beta beta = new Beta();
		Doub var1 = new Doub();
		Doub var2 = new Doub();
		Doub df = new Doub();
		Doub ave1 = new Doub();
		Doub ave2 = new Doub();
		Int n1 = data1.argvalue.size();
		Int n2 = data2.argvalue.size();
		avevar(data1.argvalue,ave1,var1);
		avevar(data2.argvalue,ave2,var2);
		t.argvalue = (ave1-ave2)/Math.sqrt(var1/n1+var2/n2);
		df = SQR(var1/n1+var2/n2)/(SQR(var1/n1)/(n1-1)+SQR(var2/n2)/(n2-1));
		prob.argvalue = beta.betai(0.5 *df,0.5,df/(df+SQR(t.argvalue)));
	}
	public static void tptest(RefObject<VecDoub_I> data1, RefObject<VecDoub_I> data2, RefObject<Doub> t, RefObject<Doub> prob)
	{
		Beta beta = new Beta();
		Int j = new Int();
		Int n = data1.argvalue.size();
		Doub var1 = new Doub();
		Doub var2 = new Doub();
		Doub ave1 = new Doub();
		Doub ave2 = new Doub();
		Doub sd = new Doub();
		Doub df = new Doub();
		Doub cov = 0.0;
		avevar(data1.argvalue,ave1,var1);
		avevar(data2.argvalue,ave2,var2);
		for (j = 0;j<n;j++)
			cov += (data1.argvalue[j]-ave1)*(data2.argvalue[j]-ave2);
		cov /= (df = n-1);
		sd = Math.sqrt((var1+var2-2.0 *cov)/n);
		t.argvalue = (ave1-ave2)/sd;
		prob.argvalue = beta.betai(0.5 *df,0.5,df/(df+t.argvalue *t.argvalue));
	}
	public static void ftest(RefObject<VecDoub_I> data1, RefObject<VecDoub_I> data2, RefObject<Doub> f, RefObject<Doub> prob)
	{
		Beta beta = new Beta();
		Doub var1 = new Doub();
		Doub var2 = new Doub();
		Doub ave1 = new Doub();
		Doub ave2 = new Doub();
		Doub df1 = new Doub();
		Doub df2 = new Doub();
		Int n1 = data1.argvalue.size();
		Int n2 = data2.argvalue.size();
		avevar(data1.argvalue,ave1,var1);
		avevar(data2.argvalue,ave2,var2);
		if (var1 > var2)
		{
			f.argvalue = var1/var2;
			df1 = n1-1;
			df2 = n2-1;
		}
		else
		{
			f.argvalue = var2/var1;
			df1 = n2-1;
			df2 = n1-1;
		}
		prob.argvalue = 2.0 *beta.betai(0.5 *df2,0.5 *df1,df2/(df2+df1 *f.argvalue));
		if (prob.argvalue > 1.0)
			prob.argvalue = 2.-prob.argvalue;
	}
public static void chsone(RefObject<VecDoub_I> bins, RefObject<VecDoub_I> ebins, RefObject<Doub> df, RefObject<Doub> chsq, RefObject<Doub> prob)
{
	chsone(bins, ebins, df, chsq, prob, 1);
}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void chsone(VecDoub_I &bins, VecDoub_I &ebins, Doub &df, Doub &chsq, Doub &prob, const Int knstrn=1)
	public static void chsone(RefObject<VecDoub_I> bins, RefObject<VecDoub_I> ebins, RefObject<Doub> df, RefObject<Doub> chsq, RefObject<Doub> prob, Int knstrn)
	{
		Gamma gam = new Gamma();
		Int j = new Int();
		Int nbins = bins.argvalue.size();
		Doub temp = new Doub();
		df.argvalue = nbins-knstrn;
		chsq.argvalue = 0.0;
		for (j = 0;j<nbins;j++)
		{
			if (ebins.argvalue[j]<0.0 || (ebins.argvalue[j]==0.&& bins.argvalue[j]>0.))
				throw("Bad expected number in chsone");
			if (ebins.argvalue[j]==0.0 && bins.argvalue[j]==0.0)
			{
				--df.argvalue;
			}
			else
			{
				temp = bins.argvalue[j]-ebins.argvalue[j];
				chsq.argvalue += temp *temp/ebins.argvalue[j];
			}
		}
		prob.argvalue = gam.gammq(0.5 *df.argvalue,0.5 *chsq.argvalue);
	}
public static void chstwo(RefObject<VecDoub_I> bins1, RefObject<VecDoub_I> bins2, RefObject<Doub> df, RefObject<Doub> chsq, RefObject<Doub> prob)
{
	chstwo(bins1, bins2, df, chsq, prob, 1);
}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void chstwo(VecDoub_I &bins1, VecDoub_I &bins2, Doub &df, Doub &chsq, Doub &prob, const Int knstrn=1)
	public static void chstwo(RefObject<VecDoub_I> bins1, RefObject<VecDoub_I> bins2, RefObject<Doub> df, RefObject<Doub> chsq, RefObject<Doub> prob, Int knstrn)
	{
		Gamma gam = new Gamma();
		Int j = new Int();
		Int nbins = bins1.argvalue.size();
		Doub temp = new Doub();
		df.argvalue = nbins-knstrn;
		chsq.argvalue = 0.0;
		for (j = 0;j<nbins;j++)
			if (bins1.argvalue[j] == 0.0 && bins2.argvalue[j] == 0.0)
				--df.argvalue;
			else
			{
				temp = bins1.argvalue[j]-bins2.argvalue[j];
				chsq.argvalue += temp *temp/(bins1.argvalue[j]+bins2.argvalue[j]);
			}
		prob.argvalue = gam.gammq(0.5 *df.argvalue,0.5 *chsq.argvalue);
	}
	public static void cntab(RefObject<MatInt_I> nn, RefObject<Doub> chisq, RefObject<Doub> df, RefObject<Doub> prob, RefObject<Doub> cramrv, RefObject<Doub> ccc)
	{
		final Doub TINY = 1.0e-30;
		Gamma gam = new Gamma();
		Int i = new Int();
		Int j = new Int();
		Int nnj = new Int();
		Int nni = new Int();
		Int minij = new Int();
		Int ni = nn.argvalue.nrows();
		Int nj = nn.argvalue.ncols();
		Doub sum = 0.0;
		Doub expctd = new Doub();
		Doub temp = new Doub();
		VecDoub sumi = new VecDoub(ni);
		VecDoub sumj = new VecDoub(nj);
		nni = ni;
		nnj = nj;
		for (i = 0;i<ni;i++)
		{
			sumi[i]=0.0;
			for (j = 0;j<nj;j++)
			{
				sumi[i] += nn.argvalue[i][j];
				sum += nn.argvalue[i][j];
			}
			if (sumi[i] == 0.0)
				--nni;
		}
		for (j = 0;j<nj;j++)
		{
			sumj[j]=0.0;
			for (i = 0;i<ni;i++)
				sumj[j] += nn.argvalue[i][j];
			if (sumj[j] == 0.0)
				--nnj;
		}
		df.argvalue = nni *nnj-nni-nnj+1;
		chisq.argvalue = 0.0;
		for (i = 0;i<ni;i++)
		{
			for (j = 0;j<nj;j++)
			{
				expctd = sumj[j]*sumi[i]/sum;
				temp = nn.argvalue[i][j]-expctd;
				chisq.argvalue += temp *temp/(expctd+TINY);
			}
		}
		prob.argvalue = gam.gammq(0.5 *df.argvalue,0.5 *chisq.argvalue);
		minij = nni < nnj != null ? nni-1 : nnj-1;
		cramrv.argvalue = Math.sqrt(chisq.argvalue/(sum *minij));
		ccc.argvalue = Math.sqrt(chisq.argvalue/(chisq.argvalue+sum));
	}
	public static void pearsn(RefObject<VecDoub_I> x, RefObject<VecDoub_I> y, RefObject<Doub> r, RefObject<Doub> prob, RefObject<Doub> z)
	{
		final Doub TINY = 1.0e-20;
		Beta beta = new Beta();
		Int j = new Int();
		Int n = x.argvalue.size();
		Doub yt = new Doub();
		Doub xt = new Doub();
		Doub t = new Doub();
		Doub df = new Doub();
		Doub syy = 0.0;
		Doub sxy = 0.0;
		Doub sxx = 0.0;
		Doub ay = 0.0;
		Doub ax = 0.0;
		for (j = 0;j<n;j++)
		{
			ax += x.argvalue[j];
			ay += y.argvalue[j];
		}
		ax /= n;
		ay /= n;
		for (j = 0;j<n;j++)
		{
			xt = x.argvalue[j]-ax;
			yt = y.argvalue[j]-ay;
			sxx += xt *xt;
			syy += yt *yt;
			sxy += xt *yt;
		}
		r.argvalue = sxy/(Math.sqrt(sxx *syy)+TINY);
		z.argvalue = 0.5 *Math.log((1.0+r.argvalue+TINY)/(1.0-r.argvalue+TINY));
		df = n-2;
		t = r.argvalue *Math.sqrt(df/((1.0-r.argvalue+TINY)*(1.0+r.argvalue+TINY)));
		prob.argvalue = beta.betai(0.5 *df,0.5,df/(df+t *t));
		// prob=erfcc(abs(z*sqrt(n-1.0))/1.4142136);
	}
	public static void crank(RefObject<VecDoub_IO> w, RefObject<Doub> s)
	{
		Int j = 1;
		Int ji = new Int();
		Int jt = new Int();
		Int n = w.argvalue.size();
		Doub t = new Doub();
		Doub rank = new Doub();
		s.argvalue = 0.0;
		while (j < n)
		{
			if (w.argvalue[j] != w.argvalue[j-1])
			{
				w.argvalue[j-1]=j;
				++j;
			}
			else
			{
				for (jt = j+1;jt<=n && w.argvalue[jt-1]==w.argvalue[j-1];jt++)
					;
				rank = 0.5*(j+jt-1);
				for (ji = j;ji<=(jt-1);ji++)
					w.argvalue[ji-1]=rank;
				t = jt-j;
				s.argvalue += (t *t *t-t);
				j = jt;
			}
		}
		if (j == n)
			w.argvalue[n-1]=n;
	}
	public static void spear(RefObject<VecDoub_I> data1, RefObject<VecDoub_I> data2, RefObject<Doub> d, RefObject<Doub> zd, RefObject<Doub> probd, RefObject<Doub> rs, RefObject<Doub> probrs)
	{
		Beta bet = new Beta();
		Int j = new Int();
		Int n = data1.argvalue.size();
		Doub vard = new Doub();
		Doub t = new Doub();
		Doub sg = new Doub();
		Doub sf = new Doub();
		Doub fac = new Doub();
		Doub en3n = new Doub();
		Doub en = new Doub();
		Doub df = new Doub();
		Doub aved = new Doub();
		VecDoub wksp1 = new VecDoub(n);
		VecDoub wksp2 = new VecDoub(n);
		for (j = 0;j<n;j++)
		{
			wksp1[j]=data1.argvalue[j];
			wksp2[j]=data2.argvalue[j];
		}
		sort2(wksp1,wksp2);
	RefObject<VecDoub_IO> tempRefObject = new RefObject<VecDoub_IO>(wksp1);
	RefObject<Doub> tempRefObject2 = new RefObject<Doub>(sf);
		crank(tempRefObject, tempRefObject2);
		wksp1 = tempRefObject.argvalue;
		sf = tempRefObject2.argvalue;
		sort2(wksp2,wksp1);
	RefObject<VecDoub_IO> tempRefObject3 = new RefObject<VecDoub_IO>(wksp2);
	RefObject<Doub> tempRefObject4 = new RefObject<Doub>(sg);
		crank(tempRefObject3, tempRefObject4);
		wksp2 = tempRefObject3.argvalue;
		sg = tempRefObject4.argvalue;
		d.argvalue = 0.0;
		for (j = 0;j<n;j++)
			d.argvalue += SQR(wksp1[j]-wksp2[j]);
		en = n;
		en3n = en *en *en-en;
		aved = en3n/6.0-(sf+sg)/12.0;
		fac = (1.0-sf/en3n)*(1.0-sg/en3n);
		vard = ((en-1.0)*en *en *SQR(en+1.0)/36.0)*fac;
		zd.argvalue = (d.argvalue-aved)/Math.sqrt(vard);
		probd.argvalue = erfcc(Math.abs(zd.argvalue)/1.4142136);
		rs.argvalue = (1.0-(6.0/en3n)*(d.argvalue+(sf+sg)/12.0))/Math.sqrt(fac);
		fac = (rs.argvalue+1.0)*(1.0-rs.argvalue);
		if (fac > 0.0)
		{
			t = rs.argvalue *Math.sqrt((en-2.0)/fac);
			df = en-2.0;
			probrs.argvalue = bet.betai(0.5 *df,0.5,df/(df+t *t));
		}
		else
			probrs.argvalue = 0.0;
	}
	public static void kendl1(RefObject<VecDoub_I> data1, RefObject<VecDoub_I> data2, RefObject<Doub> tau, RefObject<Doub> z, RefObject<Doub> prob)
	{
		Int is = 0;
		Int j = new Int();
		Int k = new Int();
		Int n2 = 0;
		Int n1 = 0;
		Int n = data1.argvalue.size();
		Doub svar = new Doub();
		Doub aa = new Doub();
		Doub a2 = new Doub();
		Doub a1 = new Doub();
		for (j = 0;j<n-1;j++)
		{
			for (k = j+1;k<n;k++)
			{
				a1 = data1.argvalue[j]-data1.argvalue[k];
				a2 = data2.argvalue[j]-data2.argvalue[k];
				aa = a1 *a2;
				if (aa != 0.0)
				{
					++n1;
					++n2;
					aa > 0.0 ? ++is : --is;
				}
				else
				{
					if (a1 != 0.0)
						++n1;
					if (a2 != 0.0)
						++n2;
				}
			}
		}
		tau.argvalue = is/(Math.sqrt(Doub(n1))*Math.sqrt(Doub(n2)));
		svar = (4.0 *n+10.0)/(9.0 *n*(n-1.0));
		z.argvalue = tau.argvalue/Math.sqrt(svar);
		prob.argvalue = erfcc(Math.abs(z.argvalue)/1.4142136);
	}
	public static void kendl2(RefObject<MatDoub_I> tab, RefObject<Doub> tau, RefObject<Doub> z, RefObject<Doub> prob)
	{
		Int k = new Int();
		Int l = new Int();
		Int nn = new Int();
		Int mm = new Int();
		Int m2 = new Int();
		Int m1 = new Int();
		Int lj = new Int();
		Int li = new Int();
		Int kj = new Int();
		Int ki = new Int();
		Int i = tab.argvalue.nrows();
		Int j = tab.argvalue.ncols();
		Doub svar = new Doub();
		Doub s = 0.0;
		Doub points = new Doub();
		Doub pairs = new Doub();
		Doub en2 = 0.0;
		Doub en1 = 0.0;
		nn = i *j;
		points = tab.argvalue[i-1][j-1];
		for (k = 0;k<=nn-2;k++)
		{
			ki = (k/j);
			kj = k-j *ki;
			points += tab.argvalue[ki][kj];
			for (l = k+1;l<=nn-1;l++)
			{
				li = l/j;
				lj = l-j *li;
				mm = (m1 = li-ki)*(m2 = lj-kj);
				pairs = tab.argvalue[ki][kj]*tab.argvalue[li][lj];
				if (mm != 0)
				{
					en1 += pairs;
					en2 += pairs;
					s += (mm > 0 ? pairs : -pairs);
				}
				else
				{
					if (m1 != 0)
						en1 += pairs;
					if (m2 != 0)
						en2 += pairs;
				}
			}
		}
		tau.argvalue = s/Math.sqrt(en1 *en2);
		svar = (4.0 *points+10.0)/(9.0 *points*(points-1.0));
		z.argvalue = tau.argvalue/Math.sqrt(svar);
		prob.argvalue = erfcc(Math.abs(z.argvalue)/1.4142136);
	}
}