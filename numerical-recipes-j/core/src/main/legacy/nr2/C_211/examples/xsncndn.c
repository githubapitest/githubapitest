
/* Driver for routine sncndn */

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
	float em,emmc,uu,val,sn,cn,dn;
	FILE *fp;

	if ((fp = fopen("fncval.dat","r")) == NULL)
		nrerror("Data file fncval.dat not found\n");
	fgets(txt,MAXSTR,fp);
	while (strncmp(txt,"Jacobian Elliptic Function",26)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\n%s\n",txt);
	printf("%4s %8s %16s %13s %15s %18s\n","mc","u","actual",
		"sn","sn^2+cn^2","(mc)*(sn^2)+dn^2");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%f %f %f",&em,&uu,&val);
		emmc=1.0-em;
		sncndn(uu,emmc,&sn,&cn,&dn);
		printf("%5.2f %8.2f %15.5f %15.5f %12.5f %14.5f\n",
			emmc,uu,val,sn,(sn*sn+cn*cn),(em*sn*sn+dn*dn));
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
