
/* Driver for routine sprsin */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 5
#define NMAX (2*NP*NP+1)

int main(void)
{
	unsigned long i,j,msize,*ija;
	float **a,**aa,*sa;
	static float ainit[NP][NP]={
		3.0,0.0,1.0,0.0,0.0,
		0.0,4.0,0.0,0.0,0.0,
		0.0,7.0,5.0,9.0,0.0,
		0.0,0.0,0.0,0.0,2.0,
		0.0,0.0,0.0,6.0,5.0};

	ija=lvector(1,NMAX);
	sa=vector(1,NMAX);
	aa=matrix(1,NP,1,NP);
	a=convert_matrix(&ainit[0][0],1,NP,1,NP);
	sprsin(a,NP,0.5,NMAX,sa,ija);
	msize=ija[ija[1]-1]-1;
	sa[NP+1]=0.0;
	printf("index\tija\t\tsa\n");
	for (i=1;i<=msize;i++) printf("%lu\t%lu\t%12.6f\n",i,ija[i],sa[i]);
	for (i=1;i<=NP;i++) for (j=1;j<=NP;j++) aa[i][j]=0.0;
	for (i=1;i<=NP;i++) {
		aa[i][i]=sa[i];
		for (j=ija[i];j<=ija[i+1]-1;j++) aa[i][ija[j]]=sa[j];
	}
	printf("Original Matrix\n");
	for (i=1;i<=NP;i++) {
		for (j=1;j<=NP;j++) printf("%5.2f\t",a[i][j]);
		printf("\n");
	}
	printf("Reconstructed Matrix\n");
	for (i=1;i<=NP;i++) {
		for (j=1;j<=NP;j++) printf("%5.2f\t",aa[i][j]);
		printf("\n");
	}
	free_convert_matrix(a,1,NP,1,NP);
	free_matrix(aa,1,NP,1,NP);
	free_vector(sa,1,NMAX);
	free_lvector(ija,1,NMAX);
	return 0;
}
#undef NRANSI
