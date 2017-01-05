package semesterProject.mandelbaum2016;
import java.util.ArrayList;
import java.util.LinkedList;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.*;
//import semesterProject.mandelbaum2016.DataRow.Node;
//import semesterProject.mandelbaum2016.ItemWrapper;
//import semesterProject.mandelbaum2016.StringWrapper;
//import semesterProject.mandelbaum2016.DataRow.Node;//can this be public?
//import semesterProject.mandelbaum2016.DataRow.Node;
//import semesterProject.mandelbaum2016.DataRow.Node;
public class Table2 {

	protected String tableName;
	protected ColumnDescription[] columns; 
	protected ArrayList<String> columnNames;
	protected IndexBTree2<String, ArrayList<DataRow<String>>> primaryKey;
	protected ArrayList<IndexBTree2<String, ArrayList<DataRow<String>>>> indexTrees;
	protected LinkedList<DataRow<String>> dataSet;
	protected ArrayList<TableValue> values;
	public ArrayList<DataRow<String>> alreadyDeleted;
	private static int count;

	public Table2(String tableName, ColumnDescription[] columns, String primary)
	{
		indexTrees = new ArrayList<IndexBTree2<String, ArrayList<DataRow<String>>>>();
		this.tableName = tableName;
		this.columns = columns;
		columnNames = new ArrayList<String>();
		for(int i = 0; i <= columns.length - 1; i++)
		{
			columnNames.add(columns[i].getColumnName());
		}
		primaryKey = new IndexBTree2<String, ArrayList<DataRow<String>>>();
		primaryKey.setName(primary);
		indexTrees.add(primaryKey);
		setCount(0);
		dataSet = new LinkedList<DataRow<String>>();
		//printTable();
		values = new ArrayList<TableValue>();
		alreadyDeleted= new ArrayList<DataRow<String>>();
	}

	/**
	 * @return
	 */
	public ColumnDescription[] getColumns() {
		return columns;
	}

	public void setColumns(ColumnDescription[] columns) {
		this.columns = columns;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		Table2.count = count;
	}

	public static void printTable(Table2 table)
	{
		System.out.println ("\n-----------------------------");//http://stackoverflow.com/questions/15052235/formatting-java-output-like-a-table
		System.out.println ("Table Name: " + table.getTableName());
		System.out.println("Primary Key: " + table.primaryKey.getName());
		System.out.println ("-----------------------------");
		for(String column: table.columnNames)
		{
			System.out.format("%15s", column.toUpperCase());
		}
		if(table.dataSet != null)//table.dataSet.size() >= 1 && table.dataSet != null)
		{
			System.out.println ("\n---------------------------------------------------------------------------------------------------------------");
			for(DataRow<String> row: table.dataSet)
			{
				DataRow<String>.Node node = row.header.next;
				System.out.format("\n");
				while(node.next != null)
				{
					System.out.format("%15s", node.element);
					node = node.next;
				}
			}
		}
		/*else if (table.dataSet.size() == 1)
		{
			for(DataRow<String> row: table.dataSet)
			{
				DataRow<String>.Node node = row.header;
				System.out.format("\n" + "%15s", node.element);
				break;
			}
		}*/
	}

	private String getTableName() {
		return tableName;
	}

	public void addIndex(ColumnDescription columnName, String indexName) {
		IndexBTree2<String, ArrayList<DataRow<String>>> newIndex = new IndexBTree2<String, ArrayList<DataRow<String>>>();
		//newIndex.put(columnName, "root");//fix this
		indexTrees.add(newIndex);
	}

	public ArrayList<IndexBTree2<String, ArrayList<DataRow<String>>>> getIndexTrees() {
		return indexTrees;
	}

	public void setIndexTrees(ArrayList<IndexBTree2<String, ArrayList<DataRow<String>>>> indexTrees) {
		this.indexTrees = indexTrees;
	}

	public static TableValue getValue(String val, Table2 table) {
		for(TableValue value: table.values)
		{
			if(value.getValue().equals(val))
			{
				return value;
			}
		}
		return null;
	}

	void setTableName(String string) {
		this.tableName = string;
	}

	IndexBTree2<String, ArrayList<DataRow<String>>> hasTree(String columnName, ArrayList<IndexBTree2<String, ArrayList<DataRow<String>>>> indexTrees2) {
		for(IndexBTree2<String, ArrayList<DataRow<String>>> tree : indexTrees2)
		{
			if(tree.name.equals(columnName))
			{
				return tree;
			}
		}
		return null;
	}

	public ColumnDescription findUnique() {
		for(ColumnDescription column : columns)
		{
			if(column.isUnique())
			{
				return column;
			}
		}
		return null;
	}
	
	/*LinkedList<DataRow<String>> removeRows(DataRow<String> otherRow, LinkedList<DataRow<String>> helperRows)
	{
		for(DataRow<String> row : this.dataSet)
		{
			if(!row.equalsRow(otherRow, this))
			{
				System.out.println("Added: " +  row.header.next.element);
				helperRows.add(row);
			}
			else
			{
				helperRows.remove(row);
			}
		}
		return helperRows;
		//this.dataSet = helperRows;
	}*/
	

}



