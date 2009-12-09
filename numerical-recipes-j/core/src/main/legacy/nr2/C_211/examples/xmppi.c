
/* Driver for mp routines */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define IAOFF 48
#define NMAX 1024

void mpsqr2(int n)
{
	int j,m;
	unsigned char *x,*y,*t,*q,*r,*s;

	x=cvector(1,NMAX);
	y=cvector(1,NMAX);
	t=cvector(1,NMAX);
	q=cvector(1,NMAX);
	r=cvector(1,NMAX);
	s=cvector(1,3*NMAX);
	t[1]=2;
	for (j=2;j<=n;j++) t[j]=0;
	mpsqrt(x,x,t,n,n);
	mpmov(y,x,n);
	printf("sqrt(2)=\n");
	s[1]=y[1]+IAOFF;
	s[2]='.';
	/* caution: next step is N**2! omit it for large N */
	mp2dfr(&y[1],&s[2],n-1,&m);
	s[m+3]=0;
	printf(" %64s\n",&s[1]);
	printf("Result rounded to 1 less base-256 place:\n");
	/* use s as scratch space */
	mpsad(s,x,n,128);
	mpmov(y,&s[1],n-1);
	s[1]=y[1]+IAOFF;
	s[2]='.';
	/* caution: next step is N**2! omit it for large N */
	mp2dfr(&y[1],&s[2],n-2,&m);
	s[m+3]=0;
	printf(" %64s\n",&s[1]);
	printf("2-sqrt(2)=\n");
	/* Calculate this the hard way to exercise the mpdiv function */
	mpdiv(q,r,t,x,n,n);
	s[1]=r[1]+IAOFF;
	s[2]='.';
	/* caution: next step is N**2! omit it for large N */
	mp2dfr(&r[1],&s[2],n-1,&m);
	s[m+3]=0;
	printf(" %64s\n",&s[1]);
	free_cvector(s,1,3*NMAX);
	free_cvector(r,1,NMAX);
	free_cvector(q,1,NMAX);
	free_cvector(t,1,NMAX);
	free_cvector(y,1,NMAX);
	free_cvector(x,1,NMAX);
}

int main(void)
{
	int n;

	for (;;) {
		printf("Input n\n");
		if (scanf("%d",&n) == EOF) break;
		mpsqr2(n);
		mppi(n);
	}
	printf("Normal completion\n");
	return 0;
}
#undef NRANSI
