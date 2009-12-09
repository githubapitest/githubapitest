
/* Driver for routine fitexy */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NPT 30

int main(void)
{
	long idum=(-1);
	int j;
	float a,b,chi2,q,sa,sb,siga,sigb;
	float *x,*y,*dx,*dy,*dz;

	x=vector(1,NPT);
	y=vector(1,NPT);
	dx=vector(1,NPT);
	dy=vector(1,NPT);
	dz=vector(1,NPT);
	for (j=1;j<=NPT;j++) {
		dx[j]=0.1+ran1(&idum);
		dy[j]=0.1+ran1(&idum);
		dz[j]=0.0;
		x[j]=10.0+10.0*gasdev(&idum);
		y[j]=2.0*x[j]-5.0+dy[j]*gasdev(&idum);
		x[j] += dx[j]*gasdev(&idum);
	}
	printf("Values of a,b,siga,sigb,chi2,q:\n");
	printf("Fit with x and y errors gives:\n");
	fitexy(x,y,NPT,dx,dy,&a,&b,&siga,&sigb,&chi2,&q);
	printf("%11.6f %11.6f %11.6f %11.6f %11.6f %11.6f\n\n",
		a,b,siga,sigb,chi2,q);
	printf("Setting x errors to zero gives:\n");
	fitexy(x,y,NPT,dz,dy,&a,&b,&siga,&sigb,&chi2,&q);
	printf("%11.6f %11.6f %11.6f %11.6f %11.6f %11.6f\n",
		a,b,siga,sigb,chi2,q);
	printf("...to be compared with fit result:\n");
	fit(x,y,NPT,dy,1,&a,&b,&siga,&sigb,&chi2,&q);
	printf("%11.6f %11.6f %11.6f %11.6f %11.6f %11.6f\n\n",
		a,b,siga,sigb,chi2,q);
	printf("Setting y errors to zero gives:\n");
	fitexy(x,y,NPT,dx,dz,&a,&b,&siga,&sigb,&chi2,&q);
	printf("%11.6f %11.6f %11.6f %11.6f %11.6f %11.6f\n",
		a,b,siga,sigb,chi2,q);
	printf("...to be compared with fit result:\n");
	fit(y,x,NPT,dx,1,&a,&b,&siga,&sigb,&chi2,&q);
	sa=sqrt(siga*siga+SQR(sigb*(a/b)))/b;
	sb=sigb/(b*b);
	printf("%11.6f %11.6f %11.6f %11.6f %11.6f %11.6f\n",
		-a/b,1.0/b,sa,sb,chi2,q);
	free_vector(dz,1,NPT);
	free_vector(dy,1,NPT);
	free_vector(dx,1,NPT);
	free_vector(y,1,NPT);
	free_vector(x,1,NPT);
	return 0;
}
#undef NRANSI
