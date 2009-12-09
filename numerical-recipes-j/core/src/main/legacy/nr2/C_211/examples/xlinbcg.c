
/* Driver for routine linbcg */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NSIZE 59
#define NP 20
#define ITOL 1
#define TOL 1e-9
#define ITMAX 75

unsigned long ija[NSIZE+1] ={
	0,22,23,25,27,29,31,33,35,37,39,41,43,45,47,49,51,53,55,57,
	59,60,2,1,3,2,4,3,5,4,6,5,7,6,8,7,9,8,10,9,11,10,12,11,13,12,
	14,13,15,14,16,15,17,16,18,17,19,18,20,19};

double sa[NSIZE+1] ={
	0.0,3.0,3.0,3.0,3.0,3.0,3.0,3.0,3.0,3.0,3.0,3.0,3.0,3.0,3.0,3.0,3.0,
	3.0,3.0,3.0,3.0,0.0,2.0,-2.0,2.0,-2.0,2.0,-2.0,2.0,-2.0,2.0,-2.0,
	2.0,-2.0,2.0,-2.0,2.0,-2.0,2.0,-2.0,2.0,-2.0,2.0,-2.0,2.0,-2.0,2.0,
	-2.0,2.0,-2.0,2.0,-2.0,2.0,-2.0,2.0,-2.0,2.0,-2.0,2.0,-2.0};

int main(void)
{
	int i,ii,iter;
	double *b,*bcmp,*x,err;

	b=dvector(1,NP);
	bcmp=dvector(1,NP);
	x=dvector(1,NP);
	for (i=1;i<=NP;i++) {
		x[i]=0.0;
		b[i]=1.0;
	}
	b[1]=3.0;
	b[NP] = -1.0;
	linbcg(NP,b,x,ITOL,TOL,ITMAX,&iter,&err);
	printf("%s %15e\n","Estimated error:",err);
	printf("%s %6d\n","Iterations needed:",iter);
	printf("\nSolution vector:\n");
	for (ii=1;ii<=NP/5;ii++) {
		for (i=5*(ii-1)+1;i<=5*ii;i++) printf("%12.6f",x[i]);
		printf("\n");
	}
	for (i=1;i<=(NP % 5);i++)
		printf("%12.6f",x[5*(NP/5)+i]);
	printf("\n");
	dsprsax(sa,ija,x,bcmp,NP);
	/* this is a double precision version of sprsax */
	printf("\npress RETURN to continue...\n");
	(void) getchar();
	printf("test of solution vector:\n");
	printf("%9s %12s\n","a*x","b");
	for (i=1;i<=NP;i++)
		printf("%12.6f %12.6f\n",bcmp[i],b[i]);
	free_dvector(x,1,NP);
	free_dvector(bcmp,1,NP);
	free_dvector(b,1,NP);
	return 0;
}
#undef NRANSI
