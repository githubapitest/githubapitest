
/* Driver for routine df1dim */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NDIM 3

int ncom;   /* defining declarations */
float *pcom,*xicom;
void (*nrdfun)(float [], float []);

void dfunc(float x[], float df[])
{
	int i;

	for (i=1;i<=3;i++) df[i]=(x[i]-1.0)*(x[i]-1.0);
}

int main(void)
{
	ncom=NDIM;
	pcom=vector(1,ncom);
	xicom=vector(1,ncom);
	nrdfun=dfunc;
	printf("\nEnter vector direction along which to\n");
	printf("plot the function. Minimum is in the\n");
	printf("direction 1.0 1.0 1.0 - enter x y z:\n\n");
	pcom[1]=pcom[2]=pcom[3]=0.0;
	scanf("%f %f %f",&xicom[1],&xicom[2],&xicom[3]);
	scrsho(df1dim);
	free_vector(xicom,1,ncom);
	free_vector(pcom,1,ncom);
	return 0;
}
#undef NRANSI
