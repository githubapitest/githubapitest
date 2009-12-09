
/* Driver for routine rlft3 */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"
#define IPRNT 20

static unsigned long compare(char *string, float ***arr1, float ***arr2,
	unsigned long len1, unsigned long len2, unsigned long len3, float eps)
{
	unsigned long err=0,i1,i2,i3;
	float a1,a2;

	printf("%s\n",string);
	for (i1=1;i1<=len1;i1++)
		for (i2=1;i2<=len2;i2++)
			for (i3=1;i3<=len3;i3++) {
				a1=arr1[i1][i2][i3];
				a2=arr2[i1][i2][i3];
				if ((a2 == 0.0 && fabs(a1-a2) > eps) || (fabs((a1-a2)/a2) > eps)) {
					if (++err <= IPRNT)
						printf("%d %d %d %12.6f %12.6f\n",
							i1,i2,i3,a1,a2);
				}
			}
	return err;
}

#define NX 32
#define NY 8
#define NZ 16

#define EPS 0.0008

int main(void)
{
	long idum=(-3);
	unsigned long err,i,j,k,nn1=NX,nn2=NY,nn3=NZ;
	float fnorm,***data1,***data2,**speq1;

	fnorm=(float)nn1*(float)nn2*(float)nn3/2.0;
	data1=f3tensor(1,nn1,1,nn2,1,nn3);
	data2=f3tensor(1,nn1,1,nn2,1,nn3);
	speq1=matrix(1,nn1,1,nn2<<1);
	for (i=1;i<=nn1;i++)
		for (j=1;j<=nn2;j++)
			for (k=1;k<=nn3;k++)
				data2[i][j][k]=fnorm*(data1[i][j][k]=2*ran1(&idum)-1);
	rlft3(data1,speq1,nn1,nn2,nn3,1);
	/* here would be any processing in Fourier space */
	rlft3(data1,speq1,nn1,nn2,nn3,-1);
	err=compare("data",data1,data2,nn1,nn2,nn3,EPS);
	if (err) {
		printf("Comparison error at tolerance %12.6f\n",EPS);
		printf("Total number of errors is %d\n",err);
	}
	else {
		printf("Data compares OK to tolerance %12.6f\n",EPS);
	}
	free_matrix(speq1,1,nn1,1,nn2<<1);
	free_f3tensor(data2,1,nn1,1,nn2,1,nn3);
	free_f3tensor(data1,1,nn1,1,nn2,1,nn3);
	return (err > 0);
}
#undef NRANSI
