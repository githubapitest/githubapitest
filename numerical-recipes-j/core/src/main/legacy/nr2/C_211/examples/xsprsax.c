
/* Driver for routine sprsax */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 5
#define NMAX (2*NP*NP+1)

int main(void)
{
	unsigned long i,j,msize,*ija;
	float **a,*sa,*ax,*b;
	static float ainit[NP][NP]={
		3.0,0.0,1.0,0.0,0.0,
		0.0,4.0,0.0,0.0,0.0,
		0.0,7.0,5.0,9.0,0.0,
		0.0,0.0,0.0,0.0,2.0,
		0.0,0.0,0.0,6.0,5.0};
	static float x[NP+1]={0.0,1.0,2.0,3.0,4.0,5.0};

	ija=lvector(1,NMAX);
	ax=vector(1,NP);
	b=vector(1,NP);
	sa=vector(1,NMAX);
	a=convert_matrix(&ainit[0][0],1,NP,1,NP);
	sprsin(a,NP,0.5,NMAX,sa,ija);
	msize=ija[1]-2;
	sprsax(sa,ija,x,b,msize);
	for (i=1;i<=msize;i++)
		for (ax[i]=0.0,j=1;j<=msize;j++) ax[i] += a[i][j]*x[j];
	printf("\tReference\tsprsax result\n");
	for (i=1;i<=msize;i++) printf("\t%5.2f\t\t%5.2f\n",ax[i],b[i]);
	free_convert_matrix(a,1,NP,1,NP);
	free_vector(sa,1,NMAX);
	free_vector(b,1,NP);
	free_vector(ax,1,NP);
	free_lvector(ija,1,NMAX);
	return 0;
}
#undef NRANSI
