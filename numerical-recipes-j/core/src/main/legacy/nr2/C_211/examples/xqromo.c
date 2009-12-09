
/* Driver for routine qromo */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"

#define X1 0.0
#define X2 1.5707963
#define X3 3.1415926
#define AINF 1.0E20
#define PI 3.1415926

static float funcl(float x)
{
	return (float) (sqrt(x)/sin(x));
}

static float funcu(float x)
{
	return (float) (sqrt(PI-x)/sin(x));
}

static float fncinf(float x)
{

	return (float) (sin(x)/(x*x));
}

static float fncend(float x)
{
	return (float) (exp(-x)/sqrt(x));
}

int main(void)
{
	float res1,res2,result;

	printf("\nImproper integrals:\n\n");
	result=qromo(funcl,X1,X2,midsql);
	printf("Function: sqrt(x)/sin(x)       Interval: (0,pi/2)\n");
	printf("Using: MIDSQL                  Result: %8.4f\n\n",result);
	result=qromo(funcu,X2,X3,midsqu);
	printf("Function: sqrt(pi-x)/sin(x)    Interval: (pi/2,pi)\n");
	printf("Using: MIDSQU                  Result: %8.4f\n\n",result);
	result=qromo(fncinf,X2,AINF,midinf);
	printf("Function: sin(x)/x**2          Interval: (pi/2,infty)\n");
	printf("Using: MIDINF                  Result: %8.4f\n\n",result);
	result=qromo(fncinf,-AINF,-X2,midinf);
	printf("Function: sin(x)/x**2          Interval: (-infty,-pi/2)\n");
	printf("Using: MIDINF                  Result: %8.4f\n\n",result);
	res1=qromo(fncend,X1,X2,midsql);
	res2=qromo(fncend,X2,AINF,midinf);
	printf("Function: exp(-x)/sqrt(x)      Interval: (0.0,infty)\n");
	printf("Using: MIDSQL,MIDINF           Result: %8.4f\n\n",res1+res2);
	res2=qromo(fncend,X2,AINF,midexp);
	printf("Function: exp(-x)/sqrt(x)      Interval: (0.0,infty)\n");
	printf("Using: MIDSQL,MIDEXP           Result: %8.4f\n\n",res1+res2);
	return 0;
}
#undef NRANSI
