package padroesdeprojeto;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetPrinter {
    public void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        int[] columnWidths = new int[columnCount];
        String[] columnNames = new String[columnCount];

        // Determine the column widths and column names
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            columnNames[i - 1] = columnName;
            columnWidths[i - 1] = columnName.length();

            for (int j = 1; j <= resultSet.getFetchSize(); j++) {
                resultSet.absolute(j);
                String value = resultSet.getString(i);
                if (value != null && value.length() > columnWidths[i - 1]) {
                    columnWidths[i - 1] = value.length();
                }
            }
        }

        // Print the column names
        System.out.print("+");
        for (int i = 0; i < columnCount; i++) {
            printLine(columnWidths[i], '-');
            System.out.print("+");
        }
        System.out.println();
        System.out.print("|");
        for (int i = 0; i < columnCount; i++) {
            printCentered(columnNames[i], columnWidths[i]);
            System.out.print("|");
        }
        System.out.println();

        // Print the data
        while (resultSet.next()) {
            System.out.print("|");
            for (int i = 1; i <= columnCount; i++) {
                String value = resultSet.getString(i);
                printCentered(value, columnWidths[i - 1]);
                System.out.print("|");
            }
            System.out.println();
        }

        // Print the bottom border
        System.out.print("+");
        for (int i = 0; i < columnCount; i++) {
            printLine(columnWidths[i], '-');
            System.out.print("+");
        }
        System.out.println();
    }

    private static void printLine(int width, char c) {
        for (int i = 0; i < width + 2; i++) {
            System.out.print(c);
        }
    }

    private static void printCentered(String s, int width) {
        int padding = width - s.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        for (int i = 0; i < leftPadding; i++) {
            System.out.print(" ");
        }
        System.out.print(s);
        for (int i = 0; i < rightPadding; i++) {
            System.out.print(" ");
        }
    }
}
