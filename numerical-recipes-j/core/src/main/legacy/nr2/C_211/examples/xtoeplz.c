
/* Driver for routine toeplz */

#include <stdio.h>
#define NRANSI
#include "nr.h"

#define N 5
#define TWON (2*N)

int main(void)
{
	int i,j;
	float sum,r[TWON+1],x[N+1],y[N+1];

	for (i=1;i<=N;i++) y[i]=0.1*i;
	for (i=1;i<TWON;i++) r[i]=1.0/i;
	toeplz(r,x,y,N);
	printf("Solution vector:\n");
	for (i=1;i<=N;i++)
		printf("%7s%1d%s %13f\n","x[",i,"] =",x[i]);
	printf("\nTest of solution:\n");
	printf("%13s %12s\n","mtrx*soln","original");
	for (i=1;i<=N;i++) {
		sum=0.0;
		for (j=1;j<=N;j++)
			sum += (r[N+i-j]*x[j]);
		printf("%12.4f %12.4f\n",sum,y[i]);
	}
	return 0;
}
#undef NRANSI
