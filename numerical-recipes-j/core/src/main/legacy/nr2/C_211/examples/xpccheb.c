
/* Driver for routine pccheb */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NCHECK 15
#define NFEW 13
#define NMANY 17
#define NMAX 100
#define PI 3.14159265

int main(void)
{
	int i,j;
	float a=(-PI),b=PI,fac,f,sum,sume,py,py2,*c,*d,*e,*ee;

	c=vector(0,NMAX-1);
	d=vector(0,NMAX-1);
	e=vector(0,NMAX-1);
	ee=vector(0,NMAX-1);
	/* put power series of cos(PI*y) into e */
	fac=1.0;
	e[0]=ee[0]=0.0;
	for (j=0;j<NMANY;j++) {
		i=j & 3;    /* tricky way to perform j % 4 */
		if (i == 1 || i == 3) e[j]=0.0;
		else if (i == 0) e[j]=1.0/fac;
		else e[j] = -1.0/fac;
		fac *= (j+1);
		ee[j]=e[j];
	}
	pcshft((-2.0-b-a)/(b-a),(2.0-b-a)/(b-a),e,NMANY);
	/* i.e., inverse of pcshft(a,b,...) which we do below */
	pccheb(e,c,NMANY);
	printf("Index, series, Chebyshev coefficients\n");
	for (j=0;j<NMANY;j+=2)
		printf("%3d %15.6e %15.6e\n",j,e[j],c[j]);
	chebpc(c,d,NFEW);
	pcshft(a,b,d,NFEW);
	printf("Index, new series, coefficient ratios\n");
	for (j=0;j<NFEW;j+=2) {
		printf("%3d %15.6e %15.6e\n",
			j,d[j],d[j]/(ee[j]+1.0e-30));
	}
	printf("      Point tested, function value, error power series, error Cheb.\n");
	for (i=0;i<=NCHECK;i++) {
		py=a+i*(b-a)/(float)NCHECK;
		py2=py*py;
		sum=sume=0.0;
		fac=1.0;
		for (j=0;j<NFEW;j+=2) {
			sum += fac*d[j];
			sume += fac*ee[j];
			fac *= py2;
		}
		f=cos(py);
		printf("check: %15.6e %15.6e %15.6e %15.6e\n",py,f,sume-f,sum-f);
	}
	free_vector(ee,0,NMAX-1);
	free_vector(e,0,NMAX-1);
	free_vector(d,0,NMAX-1);
	free_vector(c,0,NMAX-1);
	return 0;
}
#undef NRANSI
