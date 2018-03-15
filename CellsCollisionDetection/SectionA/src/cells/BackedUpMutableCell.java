package cells;

import java.util.ArrayDeque;
import java.util.Deque;

public class BackedUpMutableCell<T> extends MutableCell<T> implements
    BackedUpCell<T> {

  private Deque<T> previousQueue;

  private int backUpLimit;

  public BackedUpMutableCell() {
    backUpLimit = Integer.MAX_VALUE;
    value = null;
    previousQueue = new ArrayDeque<>();
  }

  public BackedUpMutableCell(int limit) {
    this();
    if (limit < 0) {
      throw new IllegalArgumentException();
    } else {
      backUpLimit = limit;
    }
  }

  @Override
  public void set(T value) {
    if (isSet()) {
      previousQueue.addLast(this.value);
      if (previousQueue.size() > backUpLimit) {
        previousQueue.removeFirst();
      }
    }
    this.value = value;
  }

  @Override
  public boolean hasBackup() {
    return !previousQueue.isEmpty();
  }

  @Override
  public void revertToPrevious() {
    if (!hasBackup()) {
      throw new UnsupportedOperationException();
    } else {
      this.value = previousQueue.getLast();
      previousQueue.removeLast();
    }
  }
}
