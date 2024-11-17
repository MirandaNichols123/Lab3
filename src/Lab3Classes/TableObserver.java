import java.util.List;

public interface TableObserver {
    void update(List<DataItem> dataItems);
}
