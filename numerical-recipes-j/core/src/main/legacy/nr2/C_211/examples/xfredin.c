
/* Driver for routine fredin */

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
	float a=0.0,ans,b=PI/2.0,x,*f;
	float *t,*w;

	t=vector(1,N);
	f=vector(1,N);
	w=vector(1,N);
	fred2(N,a,b,t,f,w,g,ak);
	for (;;) {
		printf("Enter T between 0 and PI/2\n");
		if (scanf("%f",&x) == EOF) break;
		ans=fredin(x,N,a,b,t,f,w,g,ak);
		printf("T, Calculated answer, True answer\n");
		printf("%10.6f %10.6f %10.6f\n",x,ans,sqrt(x));
	}
	free_vector(w,1,N);
	free_vector(f,1,N);
	free_vector(t,1,N);
	return 0;
}
#undef NRANSI
