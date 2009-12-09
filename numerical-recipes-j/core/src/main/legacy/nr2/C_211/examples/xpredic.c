
/* Driver for routine predic */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NPTS 500
#define NPOLES 10
#define NFUT 20
#define PI 3.1415926

float f(int n,int npts)
{
	return exp(-(1.0*n)/npts)*sin(2.0*PI*n/50.0)
		+exp(-(2.0*n)/npts)*sin(2.2*PI*n/50.0);
}

int main(void)
{
	int i;
	float dum,*d,*future,*data;

	d=vector(1,NPOLES);
	future=vector(1,NFUT);
	data=vector(1,NPTS);
	for (i=1;i<=NPTS;i++)
		data[i]=f(i,NPTS);
	memcof(data,NPTS,NPOLES,&dum,d);
	fixrts(d,NPOLES);
	predic(data,NPTS,d,NPOLES,future,NFUT);
	printf("%6s %11s %12s\n","I","Actual","PREDIC");
	for (i=1;i<=NFUT;i++)
		printf("%6d %12.6f %12.6f\n",i,f(i+NPTS,NPTS),future[i]);
	free_vector(data,1,NPTS);
	free_vector(future,1,NFUT);
	free_vector(d,1,NPOLES);
	return 0;
}
#undef NRANSI
