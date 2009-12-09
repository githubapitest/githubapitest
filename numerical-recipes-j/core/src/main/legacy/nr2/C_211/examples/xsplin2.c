
/* Driver for routine splin2 */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define M 10
#define N 10

int main(void)
{
	int i,j;
	float f,ff,x1x2,xx1,xx2,*x1,*x2,**y,**y2;

	x1=vector(1,N);
	x2=vector(1,N);
	y=matrix(1,M,1,N);
	y2=matrix(1,M,1,N);
	for (i=1;i<=M;i++) x1[i]=0.2*i;
	for (i=1;i<=N;i++) x2[i]=0.2*i;
	for (i=1;i<=M;i++) {
		for (j=1;j<=N;j++) {
			x1x2=x1[i]*x2[j];
			y[i][j]=x1x2*exp(-x1x2);
		}
	}
	splie2(x1,x2,y,M,N,y2);
	printf("%9s %12s %14s %12s\n","x1","x2","splin2","actual");
	for (i=1;i<=10;i++) {
		xx1=0.1*i;
		xx2=xx1*xx1;
		splin2(x1,x2,y,y2,M,N,xx1,xx2,&f);
		x1x2=xx1*xx2;
		ff=x1x2*exp(-x1x2);
		printf("%12.6f %12.6f %12.6f %12.6f\n",xx1,xx2,f,ff);
	}
	free_matrix(y2,1,M,1,N);
	free_matrix(y,1,M,1,N);
	free_vector(x2,1,N);
	free_vector(x1,1,N);
	return 0;
}
#undef NRANSI
