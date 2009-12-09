
#define NRANSI
#include "nrutil.h"
#define MACC 6

void mpdiv(unsigned char q[], unsigned char r[], unsigned char u[],
	unsigned char v[], int n, int m)
{
	void mpinv(unsigned char u[], unsigned char v[], int n, int m);
	void mpmov(unsigned char u[], unsigned char v[], int n);
	void mpmul(unsigned char w[], unsigned char u[], unsigned char v[], int n,
		int m);
	void mpsad(unsigned char w[], unsigned char u[], int n, int iv);
	void mpsub(int *is, unsigned char w[], unsigned char u[], unsigned char v[],
		int n);
	int is;
	unsigned char *rr,*s;

	rr=cvector(1,(n+MACC)<<1);
	s=cvector(1,(n+MACC)<<1);
	mpinv(s,v,n+MACC,m);
	mpmul(rr,s,u,n+MACC,n);
	mpsad(s,rr,n+MACC-1,1);
	mpmov(q,&s[2],n-m+1);
	mpmul(rr,q,v,n-m+1,m);
	mpsub(&is,&rr[1],u,&rr[1],n);
	if (is) nrerror("MACC too small in mpdiv");
	mpmov(r,&rr[n-m+1],m);
	free_cvector(s,1,(n+MACC)<<1);
	free_cvector(rr,1,(n+MACC)<<1);
}
#undef MACC
#undef NRANSI
