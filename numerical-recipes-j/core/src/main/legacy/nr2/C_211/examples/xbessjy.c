
/* Driver for routine bessjy */

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
	float rj,ry,rjp,ryp,x,xnu,xrj,xry,xrjp,xryp;
	FILE *fp;

	if ((fp = fopen("fncval.dat","r")) == NULL)
		nrerror("Data file fncval.dat not found\n");
	fgets(txt,MAXSTR,fp);
	while (strncmp(txt,"Ordinary Bessel Functions",25)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\n%s\n",txt);
	printf("%5s %7s\n%14s %16s %17s %16s\n%14s %16s %17s %16s\n",
		"xnu","x","rj","ry","rjp","ryp","xrj","xry","xrjp","xryp");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%f %f %f %f %f %f",&xnu,&x,&rj,&ry,&rjp,&ryp);
		bessjy(x,xnu,&xrj,&xry,&xrjp,&xryp);
		printf("%5.2f %5.2f\n\t%16.6e %16.6e %16.6e %16.6e\n",xnu,x,rj,ry,rjp,ryp);
		printf("\t%16.6e %16.6e %16.6e %16.6e\n",xrj,xry,xrjp,xryp);
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
