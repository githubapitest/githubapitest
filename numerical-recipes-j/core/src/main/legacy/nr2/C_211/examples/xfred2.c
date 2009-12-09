
/* Driver for routine fred2 */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 8
#define PI 3.1415927

float g(float t)
{
	return sqrt(t)-pow(PI/2.0,2.25)*pow(t,0.75)/2.25;
}

float ak(float t,float s)
{
	return pow(t*s,0.75);
}

int main(void)
{
	int i;
	float a=0.0,b=PI/2.0,*f;
	float *t,*w;

	t=vector(1,N);
	f=vector(1,N);
	w=vector(1,N);
	fred2(N,a,b,t,f,w,g,ak);
	/* Compare with exact solution */
	printf("Abscissa, Calc soln, True soln\n");
	for (i=1;i<=N;i++) printf("%10.6f %10.6f %10.6f\n",t[i],f[i],sqrt(t[i]));
	free_vector(w,1,N);
	free_vector(f,1,N);
	free_vector(t,1,N);
	return 0;
}
#undef NRANSI
