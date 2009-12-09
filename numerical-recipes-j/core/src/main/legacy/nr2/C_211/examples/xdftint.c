
/* Driver for routine dftint */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"

static float c,d;

float coscxd(float x)
{
	return cos(c*x+d);
}

#define ci(x) (sin((w-c)*x-d)/(2.0*(w-c))+sin((w+c)*x+d)/(2.0*(w+c)))
#define si(x) (-cos((w-c)*x-d)/(2.0*(w-c))-cos((w+c)*x+d)/(2.0*(w+c)))

void getans(float w,float a,float b,float *cans,float *sans)
{
	*cans=ci(b)-ci(a);
	*sans=si(b)-si(a);
}

int main(void)
{
	float a,b,cans,cosint,sans,sinint,w;

	printf("  Omega  Integral cosine*test func  Err");
	printf("    Integral sine*test func   Err\n");
	for (;;) {
		printf("input c,d:\n");
		if (scanf("%f %f",&c,&d) == EOF) break;
		for (;;) {
			printf("input a,b:\n");
			if (scanf("%f %f",&a,&b) == EOF) break;
			if (a == b) break;
				for (;;) {
					printf("input w:\n");
					if (scanf("%f",&w) == EOF) break;
					if (w < 0.0) break;
					dftint(coscxd,a,b,w,&cosint,&sinint);
					getans(w,a,b,&cans,&sans);
					printf("%15.6e %15.6e %15.6e %15.6e %15.6e\n",
						w,cans,cosint-cans,sans,sinint-sans);
				}
		}
	}
	printf("normal completion\n");
	return 0;
}
#undef NRANSI
