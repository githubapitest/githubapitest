
/* Driver for routine fasper */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 90
#define MP 4096
#define NPR 11
#define TWOPI 6.2831853

int main(void)
{
	long idum=(-4);
	unsigned long j=0,jmax,n,nout;
	float prob,*px,*py,*x,*y;

	x=vector(1,NP);
	y=vector(1,NP);
	px=vector(1,MP);
	py=vector(1,MP);
	for (n=1;n<=NP+10;n++) {
		if (n != 3 && n != 4 && n != 6 && n != 21 &&
			n != 38 && n != 51 && n != 67 && n != 68 &&
			n != 83 && n != 93) {
			x[++j]=n;
			y[j]=0.75*cos(0.6*x[j])+gasdev(&idum);
		}
	}
	fasper(x,y,j,4.0,1.0,px,py,MP,&nout,&jmax,&prob);
	printf("fasper results for test signal (cos(0.6x) + noise):\n");
	printf("nout,jmax,prob=%ld %ld %12.6g\n",nout,jmax,prob);
	for (n=LMAX(1,jmax-NPR/2);n<=LMIN(nout,jmax+NPR/2);n++)
		printf("%ld %12.6f %12.6f\n",n,TWOPI*px[n],py[n]);
	free_vector(py,1,MP);
	free_vector(px,1,MP);
	free_vector(y,1,NP);
	free_vector(x,1,NP);
	return 0;
}
#undef NRANSI
