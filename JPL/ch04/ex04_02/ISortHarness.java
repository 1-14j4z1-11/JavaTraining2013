package ex04_02;

public interface ISortHarness<T>
{
	SortMetrics sort(T[] values);
	SortMetrics getMetrics();
}
