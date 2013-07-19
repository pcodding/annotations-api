package com.hortonworks.mayo.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hortonworks.mayo.model.Annotation;

@Service
public class AnnotationService {
	private Logger logger = Logger.getLogger(this.getClass());
	private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
	private String jdbcUrl = "jdbc:hive://39.64.24.2:10000/default";
	Connection con = null;

	public AnnotationService() {

	}

	public Annotation getAnnotationByTitle(String title) throws Exception {
		Annotation an = queryHiveByTitle(title);
		return an;
	}

	private Annotation queryHiveByTitle(String title) throws Exception {
		Annotation an = new Annotation();
		an.setTitle(title);
		logger.info("Searching Hive for Document with annotations for title: "
				+ title);

		try {
			if (con == null) {
				Class.forName(driverName);
				con = DriverManager.getConnection(jdbcUrl, "", "");
			}
			Statement stmt = con.createStatement();
			String query = "select title, text, annotations from wikipedia_pages_annotated where title = '"
					+ title + "'";
			logger.info("Issuing Hive Query: " + query);
			long started = System.currentTimeMillis();
			ResultSet res = stmt.executeQuery(query);
			logger.info("Query returned in "
					+ Long.toString(System.currentTimeMillis() - started));

			logger.info("Got a result, trying to retrieve it");
			while (res.next()) {
				an.setText(res.getString(2));
				logger.info("Set text to size: " + an.getText().length());
				an.setAnnotations(res.getString(3));
				logger.info("Set annotations to size: "
						+ an.getAnnotations().length());
				logger.info("Retrieved result!");
			}
			logger.info("Returned result for title: " + an.getTitle());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return an;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
}
