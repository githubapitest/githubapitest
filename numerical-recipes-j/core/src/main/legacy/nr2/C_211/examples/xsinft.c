
/* Driver for routine sinft */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define EPS 1.0e-3
#define NP 16
#define WIDTH 30.0
#define PI 3.1415926

int main(void)
{
	float big,per,scal,small,*data;
	int i,j,nlim;

	data=vector(1,NP);
	for (;;) {
		printf("\nPeriod of sinusoid in channels (3-%2d)\n",NP);
		scanf("%f",&per);
		if (per <= 0.0) break;
		for (i=1;i<=NP;i++)
			data[i]=sin(2.0*PI*(i-1)/per);
		sinft(data,NP);
		big = -1.0e10;
		small=1.0e10;
		for (i=1;i<=NP;i++) {
			if  (data[i] < small) small=data[i];
			if  (data[i] > big) big=data[i];
		}
		scal=WIDTH/(big-small);
		for (i=1;i<=NP;i++) {
			nlim=(int) (scal*(data[i]-small)+EPS+0.5);
			printf("%4d  ",i);
			for (j=1;j<=nlim+1;j++) printf("*");
			printf("\n");
		}
		printf("press RETURN to continue ...\n");
		(void) getchar();
		sinft(data,NP);
		big = -1.0e10;
		small=1.0e10;
		for (i=1;i<=NP;i++) {
			if (data[i] < small) small=data[i];
			if (data[i] > big) big=data[i];
		}
		scal=WIDTH/(big-small);
		for (i=1;i<=NP;i++) {
			nlim=(int) (scal*(data[i]-small)+EPS+0.5);
			printf("%4d  ",i);
			for (j=1;j<=nlim+1;j++) printf("*");
			printf("\n");
		}
	}
	free_vector(data,1,NP);
	return 0;
}
#undef NRANSI
