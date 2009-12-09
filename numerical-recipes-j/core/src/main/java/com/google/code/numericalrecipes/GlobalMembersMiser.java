package com.google.code.numericalrecipes;
public class GlobalMembersMiser
{
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Int iran = 0;
	public static void miser(Doub func(VecDoub_I ), RefObject<VecDoub_I> regn, Int npts, Doub dith, RefObject<Doub> ave, RefObject<Doub> var)
	{
		final Int MNPT = 15;
		final Int MNBS = 60;
		final Doub PFAC = 0.1;
		final Doub TINY = 1.0e-30;
		final Doub BIG = 1.0e30;
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Int iran=0;
		Int j = new Int();
		Int jb = new Int();
		Int n = new Int();
		Int ndim = new Int();
		Int npre = new Int();
		Int nptl = new Int();
		Int nptr = new Int();
		Doub avel = new Doub();
		Doub varl = new Doub();
		Doub fracl = new Doub();
		Doub fval = new Doub();
		Doub rgl = new Doub();
		Doub rgm = new Doub();
		Doub rgr = new Doub();
		Doub s = new Doub();
		Doub sigl = new Doub();
		Doub siglb = new Doub();
		Doub sigr = new Doub();
		Doub sigrb = new Doub();
		Doub sum = new Doub();
		Doub sumb = new Doub();
		Doub summ = new Doub();
		Doub summ2 = new Doub();

		ndim = regn.argvalue.size()/2;
		VecDoub pt = new VecDoub(ndim);
		if (npts < MNBS)
		{
			summ = summ2 = 0.0;
			for (n = 0;n<npts;n++)
			{
				ranpt(pt,regn.argvalue);
				fval = func(pt);
				summ += fval;
				summ2 += fval * fval;
			}
			ave.argvalue = summ/npts;
			var.argvalue = MAX(TINY,(summ2-summ *summ/npts)/(npts *npts));
		}
		else
		{
			VecDoub rmid = new VecDoub(ndim);
			npre = MAX((Int)(npts *PFAC),(Int)MNPT);
			VecDoub fmaxl = new VecDoub(ndim);
			VecDoub fmaxr = new VecDoub(ndim);
			VecDoub fminl = new VecDoub(ndim);
			VecDoub fminr = new VecDoub(ndim);
			for (j = 0;j<ndim;j++)
			{
				iran = (iran *2661+36979) % 175000;
				s = SIGN(dith,Doub(iran-87500));
				rmid[j]=(0.5+s)*regn.argvalue[j]+(0.5-s)*regn.argvalue[ndim+j];
				fminl[j]=fminr[j]=BIG;
				fmaxl[j]=fmaxr[j]=(-BIG);
			}
			for (n = 0;n<npre;n++)
			{
				ranpt(pt,regn.argvalue);
				fval = func(pt);
				for (j = 0;j<ndim;j++)
				{
					if (pt[j]<=rmid[j])
					{
						fminl[j]=MIN(fminl[j],fval);
						fmaxl[j]=MAX(fmaxl[j],fval);
					}
					else
					{
						fminr[j]=MIN(fminr[j],fval);
						fmaxr[j]=MAX(fmaxr[j],fval);
					}
				}
			}
			sumb = BIG;
			jb = -1;
			siglb = sigrb = 1.0;
			for (j = 0;j<ndim;j++)
			{
				if (fmaxl[j] > fminl[j] && fmaxr[j] > fminr[j])
				{
					sigl = MAX(TINY,Math.pow(fmaxl[j]-fminl[j],2.0/3.0));
					sigr = MAX(TINY,Math.pow(fmaxr[j]-fminr[j],2.0/3.0));
					sum = sigl+sigr;
					if (sum<=sumb)
					{
						sumb = sum;
						jb = j;
						siglb = sigl;
						sigrb = sigr;
					}
				}
			}
			if (jb == -1)
				jb = (ndim *iran)/175000;
			rgl = regn.argvalue[jb];
			rgm = rmid[jb];
			rgr = regn.argvalue[ndim+jb];
			fracl = Math.abs((rgm-rgl)/(rgr-rgl));
			nptl = (Int)(MNPT+(npts-npre-2 *MNPT)*fracl *siglb /(fracl *siglb+(1.0-fracl)*sigrb));
			nptr = npts-npre-nptl;
			VecDoub regn_temp = new VecDoub(2 *ndim);
			for (j = 0;j<ndim;j++)
			{
				regn_temp[j]=regn.argvalue[j];
				regn_temp[ndim+j]=regn.argvalue[ndim+j];
			}
			regn_temp[ndim+jb]=rmid[jb];
		RefObject<> tempRefObject = new RefObject<>(func);
		RefObject<VecDoub_I> tempRefObject2 = new RefObject<VecDoub_I>(regn_temp);
		RefObject<Doub> tempRefObject3 = new RefObject<Doub>(avel);
		RefObject<Doub> tempRefObject4 = new RefObject<Doub>(varl);
			miser(tempRefObject, tempRefObject2, nptl, dith, tempRefObject3, tempRefObject4);
			func = tempRefObject.argvalue;
			regn_temp = tempRefObject2.argvalue;
			avel = tempRefObject3.argvalue;
			varl = tempRefObject4.argvalue;
			regn_temp[jb]=rmid[jb];
			regn_temp[ndim+jb]=regn.argvalue[ndim+jb];
		RefObject<> tempRefObject5 = new RefObject<>(func);
		RefObject<VecDoub_I> tempRefObject6 = new RefObject<VecDoub_I>(regn_temp);
			miser(tempRefObject5, tempRefObject6, nptr, dith, ave, var);
			func = tempRefObject5.argvalue;
			regn_temp = tempRefObject6.argvalue;
			ave.argvalue = fracl *avel+(1-fracl)*ave.argvalue;
			var.argvalue = fracl *fracl *varl+(1-fracl)*(1-fracl)*var.argvalue;
		}
	}
}