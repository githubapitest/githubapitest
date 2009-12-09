
/* Driver for routine sprstm */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 5
#define NMAX (2*NP*NP+1)
#define THRESH 0.99

int main(void)
{
	unsigned long i,j,k,*ija,*ijb,*ijbt,*ijc,msize;
	float *sa,*sb,*sbt,*sc,**a,**b,**c,**ab;
	static float ainit[NP][NP]={
		1.0,0.5,0.0,0.0,0.0,
		0.5,2.0,0.5,0.0,0.0,
		0.0,0.5,3.0,0.5,0.0,
		0.0,0.0,0.5,4.0,0.5,
		0.0,0.0,0.0,0.5,5.0};
	static float binit[NP][NP]={
		1.0,1.0,0.0,0.0,0.0,
		1.0,2.0,1.0,0.0,0.0,
		0.0,1.0,3.0,1.0,0.0,
		0.0,0.0,1.0,4.0,1.0,
		0.0,0.0,0.0,1.0,5.0};

	ija=lvector(1,NMAX);
	ijb=lvector(1,NMAX);
	ijbt=lvector(1,NMAX);
	ijc=lvector(1,NMAX);
	sa=vector(1,NMAX);
	sb=vector(1,NMAX);
	sbt=vector(1,NMAX);
	sc=vector(1,NMAX);
	c=matrix(1,NP,1,NP);
	ab=matrix(1,NP,1,NP);
	a=convert_matrix(&ainit[0][0],1,NP,1,NP);
	b=convert_matrix(&binit[0][0],1,NP,1,NP);
	sprsin(a,NP,0.5,NMAX,sa,ija);
	sprsin(b,NP,0.5,NMAX,sb,ijb);
	sprstp(sb,ijb,sbt,ijbt);
	msize=ija[ija[1]-1]-1;
	sprstm(sa,ija,sbt,ijbt,THRESH,msize,sc,ijc);
	for (i=1;i<=NP;i++) {
		for (j=1;j<=NP;j++) {
			ab[i][j]=0.0;
			for (k=1;k<=NP;k++) {
				ab[i][j]=ab[i][j]+a[i][k]*b[k][j];
			}
		}
	}
	printf("Reference matrix:\n");
	for (i=1;i<=NP;i++) {
		for (j=1;j<=NP;j++) printf("%5.2f\t",ab[i][j]);
		printf("\n");
	}
	printf("sprstm matrix (off-diag. elements of mag >): %12.6f\n",THRESH);
	for (i=1;i<=NP;i++) for (j=1;j<=NP;j++) c[i][j]=0.0;
	for (i=1;i<=NP;i++) {
		c[i][i]=sc[i];
		for (j=ijc[i];j<=ijc[i+1]-1;j++) c[i][ijc[j]]=sc[j];
	}
	for (i=1;i<=NP;i++) {
		for (j=1;j<=NP;j++) printf("%5.2f\t",c[i][j]);
		printf("\n");
	}
	free_convert_matrix(b,1,NP,1,NP);
	free_convert_matrix(a,1,NP,1,NP);
	free_matrix(ab,1,NP,1,NP);
	free_matrix(c,1,NP,1,NP);
	free_vector(sc,1,NMAX);
	free_vector(sbt,1,NMAX);
	free_vector(sb,1,NMAX);
	free_vector(sa,1,NMAX);
	free_lvector(ijc,1,NMAX);
	free_lvector(ijbt,1,NMAX);
	free_lvector(ijb,1,NMAX);
	free_lvector(ija,1,NMAX);
	return 0;
}
#undef NRANSI
