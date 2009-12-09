
/* Driver for routine ks2d1s */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NMAX 1000

int main(void)
{
	long idum;
	unsigned long j,jtrial,n1,ntrial;
	float d,factor,prob,u,v,*x1,*y1;

	x1=vector(1,NMAX);
	y1=vector(1,NMAX);
	for (;;) {
		printf("How many points?\n");
		if (scanf("%lu",&n1) == EOF) break;
		if (n1 > NMAX) {
			printf("n1 too large.\n");
			continue;
		}
		printf("What factor nonlinearity (0 to 1)?\n");
		for (;;) {
				if (scanf("%f",&factor) == EOF) {
						factor = -1.0;
						break;
				}
				if (factor < 0.0) {
						printf("factor less than 0\n");
						continue;
				}
				if (factor > 1.0) {
						printf("factor greater than 1\n");
						continue;
				}
				break;
		}
		if (factor == -1.0) break;
		printf("How many trials?\n");
		if (scanf("%lu",&ntrial) == EOF) break;
		idum = -289-ntrial-n1;
		for (jtrial=1;jtrial<=ntrial;jtrial++) {
			for (j=1;j<=n1;j++) {
				u=ran1(&idum);
				u=u*((1.0-factor)+u*factor);
				x1[j]=2.0*u-1.0;
				v=ran1(&idum);
				v=v*((1.0-factor)+v*factor);
				y1[j]=2.0*v-1.0;
			}
			ks2d1s(x1,y1,n1,quadvl,&d,&prob);
			printf("d, prob= %12.6f %12.6f\n",d,prob);
		}
	}
	free_vector(y1,1,NMAX);
	free_vector(x1,1,NMAX);
	printf("Normal completion\n");
	return 0;
}
#undef NRANSI
