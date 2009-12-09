
/* Driver for routine bsstep */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 4

float dxsav,*xp,**yp;   /* defining declarations */
int kmax,kount;

int nrhs;   /* counts function evaluations */

void derivs(float x,float y[],float dydx[])
{
	nrhs++;
	dydx[1] = -y[2];
	dydx[2]=y[1]-(1.0/x)*y[2];
	dydx[3]=y[2]-(2.0/x)*y[3];
	dydx[4]=y[3]-(3.0/x)*y[4];
}

int main(void)
{
	int i,nbad,nok;
	float eps=1.0e-4,h1=0.1,hmin=0.0,x1=1.0,x2=10.0,*ystart;

	ystart=vector(1,N);
	xp=vector(1,200);
	yp=matrix(1,10,1,200);
	ystart[1]=bessj0(x1);
	ystart[2]=bessj1(x1);
	ystart[3]=bessj(2,x1);
	ystart[4]=bessj(3,x1);
	nrhs=0;
	kmax=100;
	dxsav=(x2-x1)/20.0;
	odeint(ystart,N,x1,x2,eps,h1,hmin,&nok,&nbad,derivs,bsstep);
	printf("\n%s %13s %3d\n","successful steps:"," ",nok);
	printf("%s %20s %3d\n","bad steps:"," ",nbad);
	printf("%s %9s %3d\n","function evaluations:"," ",nrhs);
	printf("\n%s %3d\n","stored intermediate values:    ",kount);
	printf("\n%8s %18s %15s\n","x","integral","bessj(3,x)");
	for (i=1;i<=kount;i++)
		printf("%10.4f %16.6f %14.6f\n",
			xp[i],yp[4][i],bessj(3,xp[i]));
	free_matrix(yp,1,10,1,200);
	free_vector(xp,1,200);
	free_vector(ystart,1,N);
	return 0;
}
#undef NRANSI
