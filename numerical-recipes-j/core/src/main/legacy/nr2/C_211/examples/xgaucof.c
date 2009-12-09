
/* Driver for routine gaucof */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 64
#define SQRTPI 1.7724539

int main(void)
{
	/* Test with Gauss-Hermite */
	int i,n;
	float amu0,check,*a,*b,*x,*w;

	a=vector(1,NP);
	b=vector(1,NP);
	x=vector(1,NP);
	w=vector(1,NP);
	for (;;) {
		printf("Enter N:\n");
		if (scanf("%d",&n) == EOF) break;
		for (i=1;i<n;i++) {
			a[i]=0.0;
			b[i+1]=i*0.5;
		}
		a[n]=0.0;
		/* b[1] is arbitrary for call to tqli */
		amu0=SQRTPI;
		gaucof(n,a,b,amu0,x,w);
		printf("%3s %10s %14s\n","#","x(i)","w(i)");
		for (i=1;i<=n;i++) printf("%3d %14.6e %14.6e\n",i,x[i],w[i]);
		for (check=0.0,i=1;i<=n;i++) check += w[i];
		printf("\nCheck value: %15.7e  should be: %15.7e\n",check,SQRTPI);
	}
	free_vector(w,1,NP);
	free_vector(x,1,NP);
	free_vector(b,1,NP);
	free_vector(a,1,NP);
	return 0;
}
#undef NRANSI
