package cells;

import java.util.Comparator;
import java.util.Stack;

public class BackedUpCellComparator<U> implements Comparator<BackedUpCell<U>> {
  Comparator<U> valueComparator;

  public BackedUpCellComparator(Comparator<U> comparator) {
    this.valueComparator = comparator;
  }

  @Override
  public int compare(BackedUpCell<U> a, BackedUpCell<U> b) {
    // Both not set
    if (!a.isSet() && !b.isSet()) {
      return 0;
    }
    // a is set while b is not
    if (a.isSet() && !b.isSet()) {
      return 1;
    }
    // a is not set while b is
    if (!a.isSet() && b.isSet()) {
      return -1;
    }
    // Both set
    int compareResult = 0;
    Stack<U> removedFromA = new Stack<>();
    Stack<U> removedFromB = new Stack<>();
    while (a.hasBackup() && b.hasBackup()) {
      removedFromA.push(a.get());
      removedFromB.push(b.get());
      compareResult = valueComparator.compare(a.get(), b.get());
      if (compareResult != 0) {
        return compareResult;
      }
      a.revertToPrevious();
      b.revertToPrevious();
    }
    if (!a.hasBackup() && b.hasBackup()) {
      compareResult = 0;
    }
    if (!a.hasBackup() && b.hasBackup()) {
      compareResult = -1;
    }
    if (a.hasBackup() && !b.hasBackup()) {
      compareResult = 1;
    }
    while (!removedFromA.isEmpty()) {
      a.set(removedFromA.pop());
    }
    while (!removedFromB.isEmpty()) {
      b.set(removedFromB.pop());
    }
    return compareResult;
  }
}

