
/* Driver for routine jacobi */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 10
#define NMAT 3

int main(void)
{
	int i,j,k,kk,l,ll,nrot;
	static float a[3][3]=
		{1.0,2.0,3.0,
		2.0,2.0,3.0,
		3.0,3.0,3.0};
	static float b[5][5]=
		{-2.0,-1.0,0.0,1.0,2.0,
		-1.0,-1.0,0.0,1.0,2.0,
		0.0,0.0,0.0,1.0,2.0,
		1.0,1.0,1.0,1.0,2.0,
		2.0,2.0,2.0,2.0,2.0};
	static float c[NP][NP]=
		{5.0,4.3,3.0,2.0,1.0,0.0,-1.0,-2.0,-3.0,-4.0,
		4.3,5.1,4.0,3.0,2.0,1.0,0.0,-1.0,-2.0,-3.0,
		3.0,4.0,5.0,4.0,3.0,2.0,1.0,0.0,-1.0,-2.0,
		2.0,3.0,4.0,5.0,4.0,3.0,2.0,1.0,0.0,-1.0,
		1.0,2.0,3.0,4.0,5.0,4.0,3.0,2.0,1.0,0.0,
		0.0,1.0,2.0,3.0,4.0,5.0,4.0,3.0,2.0,1.0,
		-1.0,0.0,1.0,2.0,3.0,4.0,5.0,4.0,3.0,2.0,
		-2.0,-1.0,0.0,1.0,2.0,3.0,4.0,5.0,4.0,3.0,
		-3.0,-2.0,-1.0,0.0,1.0,2.0,3.0,4.0,5.0,4.0,
		-4.0,-3.0,-2.0,-1.0,0.0,1.0,2.0,3.0,4.0,5.0};
	float *d,*r,**v,**e;
	static int num[4]={0,3,5,10};

	d=vector(1,NP);
	r=vector(1,NP);
	v=matrix(1,NP,1,NP);
	for (i=1;i<=NMAT;i++) {
		if (i == 1) e=convert_matrix(&a[0][0],1,num[i],1,num[i]);
		else if (i == 2) e=convert_matrix(&b[0][0],1,num[i],1,num[i]);
		else if (i == 3) e=convert_matrix(&c[0][0],1,num[i],1,num[i]);
		jacobi(e,num[i],d,v,&nrot);
		printf("matrix number %2d\n",i);
		printf("number of JACOBI rotations: %3d\n",nrot);
		printf("eigenvalues: \n");
		for (j=1;j<=num[i];j++) {
			printf("%12.6f",d[j]);
			if ((j % 5) == 0) printf("\n");
		}
		printf("\neigenvectors:\n");
		for (j=1;j<=num[i];j++) {
			printf("%9s %3d \n","number",j);
			for (k=1;k<=num[i];k++) {
				printf("%12.6f",v[k][j]);
				if ((k % 5) == 0) printf("\n");
			}
			printf("\n");
		}
		/* eigenvector test */
		printf("eigenvector test\n");
		for (j=1;j<=num[i];j++) {
			for (l=1;l<=num[i];l++) {
				r[l]=0.0;
				for (k=1;k<=num[i];k++) {
					if (k > l) {
						kk=l;
						ll=k;
					} else {
						kk=k;
						ll=l;
					}
					r[l] += (e[ll][kk]*v[k][j]);
				}
			}
			printf("vector number %3d\n",j);
			printf("%11s %14s %10s\n",
				"vector","mtrx*vec.","ratio");
			for (l=1;l<=num[i];l++)
				printf("%12.6f %12.6f %12.6f\n",
					v[l][j],r[l],r[l]/v[l][j]);
		}
		printf("press RETURN to continue...\n");
		(void) getchar();
		free_convert_matrix(e,1,num[i],1,num[i]);
	}
	free_matrix(v,1,NP,1,NP);
	free_vector(r,1,NP);
	free_vector(d,1,NP);
	return 0;
}
#undef NRANSI
