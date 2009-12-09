
/* Driver for routine bandec */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

int main(void)
{
	float d,**a,**al,*b,*x;
	unsigned long i,j,*indx;
	long idum=(-1);

	a=matrix(1,7,1,4);
	x=vector(1,7);
	b=vector(1,7);
	al=matrix(1,7,1,2);
	indx=lvector(1,7);
	for (i=1;i<=7;i++) {
		x[i]=ran1(&idum);
		for (j=1;j<=4;j++) {
			a[i][j]=ran1(&idum);
		}
	}
	banmul(a,7,2,1,x,b);
	for (i=1;i<=7;i++) printf("%ld %12.6f %12.6f\n",i,b[i],x[i]);
	bandec(a,7,2,1,al,indx,&d);
	banbks(a,7,2,1,al,indx,b);
	for (i=1;i<=7;i++) printf("%ld %12.6f %12.6f\n",i,b[i],x[i]);
	free_lvector(indx,1,7);
	free_matrix(al,1,7,1,2);
	free_vector(b,1,7);
	free_vector(x,1,7);
	free_matrix(a,1,7,1,4);
	return 0;
}
#undef NRANSI
