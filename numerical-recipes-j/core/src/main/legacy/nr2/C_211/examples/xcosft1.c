
/* Driver for routine cosft1 */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define EPS 1.0e-3
#define NP 17
#define WIDTH 30.0
#define PI 3.1415926

int main(void)
{
	int i,j,nlim;
	float big,per,scal,small,*data;

	data=vector(1,NP);
	for (;;) {
		printf("Period of cosine in channels (2-%2d)\n",NP);
		scanf("%f",&per);
		if (per <= 0.0) break;
		for (i=1;i<=NP;i++)
			data[i]=cos(2.0*PI*(i-1)/per);
		cosft1(data,NP-1);
		big = -1.0e10;
		small=1.0e10;
		for (i=1;i<=NP;i++) {
			if (data[i] < small) small=data[i];
			if (data[i] > big) big=data[i];
		}
		scal=WIDTH/(big-small);
		for (i=1;i<=NP;i++) {
			nlim=(int) (0.5+scal*(data[i]-small)+EPS);
			printf("%4d %6.2f ",i,data[i]);
			for (j=1;j<=nlim+1;j++) printf("*");
			printf("\n");
		}
		printf("press RETURN to continue ...\n");
		(void) getchar();
		cosft1(data,NP-1);
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
	free_vector(data,1,NP);
	return 0;
}
#undef NRANSI
