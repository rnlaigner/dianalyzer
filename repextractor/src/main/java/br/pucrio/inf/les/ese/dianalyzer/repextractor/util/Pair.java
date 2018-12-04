package br.pucrio.inf.les.ese.dianalyzer.repextractor.util;

public class Pair<L,R> 
{

  private final L left;
  private final R right;

  public Pair(L left, R right) {
    this.left = left;
    this.right = right;
  }

  public L getLeft() { return left; }
  public R getRight() { return right; }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Pair)) return false;
    //TODO testar
    Pair<?, ?> pair = (Pair<?, ?>) o;
    return this.left.equals(pair.getLeft()) &&
           this.right.equals(pair.getRight());
  }

}
