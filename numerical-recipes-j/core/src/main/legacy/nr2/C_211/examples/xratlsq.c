
/* Driver for routine ratlsq */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NMAX 100

double fn(double t)
{
	return atan(t);
}

int main(void)
{
	int j,kk,mm;
	double a,b,*cof,dev,eee,fit,xs;

	cof=dvector(0,NMAX);
	for (;;) {
		printf("enter a,b,mm,kk\n");
		if (scanf("%lf %lf %d %d",&a,&b,&mm,&kk) == EOF) break;
		ratlsq(fn,a,b,mm,kk,cof,&dev);
		for (j=0;j<=mm+kk;j++) printf("cof(%3d)=%27.15e\n",j,cof[j]);
		printf("maximum absolute deviation= %12.6f\n",dev);
		printf("    x        error        exact\n");
		printf("--------- ------------ ---------\n");
		for (j=1;j<=50;j++) {
			xs=a+(b-a)*(j-1.0)/49.0;
			fit=ratval(xs,cof,mm,kk);
			eee=fn(xs);
			printf("%10.5f %15.7e %15.7e\n",xs,fit-eee,eee);
		}
	}
	free_dvector(cof,0,NMAX);
	return 0;
}
#undef NRANSI
