
void rank(unsigned long n, unsigned long indx[], unsigned long irank[])
{
	unsigned long j;

	for (j=1;j<=n;j++) irank[indx[j]]=j;
}
