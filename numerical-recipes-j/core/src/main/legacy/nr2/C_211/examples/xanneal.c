
/* Driver for routine anneal */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NCITY 10

int main(void)
{
	int i,ii,*iorder;
	long idum=(-111);
	float *x,*y;

	iorder=ivector(1,NCITY);
	x=vector(1,NCITY);
	y=vector(1,NCITY);
	for (i=1;i<=NCITY;i++) {
		x[i]=ran3(&idum);
		y[i]=ran3(&idum);
		iorder[i]=i;
	}
	anneal(x,y,iorder,NCITY);
	printf("*** System Frozen ***\n");
	printf("Final path:\n");
	printf("%8s %9s %12s\n","city","x","y");
	for (i=1;i<=NCITY;i++) {
		ii=iorder[i];
		printf("%4d %10.4f %10.4f\n",ii,x[ii],y[ii]);
	}
	free_vector(y,1,NCITY);
	free_vector(x,1,NCITY);
	free_ivector(iorder,1,NCITY);
	return 0;
}
#undef NRANSI
