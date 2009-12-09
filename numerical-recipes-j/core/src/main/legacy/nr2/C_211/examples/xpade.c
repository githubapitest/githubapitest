
/* Driver for routine pade */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

double fn(double x)
{
	return (x == 0.0 ? 1.0 : log(1.0+x)/x);
}

#define NMAX 100

int main(void)
{
	int j,k,n;
	float resid;
	double b,d,fac,x,*c,*cc;

	c=dvector(0,NMAX);
	cc=dvector(0,NMAX);
	for (;;) {
		printf("Enter n for PADE routine:\n");
		if (scanf("%d",&n) == EOF) break;
		fac=1;
		for (j=1;j<=2*n+1;j++) {
			c[j-1]=fac/((double) j);
			cc[j-1]=c[j-1];
			fac = -fac;
		}
		pade(c,n,&resid);
		printf("Norm of residual vector= %16.8e\n",resid);
		printf("point, func. value, pade series, power series\n");
		for (j=1;j<=21;j++) {
			x=(j-1)*0.25;
			for (b=0.0,k=2*n+1;k>=1;k--) {
				b *= x;
				b += cc[k-1];
			}
			d=ratval(x,c,n,n);
			printf("%16.8f %16.8f %16.8f %16.8f\n",x,fn(x),d,b);
		}
	}
	free_dvector(cc,0,NMAX);
	free_dvector(c,0,NMAX);
	return 0;
}
#undef NRANSI
