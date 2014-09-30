package edu.ktlab.news.vntransmon.db;

import java.io.File;
import java.util.Set;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class MapDBExample {

	public static void main(String[] args) {
		DB db = DBMaker.newFileDB(new File("mapdb/idSet"))
	               .closeOnJvmShutdown()
	               .deleteFilesAfterClose()
	               .encryptionEnable("password")
	               .make();
		Set<String> idSet = db.getHashSet("idSet");
		
		for (int i = 0; i < 10; i++)
			idSet.add(i + "");
		
		for (String id : idSet)
			System.out.println(id);
		
		db.close();
	}

}
