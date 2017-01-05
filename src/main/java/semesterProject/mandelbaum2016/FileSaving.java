package semesterProject.mandelbaum2016;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringJoiner;

public class FileSaving {
	static void saveData(FileWriter dbDataFileWriter, ArrayList<String> creations, ArrayList<Table2> tables) throws IOException {
		dbDataFileWriter.write("Database create queries:\n");
		for(String createQuery : creations)
		{
			dbDataFileWriter.write(createQuery + "\n");
		}
		Date date = new Date();
		//include data rows!!!! <------
		for(Table2 table : tables)
		{
			dbDataFileWriter.write("\nTable " + table.tableName + " rows:\n");
			if(table.dataSet == null)
			{
				dbDataFileWriter.write("No data in this table.");
			}
			else
			{
				for(DataRow<String> row : table.dataSet)
				{
					StringJoiner joiner = new StringJoiner(",");//http://stackoverflow.com/questions/63150/whats-the-best-way-to-build-a-string-of-delimited-items-in-java/63258#63258
					DataRow<String>.Node node = row.header.next;
					while(node.next != null)
					{
						joiner.add(node.element);
						node = node.next;
					}
					String joinedString = joiner.toString();
					dbDataFileWriter.write(joinedString + "\n");
				}
			}
		}
		dbDataFileWriter.write("\nMost Recent Backup: " + (new Timestamp(date.getTime())));//include timestamp
	}
	
	static String tail2(File file, int lines) {//http://stackoverflow.com/questions/686231/quickly-read-the-last-line-of-a-text-file
		java.io.RandomAccessFile fileHandler = null;
		try {
			fileHandler = new java.io.RandomAccessFile( file, "r" );
			long fileLength = fileHandler.length() - 1;
			StringBuilder sb = new StringBuilder();
			int line = 0;
			for(long filePointer = fileLength; filePointer != -1; filePointer--){
				fileHandler.seek( filePointer );
				int readByte = fileHandler.readByte();
				if( readByte == 0xA ) {
					if (filePointer < fileLength) {
						line = line + 1;
					}
				} else if( readByte == 0xD ) {
					if (filePointer < fileLength-1) {
						line = line + 1;
					}
				}
				if (line >= lines) {
					break;
				}
				sb.append( ( char ) readByte );
			}
			String lastLine = sb.reverse().toString();
			return lastLine;
		} catch( java.io.FileNotFoundException e ) {
			e.printStackTrace();
			return null;
		} catch( java.io.IOException e ) {
			e.printStackTrace();
			return null;
		}
		finally {
			if (fileHandler != null )
				try {
					fileHandler.close();
				} catch (IOException e) {
				}
		}
	}

	static int countLines(String filename) throws IOException {//http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}
}
