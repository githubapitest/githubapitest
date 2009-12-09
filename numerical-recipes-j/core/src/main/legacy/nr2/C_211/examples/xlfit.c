
/* Driver for routine lfit */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NPT 100
#define SPREAD 0.1
#define NTERM 5

void funcs(float x,float afunc[],int ma)
{
	int i;

	afunc[1]=1.0;
	afunc[2]=x;
	for (i=3;i<=ma;i++) afunc[i]=sin(i*x);
}

int main(void)
{
	long idum=(-911);
	int i,j,*ia;
	float chisq,*a,*x,*y,*sig,**covar;

	ia=ivector(1,NTERM);
	a=vector(1,NTERM);
	x=vector(1,NPT);
	y=vector(1,NPT);
	sig=vector(1,NPT);
	covar=matrix(1,NTERM,1,NTERM);

	for (i=1;i<=NPT;i++) {
		x[i]=0.1*i;
		funcs(x[i],a,NTERM);
		y[i]=0.0;
		for (j=1;j<=NTERM;j++) y[i] += j*a[j];
		y[i] += SPREAD*gasdev(&idum);
		sig[i]=SPREAD;
	}
	for (i=1;i<=NTERM;i++) ia[i]=1;
	lfit(x,y,sig,NPT,a,ia,NTERM,covar,&chisq,funcs);
	printf("\n%11s %21s\n","parameter","uncertainty");
	for (i=1;i<=NTERM;i++)
		printf("  a[%1d] = %8.6f %12.6f\n",
			i,a[i],sqrt(covar[i][i]));
	printf("chi-squared = %12f\n",chisq);
	printf("full covariance matrix\n");
	for (i=1;i<=NTERM;i++) {
		for (j=1;j<=NTERM;j++) printf("%12f",covar[i][j]);
		printf("\n");
	}
	printf("\npress RETURN to continue...\n");
	(void) getchar();
	/* Now check results of restricting fit parameters */
	for (i=2;i<=NTERM;i+=2) ia[i]=0;
	lfit(x,y,sig,NPT,a,ia,NTERM,covar,&chisq,funcs);
	printf("\n%11s %21s\n","parameter","uncertainty");
	for (i=1;i<=NTERM;i++)
		printf("  a[%1d] = %8.6f %12.6f\n",
			i,a[i],sqrt(covar[i][i]));
	printf("chi-squared = %12f\n",chisq);
	printf("full covariance matrix\n");
	for (i=1;i<=NTERM;i++) {
		for (j=1;j<=NTERM;j++) printf("%12f",covar[i][j]);
		printf("\n");
	}
	printf("\n");
	free_matrix(covar,1,NTERM,1,NTERM);
	free_vector(sig,1,NPT);
	free_vector(y,1,NPT);
	free_vector(x,1,NPT);
	free_vector(a,1,NTERM);
	free_ivector(ia,1,NTERM);
	return 0;
}
#undef NRANSI
