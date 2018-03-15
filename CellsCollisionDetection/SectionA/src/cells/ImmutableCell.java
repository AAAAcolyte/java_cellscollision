package cells;

public class ImmutableCell<T> implements Cell<T> {
  private T value;

  public ImmutableCell(T value){
    if(value == null){
      throw  new IllegalArgumentException();
    }
    else{
      this.value = value;
    }
  }

  @Override
  public void set(T value){
    throw new UnsupportedOperationException();
  }

  @Override
  public T get() {
    return this.value;
  }

  @Override
  public boolean isSet() {
    return this.value != null;
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof ImmutableCell) {
      return value.equals(((ImmutableCell) obj).get());
    }
    else
      return false;
  }
}
