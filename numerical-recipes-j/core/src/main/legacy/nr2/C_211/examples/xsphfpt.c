
/* Auxiliary routine for sphfpt */

extern int m,n;
extern float c2,dx,gmma;

void derivs(float x,float y[],float dydx[])
{
	dydx[1]=y[2];
	dydx[2]=(2.0*x*(m+1.0)*y[2]-(y[3]-c2*x*x)*y[1])/(1.0-x*x);
	dydx[3]=0.0;
}
