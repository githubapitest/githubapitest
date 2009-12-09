
/* Driver for routine chebev */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"

#define NVAL 40
#define PIO2 1.5707963

float func(float x)
{
	return x*x*(x*x-2.0)*sin(x);
}

int main(void)
{
	int i,mval;
	float a=(-PIO2),b=PIO2,x,c[NVAL];

	chebft(a,b,c,NVAL,func);
	/* Test Chebyshev evaluation routine */
	for (;;) {
		printf("\nHow many terms in Chebyshev evaluation?\n");
		printf("Enter n between 6 and %2d. (n=0 to end).\n",NVAL);
		scanf("%d",&mval);
		if ((mval <= 0) || (mval > NVAL)) break;
		printf("\n%9s %14s %16s \n","x","actual","chebyshev fit");
		for (i = -8;i<=8;i++) {
			x=i*PIO2/10.0;
			printf("%12.6f %12.6f %12.6f\n",
				x,func(x),chebev(a,b,c,mval,x));
		}
	}
	return 0;
}
#undef NRANSI
