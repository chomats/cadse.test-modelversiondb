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

import java.util.UUID;

public interface ModelVersionDBTestCST {

	/*
	 * Constants used for tests
	 */
	public static final String VAL1 = "Value1";
	public static final String VAL2 = "Value2";
	public static final String VAL3 = "Value3";
	public static final String NEW_ATTR_VALUE = "New value";
	public static final String NEW_NEW_ATTR_VALUE = "New New value";
	public static final String FIRST_ATTRIBUTE_VALUE = "first_attribute_value";
	public static final String ATTR8 = "attr8";
	public static final String ATTR7 = "attr7";
	public static final String ATTR6 = "attr6";
	public static final String ATTR5 = "attr5";
	public static final String ATTR4 = "attr4";
	public static final String ATTR3 = "attr3";
	public static final String ATTR2 = "attr2";
	public static final String ATTR1 = "attr1";
	
	/**
	 * UUID of objects, links, object types and link types
	 */
	
	public final static UUID notExistObjId = UUID.randomUUID();
	public final static UUID notExistTypeId = UUID.randomUUID();
	public final static UUID obj1Id = UUID.randomUUID();
	public final static UUID obj2Id = UUID.randomUUID();
	public final static UUID obj3Id = UUID.randomUUID();
	public final static UUID obj4Id = UUID.randomUUID();
	
	public final static UUID objType1Id = UUID.randomUUID();
	public final static UUID objType2Id = UUID.randomUUID();
	
	public final static UUID notExistLinkId = UUID.randomUUID();
	public final static UUID link1SrcId = UUID.randomUUID();
	public final static UUID link1DestId = UUID.randomUUID();
	public final static UUID link2SrcId = UUID.randomUUID();
	public final static UUID link2DestId = UUID.randomUUID();
	public final static UUID link3SrcId = UUID.randomUUID();
	public final static UUID link3DestId = UUID.randomUUID();
	public final static UUID link4SrcId = UUID.randomUUID();
	public final static UUID link4DestId = UUID.randomUUID();
	
	public final static UUID notExistLinkTypeId = UUID.randomUUID();
	public final static UUID linkType1Id = UUID.randomUUID();
	public final static UUID linkType2Id = UUID.randomUUID();
	public final static UUID linkType3Id = UUID.randomUUID();
	
	
}
