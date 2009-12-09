
/* Driver for routine zrhqr */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define M 4     /* degree of polynomial */
#define MP1 (M+1)   /* no. of polynomial coefficients */

int main(void)
{
	static float a[MP1]={-1.0,0.0,0.0,0.0,1.0};
	float *rti,*rtr;
	int i;

	rti=vector(1,M);
	rtr=vector(1,M);
	printf("\nRoots of polynomial x^4-1\n");
	printf("\n%15s %15s\n","Real","Complex");
		zrhqr(a,M,rtr,rti);
		for (i=1;i<=M;i++) {
			printf("%5d %12.6f %12.6f\n",i,rtr[i],rti[i]);
		}
	free_vector(rtr,1,M);
	free_vector(rti,1,M);
	return 0;
}
#undef NRANSI
