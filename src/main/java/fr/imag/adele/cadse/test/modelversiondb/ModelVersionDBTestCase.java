/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Copyright (C) 2006-2010 Adele Team/LIG/Grenoble University, France
 */
package fr.imag.adele.cadse.test.modelversiondb;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hsqldb.Server;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import fr.imag.adele.cadse.core.impl.CadseCore;
import fr.imag.adele.teamwork.db.DBConnectionException;
import fr.imag.adele.teamwork.db.ModelVersionDBException;
import fr.imag.adele.teamwork.db.ModelVersionDBService;
import fr.imag.adele.teamwork.db.Revision;
import fr.imag.adele.teamwork.db.TransactionException;

@RunWith(Parameterized.class)
public class ModelVersionDBTestCase extends TestUtil implements ModelVersionDBTestCST {
	
	@Parameters
	public static Collection testData() {
		return Arrays.asList(new Object[][] { 
//				{ "mysql", "localhost", 3306, "TestModelDB", "root", "ertdfg" }, 
//				{ "oracle:thin", "localhost", 1521, "XE", "thomas", "thomasPwd" },
				{ "hsqldb:hsql", "localhost", 9002, "TestModelDB", "sa", "" } 
				});
	}

	/**
	 * Service to test
	 */
	private static ModelVersionDBService m_db;
	
	/*
	 * Connection parameters to Test database
	 */
	public String _dbSpecificURLPart = "mysql";
	public int _port = 3306;
	public String _host = "localhost";
	public String _dbName = "TestModelDB";
	public String _url = "jdbc:" + _dbSpecificURLPart + "://" + _host + ":" + _port + "/" + _dbName;
	public String _pwd = "ertdfg";
	public String _login = "root";
	
	public ModelVersionDBTestCase(String dbSpecificURLPart, String host, 
			int port, String dbName, String login, String pwd) {
		_dbSpecificURLPart = dbSpecificURLPart;
		_host = host;
		_port = port;
		_dbName = dbName;
		_login = login;
		_pwd = pwd;
		if (dbSpecificURLPart.startsWith("oracle"))
			_url = "jdbc:" + _dbSpecificURLPart + ":@//" + _host + ":" + _port + "/" + _dbName;
		else
			_url = "jdbc:" + _dbSpecificURLPart + "://" + _host + ":" + _port + "/" + _dbName;
	}
	
	@BeforeClass
	public static void globalSetUp() throws TransactionException, DBConnectionException {
		if (m_db == null) {
			m_db = CadseCore.getCadseDomain().getModelVersionDBService();
		}
	}
	
	@Before
	public void setUp() throws TransactionException, DBConnectionException {
		if (m_db != null) {
			m_db.setConnectionURL(_url, _login, _pwd);
			try {
				m_db.clear();
			} catch (ModelVersionDBException e) {
				e.printStackTrace();
			}
		}
	}
	
	@After
	public void tearDown() {
		clearDB();
	}

	private static void clearDB() {
		if (m_db != null) {
			try {
				if (m_db.isConnected())
					m_db.clear();
			} catch (ModelVersionDBException e) {
				// ignore it
				e.printStackTrace();
			}
		}
	}
	
	@AfterClass
	public static void globalTearDown() {
		clearDB();
		m_db = null;
	}
	
