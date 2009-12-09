
/* Driver for routine rkqs */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 4

void derivs(float x,float y[],float dydx[])
{
	dydx[1] = -y[2];
	dydx[2]=y[1]-(1.0/x)*y[2];
	dydx[3]=y[2]-(2.0/x)*y[3];
	dydx[4]=y[3]-(3.0/x)*y[4];
}

int main(void)
{
	int i,j;
	float eps,hdid,hnext,htry,x=1.0,*y,*dydx,*dysav,*ysav,*yscal;

	y=vector(1,N);
	dydx=vector(1,N);
	dysav=vector(1,N);
	ysav=vector(1,N);
	yscal=vector(1,N);
	ysav[1]=bessj0(x);
	ysav[2]=bessj1(x);
	ysav[3]=bessj(2,x);
	ysav[4]=bessj(3,x);
	derivs(x,ysav,dysav);
	for (i=1;i<=N;i++) yscal[i]=1.0;
	htry=0.6;
	printf("%10s %11s %12s %13s\n","eps","htry","hdid","hnext");
	for (i=1;i<=15;i++) {
		eps=exp((double) -i);
		x=1.0;
		for (j=1;j<=N;j++) {
			y[j]=ysav[j];
			dydx[j]=dysav[j];
		}
		rkqs(y,dydx,N,&x,htry,eps,yscal,&hdid,&hnext,derivs);
		printf("%13f %8.2f %14.6f %12.6f \n",eps,htry,hdid,hnext);
	}
	free_vector(yscal,1,N);
	free_vector(ysav,1,N);
	free_vector(dysav,1,N);
	free_vector(dydx,1,N);
	free_vector(y,1,N);
	return 0;
}
#undef NRANSI
