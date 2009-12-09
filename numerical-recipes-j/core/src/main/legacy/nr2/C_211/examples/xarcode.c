
/* Driver for routines arcmak and arcode */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define MC 512

#define NWK 20

#define MAXLINE 256

int main(void)
{
	int k;
	unsigned long i,j,lc,lcode=MAXLINE,n,nch,nrad,nt,nfreq[257],tmp,zero=0;
	unsigned char *code,mess[MAXLINE],ness[MAXLINE];
	arithcode acode;
	FILE *fp;

	code=cvector(0,MAXLINE);
	acode.ilob=lvector(1,NWK);
	acode.iupb=lvector(1,NWK);
	acode.ncumfq=lvector(1,MC+2);
	if ((fp = fopen("text.dat","r")) == NULL)
		nrerror("Input file text.dat not found.\n");
	for (j=1;j<=256;j++) nfreq[j]=0;
	while ((k=getc(fp)) != EOF) {
		if ((k -= 31) >= 1) nfreq[k]++;
	}
	fclose(fp);
	nch=96;
	nrad=256;
	/* here is the initialization that constructs the code */
	arcmak(nfreq,(int)nch,(int)nrad,&acode);
	/* now ready to prompt for lines to encode */
	for (;;) {
		printf("Enter a line:\n");
		if (gets((char *)&mess[1]) == NULL) break;
		n=strlen((char *)&mess[1]);
		/* shift from 256 character alphabet to 96 printing characters */
		for (j=1;j<=n;j++) mess[j] -= 32;
		/* message initialization */
		lc=1;
		arcode(&zero,&code,&lcode,&lc,0,&acode);
		/* here we arithmetically encode mess(1:n) */
		for (j=1;j<=n;j++) {
			tmp=mess[j];
			arcode(&tmp,&code,&lcode,&lc,1,&acode);
		}
		/* message termination */
		arcode(&nch,&code,&lcode,&lc,1,&acode);
		printf("Length of line input, coded= %lu %lu\n",n,lc-1);
		/* here we decode the message, hopefully to get the original back */
		lc=1;
		arcode(&zero,&code,&lcode,&lc,0,&acode);
		for (j=1;j<=lcode;j++) {
			arcode(&i,&code,&lcode,&lc,-1,&acode);
			if (i == nch) break;
			else ness[j]=(unsigned char)i;
		}
		if (j > lcode) nrerror("Arith. coding: Never get here");
		nt=j-1;
		printf("Decoded output:\n");
		for (j=1;j<=nt;j++) printf("%c",(char)(ness[j]+32));
		printf("\n");
		if (nt != n) printf("Error ! j decoded != n input.\n");
	}
	free_cvector(code,0,MAXLINE);
	free_lvector(acode.ncumfq,1,MC+2);
	free_lvector(acode.iupb,1,NWK);
	free_lvector(acode.ilob,1,NWK);
	printf("Normal completion\n");
	return 0;
}
#undef NRANSI