	@Test
	public void testConnectionMethods() throws ModelVersionDBException {
		/*
		 * create 2 HSQLDB servers
		 */
		String server1Name = "TestDB1";
		int server1Port = 9007;
		Server server1 = createHSQLServer(server1Name, server1Port);
		String server2Name = "TestDB2";
		int server2Port = 9008;
		Server server2 = createHSQLServer(server2Name, server2Port);
		
		try {
			/*
			 * Check connection to database used for these tests
			 */
			assertTrue(m_db.isConnected());
			assertEquals(_url, m_db.getConnectionURL());
			assertEquals(_login, m_db.getLogin());
			
			/*
			 * try to connect to first database without login  
			 */
			String server1URL = getHSQLServerURL(server1Name, server1Port);
			m_db.setConnectionURL(server1URL);
			assertTrue(m_db.isConnected());
			assertEquals(server1URL, m_db.getConnectionURL());
			assertNull(m_db.getLogin());
			m_db.clear();
			m_db.createObject(obj1Id, objType1Id, null, false);
			
			// try to connect to second database without login  
			String server2URL = getHSQLServerURL(server2Name, server2Port);
			m_db.setConnectionURL(server2URL);
			assertTrue(m_db.isConnected());
			assertEquals(server2URL, m_db.getConnectionURL());
			assertNull(m_db.getLogin());
			m_db.clear();
			assertFalse(m_db.objExists(obj1Id));
			m_db.createObject(obj2Id, objType2Id, null, false);
			
			/*
			 * connection with login
			 */
			try {
				m_db.setConnectionURL(server1URL, null, "");
				fail();
			} catch (IllegalArgumentException e) {
				assertFalse(m_db.isConnected());
			}
			
			try {
				m_db.setConnectionURL(server1URL, "sa", null);
				fail();
			} catch (IllegalArgumentException e) {
				assertFalse(m_db.isConnected());
			}
			
			m_db.setConnectionURL(server1URL, "sa", "");
			assertTrue(m_db.isConnected());
			assertEquals(server1URL, m_db.getConnectionURL());
			assertEquals("sa", m_db.getLogin());
			assertTrue(m_db.objExists(obj1Id));
			assertFalse(m_db.objExists(obj2Id));
			
			// connection to second db with login
			m_db.setConnectionURL(server2URL, "sa", "");
			assertTrue(m_db.isConnected());
			assertEquals(server2URL, m_db.getConnectionURL());
			assertEquals("sa", m_db.getLogin());
			assertFalse(m_db.objExists(obj1Id));
			assertTrue(m_db.objExists(obj2Id));
			
			/*
			 * connection with dbType without login
			 */
			m_db.setConnectionURL(ModelVersionDBService.HSQL_IN_MEMORY_TYPE, "localhost", server1Port, server1Name);
			assertTrue(m_db.isConnected());
			assertEquals(server1URL, m_db.getConnectionURL());
			assertNull(m_db.getLogin());
			assertTrue(m_db.objExists(obj1Id));
			assertFalse(m_db.objExists(obj2Id));
			
			m_db.setConnectionURL(ModelVersionDBService.HSQL_IN_MEMORY_TYPE, "localhost", server2Port, server2Name);
			assertTrue(m_db.isConnected());
			assertEquals(server2URL, m_db.getConnectionURL());
			assertNull(m_db.getLogin());
			assertFalse(m_db.objExists(obj1Id));
			assertTrue(m_db.objExists(obj2Id));
			
			/*
			 * connection with dbType with login
			 */
			try {
				m_db.setConnectionURL(ModelVersionDBService.HSQL_IN_MEMORY_TYPE, "localhost", server1Port, server1Name, null, "");
				fail();
			} catch (IllegalArgumentException e) {
				assertFalse(m_db.isConnected());
			}
			
			try {
				m_db.setConnectionURL(ModelVersionDBService.HSQL_IN_MEMORY_TYPE, "localhost", server1Port, server1Name, "sa", null);
				fail();
			} catch (IllegalArgumentException e) {
				assertFalse(m_db.isConnected());
			}
			
			m_db.setConnectionURL(ModelVersionDBService.HSQL_IN_MEMORY_TYPE, "localhost", server1Port, server1Name, "sa", "");
			assertTrue(m_db.isConnected());
			assertEquals(server1URL, m_db.getConnectionURL());
			assertEquals("sa", m_db.getLogin());
			assertTrue(m_db.objExists(obj1Id));
			assertFalse(m_db.objExists(obj2Id));
			
			m_db.setConnectionURL(ModelVersionDBService.HSQL_IN_MEMORY_TYPE, "localhost", server2Port, server2Name, "sa", "");
			assertTrue(m_db.isConnected());
			assertEquals(server2URL, m_db.getConnectionURL());
			assertEquals("sa", m_db.getLogin());
			assertFalse(m_db.objExists(obj1Id));
			assertTrue(m_db.objExists(obj2Id));
			
		} catch (Exception e) {
			e.printStackTrace();
			
			// stop servers
			server1.stop();
			server2.stop();
			
			fail("Unexpected Error: " + e.getMessage());
		}
		
		// cleaning code
		server1.stop();
		server2.stop();
	}
	
	private void assertSetEquals(List<UUID> linkList, 
			List<UUID> expectList) { 
		assertEquals(new HashSet<UUID>(expectList), new HashSet<UUID>(linkList)); 
	}
	
	private void assertContainsRevs(int[] revs, int... expectRevs) {
		assertEquals(revs.length, expectRevs.length);
		for (int expectRev : expectRevs) 
			assertContains("Miss revision " + expectRev, revs, expectRev);
	}
	
