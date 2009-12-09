
/* Driver for routine realft */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define EPS 1.0e-3
#define NP 32
#define WIDTH 50.0
#define PI 3.1415926

int main(void)
{
	int i,j,n=NP/2,nlim;
	float big,per,scal,small,*data,*size;

	data=vector(1,NP);
	size=vector(1,NP/2+1);
	for (;;) {
		printf("Period of sinusoid in channels (2-%2d)\n",NP);
		scanf("%f",&per);
		if (per <= 0.0) break;
		for (i=1;i<=NP;i++)
			data[i]=cos(2.0*PI*(i-1)/per);
		realft(data,NP,1);
		big = -1.0e10;
		for (i=2;i<=n;i++) {
			size[i]=sqrt(SQR(data[2*i-1])+SQR(data[2*i]));
			if (size[i] > big) big=size[i];
		}
		size[1]=fabs(data[1]);
		if (size[1] > big) big=size[1];
		size[n+1]=fabs(data[2]);
		if (size[n+1] > big) big=size[n+1];
		scal=WIDTH/big;
		for (i=1;i<=n;i++) {
			nlim=(int) (0.5+scal*size[i]+EPS);
			printf("%4d ",i);
			for (j=1;j<=nlim+1;j++) printf("*");
			printf("\n");
		}
		printf("press RETURN to continue ...\n");
		(void) getchar();
		realft(data,NP,-1);
		big = -1.0e10;
		small=1.0e10;
		for (i=1;i<=NP;i++) {
			if (data[i] < small) small=data[i];
			if (data[i] > big) big=data[i];
		}
		scal=WIDTH/(big-small);
		for (i=1;i<=NP;i++) {
			nlim=(int) (0.5+scal*(data[i]-small)+EPS);
			printf("%4d ",i);
			for (j=1;j<=nlim+1;j++) printf("*");
			printf("\n");
		}
	}
	free_vector(size,1,NP/2+1);
	free_vector(data,1,NP);
	return 0;
}
#undef NRANSI
