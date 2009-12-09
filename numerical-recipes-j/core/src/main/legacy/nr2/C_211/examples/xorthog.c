
/* Driver for routine orthog */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 64

float func(float x)
{
	return 1.0/SQR(1.0+x);
}

int main(void)
{
	int i,n;
	float amu0,check,xx,*a,*b,*x,*w,*anu,*alpha,*beta;

	a=vector(1,NP);
	b=vector(1,NP);
	x=vector(1,NP);
	w=vector(1,NP);
	anu=vector(1,2*NP);
	alpha=vector(1,2*NP-1);
	beta=vector(1,2*NP-1);

	/* Test with w[x] = -log x */
	for (;;) {
		printf("Enter N\n");
		if (scanf("%d",&n) == EOF) break;
		alpha[1]=0.5;
		beta[1]=1.0;
		for (i=2;i<=2*n-1;i++) {
			alpha[i]=0.5;
			beta[i]=1.0/(4.0*(4.0-1.0/((i-1)*(i-1))));
		}
		anu[1]=1.0;
		anu[2] = -0.25;
		for (i=2;i<=2*n-1;i++) anu[i+1] = -anu[i]*i*(i-1)/(2.0*(i+1)*(2*i-1));
		orthog(n,anu,alpha,beta,a,b);
		amu0=1.0;
		gaucof(n,a,b,amu0,x,w);
		printf("%3s %10s %14s\n","#","x(i)","w(i)");
		for (i=1;i<=n;i++) printf("%3d %14.6e %14.6e\n",i,x[i],w[i]);
		for (check=0.0,i=1;i<=n;i++) check += w[i];
		printf("\nCheck value: %15.7e  should be: %15.7e\n",check,amu0);
		/* demonstrate the use of ORTHOG for an integral */
		for (xx=0.0,i=1;i<=n;i++) xx += w[i]*func(x[i]);
		printf("\nIntegral from orthog: %12.6f\n",xx);
		printf("Actual value:         %12.6f\n",log(2.0));
	}
	free_vector(beta,1,2*NP-1);
	free_vector(alpha,1,2*NP-1);
	free_vector(anu,1,2*NP);
	free_vector(w,1,NP);
	free_vector(x,1,NP);
	free_vector(b,1,NP);
	free_vector(a,1,NP);
	return 0;
}
#undef NRANSI
