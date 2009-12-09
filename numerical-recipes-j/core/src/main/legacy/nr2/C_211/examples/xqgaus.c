
/* Driver for routine qgaus */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"

#define X1 0.0
#define X2 5.0
#define NVAL 10

float func(float x)
{
	return x*exp(-x);
}

int main(void)
{
	float dx,ss,x;
	int i;

	dx=(X2-X1)/NVAL;
	printf("\n%s %10s %13s\n\n","0.0 to","qgaus","expected");
	for (i=1;i<=NVAL;i++) {
		x=X1+i*dx;
		ss=qgaus(func,X1,x);
		printf("%5.2f %12.6f %12.6f\n",x,ss,
			(-(1.0+x)*exp(-x)+(1.0+X1)*exp(-X1)));
	}
	return 0;
}
#undef NRANSI
