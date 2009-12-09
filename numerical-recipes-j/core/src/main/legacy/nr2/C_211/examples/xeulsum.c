
/* Driver for routine eulsum */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NVAL 40

int main(void)
{
	int i,j,mval;
	float sum,term,x,xpower,*wksp;

	wksp=vector(1,NVAL);
	/* evaluate ln(1+x)=x-x^2/2+x^3/3-x^4/4 ... for -1<x<1 */
	for (;;) {
		printf("\nHow many terms in polynomial?\n");
		printf("Enter n between 1 and %2d. (n=0 to end) ",NVAL);
		scanf("%d",&mval);
		printf("\n");
		if ((mval <= 0) || (mval > NVAL)) break;
		printf("%9s %14s %14s\n","x","actual","polynomial");
		for (i = -8;i<=8;i++) {
			x=i/10.0;
			sum=0.0;
			xpower = -1;
			for (j=1;j<=mval;j++) {
				xpower *= (-x);
				term=xpower/j;
				eulsum(&sum,term,j,wksp);
			}
			printf("%12.6f %12.6f %12.6f\n",x,log(1.0+x),sum);
		}
	}
	free_vector(wksp,1,NVAL);
	return 0;
}
#undef NRANSI
