package org.trendak.finderapp.textintelligence;

public enum PostCategories {
Irrelevant(0),
Found(1),
Found_Suspected(2),
Lost(3), 
Found_Dead(4);
private final int val;
private PostCategories (int v) 
{ val = v; }
public int getVal() { return val; }
}
