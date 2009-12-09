
/* Driver for routine quad3d */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"

#define PI 3.1415927
#define NVAL 10

static float xmax;

float func(float x,float y,float z)
{
	return x*x+y*y+z*z;
}

float z1(float x,float y)
{
	return (float) -sqrt(xmax*xmax-x*x-y*y);
}

float z2(float x,float y)
{
	return (float) sqrt(xmax*xmax-x*x-y*y);
}

float yy1(float x)
{
	return (float) -sqrt(xmax*xmax-x*x);
}

float yy2(float x)
{
	return (float) sqrt(xmax*xmax-x*x);
}

int main(void)
{
	int i;
	float xmin,s;

	printf("Integral of r^2 over a spherical volume\n\n");
	printf("%13s %10s %11s\n","radius","QUAD3D","Actual");
	for (i=1;i<=NVAL;i++) {
		xmax=0.1*i;
		xmin = -xmax;
		s=quad3d(func,xmin,xmax);
		printf("%12.2f %12.6f %11.6f\n",
			xmax,s,4.0*PI*pow(xmax,5.0)/5.0);
	}
	return 0;
}
#undef NRANSI
