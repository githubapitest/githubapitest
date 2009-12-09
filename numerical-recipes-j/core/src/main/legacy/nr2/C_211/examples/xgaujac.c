
/* Driver for routine gaujac */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 64
#define PIBY2 1.5707963

float func(float ak,float x)
{
	return 1.0/sqrt(1.0-ak*ak*(1.0+x)/2.0);
}

int main(void)
{
	int i,n;
	float ak,alf=(-0.5),bet=(-0.5),checkw,checkx,xx,*x,*w;

	x=vector(1,NP);
	w=vector(1,NP);
	for (;;) {
		printf("Enter N\n");
		if (scanf("%d",&n) == EOF) break;
		gaujac(x,w,n,alf,bet);
		printf("%3s %10s %14s\n","#","x(i)","w(i)");
		for (i=1;i<=n;i++) printf("%3d %14.6e %14.6e\n",i,x[i],w[i]);
		checkx=checkw=0.0;
		for (i=1;i<=n;i++) {
			checkx += x[i];
			checkw += w[i];
		}
		printf("\nCheck value: %15.7e  should be: %15.7e\n",
			checkx,n*(bet-alf)/(alf+bet+2*n));
		printf("\nCheck value: %15.7e  should be: %15.7e\n",
			checkw,exp(gammln(1.0+alf)+gammln(1.0+bet)-
			gammln(2.0+alf+bet))*pow(2.0,alf+bet+1.0));
		/* demonstrate the use of GAUJAC for an integral */
		ak=0.5;
		for (xx=0.0,i=1;i<=n;i++) xx += w[i]*func(ak,x[i]);
		printf("\nIntegral from gaujac: %12.6f\n",xx);
		printf("Actual value:         %12.6f\n",2.0*ellf(PIBY2,ak));
	}
	free_vector(w,1,NP);
	free_vector(x,1,NP);
	return 0;
}
#undef NRANSI
