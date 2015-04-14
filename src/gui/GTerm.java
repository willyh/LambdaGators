package gui;

import calc.Term;

public interface GTerm extends Term {
int getX();
int getY();
int getWidth();
int getHeight();
GTerm copySize(GTerm t);
void setWidth(int i);
void setHeight(int h);
}
