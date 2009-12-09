
/* Driver for routine mrqcof */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NPT 100
#define MA 6
#define SPREAD 0.1

int main(void)
{
	long idum=(-911);
	int i,j,mfit=MA,*ia;
	float chisq,*beta,*x,*y,*sig,**covar,**alpha;
	static float a[MA+1]=
		{0.0,5.0,2.0,3.0,2.0,5.0,3.0};
	static float gues[MA+1]=
		{0.0,4.9,2.1,2.9,2.1,4.9,3.1};

	ia=ivector(1,MA);
	beta=vector(1,MA);
	x=vector(1,NPT);
	y=vector(1,NPT);
	sig=vector(1,NPT);
	covar=matrix(1,MA,1,MA);
	alpha=matrix(1,MA,1,MA);
	/* First try sum of two gaussians */
	for (i=1;i<=NPT;i++) {
		x[i]=0.1*i;
		y[i]=0.0;
		y[i] += a[1]*exp(-SQR((x[i]-a[2])/a[3]));
		y[i] += a[4]*exp(-SQR((x[i]-a[5])/a[6]));
		y[i] *= (1.0+SPREAD*gasdev(&idum));
		sig[i]=SPREAD*y[i];
	}
	for (i=1;i<=mfit;i++) ia[i]=1;
	for (i=1;i<=mfit;i++) a[i]=gues[i];
	mrqcof(x,y,sig,NPT,a,ia,MA,alpha,beta,&chisq,fgauss);
	printf("\nmatrix alpha\n");
	for (i=1;i<=MA;i++) {
		for (j=1;j<=MA;j++) printf("%12.4f",alpha[i][j]);
		printf("\n");
	}
	printf("vector beta\n");
	for (i=1;i<=MA;i++) printf("%12.4f",beta[i]);
	printf("\nchi-squared: %12.4f\n\n",chisq);
	/* Next fix one line and improve the other */
	mfit=3;
	for (i=1;i<=mfit;i++) ia[i]=0;
	for (i=1;i<=MA;i++) a[i]=gues[i];
	mrqcof(x,y,sig,NPT,a,ia,MA,alpha,beta,&chisq,fgauss);
	printf("matrix alpha\n");
	for (i=1;i<=mfit;i++) {
		for (j=1;j<=mfit;j++) printf("%12.4f",alpha[i][j]);
		printf("\n");
	}
	printf("vector beta\n");
	for (i=1;i<=mfit;i++) printf("%12.4f",beta[i]);
	printf("\nchi-squared: %12.4f\n\n",chisq);
	free_matrix(alpha,1,MA,1,MA);
	free_matrix(covar,1,MA,1,MA);
	free_vector(sig,1,NPT);
	free_vector(y,1,NPT);
	free_vector(x,1,NPT);
	free_vector(beta,1,MA);
	free_ivector(ia,1,MA);
	return 0;
}
#undef NRANSI
