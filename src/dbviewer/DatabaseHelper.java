package dbviewer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class DatabaseHelper
{
	private static final String URL_PREFIX = "jdbc:oracle:thin:@";
	private static final String URL_SUFFIX = ":orcl";
	public static final String DEFAULT_PORT = "1521";
	
	private static final int TABLE_COLUMN_PREF_WIDTH = 150;
	
	public static Connection createConnection(DatabaseProfile profile) throws SQLException
	{
		if(profile == null)
		{
			throw new IllegalArgumentException("profile cannot be null");
		}
		
		StringBuilder sb = new StringBuilder(64);
		sb.append(URL_PREFIX);
		sb.append(profile.address);
		sb.append(":");
		sb.append(profile.port);
		sb.append(URL_SUFFIX);
		
		return DriverManager.getConnection(sb.toString(), profile.username, profile.password);
	}
	
	public static void fillFXTable(TableView<StringArrayTableRow> table, ResultSet rs) throws SQLException
	{
		table.getItems().clear();
		table.getColumns().clear();
		
		final ResultSetMetaData metadata = rs.getMetaData();
		final int columns = metadata.getColumnCount();
		
		int i;
		StringProperty[] currentCols;
		
		while(rs.next())
		{
			currentCols = new StringProperty[columns];
			for(i = 0; i < columns; ++i)
			{
				currentCols[i] = new SimpleStringProperty(rs.getString(i + 1));
			}
			table.getItems().add(new StringArrayTableRow(currentCols));
		}
		
		for(i = 0; i < columns; ++i)
		{
			final TableColumn<StringArrayTableRow, String> column = new TableColumn<>();
			table.getColumns().add(column);
			column.setSortable(false);
			column.setEditable(false);
			column.setCellValueFactory(new StringArrayValueFactory(i));
			column.setPrefWidth(TABLE_COLUMN_PREF_WIDTH);
			column.setGraphic(new Label(metadata.getColumnLabel(i + 1)));
		}
	}
	
	private static class StringArrayValueFactory implements Callback<TableColumn.CellDataFeatures<StringArrayTableRow, String>, ObservableValue<String>>
	{
		private final int columnIndex;
		
		private StringArrayValueFactory(int columnIndex)
		{
			this.columnIndex = columnIndex;
		}
		
		@Override
		public ObservableValue<String> call(CellDataFeatures<StringArrayTableRow, String> param)
		{
			return param.getValue().getValue(this.columnIndex);
		}
	}
	
	public static class StringArrayTableRow
	{
		private StringProperty[] columns;
		
		public StringArrayTableRow(StringProperty[] values) throws SQLException
		{
			this.columns = values;
		}
		
		public final StringProperty getValue(int columnIndex)
		{
			return this.columns[columnIndex];
		}
	}
}
