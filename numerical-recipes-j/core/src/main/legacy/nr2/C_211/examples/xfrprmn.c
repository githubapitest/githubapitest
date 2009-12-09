
/* Driver for routine frprmn */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NDIM 3
#define FTOL 1.0e-6
#define PIO2 1.5707963

float func(float x[])
{
	return 1.0-bessj0(x[1]-0.5)*bessj0(x[2]-0.5)*bessj0(x[3]-0.5);
}

void dfunc(float x[],float df[])
{
	df[1]=bessj1(x[1]-0.5)*bessj0(x[2]-0.5)*bessj0(x[3]-0.5);
	df[2]=bessj0(x[1]-0.5)*bessj1(x[2]-0.5)*bessj0(x[3]-0.5);
	df[3]=bessj0(x[1]-0.5)*bessj0(x[2]-0.5)*bessj1(x[3]-0.5);
}

int main(void)
{
	int iter,k;
	float angl,fret,*p;

	p=vector(1,NDIM);
	printf("Program finds the minimum of a function\n");
	printf("with different trial starting vectors.\n");
	printf("True minimum is (0.5,0.5,0.5)\n");
	for (k=0;k<=4;k++) {
		angl=PIO2*k/4.0;
		p[1]=2.0*cos(angl);
		p[2]=2.0*sin(angl);
		p[3]=0.0;
		printf("\nStarting vector: (%6.4f,%6.4f,%6.4f)\n",
			p[1],p[2],p[3]);
		frprmn(p,NDIM,FTOL,&iter,&fret,func,dfunc);
		printf("Iterations: %3d\n",iter);
		printf("Solution vector: (%6.4f,%6.4f,%6.4f)\n",
			p[1],p[2],p[3]);
		printf("Func. value at solution %14f\n",fret);
	}
	free_vector(p,1,NDIM);
	return 0;
}
#undef NRANSI
