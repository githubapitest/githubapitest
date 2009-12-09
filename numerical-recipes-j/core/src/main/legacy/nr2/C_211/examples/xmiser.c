
/* Driver for routine miser */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

long idum;     /* for ranno */

int ndim;      /* for func */
float xoff;

float func(float pt[])
{
	int j;
	float ans,sum;

	for (sum=0.0,j=1;j<=ndim;j++) sum += (100.0*SQR(pt[j]-xoff));
	ans=(sum < 80.0 ? exp(-sum) : 0.0);
	ans *= pow(5.64189,(double)ndim);
	return ans;
}

int main(void)
{
	unsigned long n;
	int j,nt,ntries;
	float ave,dith,sumav,sumsd,var,*regn;

	printf("IDUM=\n");
	scanf("%ld",&idum);
	if (idum > 0) idum = -idum;
	for (;;) {
		printf("ENTER N,NDIM,XOFF,DITH,NTRIES\n");
		if (scanf("%ld %d %f %f %d",&n,&ndim,&xoff,&dith,&ntries) == EOF) break;
		regn=vector(1,2*ndim);
		sumav=sumsd=0.0;
		for (nt=1;nt<=ntries;nt++) {
			for (j=1;j<=ndim;j++) {
				regn[j]=0.0;
				regn[ndim+j]=1.0;
			}
			miser(func,regn,ndim,n,dith,&ave,&var);
			sumav += SQR(ave-1.0);
			sumsd += sqrt(fabs(var));
		}
		sumav=sqrt(sumav/ntries);
		sumsd /= ntries;
		printf("Fractional error: actual,indicated= %12.6f% 12.6f\n",sumav,sumsd);
		free_vector(regn,1,2*ndim);
	}
	printf("Normal completion\n");
	return 0;
}
#undef NRANSI
