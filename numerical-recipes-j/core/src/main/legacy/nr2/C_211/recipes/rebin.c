
void rebin(float rc, int nd, float r[], float xin[], float xi[])
{
	int i,k=0;
	float dr=0.0,xn=0.0,xo=0.0;

	for (i=1;i<nd;i++) {
		while (rc > dr)
			dr += r[++k];
		if (k > 1) xo=xi[k-1];
		xn=xi[k];
		dr -= rc;
		xin[i]=xn-(xn-xo)*dr/r[k];
	}
	for (i=1;i<nd;i++) xi[i]=xin[i];
	xi[nd]=1.0;
}
