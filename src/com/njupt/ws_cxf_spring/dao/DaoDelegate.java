package com.njupt.ws_cxf_spring.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@javax.jws.WebService(targetNamespace = "http://dao.ws_cxf_spring.njupt.com/", serviceName = "DaoService", portName = "DaoPort")
public class DaoDelegate {

	com.njupt.ws_cxf_spring.dao.Dao dao = new com.njupt.ws_cxf_spring.dao.Dao();

	public String sayHello() {
		return dao.sayHello();
	}

}