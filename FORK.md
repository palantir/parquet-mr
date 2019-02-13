# Differences to mainline
This repo exists mostly to make releases of parquet-mr more often. The only difference to upstream is as follows:

1. Solution for [PARQUET-686](https://issues.apache.org/jira/browse/PARQUET-686).
2. Temporarily revert [PARQUET-1414](https://issues.apache.org/jira/browse/PARQUET-1414) because it causes Spark to write unreadable empty Parquet pages.

The change that we had made that upstream only made in statistics v2 is to change binary comparison to be unsigned and declare all statistics priori to that change as corrupted. This lets us more quickly take advantage of binary statistics and removes burden on user to know whether they should account for signed binary comparison in their values.

As a result statistics on binary columns produced by this fork and mainline will be different. This fork will follow lexicographical ordering while upstream won't. It's important when reading those with upstream version to ignore the statistics as it can lead to wrong results.

## Code changes
Most of the diff is contained in `parquet-hadoop/src/main/java/org/apache/parquet/format/converter/ParquetMetadataConverter.java` which should be carefully reconciled with upstream on merges. We ignore `parquet.strings.signed-min-max.enabled` option and always return statistics if `parquet-column/src/main/java/org/apache/parquet/CorruptStatistics.java` reports them as not corrupted.
