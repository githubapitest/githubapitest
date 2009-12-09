package com.google.code.numericalrecipes;
public class PSpage
{

	public FILE PLT;
	public String file;
	public String fontname = new String(new char[128]);
	public Doub fontsize = new Doub();

	public PSpage(RefObject<String> filnam)
	{
		file = new byte[128];
		file = filnam.argvalue;
		PLT = fopen(file,"wb");
		if (PLT == null)
			throw("failure opening output file for plot");
		fprintf(PLT,"%%!\n/mt{moveto}def /lt{lineto}def /np{newpath}def\n");
		fprintf(PLT,"/st{stroke}def /cp{closepath}def /fi{fill}def\n");
		fprintf(PLT,"/zp {gsave /ZapfDingbats findfont exch ");
		fprintf(PLT,"scalefont setfont moveto show grestore} def\n");
		setfont("Times-Roman", 12.);
		setlinewidth(0.5);
	}
	public PSpage(RefObject<FILE> PPLT, RefObject<String> ffile)
	{
		PLT = PPLT.argvalue;
		file = ffile.argvalue;
	}
	public void Dispose()
	{
		if (PLT != null)
			close();
	}

	public final void setfont(RefObject<String> fontnam, Doub size)
	{
		fontname = fontnam.argvalue;
		fontsize = size;
		fprintf(PLT,"/%s findfont %g scalefont setfont\n",fontnam.argvalue,size);
	}

	public final void setcolor(Int r, Int g, Int b)
	{
		fprintf(PLT,"%g %g %g setrgbcolor\n",r/255.,g/255.,b/255.);
	}

	public final void setdash(RefObject<String> patt)
	{
		setdash(patt, 0);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void setdash(byte *patt, Int phase=0)
	public final void setdash(RefObject<String> patt, Int phase)
	{
		fprintf(PLT,"[%s] %d setdash\n",patt.argvalue,phase);
	}

	public final void setlinewidth(Doub w)
	{
		fprintf(PLT,"%g setlinewidth\n",w);
	}

	public final void setgray(Doub w)
	{
		fprintf(PLT,"%g setgray\n",w);
	}

	public final void gsave()
	{
		fprintf(PLT,"gsave\n");
	}

	public final void grestore()
	{
		fprintf(PLT,"grestore\n");
	}

	public final void rawps(RefObject<String> text)
	{
		fprintf(PLT,"%s\n",text.argvalue);
	}

	public final void addtext(RefObject<String> text)
	{
		fprintf(PLT,"(%s) show ",text.argvalue);
	}

	public final void puttext(RefObject<String> text, Doub x, Doub y)
	{
		puttext(text, x, y, 0.0);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void puttext(byte *text, Doub x, Doub y, Doub rot=0.0)
	public final void puttext(RefObject<String> text, Doub x, Doub y, Doub rot)
	{
		fprintf(PLT,"gsave %g %g translate %g rotate 0 0 mt ",x,y,rot);
		addtext(text);
		fprintf(PLT,"grestore \n");
	}

	public final void putctext(RefObject<String> text, Doub x, Doub y)
	{
		putctext(text, x, y, 0.0);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void putctext(byte *text, Doub x, Doub y, Doub rot=0.0)
	public final void putctext(RefObject<String> text, Doub x, Doub y, Doub rot)
	{
		fprintf(PLT,"gsave %g %g translate %g rotate 0 0 mt (%s) ",x,y,rot,text.argvalue);
		fprintf(PLT,"dup stringwidth pop 2 div neg 0 rmoveto show grestore\n");
	}

	public final void putrtext(RefObject<String> text, Doub x, Doub y)
	{
		putrtext(text, x, y, 0.0);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void putrtext(byte *text, Doub x, Doub y, Doub rot=0.0)
	public final void putrtext(RefObject<String> text, Doub x, Doub y, Doub rot)
	{
		fprintf(PLT,"gsave %g %g translate %g rotate 0 0 mt (%s) ",x,y,rot,text.argvalue);
		fprintf(PLT,"dup stringwidth pop neg 0 rmoveto show grestore\n");
	}

	public final void close()
	{
		fprintf(PLT,"showpage\n");
		fclose(PLT);
		PLT = null;
	}

	public final void display()
	{
		String cmd = new String(new char[128]);
		if (PLT != null)
			close();
		cmd = "\"C:\\Program Files\\Ghostgum\\gsview\\gsview32.exe\" ";
		cmd += file;
		system(cmd);
	}

	public final void pointsymbol(Doub x, Doub y, Int num, double size)
	{
		fprintf(PLT,"(\\%03o) %g %g %g zp\n",num,x-0.394 *size,y-0.343 *size,size);
	}

	public final void lineseg(Doub xs, Doub ys, Doub xf, Doub yf)
	{
		fprintf(PLT,"np %g %g mt %g %g lt st\n",xs,ys,xf,yf);
	}

	public final void polyline(RefObject<VecDoub> x, RefObject<VecDoub> y, Int close, Int fill)
	{
		polyline(x, y, close, fill, 0);
	}
	public final void polyline(RefObject<VecDoub> x, RefObject<VecDoub> y, Int close)
	{
		polyline(x, y, close, 0, 0);
	}
	public final void polyline(RefObject<VecDoub> x, RefObject<VecDoub> y)
	{
		polyline(x, y, 0, 0, 0);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void polyline(VecDoub &x, VecDoub &y, Int close=0, Int fill=0, Int clip=0)
	public final void polyline(RefObject<VecDoub> x, RefObject<VecDoub> y, Int close, Int fill, Int clip)
	{
		Int i = new Int();
		Int n = MIN(x.argvalue.size(),y.argvalue.size());
		fprintf(PLT,"np %g %g mt\n",x.argvalue[0],y.argvalue[0]);
		for (i = 1;i<n;i++)
			fprintf(PLT,"%g %g lt\n",x.argvalue[i],y.argvalue[i]);
		if (close != null || fill || clip != null)
			fprintf(PLT,"cp ");
		if (fill != null)
			fprintf(PLT,"fi\n");
		else if (clip != null)
			fprintf(PLT,"clip\n");
		else
			fprintf(PLT,"st\n");
	}
}