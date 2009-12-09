
/* Driver for routine gaulag */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 64

float func(float x)
{
	return bessj0(x);
}

int main(void)
{
	int i,n;
	float alf=1.0,checkw,checkx,xx,*x,*w;

	x=vector(1,NP);
	w=vector(1,NP);
	for (;;) {
		printf("Enter N\n");
		if (scanf("%d",&n) == EOF) break;
		gaulag(x,w,n,alf);
		printf("%3s %10s %14s\n","#","x(i)","w(i)");
		for (i=1;i<=n;i++) printf("%3d %14.6e %14.6e\n",i,x[i],w[i]);
		checkx=checkw=0.0;
		for (i=1;i<=n;i++) {
			checkx += x[i];
			checkw += w[i];
		}
		printf("\nCheck value: %15.7e  should be: %15.7e\n",checkx,n*(n+alf));
		printf("\nCheck value: %15.7e  should be: %15.7e\n",checkw,
			exp(gammln(1.0+alf)));
		/* demonstrate the use of GAULAG for an integral */
		for (xx=0.0,i=1;i<=n;i++) xx += w[i]*func(x[i]);
		printf("\nIntegral from gaulag: %12.6f\n",xx);
		printf("Actual value:         %12.6f\n",1.0/(2.0*sqrt(2.0)));
	}
	free_vector(w,1,NP);
	free_vector(x,1,NP);
	return 0;
}
#undef NRANSI
