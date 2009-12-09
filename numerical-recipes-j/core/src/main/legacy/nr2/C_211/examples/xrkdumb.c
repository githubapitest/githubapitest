
/* Driver for routine rkdumb */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NVAR 4
#define NSTEP 150

void derivs(float x,float y[],float dydx[])
{
	dydx[1] = -y[2];
	dydx[2]=y[1]-(1.0/x)*y[2];
	dydx[3]=y[2]-(2.0/x)*y[3];
	dydx[4]=y[3]-(3.0/x)*y[4];
}

extern float **y,*xx;   /* referencing declaration */

int main(void)
{
	int j;
	float x1=1.0,x2=20.0,*vstart;

	vstart=vector(1,NVAR);
	/* Note: The arrays xx and y must have indices up to NSTEP+1 */
	xx=vector(1,NSTEP+1);
	y=matrix(1,NVAR,1,NSTEP+1);
	vstart[1]=bessj0(x1);
	vstart[2]=bessj1(x1);
	vstart[3]=bessj(2,x1);
	vstart[4]=bessj(3,x1);
	rkdumb(vstart,NVAR,x1,x2,NSTEP,derivs);
	printf("%8s %17s %10s\n","x","integrated","bessj3");
	for (j=10;j<=NSTEP;j+=10)
		printf("%10.4f %14.6f %12.6f\n",
			xx[j],y[4][j],bessj(3,xx[j]));
	free_matrix(y,1,NVAR,1,NSTEP+1);
	free_vector(xx,1,NSTEP+1);
	free_vector(vstart,1,NVAR);
	return 0;
}
#undef NRANSI
