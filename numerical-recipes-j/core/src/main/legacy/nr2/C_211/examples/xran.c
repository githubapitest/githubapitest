
/* Driver for routines ran0, ran1, ran2, ran3 */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"

#define PI 3.1415926

static unsigned long twotoj[16]={0x1L,0x2L,0x4L,0x8L,0x10L,0x20L,0x40L,0x80L,
	0x100L,0x200L,0x400L,0x800L,0x1000L,0x2000L,0x4000L,0x8000L};

float fnc(float x1,float x2,float x3,float x4)
{
	return (float) sqrt(x1*x1+x2*x2+x3*x3+x4*x4);
}

void integ(float (*func)(long *))
{
	long idum=(-1),iy[4],jpower,k;
	int i,j;
	float x1,x2,x3,x4,yprob[4];

	/* Calculates pi statistically using volume of unit n-sphere */
	for (i=1;i<=3;i++) iy[i]=0;
	printf("volume of unit n-sphere, n = 2, 3, 4\n");
	printf("# points      pi        (4/3)*pi    (1/2)*pi^2\n\n");
	for (j=1;j<=15;j++) {
		for (k=twotoj[j-1];k>=0;k--) {
			x1=(*func)(&idum);
			x2=(*func)(&idum);
			x3=(*func)(&idum);
			x4=(*func)(&idum);
			if (fnc(x1,x2,0.0,0.0) < 1.0) ++iy[1];
			if (fnc(x1,x2,x3,0.0) < 1.0) ++iy[2];
			if (fnc(x1,x2,x3,x4) < 1.0) ++iy[3];
		}
		jpower=twotoj[j];
		for (i=1;i<=3;i++)
			yprob[i]=(float) twotoj[i+1]*iy[i]/jpower;
		printf("%6ld %12.6f %12.6f %12.6f\n",
			jpower,yprob[1],yprob[2],yprob[3]);
	}
	printf("\nactual %12.6f %12.6f %12.6f\n",
		PI,4.0*PI/3.0,0.5*PI*PI);
}

int main(void)
{
	printf("\nTesting ran0:\n"); integ(ran0);
	printf("\nTesting ran1:\n"); integ(ran1);
	printf("\nTesting ran2:\n"); integ(ran2);
	printf("\nTesting ran3:\n"); integ(ran3);
	return 0;
}
#undef NRANSI
