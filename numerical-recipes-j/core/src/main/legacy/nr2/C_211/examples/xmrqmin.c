
/* Driver for routine mrqmin */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NPT 100
#define MA 6
#define SPREAD 0.001

int main(void)
{
	long idum=(-911);
	int i,*ia,iter,itst,j,k,mfit=MA;
	float alamda,chisq,ochisq,*x,*y,*sig,**covar,**alpha;
	static float a[MA+1]=
		{0.0,5.0,2.0,3.0,2.0,5.0,3.0};
	static float gues[MA+1]=
		{0.0,4.5,2.2,2.8,2.5,4.9,2.8};

	ia=ivector(1,MA);
	x=vector(1,NPT);
	y=vector(1,NPT);
	sig=vector(1,NPT);
	covar=matrix(1,MA,1,MA);
	alpha=matrix(1,MA,1,MA);
	/* First try a sum of two Gaussians */
	for (i=1;i<=NPT;i++) {
		x[i]=0.1*i;
		y[i]=0.0;
		for (j=1;j<=MA;j+=3) {
			y[i] += a[j]*exp(-SQR((x[i]-a[j+1])/a[j+2]));
		}
		y[i] *= (1.0+SPREAD*gasdev(&idum));
		sig[i]=SPREAD*y[i];
	}
	for (i=1;i<=mfit;i++) ia[i]=1;
	for (i=1;i<=MA;i++) a[i]=gues[i];
	for (iter=1;iter<=2;iter++) {
		alamda = -1;
		mrqmin(x,y,sig,NPT,a,ia,MA,covar,alpha,&chisq,fgauss,&alamda);
		k=1;
		itst=0;
		for (;;) {
			printf("\n%s %2d %17s %10.4f %10s %9.2e\n","Iteration #",k,
				"chi-squared:",chisq,"alamda:",alamda);
			printf("%8s %8s %8s %8s %8s %8s\n",
				"a[1]","a[2]","a[3]","a[4]","a[5]","a[6]");
			for (i=1;i<=6;i++) printf("%9.4f",a[i]);
			printf("\n");
			k++;
			ochisq=chisq;
			mrqmin(x,y,sig,NPT,a,ia,MA,covar,alpha,&chisq,fgauss,&alamda);
			if (chisq > ochisq)
				itst=0;
			else if (fabs(ochisq-chisq) < 0.1)
				itst++;
			if (itst < 4) continue;
			alamda=0.0;
			mrqmin(x,y,sig,NPT,a,ia,MA,covar,alpha,&chisq,fgauss,&alamda);
			printf("\nUncertainties:\n");
			for (i=1;i<=6;i++) printf("%9.4f",sqrt(covar[i][i]));
			printf("\n");
			printf("\nExpected results:\n");
			printf(" %7.2f %8.2f %8.2f %8.2f %8.2f %8.2f\n",
				5.0,2.0,3.0,2.0,5.0,3.0);
			break;
		}
		if (iter == 1) {
			printf("press return to continue with constraint\n");
			(void) getchar();
			printf("holding a[2] and a[5] constant\n");
			for (j=1;j<=MA;j++) a[j] += 0.1;
			a[2]=2.0;
			ia[2]=0;
			a[5]=5.0;
			ia[5]=0;
		}
	}
	free_matrix(alpha,1,MA,1,MA);
	free_matrix(covar,1,MA,1,MA);
	free_vector(sig,1,NPT);
	free_vector(y,1,NPT);
	free_vector(x,1,NPT);
	free_ivector(ia,1,MA);
	return 0;
}
#undef NRANSI
