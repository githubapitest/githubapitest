
/* Driver for routine airy */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define MAXSTR 80

int main(void)
{
	char txt[MAXSTR];
	int i,nval;
	float ai,bi,aip,bip,x,xai,xbi,xaip,xbip;
	FILE *fp;

	if ((fp = fopen("fncval.dat","r")) == NULL)
		nrerror("Data file fncval.dat not found\n");
	fgets(txt,MAXSTR,fp);
	while (strncmp(txt,"Airy Functions",14)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\n%s\n",txt);
	printf("%5s\n%14s %16s %17s %16s\n%14s %16s %17s %16s\n",
		"x","ai","bi","aip","bip","xai","xbi","xaip","xbip");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%f %f %f %f %f",&x,&ai,&bi,&aip,&bip);
		airy(x,&xai,&xbi,&xaip,&xbip);
		printf("%5.2f\n\t%16.6e %16.6e %16.6e %16.6e\n",x,ai,bi,aip,bip);
		printf("\t%16.6e %16.6e %16.6e %16.6e\n",xai,xbi,xaip,xbip);
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
