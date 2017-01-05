package semesterProject.mandelbaum2016;

import java.util.ArrayList;

public class TableValue {
	protected String value;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	protected ArrayList<DataRow<String>> rows;
	public TableValue(String val)
	{
		this.value = val;
		rows = new ArrayList<DataRow<String>>();
	}
}