	private void checkLinkRevs(UUID linkType, UUID srcId, int srcRev,
			CheckRevision... expectDestRevs) throws ModelVersionDBException {
		assertEquals(expectDestRevs.length, m_db.getLinkNumber(linkType, srcId,
				srcRev));

		List<Revision> destRevs = m_db.getLinkDestRev(linkType, srcId, srcRev);
		assertNotNull(destRevs);
		assertEquals(expectDestRevs.length, destRevs.size());

		for (CheckRevision expectDestRev : expectDestRevs) {
			UUID destId = expectDestRev.getId();
			int destRev = expectDestRev.getRev();
			checkFindRev(destRevs, destId, destRev);

			Revision linkRev = m_db.getLinkRev(linkType, srcId, srcRev, destId,
					destRev);
			assertEquals(expectDestRev.getStateMap(), m_db.getLinkState(linkRev
					.getId(), linkRev.getRev()));
		}
	}
	
	private void checkFindRev(List<Revision> revs, UUID objId,
			int objRev) {
		if (revs == null)
			fail("Revision list cannot be null.");
		
		for (Revision rev : revs) {
			if (objId.equals(rev.getId()) &&
					(objRev == rev.getRev()))
				return;
		}
		
		fail("Revision " + objRev + " of object " + objId + " canot be found.");
	}

	private void assertRevListMatch(List<Revision> expectRevs,
			List<Revision> revs) {
		if (expectRevs == null) {
			assertEquals(expectRevs, revs);
		}
		if (revs == null)
			fail();
		
		assertEquals("Revision lists must have same size.", expectRevs.size(), revs.size());
		
		for (UUID objectId : getObjectIds(expectRevs)) {
			int expectIdx = findFirstRev(objectId, expectRevs);
			int idx = findFirstRev(objectId, revs);
			
			if (idx == -1)
				fail("There is no revision of " + objectId + ".");
			
			for (int j = expectIdx; j < expectRevs.size(); j++) {
				Revision expectRev = expectRevs.get(j);
				if (!objectId.equals(expectRev.getId()))
					break;
				
				int expectRevNb = expectRev.getRev();
				
				int revIdx = idx + (j - expectIdx);
				if (revIdx >= revs.size())
					fail("Revision " + expectRevNb + " of object " + objectId + 
							" is missing or revision list is not sorted by rev number per each object.");
				Revision rev = revs.get(revIdx);
				assertEquals("Revision " + expectRevNb + " of object " + objectId + 
						" is missing or revision list is not sorted by rev number per each object.", 
						expectRevNb, rev.getRev());
				
				UUID expectTypeId = expectRev.getTypeId();
				UUID typeId = rev.getTypeId();
				assertEquals("Revision " + expectRevNb + " of object " + objectId + 
						" has object type " + typeId + " instead of " + expectTypeId + ".", expectTypeId, typeId);
			}
		}
	}

	private int findFirstRev(UUID objectId, List<Revision> revs) {
		for (int i = 0; i < revs.size(); i++) {
			if (revs.get(i).getId().equals(objectId))
				return i;
		}
		
		return -1;
	}

	private Set<UUID> getObjectIds(List<Revision> expectRevs) {
		Set<UUID> objectIds = new HashSet<UUID>();
		
		for (Revision rev : expectRevs) {
			objectIds.add(rev.getId());
		}
		
		return objectIds;
	}

	private String getHSQLServerURL(String dbName, int port) {
		return "jdbc:hsqldb:mem:" + dbName;
	}

	private Server createHSQLServer(String dbName, int port) {
		Server server = new Server();
		server.putPropertiesFromString("database.0=mem:" + dbName + ";sql.enforce_strict_size=true");
		server.setLogWriter(null);
		server.setErrWriter(null);
		server.setPort(port);
		server.start();
		
		return server;
	}
	
	private String getBaseType() {
		String[] urlParts = _url.split(":");
		if (urlParts.length < 2)
			return null;
		
		String jdbcType = urlParts[1];
		String baseType = null;
		if (jdbcType.equals("hsqldb")) {
			if ((urlParts.length > 2) && (urlParts[2].equalsIgnoreCase("mem")))
				baseType = ModelVersionDBService.HSQL_IN_MEMORY_TYPE;
			else
				baseType = ModelVersionDBService.HSQL_TYPE;
		}
		if (jdbcType.equals("mysql")) {
			baseType = ModelVersionDBService.MYSQL_TYPE;
		}
		if (jdbcType.startsWith("oracle")) {
			baseType = ModelVersionDBService.ORACLE_TYPE;
		}
		
		return baseType;
	}
}
