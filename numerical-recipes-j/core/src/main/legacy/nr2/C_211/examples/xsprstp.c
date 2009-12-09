
/* Driver for routine sprstp */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 5
#define NMAX (2*NP*NP+1)

int main(void)
{
	unsigned long i,j,*ija,*ijat;
	float **a,**at,*sa,*sat;
	static float ainit[NP][NP]={
		3.0,0.0,1.0,0.0,0.0,
		0.0,4.0,0.0,0.0,0.0,
		0.0,7.0,5.0,9.0,0.0,
		0.0,0.0,0.0,0.0,2.0,
		0.0,0.0,0.0,6.0,5.0};

	ija=lvector(1,NMAX);
	ijat=lvector(1,NMAX);
	sa=vector(1,NMAX);
	sat=vector(1,NMAX);
	at=matrix(1,NP,1,NP);
	a=convert_matrix(&ainit[0][0],1,NP,1,NP);
	sprsin(a,NP,0.5,NMAX,sa,ija);
	sprstp(sa,ija,sat,ijat);
	for (i=1;i<=NP;i++) for (j=1;j<=NP;j++) at[i][j]=0.0;
	for (i=1;i<=NP;i++) {
		at[i][i]=sat[i];
		for (j=ijat[i];j<=ijat[i+1]-1;j++) at[i][ijat[j]]=sat[j];
	}
	printf("Original Matrix\n");
	for (i=1;i<=NP;i++) {
		for (j=1;j<=NP;j++) printf("%5.2f\t",a[i][j]);
		printf("\n");
	}
	printf("Transpose\n");
	for (i=1;i<=NP;i++) {
		for (j=1;j<=NP;j++) printf("%5.2f\t",at[i][j]);
		printf("\n");
	}
	free_convert_matrix(a,1,NP,1,NP);
	free_matrix(at,1,NP,1,NP);
	free_vector(sat,1,NMAX);
	free_vector(sa,1,NMAX);
	free_lvector(ijat,1,NMAX);
	free_lvector(ija,1,NMAX);
	return 0;
}
#undef NRANSI
