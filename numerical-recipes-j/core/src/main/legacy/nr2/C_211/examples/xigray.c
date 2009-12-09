
/* Driver for routine igray */

#include <stdio.h>
#define NRANSI
#include "nr.h"

int main(void)
{
	unsigned long jp,n,ng,nmax,nmin,nni,nxor;

	for (;;) {
		printf("input nmin,nmax: \n");
		if (scanf("%lu %lu",&nmin,&nmax) == EOF) break;
		jp=(nmax-nmin)/11;
		if (jp < 1) jp=1;
		printf("n, Gray[n], Gray(Gray[n]), Gray[n] ^ Gray[n+1]\n");
		for (n=nmin;n<=nmax;n++) {
			ng=igray(n,1);
			nni=igray(ng,-1);
			if (nni != n) printf("WRONG ! AT %d %d %d\n",n,ng,nni);
			if (!((n-nmin) % jp)) {
					nxor=ng ^ igray(n+1,1);
					printf("%lu %lu %lu %lu\n",n,ng,nni,nxor);
			}
		}
	}
	printf("Normal completion\n");
	return 0;
}
#undef NRANSI
