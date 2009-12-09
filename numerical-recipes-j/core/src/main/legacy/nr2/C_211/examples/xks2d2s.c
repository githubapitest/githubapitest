
/* Driver for routine ks2d2s */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NMAX 1000

int main(void)
{
	long idum;
	unsigned long j,jtrial,n1,n2,ntrial;
	float d,prob,shrink,u,v;
	float *x1,*y1,*x2,*y2;

	x1=vector(1,NMAX);
	y1=vector(1,NMAX);
	x2=vector(1,NMAX);
	y2=vector(1,NMAX);
	for (;;) {
		printf("Input N1,N2\n");
		if (scanf("%lu %lu",&n1,&n2) == EOF) break;
		if (n1 > NMAX) {
			printf("n1 too large.\n");
			continue;
		}
		if (n2 > NMAX) {
			printf("n2 too large.\n");
			continue;
		}
		printf("What shrinkage?\n");
		if (scanf("%f",&shrink) == EOF) break;
		printf("How many trials?\n");
		if (scanf("%lu",&ntrial) == EOF) break;
		if (ntrial > NMAX) {
			printf("Too many trials.\n");
			continue;
		}
		idum = -287-ntrial-n1-n2;
		for (jtrial=1;jtrial<=ntrial;jtrial++) {
			for (j=1;j<=n1;j++) {
				u=gasdev(&idum);
				v=gasdev(&idum)*shrink;
				x1[j]=u+v;
				y1[j]=u-v;
			}
			for (j=1;j<=n2;j++) {
				u=gasdev(&idum)*shrink;
				v=gasdev(&idum);
				x2[j]=u+v;
				y2[j]=u-v;
			}
			ks2d2s(x1,y1,n1,x2,y2,n2,&d,&prob);
			printf("d, prob= %12.6f %12.6f\n",d,prob);
		}
	}
	free_vector(y2,1,NMAX);
	free_vector(x2,1,NMAX);
	free_vector(y1,1,NMAX);
	free_vector(x1,1,NMAX);
	printf("Normal completion\n");
	return 0;
}
#undef NRANSI
