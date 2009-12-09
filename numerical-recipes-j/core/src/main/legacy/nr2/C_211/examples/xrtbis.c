
/* Driver for routine rtbis */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 100
#define NBMAX 20
#define X1 1.0
#define X2 50.0

static float fx(float x)
{
	return bessj0(x);
}

int main(void)
{
	int i,nb=NBMAX;
	float xacc,root,*xb1,*xb2;

	xb1=vector(1,NBMAX);
	xb2=vector(1,NBMAX);
	zbrak(fx,X1,X2,N,xb1,xb2,&nb);
	printf("\nRoots of bessj0:\n");
	printf("%21s %15s\n","x","f(x)");
	for (i=1;i<=nb;i++) {
		xacc=(1.0e-6)*(xb1[i]+xb2[i])/2.0;
		root=rtbis(fx,xb1[i],xb2[i],xacc);
		printf("root %3d %14.6f %14.6f\n",i,root,fx(root));
	}
	free_vector(xb2,1,NBMAX);
	free_vector(xb1,1,NBMAX);
	return 0;
}
#undef NRANSI
