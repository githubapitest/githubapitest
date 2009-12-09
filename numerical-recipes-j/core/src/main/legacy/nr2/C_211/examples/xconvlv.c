
/* Driver for routine convlv */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 16        /* data array size */
#define M 9         /* response function dimension - must be odd */
#define N2 (2*N)

int main(void)
{
	unsigned long i,j;
	int isign;
	float cmp,*data,*respns,*resp,*ans;

	data=vector(1,N);
	respns=vector(1,N);
	resp=vector(1,N);
	ans=vector(1,N2);
	for (i=1;i<=N;i++)
		if ((i >= N/2-N/8) && (i <= N/2+N/8))
			data[i]=1.0;
		else
			data[i]=0.0;
	for (i=1;i<=M;i++) {
		if ((i > 2) && (i < 7))
			respns[i]=1.0;
		else
			respns[i]=0.0;
		resp[i]=respns[i];
	}
	isign=1;
	convlv(data,N,resp,M,isign,ans);
	/* compare with a direct convolution */
	printf("%3s %14s %13s\n","i","CONVLV","Expected");
	for (i=1;i<=N;i++) {
		cmp=0.0;
		for (j=1;j<=M/2;j++) {
			cmp += data[((i-j-1+N) % N)+1]*respns[j+1];
			cmp += data[((i+j-1) % N)+1]*respns[M-j+1];
		}
		cmp += data[i]*respns[1];
		printf("%3ld %15.6f %12.6f\n",i,ans[i],cmp);
	}
	free_vector(ans,1,N2);
	free_vector(resp,1,N);
	free_vector(respns,1,N);
	free_vector(data,1,N);
	return 0;
}
#undef NRANSI
