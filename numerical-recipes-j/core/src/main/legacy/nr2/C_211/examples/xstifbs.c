
/* Driver for routine stifbs */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

int kmax,kount;        /* defining declarations */
float *xp,**yp,dxsav;

int main(void)
{
	float eps,hstart,x1=0.0,x2=50.0,y[4];
	int nbad,nok;

	for (;;) {
		printf("Enter eps,hstart\n");
		if (scanf("%f %f",&eps,&hstart) == EOF) break;
		kmax=0;
		y[1]=y[2]=1.0;
		y[3]=0.0;
		odeint(y,3,x1,x2,eps,hstart,0.0,&nok,&nbad,derivs,stifbs);
		printf("\n%s %13s %3d\n","successful steps:"," ",nok);
		printf("%s %20s %3d\n","bad steps:"," ",nbad);
		printf("Y(END) = %12.6f %12.6f %12.6f\n",y[1],y[2],y[3]);
	}
	printf("Normal completion\n");
	return 0;
}
#undef NRANSI
