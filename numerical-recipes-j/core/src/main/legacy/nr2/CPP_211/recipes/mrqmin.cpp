#include "nr.h"

void NR::mrqmin(Vec_I_DP &x, Vec_I_DP &y, Vec_I_DP &sig, Vec_IO_DP &a,
	Vec_I_BOOL &ia, Mat_O_DP &covar, Mat_O_DP &alpha, DP &chisq,
	void funcs(const DP, Vec_I_DP &, DP &, Vec_O_DP &), DP &alamda)
{
	static int mfit;
	static DP ochisq;
	int j,k,l;

	int ma=a.size();
	static Mat_DP *oneda_p;
	static Vec_DP *atry_p,*beta_p,*da_p;
	if (alamda < 0.0) {
		atry_p = new Vec_DP(ma);
		beta_p = new Vec_DP(ma);
		da_p = new Vec_DP(ma);
		mfit=0;
		for (j=0;j<ma;j++)
			if (ia[j]) mfit++;
		oneda_p = new Mat_DP(mfit,1);
		alamda=0.001;
		mrqcof(x,y,sig,a,ia,alpha,*beta_p,chisq,funcs);
		ochisq=chisq;
		for (j=0;j<ma;j++) (*atry_p)[j]=a[j];
	}
	Mat_DP &oneda=*oneda_p;
	Vec_DP &atry=*atry_p,&beta=*beta_p,&da=*da_p;
	Mat_DP temp(mfit,mfit);
	for (j=0;j<mfit;j++) {
		for (k=0;k<mfit;k++) covar[j][k]=alpha[j][k];
		covar[j][j]=alpha[j][j]*(1.0+alamda);
		for (k=0;k<mfit;k++) temp[j][k]=covar[j][k];
		oneda[j][0]=beta[j];
	}
	gaussj(temp,oneda);
	for (j=0;j<mfit;j++) {
		for (k=0;k<mfit;k++) covar[j][k]=temp[j][k];
		da[j]=oneda[j][0];
	}
	if (alamda == 0.0) {
		covsrt(covar,ia,mfit);
		covsrt(alpha,ia,mfit);
		delete oneda_p; delete da_p; delete beta_p; delete atry_p;
		return;
	}
	for (j=0,l=0;l<ma;l++)
		if (ia[l]) atry[l]=a[l]+da[j++];
	mrqcof(x,y,sig,atry,ia,covar,da,chisq,funcs);
	if (chisq < ochisq) {
		alamda *= 0.1;
		ochisq=chisq;
		for (j=0;j<mfit;j++) {
			for (k=0;k<mfit;k++) alpha[j][k]=covar[j][k];
				beta[j]=da[j];
		}
		for (l=0;l<ma;l++) a[l]=atry[l];
	} else {
		alamda *= 10.0;
		chisq=ochisq;
	}
}
