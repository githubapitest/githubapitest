
/* Driver for routine crank */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NDAT 20
#define NMON 12
#define MAXSTR 80

int main(void)
{
	int i,j;
	float *data,*order,*s,**rays;
	char dummy[MAXSTR],txt[MAXSTR],city[NDAT+1][17],mon[NMON+1][5];
	FILE *fp;

	data=vector(1,NDAT);
	order=vector(1,NDAT);
	s=vector(1,NMON);
	rays=matrix(1,NDAT,1,NMON);
	if ((fp = fopen("table2.dat","r")) == NULL)
		nrerror("Data file table2.dat not found\n");
	fgets(dummy,MAXSTR,fp);
	fgets(txt,MAXSTR,fp);
	fscanf(fp,"%*15c");
	for (i=1;i<=NMON;i++) fscanf(fp," %s",mon[i]);
	fgets(dummy,MAXSTR,fp);
	fgets(dummy,MAXSTR,fp);
	for (i=1;i<=NDAT;i++) {
		fscanf(fp,"%[^0123456789]",city[i]);
		city[i][16]='\0';
		for (j=1;j<=NMON;j++) fscanf(fp,"%f",&rays[i][j]);
		fgets(dummy,MAXSTR,fp);
	}
	fclose(fp);
	printf("%s\n%16s",txt," ");
	for (i=1;i<=12;i++) printf(" %s",mon[i]);
	printf("\n");
	for (i=1;i<=NDAT;i++) {
		printf("%s",city[i]);
		for (j=1;j<=12;j++)
			printf("%4d",(int) (0.5+rays[i][j]));
		printf("\n");
	}
	printf(" press return to continue ...\n");
	getchar();
	/* Replace solar flux in each column by rank order */
	for (j=1;j<=12;j++) {
		for (i=1;i<=NDAT;i++) {
			data[i]=rays[i][j];
			order[i]=i;
		}
		sort2(NDAT,data,order);
		crank(NDAT,data,&s[j]);
		for (i=1;i<=NDAT;i++)
			rays[(int) (0.5+order[i])][j]=data[i];
	}
	printf("%16s"," ");
	for (i=1;i<=12;i++) printf(" %s",mon[i]);
	printf("\n");
	for (i=1;i<=NDAT;i++) {
		printf("%s",city[i]);
		for (j=1;j<=12;j++)
			printf("%4d",(int) (0.5+rays[i][j]));
		printf("\n");
	}
	free_matrix(rays,1,NDAT,1,NMON);
	free_vector(s,1,NMON);
	free_vector(order,1,NDAT);
	free_vector(data,1,NDAT);
	return 0;
}
#undef NRANSI
