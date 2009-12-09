package com.google.code.numericalrecipes;
public class GlobalMembersPhylo
{
	public static void newick(RefObject<Phylagglom> p, MatChar str, RefObject<String> filename)
	{
		FILE OUT = fopen(filename.argvalue,"wb");
		Int i = new Int();
		Int s = new Int();
		Int ntask = 0;
		Int n = p.argvalue.n;
		Int root = p.argvalue.root;
		VecInt tasklist = new VecInt(2 *n+1);
		tasklist[ntask++] = (1 << 16) + root;
		while (ntask-- > 0)
		{
			s = tasklist[ntask] >> 16;
			i = tasklist[ntask] & 0xffff;
			if (s == 1 || s == 2)
			{
				tasklist[ntask++] = ((s+2) << 16) + p.argvalue.t.get(i).mo;
				if (p.argvalue.t.get(i).ldau >= 0)
				{
					fprintf(OUT,"(");
					tasklist[ntask++] = (2 << 16) + p.argvalue.t.get(i).rdau;
					tasklist[ntask++] = (1 << 16) + p.argvalue.t.get(i).ldau;
				}
				else
					fprintf(OUT, "%s:%f", str[i][0], p.argvalue.t.get(i).modist);
			}
			else if (s == 3)
			{
				if (ntask > 0)
					fprintf(OUT,",\n");
			}
			else if (s == 4)
			{
				if (i == root)
					fprintf(OUT,");\n");
				else
					fprintf(OUT,"):%f",p.argvalue.t.get(i).modist);
			}
		}
		fclose(OUT);
	}
	public static void phyl2ps(RefObject<String> filename, RefObject<Phylagglom> ph, MatChar str, Int extend, Doub xl, Doub xr, Doub yt, Doub yb)
	{
		Int i = new Int();
		Int j = new Int();
		Doub id = new Doub();
		Doub jd = new Doub();
		Doub xi = new Doub();
		Doub yi = new Doub();
		Doub xj = new Doub();
		Doub yj = new Doub();
		Doub seqmax = new Doub();
		Doub depmax = new Doub();
		FILE OUT = fopen(filename.argvalue,"wb");
		fprintf(OUT,"%%!PS\n/Courier findfont 8 scalefont setfont\n");
		seqmax = ph.argvalue.seqmax;
		depmax = ph.argvalue.depmax;
		for (i = 0; i<2*(ph.argvalue.n)-1; i++)
		{
			j = ph.argvalue.t.get(i).mo;
			id = ph.argvalue.t.get(i).dep;
			jd = ph.argvalue.t.get(j).dep;
			xi = xl + (xr-xl)*id/depmax;
			yi = yt - (yt-yb)*(ph.argvalue.t.get(i).seq+0.5)/seqmax;
			xj = xl + (xr-xl)*jd/depmax;
			yj = yt - (yt-yb)*(ph.argvalue.t.get(j).seq+0.5)/seqmax;
			fprintf(OUT,"%f %f moveto %f %f lineto %f %f lineto 0 setgray stroke\n", xj,yj,xj,yi,xi,yi);
			if (extend != null)
			{
				if (i < ph.argvalue.n)
				{
					fprintf(OUT,"%f %f moveto %f %f lineto 0.7 setgray stroke\n", xi,yi,xr,yi);
					fprintf(OUT, "%f %f moveto (%s (%02d)) 0 setgray show\n", xr+3., yi-2., str[i][0], i);
				}
			}
			else
			{
				if (i < ph.argvalue.n)
					fprintf(OUT, "%f %f moveto (%s (%02d)) 0 setgray show\n", xi+3., yi-2., str[i][0], i);
			}
		}
		fprintf(OUT,"showpage\n\004");
		fclose(OUT);
	}
}